package com.shultrea.rin.enchantments.weapon.ancient;

import com.shultrea.rin.Interfaces.IAncientEnchantment;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentSweepingEdge;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAncientSwordMastery extends EnchantmentBase implements IAncientEnchantment {
	
	public static String nbtFlag = "smeFlag";
	
	public EnchantmentAncientSwordMastery(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.ancientSwordMastery;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.ancientSwordMastery;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 80 + 80 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + par1 * 80;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.ancientSwordMastery;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.YELLOW.toString();
	}
	
	@Override
	public boolean canApplyTogether(Enchantment e) {
		return super.canApplyTogether(e) && !(e instanceof EnchantmentSweepingEdge);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	//public void HandleEnchant(LivingDamageEvent e)
	public void HandleEnchant(LivingHurtEvent e) {
		//Moved to LivingHurtEvent to avoid recursion
		if(!EnchantmentBase.isDamageSourceAllowed(e.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)e.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)e.getSource().getTrueSource()).getHeldItemMainhand();
		if(stack.isEmpty()) return;
		EntityLivingBase victim = e.getEntityLiving();
		if(victim == null) return;
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(enchantmentLevel <= 0) return;
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if(stack.getTagCompound().getBoolean(nbtFlag)) {
			stack.getTagCompound().setBoolean(nbtFlag, false);
			return;
		}
		stack.getTagCompound().setBoolean(nbtFlag, true);
		//if(EnchantmentsUtility.isLevelMax(stack, this) || EnchantmentsUtility.RANDOM.nextInt(3) < enchantmentLevel - 1)
		if(enchantmentLevel >= 3 || EnchantmentsUtility.RANDOM.nextInt(3) < enchantmentLevel - 1) {
			if(stack.getItem() instanceof ItemSword && attacker instanceof EntityPlayer) {
				ItemSword sword = (ItemSword)stack.getItem();
				sword.onLeftClickEntity(stack, (EntityPlayer)attacker, victim);
				sword.hitEntity(stack, victim, attacker);
				NBTTagList nbt = stack.getEnchantmentTagList();
				for(int x = 0; x < nbt.tagCount(); x++) {
					NBTTagCompound tag = nbt.getCompoundTagAt(x);
					int enchId = tag.getShort("id");
					int currEnchLevel = tag.getShort("lvl");
					Enchantment enchantment = Enchantment.getEnchantmentByID(enchId);
					if(enchantment == null) continue;
					//System.out.println(enchantment);
					enchantment.onEntityDamaged(attacker, victim, currEnchLevel);
					//System.out.println("is IT WORKING");
				}
			}
		}
		//Magical Blessing and Piercing Capabilites run on LivingHurt
		//They both call LivingDamage, which means they aren't recursive on their own
		//This however runs on LivingDamage, and calls LivingHurt, which can and will cause the recursion
		// H of P
		//  D0 of M
		//   H of P
		//     D1 of M
		//   D0 of M
		//   and so on
		//If it was Living hurt
		// H of P
		//  D
		// H0 of M
		//  H of P
		//   D
		//  H1 of M
		//  D
		//
		//if(EnchantmentsUtility.isLevelMax(stack, this))
		if(enchantmentLevel >= 3) {
			float damage = net.minecraftforge.common.ForgeHooks.onLivingHurt(e.getEntityLiving(), e.getSource(), 1);
			damage += net.minecraftforge.common.ForgeHooks.onLivingDamage(e.getEntityLiving(), e.getSource(), 1);
			damage = (damage - 2) * (0.4f + (enchantmentLevel * 0.1f));
			damage = damage + e.getAmount();
			e.setAmount(damage);
		}
	}
}
package com.shultrea.rin.enchantments.weapon.conditionaldamage;

import com.shultrea.rin.Interfaces.IConditionalDamage;
import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentMortalitas extends EnchantmentBase {
	
	public EnchantmentMortalitas(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.mortalitas;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.mortalitas;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 18 + 9 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.mortalitas;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment e) {
		return super.canApplyTogether(e) && !(e instanceof IDamageMultiplier) && !(e instanceof IConditionalDamage);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onDamage(LivingHurtEvent e) {
		if(e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof EntityPlayer && e.getSource().damageType.equals("player")) {
			if(!EnchantmentBase.isDamageSourceAllowed(e.getSource())) return;
			EntityPlayer attacker = (EntityPlayer)e.getSource().getTrueSource();
			ItemStack stack = attacker.getHeldItemMainhand();
			if(stack.isEmpty()) return;
			int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.mortalitas, stack);
			if(level <= 0) return;
			if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
			float damage = stack.getTagCompound().getFloat("additionalDamage");
			e.setAmount(e.getAmount() + damage);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void Death(LivingDeathEvent fEvent) {
		if(fEvent.getSource().getTrueSource() == null) return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityPlayer)) return;
		EntityLivingBase victim = fEvent.getEntityLiving();
		if(victim == null) return;
		EntityPlayer attacker = (EntityPlayer)fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(stack.isEmpty() || stack == null) return;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.mortalitas, stack);
		if(level <= 0) return;
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		float amount = stack.getTagCompound().getFloat("additionalDamage");
		amount = amount + (0.05f / this.getMaxLevel()) * level;
		amount = MathHelper.clamp(amount, 0, level);
		stack.getTagCompound().setFloat("additionalDamage", amount);
	}
}
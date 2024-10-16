package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.Interfaces.IEnchantmentRune;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRunePiercingCapabilities extends EnchantmentBase implements IEnchantmentRune {
	
	public EnchantmentRunePiercingCapabilities(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runePiercingCapabilities;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runePiercingCapabilities;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 10 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runePiercingCapabilities;
	}
	
	//TODO
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentRune);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		//System.out.println("Piercing Capabilities");
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource()))
			return;
		if(fEvent.getSource().isUnblockable()) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = attacker.getHeldItemMainhand();
		int pierceLevel = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(pierceLevel <= 0) return;
		float damage = fEvent.getAmount() * 0.25f * pierceLevel;
		fEvent.setAmount(fEvent.getAmount() - (fEvent.getAmount() * pierceLevel * 0.25f));
		if(attacker instanceof EntityPlayer)
			UtilityAccessor.damageTargetEvent(fEvent.getEntityLiving(), new EntityDamageSource("player", attacker).setDamageBypassesArmor(), damage);
		else
			UtilityAccessor.damageTargetEvent(fEvent.getEntityLiving(), new EntityDamageSource("mob", attacker).setDamageBypassesArmor(), damage);
	}
}
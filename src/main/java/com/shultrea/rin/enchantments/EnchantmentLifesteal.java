package com.shultrea.rin.enchantments;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_020;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentLifesteal extends EnchantmentBase {
	
	public EnchantmentLifesteal(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.lifesteal;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.lifesteal;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 6 + 8 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.lifesteal;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != Smc_010.BlessedEdge && fTest != Smc_010.CursedEdge;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW)
	public void HandlingFirst(LivingHurtEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack weapon = attacker.getHeldItemMainhand();
		if(weapon == null) return;
		int levellifesteal = EnchantmentHelper.getEnchantmentLevel(Smc_020.Lifesteal, weapon);
		if(EnchantmentHelper.getEnchantmentLevel(Smc_020.Lifesteal, weapon) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		attacker.heal(fEvent.getAmount() * (levellifesteal * 0.025f + 0.025f));
		UtilityAccessor.damageTarget(fEvent.getEntityLiving(), SoManyEnchantments.PhysicalDamage, fEvent.getAmount() * (0.05F + ((levellifesteal * 0.05F))));
	}
}
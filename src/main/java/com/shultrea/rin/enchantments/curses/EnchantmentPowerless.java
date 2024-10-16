package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.enchantments.bow.EnchantmentAdvancedPower;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * Enchantment arrow power handled in;
 * com.shultrea.rin.mixin.vanilla.ItemBowMixin
 * com.shultrea.rin.mixin.vanilla.EntityArrowMixin
 */
public class EnchantmentPowerless extends EnchantmentCurse {
	
	public EnchantmentPowerless(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.powerless;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.powerless;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 12 + (par1 - 1) * 12;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.powerless;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return ench != Enchantments.POWER && !(ench instanceof EnchantmentAdvancedPower) && super.canApplyTogether(ench);
	}
}
package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentTrueStrike extends EnchantmentBase {
	//TODO what is this supposed to do? It's referenced in Parry and Evasion
	
	public EnchantmentTrueStrike(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.trueStrike;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.trueStrike;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + (par1 - 1) * 15;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.trueStrike;
	}
}
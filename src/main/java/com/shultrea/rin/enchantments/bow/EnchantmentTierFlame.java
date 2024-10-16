package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentTierFlame extends EnchantmentBase {
	
	private static final String[] DAMAGE_NAMES = new String[]{"lfl", "afl", "sfl"};
	/**
	 * Holds the base factor of enchantability needed to be able to use the enchant.
	 */
	private static final int[] MIN_COST = new int[]{10, 35, 160};
	/**
	 * None
	 */
	private static final int[] LEVEL_COST = new int[]{0, 0, 0};
	/**
	 * None
	 */
	private static final int[] LEVEL_COST_SPAN = new int[]{30, 70, 140};
	/**
	 * Defines the type of damage of the enchantment, 0 = lesserfla, 1 = advfla, 2 = supfla
	 */
	public final int damageType;
	
	public EnchantmentTierFlame(String name, Rarity rarity, EnumEnchantmentType type, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
		this.damageType = damageTypeIn;
		this.type = type;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case 0:
				return ModConfig.enabled.lesserFlame;
			case 1:
				return ModConfig.enabled.advancedFlame;
			case 2:
				return ModConfig.enabled.supremeFlame;
			default:
				return false;
		}
	}
	//TODO
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case 0:
				return ModConfig.level.lesserFlame;
			case 1:
				return ModConfig.level.advancedFlame;
			case 2:
				return ModConfig.level.supremeFlame;
			default:
				return 1;
		}
	}
	//TODO
	
	/**
	 * Returns the minimal value of enchantability needed on the enchantment level passed.
	 */
	public int getMinEnchantability(int enchantmentLevel) {
		return MIN_COST[this.damageType] + (enchantmentLevel - 1) * LEVEL_COST[this.damageType];
	}
	
	/**
	 * Returns the maximum value of enchantability nedded on the enchantment level passed.
	 */
	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + LEVEL_COST_SPAN[this.damageType];
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case 0:
				return ModConfig.treasure.lesserFlame;
			case 1:
				return ModConfig.treasure.advancedFlame;
			case 2:
				return ModConfig.treasure.supremeFlame;
			default:
				return false;
		}
	}
	//TODO
	
	/**
	 * Determines if the enchantment passed can be applyied together with this enchantment.
	 */
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentTierFlame);
	}
	//TODO
	
	/**
	 * Return the name of key in translation table of this enchantment.
	 */
	@Override
	public String getName() {
		return "enchantment." + DAMAGE_NAMES[this.damageType];
	}
}
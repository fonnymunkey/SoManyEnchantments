package com.shultrea.rin.enchantments.hoe;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class EnchantmentJaggedRake extends EnchantmentBase {
	
	public EnchantmentJaggedRake(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.jaggedRake;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.jaggedRake;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 14 + 12 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.jaggedRake;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return super.canApplyAtEnchantingTable(stack) && stack.getItem() instanceof ItemHoe;
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		if(ModConfig.enabled.jaggedRake) return (1.0f + 0.55f * level);
		return 0;
	}
}
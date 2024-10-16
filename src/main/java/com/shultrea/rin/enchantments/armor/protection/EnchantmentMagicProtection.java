package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Interfaces.IEnchantmentProtection;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class EnchantmentMagicProtection extends EnchantmentBase implements IEnchantmentProtection {
	
	public EnchantmentMagicProtection(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS,
				EntityEquipmentSlot.FEET});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.magicProtection;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.magicProtection;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 12 + (par1 - 1) * 14;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 45;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.magicProtection;
	}
	
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : (source.isMagicDamage() ? level * 2 : 0);
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		if(fTest instanceof EnchantmentProtection) {
			EnchantmentProtection p = (EnchantmentProtection)fTest;
			if(p.protectionType != EnchantmentProtection.Type.FALL) return false;
			else return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentProtection);
		}
		return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentProtection);
	}
}


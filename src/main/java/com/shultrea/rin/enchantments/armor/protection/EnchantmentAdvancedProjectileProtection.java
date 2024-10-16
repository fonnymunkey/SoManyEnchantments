package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Interfaces.IEnchantmentProtection;
import com.shultrea.rin.Interfaces.IEnhancedEnchantment;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class EnchantmentAdvancedProjectileProtection extends EnchantmentBase implements IEnchantmentProtection, IEnhancedEnchantment {
	
	public EnchantmentAdvancedProjectileProtection(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS,
				EntityEquipmentSlot.FEET});
		this.setName("AdvancedProjectileProtection");
		this.setRegistryName("AdvancedProjectileProtection");
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedProjectileProtection;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedProjectileProtection;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 27 + (par1 - 1) * 11;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedProjectileProtection;
	}
	
	//TODO
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : source.isProjectile() ? level * 3 : 0;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		if(fTest instanceof EnchantmentProtection) {
			EnchantmentProtection p = (EnchantmentProtection)fTest;
			return p.protectionType == EnchantmentProtection.Type.FALL;
		}
		return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentProtection);
	}
}
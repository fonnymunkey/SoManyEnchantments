package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Interfaces.IEnchantmentProtection;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class EnchantmentPhysicalProtection extends EnchantmentBase implements IEnchantmentProtection {
	
	public EnchantmentPhysicalProtection(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS,
				EntityEquipmentSlot.FEET});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.physicalProtection;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.physicalProtection;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 14 + (par1 - 1) * 12;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 45;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.physicalProtection;
	}
	
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 :
			   (!(source.isMagicDamage() || source.isFireDamage() || source.isExplosion() || source.isProjectile() || source.damageType.equals("outOfWorld") || source.damageType.equals("drown") || source.damageType.equals("generic") || source.damageType.equals("wither") || source.damageType.equals("lightningBolt") || source.damageType.equals("inFire") || source.damageType.equals("onFire") || source.damageType.equals("hotFloor") || source.damageType.equals("Ethereal") || source.damageType.equals("Culled")) ?
				level * 3 : 0);
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		if(fTest instanceof EnchantmentProtection) {
			EnchantmentProtection p = (EnchantmentProtection)fTest;
			return p.protectionType == EnchantmentProtection.Type.FALL;
		}
		return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentProtection);
	}
}
package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentFlinging extends EnchantmentBase {
	
	public EnchantmentFlinging(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.flinging;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.flinging;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 5 + 10 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 20;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.flinging;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		int levelknockBack = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.flinging, stack);
		double Y = levelknockBack * 0.1875D + 0.075f;
		target.motionY += Y;
		target.velocityChanged = true;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.KNOCKBACK && fTest != EnchantmentRegistry.advancedKnockback && super.canApplyTogether(fTest);
	}
}
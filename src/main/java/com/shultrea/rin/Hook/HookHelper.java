package com.shultrea.rin.Hook;

import com.shultrea.rin.enchantments.armor.protection.EnchantmentAdvancedBlastProtection;
import com.shultrea.rin.enchantments.weapon.EnchantmentAdvancedLooting;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLuckOfTheSea;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class HookHelper {
	//Enchantment Helper hooks
	
	public static int modifyFishingLuckBonus(int original, ItemStack stack) {
		return Math.max(original, EnchantmentAdvancedLuckOfTheSea.getValue(stack));
	}
	
	public static int modifyFishingSpeedBonus(int original, ItemStack stack) {
		return Math.max(original, EnchantmentAdvancedLure.getValue(stack));
	}
	
	public static int modifyLootingModifier(int original, EntityLivingBase entity) {
		return Math.max(original, EnchantmentAdvancedLooting.getValue(original, entity));
	}
	//EnchantmentProtection hooks
	
	//This is actually knockback, but whatever
	public static double modifyBlastDamageReduction(double damage, EntityLivingBase entity) {
		return EnchantmentAdvancedBlastProtection.getKnockbackValue(damage, entity);
	}
}

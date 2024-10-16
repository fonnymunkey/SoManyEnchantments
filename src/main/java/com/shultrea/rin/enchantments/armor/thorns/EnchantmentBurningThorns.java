package com.shultrea.rin.enchantments.armor.thorns;

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
import net.minecraft.util.DamageSource;

import java.util.Random;

public class EnchantmentBurningThorns extends EnchantmentBase {
	
	public EnchantmentBurningThorns(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS,
				EntityEquipmentSlot.FEET,});
	}
	
	//TODO
	public static boolean shouldHit(int level, Random rnd) {
		return level > 0 && rnd.nextFloat() < 0.14F * (float)level;
	}
	
	//TODO
	public static int getDamage(int level, Random rnd) {
		return level > 10 ? level - 9 : 2 + rnd.nextInt(3);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.burningThorns;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.burningThorns;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 12 + (par1 - 1) * 9;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.burningThorns;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != EnchantmentRegistry.advancedThorns && fTest != Enchantments.THORNS && super.canApplyTogether(fTest);
	}
	
	//TODO
	@Override
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
		if(user == null) return;
		if(attacker == null || attacker.isDead) return;
		Random random = user.getRNG();
		ItemStack itemstack = EnchantmentHelper.getEnchantedItem(Enchantments.THORNS, user);
		if(shouldHit(level, random)) {
			if(attacker != null) {
				attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float)getDamage(level, random));
				attacker.setFire((level) + 2);
			}
			if(!itemstack.isEmpty()) {
				damageArmor(itemstack, 5, user);
			}
		}
		else if(!itemstack.isEmpty()) {
			damageArmor(itemstack, 2, user);
		}
	}
	
	//TODO
	private void damageArmor(ItemStack stack, int amount, EntityLivingBase entity) {
		int slot = -1;
		int x = 0;
		for(ItemStack i : entity.getArmorInventoryList()) {
			if(i == stack) {
				slot = x;
				break;
			}
			x++;
		}
		if(slot == -1 || !(stack.getItem() instanceof net.minecraftforge.common.ISpecialArmor)) {
			stack.damageItem(1, entity);
			return;
		}
		net.minecraftforge.common.ISpecialArmor armor = (net.minecraftforge.common.ISpecialArmor)stack.getItem();
		armor.damageArmor(entity, stack, DamageSource.causeThornsDamage(entity), amount, slot);
	}
}
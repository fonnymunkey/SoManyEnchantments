package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentBrutality extends EnchantmentBase {
	
	public EnchantmentBrutality(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.brutality;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.brutality;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.brutality;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(!ModConfig.enabled.brutality) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		Iterable<ItemStack> iter = victim.getArmorInventoryList();
		int x = 5;
		for(ItemStack item : iter) {
			x--;
		}
		for(ItemStack item : iter) {
			ItemStack armor = item;
			armor.damageItem((int)(armor.getMaxDamage() * (0.0025f * x) + EnchantmentsUtility.RANDOM.nextInt(x + 2)) + 1, victim);
		}
	}
}
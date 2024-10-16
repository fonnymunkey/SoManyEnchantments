package com.shultrea.rin.enchantments.fishing;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentAdvancedLure extends EnchantmentBase {
	
	public EnchantmentAdvancedLure(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
		this.setName("AdvancedLure");
		this.setRegistryName("AdvancedLure");
	}
	
	//TODO
	public static int getValue(ItemStack stack) {
		if(!EnchantmentRegistry.advancedLure.isEnabled()) return 0;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedLure, stack);
		if(level <= 0) return 0;
		int toReturn = level + 1;
		if(Math.random() < 0.15f) toReturn = level + 2;
		return toReturn;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedLure;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedLure;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + 15 * (par1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedLure;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.LURE && super.canApplyTogether(fTest);
	}
	
	/*
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onEvent(EntityJoinWorldEvent fEvent)
	{
		if (fEvent.getEntity() instanceof EntityFishHook)
		{
			EntityFishHook hook = (EntityFishHook) fEvent.getEntity();
			EntityLivingBase fisher = hook.getAngler();
			ItemStack fishingRod = fisher.getHeldItemMainhand();
			
			if(fishingRod == null || fishingRod == ItemStack.EMPTY){
				
			fishingRod = fisher.getHeldItemOffhand();
			if(fishingRod == null || fishingRod == ItemStack.EMPTY){
				
				return;
			}
		}
		
			
			//ItemStack item = EnchantmentHelper.getEnchantedItem(EnchantmentRegistry.AdvancedLure, fisher);
			
			int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.AdvancedLure, fishingRod);
			
			if(level <= 0)
			return;
			
			hook.setLureSpeed(level + 1);
			if(Math.random() < 0.15f){
			hook.setLureSpeed(level + 2);
			}
		}
	}
	 */
}
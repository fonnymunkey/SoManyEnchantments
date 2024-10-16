package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentAdvancedLooting extends EnchantmentBase {
	
	public EnchantmentAdvancedLooting(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setName("AdvancedLooting");
		this.setRegistryName("AdvancedLooting");
	}
	
	//TODO
	public static int getValue(int original, EntityLivingBase entity) {
		if(!EnchantmentRegistry.advancedLooting.isEnabled()) return 0;
		int levelLooting = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.advancedLooting, entity);
		if(levelLooting <= 0) return 0;
		int toReturn = original + 2 + ((levelLooting - 1) * 2);
		if(Math.random() < 0.25f) toReturn = toReturn + 2 + (levelLooting * 2);
		return toReturn;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedLooting;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedLooting;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 30 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 60;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedLooting;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.LOOTING && super.canApplyTogether(fTest);
	}
	
	/*
	@SubscribeEvent(priority = EventPriority.LOWEST) 
	public void HandleEnchant(LootingLevelEvent fEvent)
	{	
		if(!(fEvent.getDamageSource().getTrueSource() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer) fEvent.getDamageSource().getTrueSource();
		ItemStack sword = player.getHeldItemMainhand();
					
		if(sword == null)
			return;
		
		int levelLooting = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.AdvancedLooting, sword);
			
		if(levelLooting <= 0)
			return;
		
		fEvent.setLootingLevel(fEvent.getLootingLevel() + 2 + ((levelLooting - 1) * 2));
			
		if(Math.random() < 0.25f){
			
		fEvent.setLootingLevel(fEvent.getLootingLevel() + 2 + (levelLooting * 2));
		}	 
	}
	*/
}
package com.shultrea.rin.enchantments.tier;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_030;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdvancedPower extends EnchantmentBase {
	
	public EnchantmentAdvancedPower(String name, Rarity rarity, EnumEnchantmentType type) {
		super(Rarity.VERY_RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
		this.setName("AdvancedPower");
		this.setRegistryName("AdvancedPower");
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedPower;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedPower;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 10 + (par1 - 1) * 8;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedPower;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		if(fTest == Enchantments.POWER) return false;
		return super.canApplyTogether(fTest);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onEvent(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow)event.getEntity();
			EntityLivingBase shooter = (EntityLivingBase)arrow.shootingEntity;
			if(shooter == null) return;
			ItemStack bow = shooter.getActiveItemStack();
			if(bow == null || bow == ItemStack.EMPTY) {
				bow = shooter.getHeldItemOffhand();
				if(bow == null || bow == ItemStack.EMPTY) {
					return;
				}
			}
			int p = EnchantmentHelper.getEnchantmentLevel(Smc_030.AdvancedPower, bow);
			if(p > 0) {
				arrow.setDamage(arrow.getDamage() + 1.25D + (double)p * 0.75D);
				if(p >= 4) {
					arrow.setIsCritical(true);
				}
				else if(shooter.getRNG().nextFloat() < (p * 0.25f)) {
					arrow.setIsCritical(true);
				}
			}
		}
	}
}
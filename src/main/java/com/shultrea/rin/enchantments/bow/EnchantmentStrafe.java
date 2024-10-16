package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentStrafe extends EnchantmentBase {
	
	public EnchantmentStrafe(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.strafe;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.strafe;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 14 + 12 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.strafe;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.INFINITY && fTest != Enchantments.PUNCH && fTest != EnchantmentRegistry.advancedPunch && super.canApplyTogether(fTest);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onEvent(LivingEntityUseItemEvent.Tick event) {
		EntityLivingBase entity = event.getEntityLiving();
		ItemStack bow = event.getItem();
		if(bow.isEmpty()) {
			return;
		}
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.strafe, bow) > 0) if(bow.getItem() instanceof ItemBow) {
			int d = event.getDuration();
			if(EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strafe, entity) <= 4) {
				if(d % (5 - EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strafe, entity)) == 0) {
					event.setDuration(d - 1);
					if(event.getDuration() < 5000) event.setDuration(20000);
				}
			}
			else if(EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strafe, entity) == 5) {
				event.setDuration(d - (EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strafe, entity)) - 8);
				if(event.getDuration() < 5000) event.setDuration(20000);
			}
			else if(EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strafe, entity) > 5) {
				event.setDuration(d - (EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strafe, entity)) - 1200);
				if(event.getDuration() < 5000) event.setDuration(20000);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onEvent(LivingHurtEvent fEvent) {
		if(!(fEvent.getSource().getDamageType().equals("arrow"))) return;
		if(!(fEvent.getSource().getImmediateSource() instanceof EntityArrow)) return;
		EntityArrow arrow = (EntityArrow)fEvent.getSource().getImmediateSource();
		if(!(arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null))) return;
		IArrowProperties ar = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		if(ar.isArrowRapidDamage()) {
			fEvent.getEntityLiving().hurtResistantTime = 0;
		}
	}
}
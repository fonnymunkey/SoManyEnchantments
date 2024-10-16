package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
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

public class EnchantmentAdvancedPunch extends EnchantmentBase {
	
	public EnchantmentAdvancedPunch(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
		this.setName("AdvancedPunch");
		this.setRegistryName("AdvancedPunch");
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedPunch;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedPunch;
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
		return ModConfig.treasure.advancedPunch;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.PUNCH && super.canApplyTogether(fTest);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onEvent(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityArrow) {
			/**EntityArrow arrow = (EntityArrow) event.getEntity();
			 EntityLivingBase shooter = (EntityLivingBase) arrow.shootingEntity;
			 if(shooter == null)
			 return;
			 ItemStack bow = shooter.getHeldItemMainhand();
			 int PunchLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.ExtremePunch, bow);
			 if(PunchLevel == 0)
			 return;
			 arrow.setKnockbackStrength((PunchLevel * 2) + 1);
			 */
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
			int p = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedPunch, bow);
			if(p > 0) {
				arrow.setKnockbackStrength((p * 2) + 1);
			}
		}
	}
}
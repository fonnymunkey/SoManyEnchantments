package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDrag extends EnchantmentBase {
	
	public EnchantmentDrag(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.drag;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.drag;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 10 + (par1 - 1) * 8;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.drag;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.PUNCH && !(fTest instanceof EnchantmentAdvancedPunch) && super.canApplyTogether(fTest);
	}
	
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
			ItemStack bow = shooter instanceof EntityPlayer ? shooter.getActiveItemStack() :
							!shooter.getHeldItemMainhand().isEmpty() ? shooter.getHeldItemMainhand() :
							shooter.getHeldItemOffhand();
			if(bow == null || bow == ItemStack.EMPTY) {
				bow = shooter.getHeldItemOffhand();
				if(bow == null || bow == ItemStack.EMPTY) {
					return;
				}
			}
			int p = EnchantmentHelper.getEnchantmentLevel(this, bow);
			if(p > 0) {
				if(!arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) return;
				IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
				properties.setPullPower(1.25f + p * 1.75f);
			}
		}
	}
}
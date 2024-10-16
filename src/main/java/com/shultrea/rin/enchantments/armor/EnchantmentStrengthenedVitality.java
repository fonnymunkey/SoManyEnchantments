package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.UUID;

public class EnchantmentStrengthenedVitality extends EnchantmentBase {
	
	public static final UUID CACHED_UUID = UUID.fromString("e681-134f-4c54-a535-29c3ae5c7a21");
	
	public EnchantmentStrengthenedVitality(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.strengthenedVitality;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.strengthenedVitality;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + 15 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 75;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.strengthenedVitality;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment e) {
		return super.canApplyTogether(e);
		//return super.canApplyTogether(e) && !(e instanceof IEnchantmentProtection) && !(e instanceof EnchantmentProtection);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void updateHealth(PlayerTickEvent fEvent) {
		if(fEvent.phase != Phase.END) return;
		int i = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strengthenedVitality, fEvent.player);
		if(i <= 0) {
			RemoveHealth(fEvent.player);
			return;
		}
		AddHealth(fEvent.player);
	}
	
	private void AddHealth(EntityPlayer fEntity) {
		int level = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strengthenedVitality, fEntity);
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "StrengthenedHealthBoost", 0.1D * level, 2);
		speedAttr.removeModifier(modSpeed);
		speedAttr.applyModifier(modSpeed);
		if(speedAttr.getModifier(CACHED_UUID) != null) {}
	}
	
	private void RemoveHealth(EntityLivingBase fEntity) {
		int level = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strengthenedVitality, fEntity);
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
		if(speedAttr.getModifier(CACHED_UUID) == null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "StrengthenedHealthBoost", 0.2D * level, 2);
		speedAttr.removeModifier(modSpeed);
	}
}
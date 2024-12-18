package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.ItemMixin
 */
public class EnchantmentSwifterSlashes extends EnchantmentBase {
	
	//TODO
	public static final UUID CACHED_UUID = UUID.fromString("e6109481-134f-4c54-a535-29c3ae5c7a21");
	
	public EnchantmentSwifterSlashes(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.swifterSlashes;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.swifterSlashes;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.swifterSlashes, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.swifterSlashes, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.swifterSlashes, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.swifterSlashes, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.swifterSlashes;
	}
	
	//TODO
	public boolean isValidPlayer(Entity entity) {
		if(entity instanceof EntityPlayer) {
			if(((EntityPlayer)entity).getHeldItemMainhand() != null) {
				return level(((EntityPlayer)entity).getHeldItemMainhand()) > 0;
			}
		}
		return false;
	}
	
	//TODO
	public boolean isValidMob(Entity entity) {
		if(entity instanceof EntityMob || entity instanceof EntityAnimal) {
			if(((EntityMob)entity).getHeldItemMainhand() != null) {
				if(level(((EntityMob)entity).getHeldItemMainhand()) > 0) {
					return true;
				}
			}
			if(((EntityAnimal)entity).getHeldItemMainhand() != null) {
				return level(((EntityAnimal)entity).getHeldItemMainhand()) > 0;
			}
		}
		return false;
	}
	
	//TODO
	public int level(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.swifterSlashes, stack);
	}
	
	//TODO
	@SubscribeEvent
	public void onEntityHit(AttackEntityEvent event) {
		if(isValidPlayer(event.getEntityPlayer()) || isValidMob(event.getEntityLiving())) {
			ItemStack stack = event.getEntityLiving().getHeldItemMainhand();
			if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.swifterSlashes, stack) == 0) return;
			int levelFasterStrikes = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.swifterSlashes, stack);
			if(event.getEntity().world.rand.nextInt(100) < 25 + (level(stack))) {
				event.getTarget().hurtResistantTime = 23 - (level(stack) * 3);
			}
		}
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		ItemStack weapon = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.swifterSlashes, weapon);
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.swifterSlashes, weapon) <= 0) return;
		if(fEvent.getEntityLiving().world.rand.nextInt(100) < 25 + (level * 4)) {
			fEvent.getEntityLiving().hurtResistantTime = 23 - (level * 3);
		}
	}
	
	//TODO
	@SubscribeEvent
	public void HandleEnchant(LivingUpdateEvent fEvent) {
		if(!(fEvent.getEntity() instanceof EntityPlayer)) return;
		EntityLivingBase entity = (EntityLivingBase)fEvent.getEntity();
		ItemStack weapon = entity.getHeldItemMainhand();
		if(weapon == null) {
			RemoveAttackSpeedBuff(entity);
			return;
		}
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.swifterSlashes, weapon);
		if(level > 0) AddAttackSpeedBuff(entity);
		else RemoveAttackSpeedBuff(entity);
	}
	
	private void AddAttackSpeedBuff(EntityLivingBase fEntity) {
		ItemStack weapon = fEntity.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.swifterSlashes, weapon);
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED);
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "attackSpeed", ((double)level * 0.45D + (0.04D * level)), 1);
		speedAttr.removeModifier(modSpeed);
		speedAttr.applyModifier(modSpeed);
		if(speedAttr.getModifier(CACHED_UUID) != null) {}
	}
	
	private void RemoveAttackSpeedBuff(EntityLivingBase fEntity) {
		ItemStack weapon = fEntity.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.swifterSlashes, weapon);
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED);
		if(speedAttr.getModifier(CACHED_UUID) == null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "attackSpeed", ((double)level * 0.45D + (0.04D * level)), 1);
		speedAttr.removeModifier(modSpeed);
	}
}
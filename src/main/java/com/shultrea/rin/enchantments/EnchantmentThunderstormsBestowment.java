package com.shultrea.rin.enchantments;

import com.shultrea.rin.Interfaces.IWeatherEnchantment;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_030;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentThunderstormsBestowment extends EnchantmentBase implements IWeatherEnchantment {
	
	public EnchantmentThunderstormsBestowment(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.thunderstormsBestowment;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.thunderstormsBestowment;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + (par1 - 1) * 15;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.thunderstormsBestowment;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IWeatherEnchantment);
	}
	
	public boolean isValidPlayer(Entity entity) {
		if(entity instanceof EntityPlayer) {
			if(((EntityPlayer)entity).getHeldItemMainhand() != null) {
				return level(((EntityPlayer)entity).getHeldItemMainhand()) > 0;
			}
		}
		return false;
	}
	
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
	
	public int level(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(Smc_030.Thunderstorm, stack);
	}
	
	/**
	 *
	 */
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		float SkyDamage = 0.0f;
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(Smc_030.Thunderstorm, dmgSource) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		int levelWeather = EnchantmentHelper.getEnchantmentLevel(Smc_030.Thunderstorm, dmgSource);
		float Damage = fEvent.getAmount();
		if(attacker.world.isThundering() && EnchantmentsUtility.noBlockLight(attacker)) {
			//SkyDamage = (levelWeather * 1.30f);
			//fEvent.setAmount(Damage + SkyDamage);
			float FDamage = EnchantmentsUtility.CalculateDamageIgnoreSwipe(Damage, 0.0f, 1.25f, 1.00f, attacker, Smc_030.Thunderstorm);
			fEvent.setAmount(FDamage);
		}
		else if(attacker.world.isRaining()) {
			fEvent.setAmount(Damage);
		}
		else if(EnchantmentsUtility.noBlockLight(attacker)) {
			float Fin = EnchantmentsUtility.CalculateDamageForNegativeSwipe(Damage, 0.00f, -0.5f, 1.0f, attacker, Smc_030.Thunderstorm);
			fEvent.setAmount(Fin);
			if(fEvent.getEntity().world.rand.nextInt(800) < 2 + (levelWeather * 2)) {
				EnchantmentsUtility.setThunderstorm(fEvent.getEntityLiving().getEntityWorld());
			}
		}
		else if(!EnchantmentsUtility.noBlockLight(attacker)) {
			float FI = EnchantmentsUtility.CalculateDamageForNegativeSwipe(Damage, -0.05f, -0.75f, 1.0f, attacker, Smc_030.Thunderstorm);
			fEvent.setAmount(FI);
			if(fEvent.getEntity().world.rand.nextInt(800) < 2 + (levelWeather * 2)) {
			}
		}
	}
}
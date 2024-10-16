package com.shultrea.rin.enchantments.weapon.weather;

import com.shultrea.rin.Interfaces.IWeatherEnchantment;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRainsBestowment extends EnchantmentBase implements IWeatherEnchantment {
	
	public EnchantmentRainsBestowment(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.rainsBestowment;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.rainsBestowment;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + (par1 - 1) * 15;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.rainsBestowment;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IWeatherEnchantment);
	}
	
	@SubscribeEvent
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.rainsBestowment, stack);
		if(enchantmentLevel <= 0) return;
		float Damage = fEvent.getAmount();
		if(attacker.world.isRaining() && EnchantmentsUtility.noBlockLight(attacker)) {
			float FDamage = EnchantmentsUtility.CalculateDamageIgnoreSwipe(Damage, 0.2f, 0.80f, 1.0f, attacker, EnchantmentRegistry.rainsBestowment);
			fEvent.setAmount(FDamage);
		}
		else if(!attacker.world.isRaining() && EnchantmentsUtility.noBlockLight(attacker)) {
			float Fi = EnchantmentsUtility.CalculateDamageForNegativeSwipe(Damage, -0.2f, -0.3f, 1.0f, attacker, EnchantmentRegistry.rainsBestowment);
			fEvent.setAmount(Fi);
			if(fEvent.getEntity().world.rand.nextInt(500) < 3 + enchantmentLevel) {
				EnchantmentsUtility.Raining(fEvent.getEntityLiving().getEntityWorld());
			}
			else if(!EnchantmentsUtility.noBlockLight(attacker)) {
				float Fin = EnchantmentsUtility.CalculateDamageForNegativeSwipe(Damage, 0.0f, -0.5f, 1.0f, attacker, EnchantmentRegistry.rainsBestowment);
				fEvent.setAmount(Fin);
			}
		}
	}
}
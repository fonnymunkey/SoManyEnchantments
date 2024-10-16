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
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentClearSkiesFavor extends EnchantmentBase implements IWeatherEnchantment {
	
	public EnchantmentClearSkiesFavor(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.clearSkiesFavor;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.clearSkiesFavor;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + (par1 - 1) * 15;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.clearSkiesFavor;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IWeatherEnchantment);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.clearSkiesFavor, dmgSource);
		if(enchantmentLevel <= 0) return;
		//System.out.println("ClearSky Initiating Damage");
		//Boolean PrintThis = EnchantmentsUtility.noBlockLight(attacker);
		//System.out.println(PrintThis);
		if(!attacker.world.isRaining() && EnchantmentsUtility.noBlockLight(attacker)) {
			//SkyDamage = (enchantmentLevel * 0.90f);
			//float Damage = fEvent.getAmount();
			//fEvent.setAmount(Damage + SkyDamage);
			float FDamage = EnchantmentsUtility.CalculateDamageIgnoreSwipe(fEvent.getAmount(), 0.5f, 0.75f, 1.00f, attacker, EnchantmentRegistry.clearSkiesFavor);
			fEvent.setAmount(FDamage);
			//System.out.println(SecondDamage);
			//System.out.println(Damage);
		}
		else if(attacker.world.isRaining() || attacker.world.isThundering()) {
			if(Math.random() * 2000 < 3 + (enchantmentLevel * 2)) {
				EnchantmentsUtility.clearSky(fEvent.getEntityLiving().getEntityWorld());
			}
			float Fi = EnchantmentsUtility.CalculateDamageForNegativeSwipe(fEvent.getAmount(), 0.0f, -0.6f, 1.0f, attacker, EnchantmentRegistry.clearSkiesFavor);
			fEvent.setAmount(Fi);
		}
	}
}
package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentEvasion extends EnchantmentBase {
	
	public EnchantmentEvasion(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.evasion;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.evasion;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.evasion;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = false)
	public void HandleEnchant(LivingAttackEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob" && fEvent.getSource().damageType == "arrow")
			return;
		if(!(fEvent.getEntity() instanceof EntityLivingBase)) return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase victim = fEvent.getEntityLiving();
		if(victim == null) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.trueStrike, attacker.getHeldItemMainhand()) > 0) {
			if(EnchantmentsUtility.RANDOM.nextInt(100) < 75) return;
		}
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, victim);
		if(level <= 0) return;
		double randX = 0.65 + victim.getRNG().nextDouble() * 0.25f;
		randX = victim.getRNG().nextBoolean() ? randX * -1 : randX;
		double randZ = 0.65 + victim.getRNG().nextDouble() * 0.25f;
		randZ = victim.getRNG().nextBoolean() ? randZ * -1 : randZ;
		if(fEvent.getEntityLiving().world.rand.nextInt(100) < 5 + (level * 15)) {
			if(!attacker.world.isRemote && ModConfig.miscellaneous.evasionDodgeEffect)
				EnchantmentsUtility.ImprovedKnockBack(victim, 0.7f, (attacker.posX - victim.posX) * randX, (attacker.posZ - victim.posZ) * randZ);
			victim.getEntityWorld().playSound(null, victim.posX, victim.posY, victim.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.3f, victim.getRNG().nextFloat() * 2.25f + 0.75f);
			fEvent.setCanceled(true);
			victim.hurtResistantTime = 15 + 5 * level;
		}
	}
}
package com.shultrea.rin.enchantments.shield;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentBurningShield extends EnchantmentBase {
	
	public EnchantmentBurningShield(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.burningShield;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.burningShield;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 18 + 12 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.burningShield;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void shieldBurn(LivingAttackEvent fEvent) {
		if(!(fEvent.getEntity() instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)fEvent.getEntity();
		ItemStack shield = victim.getHeldItemMainhand();
		if(shield.isEmpty() || !shield.getItem().isShield(shield, victim)) {
			shield = victim.getHeldItemOffhand();
			if(shield.isEmpty()) return;
		}
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.burningShield, shield) <= 0) return;
		int levelfs = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.burningShield, shield);
		Entity attacker = fEvent.getSource().getImmediateSource();
		if(shield.getItem().isShield(shield, victim)) {
			if(fEvent.getEntityLiving().world.rand.nextInt(100) < 40 + (levelfs * 10) && EnchantmentsUtility.canBlockDamageSource(fEvent.getSource(), victim)) {
				attacker.attackEntityFrom(new EntityDamageSource("player", victim).setFireDamage(), fEvent.getAmount() * (levelfs * 0.1f));
				attacker.setFire(4 + levelfs * 2);
			}
		}
	}
}
//@Override
/**
 * public void onUserHurt(EntityLivingBase user, Entity attacker, int level){
 *
 * if(GotHit == true){
 *
 * double XMot = attacker.motionX; double ZMot = attacker.motionZ; double YMot = attacker.motionY;
 *
 * XMot += (double)(-MathHelper.sin(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float) 0.02f + level * 0.03125F);
 * ZMot += (double)(MathHelper.cos(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)0.02f + level * 0.03125F);
 * attacker.motionX = XMot /1.1D; attacker.motionZ = ZMot /1.1D; attacker.motionY = YMot + level * 0.125;
 *
 * }
 *
 * } }
 */
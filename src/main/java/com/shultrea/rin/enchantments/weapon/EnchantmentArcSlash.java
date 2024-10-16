package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentSweepingEdge;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.ListIterator;

public class EnchantmentArcSlash extends EnchantmentBase {
	
	public EnchantmentArcSlash(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.arcSlash;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.arcSlash;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 15 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.arcSlash;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment e) {
		return super.canApplyTogether(e) && !(e instanceof EnchantmentSweepingEdge);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void HandleEnchant(LivingDamageEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource()))
			return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		//Cap out cleave level to avoid large AABB checks
		int levelCleave = Math.min(10, EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.arcSlash, dmgSource));
		//TODO: stupid way of getting the fire ticks
		int lf = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.fieryEdge, dmgSource) * 5;
		lf += EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, dmgSource) * 3;
		lf += EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedFireAspect, dmgSource) * 6;
		lf += EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.supremeFireAspect, dmgSource) * 12;
		lf += (int)(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.lesserFireAspect, dmgSource) * 1.5f);
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.arcSlash, dmgSource) <= 0) return;
		int levitationLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.levitator, dmgSource);
		// We have a cleaving level, let's figure out our damage value.
		float splashDamage = fEvent.getAmount() * (levelCleave * 0.25f);
		// Next, find our entities to hit.
		AxisAlignedBB boundBox = new AxisAlignedBB(attacker.posX - 5 - levelCleave, attacker.posY - 5 - levelCleave, attacker.posZ - 5 - levelCleave, attacker.posX + 5 + levelCleave, attacker.posY + 5 + levelCleave, attacker.posZ + 5 + levelCleave);
		//@SuppressWarnings("unchecked")
		ArrayList<Entity> targetEntities = new ArrayList<Entity>(attacker.getEntityWorld().getEntitiesWithinAABBExcludingEntity(fEvent.getEntity(), boundBox));
		// Let's remove all the entries that aren't within range of our attacker
        for (Entity target : targetEntities) {
            if (!(target instanceof EntityLivingBase)) continue;
            if (target == attacker) continue;
            if (target == fEvent.getEntityLiving()) continue;
            //Old value was 4.00f
            if (target.getDistance(attacker) > 3.00f + levelCleave * 0.25f) continue;
            Vec3d attackerCheck = EnchantmentsUtility.Cleave(target.posX - attacker.posX, target.posY - attacker.posY, target.posZ - attacker.posZ);
            double angle = Math.toDegrees(Math.acos(attackerCheck.normalize().dotProduct(attacker.getLookVec())));
            if (angle < MathHelper.clamp(60.0D + (levelCleave * 10), 60, 359)) {
                // This is within our arc, let's deal our damage.
                DamageSource source = null;
                if (attacker instanceof EntityPlayer) {
                    source = new EntityDamageSource("playerCleave", attacker);//.setDamageBypassesArmor();
                    target.attackEntityFrom(source, splashDamage);
                    target.setFire(lf);
                    if (levitationLevel > 0)
                        ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 30 + (levitationLevel * 12), 1 + levitationLevel));
                }
                if (attacker instanceof EntityMob) {
                    source = new EntityDamageSource("mobCleave", attacker);//.setDamageBypassesArmor();
                    target.attackEntityFrom(source, splashDamage);
                    target.setFire(lf);
                    if (levitationLevel > 0)
                        ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 30 + (levitationLevel * 12), 1 + levitationLevel));
                }
                if (source == null) {
                    target.attackEntityFrom(DamageSource.GENERIC, splashDamage);
                }
                if (attacker instanceof EntityLivingBase) {
                    // Apply knockback
                    int modKnockback = 1;
                    modKnockback += EnchantmentHelper.getKnockbackModifier(attacker) * 0.5f;
                    if (attacker.isSprinting()) modKnockback++;
                    //Entity victim = fEvent.getEntityLiving();
                    if (modKnockback > 0)
                        //target.setVelocity((double)(-MathHelper.sin(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)modKnockback * 0.85F), 0.2D, (double)(MathHelper.cos(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)modKnockback * 0.85F));
                        EnchantmentsUtility.ImprovedKnockBack(target, 0.3F * modKnockback, attacker.posX - target.posX, attacker.posZ - target.posZ);
                }
            }
        }
		// Stop the player sprinting
		attacker.setSprinting(false);
	}
}
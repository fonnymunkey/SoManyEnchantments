package com.shultrea.rin.enchantments.shield;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentEmpoweredDefence extends EnchantmentBase {
	
	public EnchantmentEmpoweredDefence(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.empoweredDefence;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.empoweredDefence;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	//TODO
	@Override
	public boolean canApply(ItemStack e) {
		return super.canApply(e) || e.getItem().isShield(e, null);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.empoweredDefence;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW)
	public void EmpoweredDefenceEvent(LivingAttackEvent fEvent) {
		if(!(fEvent.getEntity() instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)fEvent.getEntity();
		ItemStack shield = victim.getHeldItemOffhand();
		if(shield == ItemStack.EMPTY) {
			shield = victim.getHeldItemMainhand();
			if(shield == ItemStack.EMPTY) return;
		}
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.empoweredDefence, shield) <= 0) return;
		int levelED = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.empoweredDefence, shield);
		Entity attacker = fEvent.getSource().getImmediateSource();
		ItemStack shield2 = fEvent.getEntityLiving().getActiveItemStack();
		if(shield2.getItem().isShield(shield, victim)) {
			if(fEvent.getEntityLiving().world.rand.nextInt(100) < 20 + (levelED * 5) && EnchantmentsUtility.canBlockDamageSource(fEvent.getSource(), victim)) {
				fEvent.setCanceled(true);
				EnchantmentsUtility.ImprovedKnockBack(attacker, 0.4f + 0.2F * levelED, victim.posX - attacker.posX, victim.posZ - attacker.posZ);
				if(victim instanceof EntityPlayer) {
					attacker.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)victim), fEvent.getAmount() * (0.225f * levelED));
				}
				else {
					attacker.attackEntityFrom(DamageSource.causeMobDamage(victim), fEvent.getAmount() * (0.225f * levelED));
				}
				victim.hurtResistantTime = 15;
			}
		}
	}
	/** @SubscribeEvent public void OnShieldCooldown(LivingUpdateEvent fEvent){
	if(!(fEvent.getEntityLiving() instanceof EntityPlayer))
	return;
	EntityPlayer player = (EntityPlayer) fEvent.getEntityLiving();
	int levelED = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.EmpoweredDefence, player);
	float cooldown = player.getCooldownTracker().getCooldown(Items.SHIELD, 100f);
	if(cooldown == 0)
	return;
	if(cooldown <= 100){
	player.getCooldownTracker().setCooldown(Items.SHIELD, (int)(100 / levelED));
	player.resetActiveHand();
	fEvent.getEntityLiving().getEntityWorld().setEntityState(player, (byte)30);
	}
	}
	 */
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
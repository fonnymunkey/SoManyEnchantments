package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentParry extends EnchantmentBase {
	
	public EnchantmentParry(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.parry;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.parry;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 15 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.parry;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = false)
	public void HandleEnchant(LivingAttackEvent e) {
		if(e.getSource().damageType != "player" && e.getSource().damageType != "mob" && e.getSource().damageType == "arrow")
			return;
		if(!(e.getEntity() instanceof EntityLivingBase)) return;
		if(!(e.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase victim = e.getEntityLiving();
		if(victim == null) return;
		ItemStack weaponSword = victim.getHeldItemMainhand();
		EntityLivingBase attacker = (EntityLivingBase)e.getSource().getTrueSource();
		if(attacker == null) return;
		if(weaponSword == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.parry, weaponSword) <= 0) return;
		//0.5.1 Rollback, remove True Strike nerf
		/*
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.TrueStrike, attacker.getHeldItemMainhand()) > 0 && victim.getRNG().nextInt(100) < 75)
			return;
		*/
		int levelParry = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.parry, weaponSword);
		//0.5.1 Rollback, remove canBlockDamageSourceIgnoreUnblockable nerf
		if(victim.world.rand.nextInt(100) < 16 + (levelParry * 8) /*&& EnchantmentsUtility.canBlockDamageSourceIgnoreUnblockable(e.getSource(), victim)*/) {
			if(!attacker.world.isRemote)
				EnchantmentsUtility.ImprovedKnockBack(attacker, 0.3F + (0.15f * levelParry), victim.posX - attacker.posX, victim.posZ - attacker.posZ);
			attacker.getEntityWorld().playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.3f, 3f);
			e.setCanceled(true);
			if(victim instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)victim;
				player.hurtResistantTime = 15;
			}
			else {
				e.getEntityLiving().hurtResistantTime = 15;
			}
		}
	}
}


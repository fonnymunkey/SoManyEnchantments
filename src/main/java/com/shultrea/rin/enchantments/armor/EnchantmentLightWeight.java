package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EnchantmentLightWeight extends EnchantmentBase {
	
	public EnchantmentLightWeight(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.lightWeight;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.lightWeight;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + 15 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.lightWeight;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void onExist(PlayerTickEvent e) {
		if(e.phase == Phase.START) return;
		if(e.player == null) return;
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, e.player);
		if(level > 0 && !e.player.onGround) {
			if(e.player.isCreative()) {
				if(!e.player.capabilities.isFlying) {
					lightStep(level, e.player);
				}
			}
			else {
				lightStep(level, e.player);
			}
		}
	}
	
	public void lightStep(int level, EntityPlayer player) {
		float cloud = MathHelper.clamp(level / 3, 0.33f, 1);
		if(player.fallDistance >= 3.0F) {
			if(player.world.isRemote) {
				for(int i = 0; i < 3; i++) {
					player.world.spawnParticle(EnumParticleTypes.CLOUD, player.posX, player.posY - 2.0D, player.posZ, (player.getRNG().nextFloat() - 0.5F) / 2.0F * cloud, -0.5D * cloud, (player.getRNG().nextFloat() - 0.5F) / 2.0F * cloud);
				}
			}
		}
		if((player.isSprinting()) && (player.world.isRemote)) {
			player.world.spawnParticle(EnumParticleTypes.CLOUD, player.posX, player.posY - 1.5D, player.posZ, (player.getRNG().nextFloat() - 0.5F) / 2.0F * cloud, 0.1D * cloud, (player.getRNG().nextFloat() - 0.5F) / 2.0F * cloud);
		}
		if(!player.onGround) {
			if(!player.world.isRemote)
				// player.motionY = player.motionY + MathHelper.clamp(0.005D + 0.005D * level, 0, 0.04D);
				player.jumpMovementFactor += 0.0075F * level + 0.0025f;
		}
		if(player.collidedHorizontally) {
			player.stepHeight = 1.0F;
		}
		else {
			player.stepHeight = 0.5F;
		}
	}
	
	@SubscribeEvent
	public void onJump(LivingJumpEvent e) {
		if(e.getEntityLiving() == null) return;
		if(EnchantmentHelper.getMaxEnchantmentLevel(this, e.getEntityLiving()) > 0)
			e.getEntityLiving().motionY *= (1.05D + EnchantmentHelper.getMaxEnchantmentLevel(this, e.getEntityLiving()) * 0.15D);
	}
	
	@SubscribeEvent
	public void onEvent(LivingFallEvent e) {
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, e.getEntityLiving());
		if(level > 0) {
			e.setDistance(MathHelper.clamp(e.getDistance() - 4 - level * 2f, 0, 20));
		}
	}
}
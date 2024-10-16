package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentWaterWalker;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.UUID;

public class EnchantmentUnderwaterStrider extends EnchantmentBase {
	
	public static final UUID CACHED_UUID = UUID.fromString("a612fe81-132f-4c58-a335-13c4ae5cba21");
	
	public EnchantmentUnderwaterStrider(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.underwaterStrider;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.underwaterStrider;
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
		return ModConfig.treasure.underwaterStrider;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment e) {
		return super.canApplyTogether(e) && !(e instanceof EnchantmentWaterWalker);
	}
	
	@SubscribeEvent
	public void onUnder(PlayerTickEvent e) {
		if(e.phase == Phase.END) return;
		EntityPlayer player = e.player;
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, player);
		IAttributeInstance s = player.getEntityAttribute(EntityLivingBase.SWIM_SPEED);
		if(level <= 0) {
			removeSpeed(s, player);
			return;
		}
		IBlockState water = player.world.getBlockState(new BlockPos(player.posX, player.posY + 1, player.posZ));
		if(player.isInWater() && water.getMaterial() == Material.WATER) {
			removeSpeed(s, player);
			addSpeed(s, player);
		}
		else removeSpeed(s, player);
	}
	
	private void addSpeed(IAttributeInstance s, EntityLivingBase p) {
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, p);
		if(s.getModifier(CACHED_UUID) != null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "moveSpeed", 1.30 + ((double)level * 0.4D), 1);
		s.removeModifier(modSpeed);
		s.applyModifier(modSpeed);
	}
	
	private void removeSpeed(IAttributeInstance s, EntityLivingBase p) {
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, p);
		if(s.getModifier(CACHED_UUID) == null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "moveSpeed", 1.30 + ((double)level * 0.4D), 1);
		s.removeModifier(modSpeed);
	}
}
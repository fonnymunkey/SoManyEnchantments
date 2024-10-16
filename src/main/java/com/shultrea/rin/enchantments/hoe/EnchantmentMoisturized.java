package com.shultrea.rin.enchantments.hoe;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentMoisturized extends EnchantmentBase {
	
	public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
	
	public EnchantmentMoisturized(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.moisturized;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.moisturized;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 20;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.moisturized;
	}
	
	private void setFarmland(World worldIn, BlockPos pos, Block block) {
		worldIn.setBlockState(pos, block.getDefaultState().withProperty(MOISTURE, 7), 3);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onTill(UseHoeEvent fEvent) {
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.moisturized, fEvent.getCurrent());
		if(level <= 0) return;
		if(fEvent.isCanceled()) return;
		//Note that it can convert blocks into a farmland! Heh not anynore
		if(fEvent.getResult() == Event.Result.DENY) return;
		BlockPos blockpos = fEvent.getPos();
		IBlockState state = fEvent.getWorld().getBlockState(blockpos);
		if(state.getBlock() instanceof BlockDirt || state.getBlock() instanceof BlockGrass || state.getBlock() instanceof BlockMycelium) {
			this.setFarmland(fEvent.getWorld(), blockpos, Blocks.FARMLAND);
			fEvent.getWorld().scheduleUpdate(blockpos, fEvent.getWorld().getBlockState(blockpos).getBlock(), MathHelper.getInt(fEvent.getEntityLiving().getRNG(), fEvent.getEntityLiving().getRNG().nextInt(4) + 120, fEvent.getEntityLiving().getRNG().nextInt(4) + 240));
			fEvent.setResult(Result.ALLOW);
			fEvent.getWorld().playSound(null, fEvent.getEntityLiving().posX, fEvent.getEntityLiving().posY, fEvent.getEntityLiving().posZ, SoundEvents.ITEM_HOE_TILL, SoundCategory.PLAYERS, 1.0f, 1f);
		}
	}
}
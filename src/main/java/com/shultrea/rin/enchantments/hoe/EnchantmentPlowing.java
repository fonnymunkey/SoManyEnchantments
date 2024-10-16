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

public class EnchantmentPlowing extends EnchantmentBase {
	
	public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
	
	public EnchantmentPlowing(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.wellTilled;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.wellTilled;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 25 * (par1 - 1) * 15;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.wellTilled;
	}
	
	private void setFarmland(World worldIn, BlockPos pos, Block block, int level) {
		if(level > 0) worldIn.setBlockState(pos, block.getDefaultState().withProperty(MOISTURE, 7), 3);
		else worldIn.setBlockState(pos, block.getDefaultState(), 3);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onTill(UseHoeEvent fEvent) {
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.plowing, fEvent.getCurrent());
		if(level <= 0) return;
		if(fEvent.isCanceled()) return;
		if(fEvent.getEntityPlayer() == null) return;
		if(fEvent.getResult() == Event.Result.DENY) return;
		BlockPos blockpos = fEvent.getPos();
		BlockPos.MutableBlockPos m = new BlockPos.MutableBlockPos(0, 0, 0);
		float f = (float)Math.min(9, level);
		for(BlockPos.MutableBlockPos blockPosMutable : BlockPos.getAllInBoxMutable(blockpos.add(-f, -1.0D, -f), blockpos.add(f, -1.0D, f))) {
			m.setPos(blockPosMutable.getX(), blockPosMutable.getY() + 1, blockPosMutable.getZ());
			IBlockState state = fEvent.getWorld().getBlockState(m);
			if(state.getBlock() instanceof BlockDirt || state.getBlock() instanceof BlockGrass || state.getBlock() instanceof BlockMycelium) {
				if(fEvent.getResult() != Result.ALLOW) fEvent.setResult(Result.ALLOW);
				else fEvent.getCurrent().damageItem(1, fEvent.getEntityPlayer());
				this.setFarmland(fEvent.getWorld(), m, Blocks.FARMLAND, EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.plowing, fEvent.getCurrent()));
				fEvent.getWorld().scheduleUpdate(blockpos, fEvent.getWorld().getBlockState(blockpos).getBlock(), MathHelper.getInt(fEvent.getEntityLiving().getRNG(), fEvent.getEntityLiving().getRNG().nextInt(4) + 120, fEvent.getEntityLiving().getRNG().nextInt(4) + 240));
				fEvent.getWorld().playSound(null, fEvent.getEntityLiving().posX, fEvent.getEntityLiving().posY, fEvent.getEntityLiving().posZ, SoundEvents.ITEM_HOE_TILL, SoundCategory.PLAYERS, 1.0f, 1f);
			}
		}
	}
}
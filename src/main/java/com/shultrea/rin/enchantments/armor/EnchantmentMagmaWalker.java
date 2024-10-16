package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class EnchantmentMagmaWalker extends EnchantmentBase {
	
	public EnchantmentMagmaWalker(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET});
	}
	
	public static void walkOnMagma(EntityLivingBase living, World worldIn, BlockPos pos, int level) {
		if(living.onGround) {
			float f = (float)Math.min(16, 2 + level);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);
			for(BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add(-f, -1.0D, -f), pos.add(f, -1.0D, f))) {
				if(blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double)(f * f)) {
					blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
					IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);
					if(iblockstate.getMaterial() == Material.AIR) {
						IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);
						if(iblockstate1.getMaterial() == Material.LAVA && (iblockstate1.getBlock() == net.minecraft.init.Blocks.LAVA || iblockstate1.getBlock() == net.minecraft.init.Blocks.FLOWING_LAVA) && iblockstate1.getValue(BlockLiquid.LEVEL).intValue() == 0 && worldIn.mayPlace(Blocks.MAGMA, blockpos$mutableblockpos1, false, EnumFacing.DOWN, null)) {
							worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.MAGMA.getDefaultState());
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.magmaWalker;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.magmaWalker;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + 25 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.magmaWalker;
	}
	
	@SubscribeEvent
	public void magmaWalk(PlayerTickEvent fEvent) {
		if(fEvent.side != Side.SERVER) return;
		EntityPlayer p = fEvent.player;
		int level = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.magmaWalker, p);
		if(level <= 0) return;
		BlockPos blockpos = new BlockPos(p);
		//  if (!Objects.equal(new BlockPos(p.prevPosX, p.prevPosY, p.prevPosZ), blockpos))
		{
			walkOnMagma(p, p.world, blockpos, level);
		}
	}
	
	@SubscribeEvent
	public void onMagmaStep(LivingAttackEvent e) {
		if(e.getSource() == DamageSource.LAVA && e.getSource().getTrueSource() == null && e.getEntityLiving() != null && !e.getEntityLiving().isInLava()) {
			e.setCanceled(true);
		}
		if(e.getSource() == DamageSource.HOT_FLOOR && e.getSource().getTrueSource() == null) {
			if(EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.magmaWalker, e.getEntityLiving()) > 0)
				e.setCanceled(true);
		}
	}
}
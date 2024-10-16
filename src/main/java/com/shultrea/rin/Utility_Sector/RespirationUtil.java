package com.shultrea.rin.Utility_Sector;
/*
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RespirationUtil {
	
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
		if(state.getMaterial() == Material.WATER) {
			float f12 = 0.0F;
			if(entity instanceof net.minecraft.entity.EntityLivingBase) {
				net.minecraft.entity.EntityLivingBase ent = (net.minecraft.entity.EntityLivingBase)entity;
				f12 = (float)net.minecraft.enchantment.EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.advancedRespiration, ent) * 0.4F;
				/**if (ent.isPotionActive(net.minecraft.init.MobEffects.WATER_BREATHING))
				 {
				 f12 = f12 * 0.3F + 0.6F;
				 }
				 */
/*			}
			return new Vec3d(0.02F + f12, 0.02F + f12, 0.2F + f12);
		}
		else if(state.getMaterial() == Material.LAVA) {
			return new Vec3d(0.6F, 0.1F, 0.0F);
		}
		return originalColor;
	}
}
*/
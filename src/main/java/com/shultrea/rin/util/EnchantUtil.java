package com.shultrea.rin.util;

import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.curses.EnchantmentPandorasCurse;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Recreated by Rin on 13/05/2017. This is an utility to be used for some enchantments.
 */
public abstract class EnchantUtil {
	
	public static final Random RANDOM = new Random();
	
	private static List<Enchantment> CURSES = null;
	
	public static List<Enchantment> getCurses() {
		if(CURSES == null) {
			CURSES = new ArrayList<>();
			for(Enchantment e : Enchantment.REGISTRY)
				if(e.isCurse() && !(e instanceof EnchantmentPandorasCurse)) {
					CURSES.add(e);
				}
		}
		return CURSES;
	}
	
	public static boolean canEntitySeeSky(EntityLivingBase attacker) {
		if(attacker == null) return false;
		BlockPos pos = new BlockPos(attacker.posX, attacker.posY, attacker.posZ);
		return attacker.world.canBlockSeeSky(pos);
	}

	public static void setRaining(World world) {
		if(!ModConfig.miscellaneous.enableWeatherChanges) return;
		if(world.isRemote) return;
		int i = (300 + RANDOM.nextInt(600)) * 20;
		world.getWorldInfo().setCleanWeatherTime(0);
		world.getWorldInfo().setRainTime(i);
		world.getWorldInfo().setThunderTime(i);
		world.getWorldInfo().setRaining(true);
		world.getWorldInfo().setThundering(false);
	}
	
	public static void setThundering(World world) {
		if(!ModConfig.miscellaneous.enableWeatherChanges) return;
		if(world.isRemote) return;
		int i = (300 + RANDOM.nextInt(600)) * 20;
		world.getWorldInfo().setCleanWeatherTime(0);
		world.getWorldInfo().setRainTime(i);
		world.getWorldInfo().setThunderTime(i);
		world.getWorldInfo().setRaining(true);
		world.getWorldInfo().setThundering(true);
	}

	public static void setClearWeather(World world) {
		if(!ModConfig.miscellaneous.enableWeatherChanges) return;
		if(world.isRemote) return;
		int i = (300 + RANDOM.nextInt(600)) * 20;
		world.getWorldInfo().setCleanWeatherTime(i);
		world.getWorldInfo().setRainTime(0);
		world.getWorldInfo().setThunderTime(0);
		world.getWorldInfo().setRaining(false);
		world.getWorldInfo().setThundering(false);
	}
	
	/**
	 * Used to calculate modified damage
	 * @param damage - The original damage.
	 * @param constant - The constant damage you want to add in the calculation.
	 * @param multiplier - Multiplies the given value based on the enchantment's level you want to add in the
	 * calculation.
	 * @param trueMultiplier - Multiplies the damage based on the total damage and not the level of the enchantment you
	 * want to add in the calculation. Avoid using zero as this negates your damage.
	 * @param level - The enchantment level.
	 * @return The finished calculated damage.
	 */
	public static float modifyDamage(float damage, float constant, float multiplier, float trueMultiplier, int level) {
		if(damage <= 1.0F) return 1.0F;
		return (damage + constant + level * multiplier) * trueMultiplier;
	}
	
	/**
	 * An improved vanilla knockback mechanic that ignores the knockback resistance of a mob
	 */
	public static void knockBackIgnoreKBRes(Entity entityIn, float strength, double xRatio, double zRatio) {
		entityIn.isAirBorne = true;
		float f = MathHelper.sqrt(Math.max(0.1F, xRatio * xRatio + zRatio * zRatio));
		entityIn.motionX /= 2.0;
		entityIn.motionZ /= 2.0;
		entityIn.motionX -= xRatio / (double)f * (double)strength;
		entityIn.motionZ -= zRatio / (double)f * (double)strength;
		//Protection from non-finite XZ
		if(!Double.isFinite(entityIn.motionX)) entityIn.motionX = 0;
		if(!Double.isFinite(entityIn.motionZ)) entityIn.motionZ = 0;
		if(entityIn.onGround) {
			entityIn.motionY /= 2.0;
			entityIn.motionY += strength;
			if(entityIn.motionY > 0.4) {
				entityIn.motionY = 0.4;
			}
		}
		//Protection from non-finite Y
		if(!Double.isFinite(entityIn.motionY)) entityIn.motionY = 0;
		entityIn.velocityChanged = true;
	}
	
	public static boolean canBlockDamageSource(DamageSource damageSourceIn, EntityLivingBase entity) {
		if(!damageSourceIn.isUnblockable() && entity.isActiveItemStackBlocking()) {
			Vec3d vec3d = damageSourceIn.getDamageLocation();
			if(vec3d != null) {
				Vec3d vec3d1 = entity.getLook(1.0F);
				Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(entity.posX, entity.posY, entity.posZ)).normalize();
				vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);
				return vec3d2.dotProduct(vec3d1) < 0.0D;
			}
		}
		return false;
	}

	/**
	 * For armors, get total sum of this enchantments levels on equipment
	 */
	public static int getTotalArmorEnchantmentLevel(Enchantment enchantment, EntityLivingBase user) {
		List<ItemStack> list = enchantment.getEntityEquipment(user);
		if(list.isEmpty()) return 0;
		int enchantLevelSum = 0;
		for(ItemStack itemstack : list) {
			if(itemstack.isEmpty()) continue;
			enchantLevelSum += EnchantmentHelper.getEnchantmentLevel(enchantment, itemstack);
		}
		return enchantLevelSum;
	}
	
	public static float getDamageAfterMagicAbsorb(float damage, float enchantModifiers) {
		float f = MathHelper.clamp(enchantModifiers * 1.5F, 0.0F, 60.0F);
		return damage * (1.0F - f / 80.0F);
	}
	
	/**
	 * Returns damage modifier based on day/night
	 */
	public static float modifyDamageForTime(EntityLivingBase attacker, boolean mustBeDaytime, ItemStack stack, Enchantment enchantment) {
		if(stack.isEmpty()) return 0;
		int level = EnchantmentHelper.getEnchantmentLevel(enchantment, stack);
		if(level == 0) return 0;
		boolean isCorrectDayTime = attacker.world.isDaytime() == mustBeDaytime;
		if(!isCorrectDayTime) return 0;
		float damage = level * 0.5F + 1.5F;
		if(!EnchantUtil.canEntitySeeSky(attacker)) {
			damage -= level * 0.25F + 1.0F;
		}
		return damage;
	}

	public static boolean isNonBlackListedEnchant(ResourceLocation chosenEnchant) {
		String enchantName = chosenEnchant.toString();
		if(!enchantName.contains(":")) enchantName = "somanyenchantments:"+enchantName;
		boolean isInList = Arrays.asList(ModConfig.miscellaneous.blacklistedEnchants).contains(enchantName);
		return isInList == ModConfig.miscellaneous.blacklistedEnchantsIsWhitelist;
	}
}
package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class EnchantmentAdvancedKnockback extends EnchantmentBase {
	
	public EnchantmentAdvancedKnockback(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setName("AdvancedKnockback");
		this.setRegistryName("AdvancedKnockback");
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedKnockback;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedKnockback;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + 10 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedKnockback;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(target instanceof EntityLivingBase) {
			int levelknockBack = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedKnockback, stack);
			int modKnockback = 1;
			double Y = levelknockBack * 0.075D;
			target.motionY += Y;
			int Thelevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedKnockback, stack);
			int Knockback = 1;
			Knockback += Thelevel * 4;
			double XMot = target.motionX;
			double ZMot = target.motionZ;
			XMot += -MathHelper.sin(user.rotationYaw * (float)Math.PI / 180.0F) * (float)Knockback * 0.125F;
			ZMot += MathHelper.cos(user.rotationYaw * (float)Math.PI / 180.0F) * (float)Knockback * 0.125F;
			target.motionX = XMot / 1.1D;
			target.motionZ = ZMot / 1.1D;
			//System.out.println(target.motionX);
			//System.out.println(target.motionZ);
		}
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != Enchantments.KNOCKBACK;
	}
	
	//TODO
	public boolean isValidPlayer(Entity entity) {
		if(entity instanceof EntityPlayer) {
			if(((EntityPlayer)entity).getHeldItemMainhand() != null) {
				return level(((EntityPlayer)entity).getHeldItemMainhand()) > 0;
			}
		}
		return false;
	}
	
	//TODO
	public boolean isValidMob(Entity entity) {
		if(entity instanceof EntityMob || entity instanceof EntityAnimal) {
			if(((EntityMob)entity).getHeldItemMainhand() != null) {
				if(level(((EntityMob)entity).getHeldItemMainhand()) > 0) {
					return true;
				}
			}
			if(((EntityAnimal)entity).getHeldItemMainhand() != null) {
				return level(((EntityAnimal)entity).getHeldItemMainhand()) > 0;
			}
		}
		return false;
	}
	
	//TODO
	public int level(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedKnockback, stack);
	}
}
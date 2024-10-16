package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.Random;

public class EnchantmentMeltdown extends EnchantmentBase {
	
	public EnchantmentMeltdown(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.meltdown;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.meltdown;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 20 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.meltdown;
	}
	
	@Override
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
		Random random = user.getRNG();
		ItemStack itemstack = EnchantmentHelper.getEnchantedItem(EnchantmentRegistry.meltdown, user);
		if(Math.random() < 0.05f + 0.05f * level) {
			if(attacker != null) {
				attacker.getEntityWorld().newExplosion(user, user.posX, user.posY, user.posZ, 1.90f + 0.30f * level, true, false);
			}
			if(!itemstack.isEmpty()) {
				damageArmor(itemstack, 10 + random.nextInt(15 * level + 1), user);
			}
		}
		else if(!itemstack.isEmpty()) {
			damageArmor(itemstack, 1 + random.nextInt(3 * level), user);
		}
	}
	
	private void damageArmor(ItemStack stack, int amount, EntityLivingBase entity) {
		int slot = -1;
		int x = 0;
		for(ItemStack i : entity.getArmorInventoryList()) {
			if(i == stack) {
				slot = x;
				break;
			}
			x++;
		}
		if(slot == -1 || !(stack.getItem() instanceof net.minecraftforge.common.ISpecialArmor)) {
			if(entity instanceof EntityPlayerMP) stack.attemptDamageItem(amount, new Random(), (EntityPlayerMP)entity);
			else stack.damageItem(amount, entity);
			return;
		}
		net.minecraftforge.common.ISpecialArmor armor = (net.minecraftforge.common.ISpecialArmor)stack.getItem();
		armor.damageArmor(entity, stack, DamageSource.causeExplosionDamage(entity), amount, slot);
	}
}
	  
	  

package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.Interfaces.IPotionDebuffer;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentHorsDeCombat extends EnchantmentBase implements IPotionDebuffer {
	
	public EnchantmentHorsDeCombat(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.horsDeCombat;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.horsDeCombat;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 10 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.horsDeCombat;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity victims, ItemStack stack, int level) {
		if(!isEnabled()) return;
		if(!(victims instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)victims;
		if(Math.random() * 100 < 10) {
			if(level == 1 || level == 2) {
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + (level * 10), level - 1));
				victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 + (level * 10), level * 2));
			}
			if(level >= 3) {
				victim.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 20 + (level * 10), level - 1));
				victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 + (level * 10), level - 1));
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + (level * 10), level - 1));
				victim.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20 + (level * 10), level - 3));
			}
		}
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return !(fTest instanceof IPotionDebuffer) && super.canApplyTogether(fTest);
	}
}
package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.Interfaces.IPotionDebuffer;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDisorientatingBlade extends EnchantmentBase implements IPotionDebuffer {
	
	public EnchantmentDisorientatingBlade(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.disorientatingBlade;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.disorientatingBlade;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 10 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.disorientatingBlade;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity entiti, ItemStack stack, int level) {
		if(!(entiti instanceof EntityLivingBase)) return;
		EntityLivingBase entity = (EntityLivingBase)entiti;
		//TODO this RNG check may also be meant for levels 3 and above, even though it originally only surrounded 1 and 2
		if(entity.getRNG().nextInt(100) < 10) {
			if(level == 1 || level == 2) {
				entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + (level * 10), level - 1));
				entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 80 + (level * 10), 0));
			}
		}
		if(level >= 3) {
			entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + (level * 10), level - 1));
			entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 + (level * 10), level - 1));
			entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 25 + (level * 7), 0));
		}
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IPotionDebuffer);
	}
	
	//TODO
	@SubscribeEvent
	public void criticalWhenDisoriented(CriticalHitEvent e) {
		if(!(e.getTarget() instanceof EntityLivingBase)) return;
		EntityLivingBase eb = (EntityLivingBase)e.getTarget();
		if(eb.isPotionActive(MobEffects.SLOWNESS) && eb.isPotionActive(MobEffects.NAUSEA)) {
			//if(EnchantmentsUtility.isLevelMax(e.getEntityPlayer().getHeldItemMainhand(), EnchantmentRegistry.Disorientation))
			if(EnchantmentHelper.getEnchantmentLevel(this, e.getEntityPlayer().getHeldItemMainhand()) >= 4) {
				e.setDamageModifier(e.getDamageModifier() + 0.25f);
			}
		}
	}
}
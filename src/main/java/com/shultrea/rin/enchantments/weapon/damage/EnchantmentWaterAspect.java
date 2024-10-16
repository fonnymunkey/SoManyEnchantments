package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentWaterAspect extends EnchantmentBase implements IEnchantmentDamage {
	
	public EnchantmentWaterAspect(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.waterAspect;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.waterAspect;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 5 + 10 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.waterAspect;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != EnchantmentRegistry.bluntness && !(fTest instanceof EnchantmentDamage) && !(fTest instanceof IEnchantmentDamage) && fTest != Enchantments.FIRE_ASPECT;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.waterAspect, dmgSource);
		if(enchantmentLevel <= 0) return;
		Entity target = fEvent.getEntity();
		if(target instanceof EntityEnderman ||
				target instanceof EntityBlaze ||
				target instanceof EntityMagmaCube)
		{
			float Damage = fEvent.getAmount();
			UtilityAccessor.damageTarget(fEvent.getEntityLiving(), SoManyEnchantments.PhysicalDamage, 2.5f * enchantmentLevel);
			fEvent.setAmount(Damage);
		}
		if(fEvent.getEntity().handleWaterMovement() || fEvent.getEntity().isInWater() || fEvent.getEntity().isWet()) {
			float Damager = fEvent.getAmount();
			UtilityAccessor.damageTarget(fEvent.getEntityLiving(), SoManyEnchantments.PhysicalDamage, 0.75f * enchantmentLevel);
			fEvent.setAmount(Damager);
			//  attacker.getEntityWorld().playSound(attacker.posX, attacker.posY, attacker.posZ, SMEsounds.wateraspecthit, SoundCategory.PLAYERS, 2f, 0f, true);
			//   attacker.getEntityWorld().playSound(null, attacker.posX, attacker.posY, attacker.posZ, SMEsounds.wateraspecthit, SoundCategory.PLAYERS, 0.4f, 1f);
		}
		if(fEvent.getEntity().isBurning()) {
			if(enchantmentLevel <= 5) {
				float Damagest = fEvent.getAmount();
				fEvent.setAmount(Damagest - ((Damagest / (5)) * (5 - enchantmentLevel)));
			}
			else {
				fEvent.setAmount(fEvent.getAmount());
			}
			if(fEvent.getEntity().world.rand.nextInt(50) < 10 + (enchantmentLevel * 5))
				fEvent.getEntityLiving().extinguish();
		}
		if(attacker.isWet() || attacker.isInWater() || attacker.handleWaterMovement()) {
			float Damet = fEvent.getAmount();
			UtilityAccessor.damageTarget(fEvent.getEntityLiving(), SoManyEnchantments.PhysicalDamage, 0.75f * enchantmentLevel);
			fEvent.setAmount(Damet);
			//  attacker.getEntityWorld().playSound(null, attacker.posX, attacker.posY, attacker.posZ, SMEsounds.wateraspecthit, SoundCategory.PLAYERS, 0.4f, 1f);
		}
	}
}
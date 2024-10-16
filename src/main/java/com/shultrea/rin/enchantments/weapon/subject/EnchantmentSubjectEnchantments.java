package com.shultrea.rin.enchantments.weapon.subject;

import com.shultrea.rin.Enum.EnumList;
import com.shultrea.rin.Interfaces.ISubjectEnchantment;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentSubjectEnchantments extends EnchantmentBase implements ISubjectEnchantment {
	
	private static final String[] DAMAGE_NAMES = new String[]{
			"Mathematics", "Science", "History", "Physics", "English", "PE"};
	/**
	 * Holds the base factor of enchantability needed to be able to use the enchant.
	 */
	private static final int[] MIN_COST = new int[]{8, 9, 7, 11, 5, 6};
	private static final int[] LEVEL_COST = new int[]{13, 14, 10, 15, 9, 10};
	private static final int[] LEVEL_COST_SPAN = new int[]{25, 28, 23, 30, 21, 20};
	/**
	 * Defines the type of damage of the enchantment, 0 = Math, 1 = Science, 2 = History, 3 = Physics, 4 = English, 5 =
	 * PE
	 */
	public final int damageType;
	
	public EnchantmentSubjectEnchantments(String name, Rarity rarity, EnumEnchantmentType type, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
		this.damageType = damageTypeIn;
		this.type = type;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case 0:
				return ModConfig.enabled.subjectMathematics;
			case 1:
				return ModConfig.enabled.subjectScience;
			case 2:
				return ModConfig.enabled.subjectHistory;
			case 3:
				return false;
			case 4:
				return ModConfig.enabled.subjectEnglish;
			case 5:
				return ModConfig.enabled.subjectPE;
			default:
				return false;
		}
	}
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case 0:
				return ModConfig.level.subjectMathematics;
			case 1:
				return ModConfig.level.subjectScience;
			case 2:
				return ModConfig.level.subjectHistory;
			case 3:
				return 4;
			case 4:
				return ModConfig.level.subjectEnglish;
			case 5:
				return ModConfig.level.subjectPE;
			default:
				return 4;
		}
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return MIN_COST[this.damageType] + (enchantmentLevel - 1) * LEVEL_COST[this.damageType];
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + LEVEL_COST_SPAN[this.damageType];
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemAxe || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case 0:
				return ModConfig.treasure.subjectMathematics;
			case 1:
				return ModConfig.treasure.subjectScience;
			case 2:
				return ModConfig.treasure.subjectHistory;
			case 3:
				return true;
			case 4:
				return ModConfig.treasure.subjectEnglish;
			case 5:
				return ModConfig.treasure.subjectPE;
			default:
				return true;
		}
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(target instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase)target;
			if(this.damageType == 1 && ModConfig.enabled.subjectScience) {
				if(user.getRNG().nextDouble() * 100D < 20) {
					user.getEntityWorld().newExplosion(user, target.posX, target.posY - 1.5D, target.posZ, 1.1f + (level * 0.4f), false, false);
				}
			}
			if(this.damageType == 3) {
				if(user.getRNG().nextDouble() * 180D < 15D) {
					user.getEntityWorld().newExplosion(user, target.posX, target.posY - 1.5D, target.posZ, 1.0f + (level * 0.65f), true, false);
					user.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 60, 0, true, true));
				}
			}
			if(this.damageType == 5 && ModConfig.enabled.subjectPE) {
				if(user.getRNG().nextDouble() * 100D < 8.5D) {
					if(level == 1 || level == 2) {
						user.addPotionEffect(new PotionEffect(MobEffects.HASTE, 150 + (level * 30), level - 1));
						user.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 + (level * 5), level - 1));
					}
					if(level == 3 || level == 4) {
						user.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10 + (level * 5), level - 4));
						user.addPotionEffect(new PotionEffect(MobEffects.HASTE, 150 + (level * 30), level - 2));
						user.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 + (level * 5), level - 2));
						user.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 + (level * 5), level - 3));
					}
					if(level >= 5) {
						user.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 10 + (level * 5), level - 1));
						user.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20 + (level * 5), level - 2));
						user.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10 + (level * 5), level - 4));
						user.addPotionEffect(new PotionEffect(MobEffects.HASTE, 150 + (level * 5), level - 2));
						user.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 + (level * 5), level - 2));
						user.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 + (level * 5), level - 3));
					}
				}
			}
		}
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		/** if (this.damageType == 0)
		 {
		 return 1.0F + (float)Math.max(0, level - 1) * 0.5F;
		 }
		 else if (this.damageType == 1 && creatureType == EnumCreatureAttribute.UNDEAD)
		 {
		 return (float)level * 2.5F;
		 }
		 else
		 {
		 return this.damageType == 2 && creatureType == EnumCreatureAttribute.ARTHROPOD ? (float)level * 2.5F : 0.0F;
		 }
		 */
		if(!isEnabled()) return 0.0f;
		if(damageType == 5) return 0.75f + level * 0.25f;
		return 0.80f + level * 0.30f;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentSubjectEnchantments);
	}
	
	@Override
	public String getName() {
		return "enchantment." + DAMAGE_NAMES[this.damageType];
	}
}
package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.Interfaces.IPotionDebuffer;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentFreezing extends EnchantmentBase implements IPotionDebuffer {
	
	public EnchantmentFreezing(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.freezing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.freezing;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 24 + 13 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.freezing;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IPotionDebuffer);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntityDamaged(LivingDamageEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = attacker.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.freezing, stack);
		if(level <= 0) return;
		EntityLivingBase victim = fEvent.getEntityLiving();
		int ice = 0;
		int numb = 0;
		//Capping at 3 (previously was 7) for Roguelike Dungeons compatibility
		int numbCap = 3;
		int iceCap = 7;
		if(victim.isPotionActive(MobEffects.SLOWNESS)&victim.isPotionActive(MobEffects.MINING_FATIGUE)) {
			PotionEffect pot1 = victim.getActivePotionEffect(MobEffects.SLOWNESS);
			PotionEffect pot2 = victim.getActivePotionEffect(MobEffects.MINING_FATIGUE);
			ice = pot1.getAmplifier() + 1;
			numb = pot2.getAmplifier() + 1;
			if(ice > iceCap) ice = iceCap;
			if(numb > numbCap) numb = numbCap;
			if(victim instanceof EntityPlayer) {
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30 * level + 40, ice));
				victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 30 * level + 40, numb));
			}
			else if(victim instanceof EntityLivingBase) {
				ice += 1;
				numb += 1;
				if(ice > iceCap) ice = iceCap;
				if(numb > numbCap) numb = numbCap;
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30 * level + 40, ice));
				victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 30 * level + 40, numb));
			}
		}
		else if(victim instanceof EntityPlayer) {
			victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * level + 40, 0));
			victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 * level + 40, 0));
		}
		else if(victim instanceof EntityLivingBase) {
			victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * level + 40, 1));
			victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 * level + 40, 1));
		}
		if(ice >= iceCap && numb >= numbCap && victim.getEntityWorld().getGameRules().getBoolean("mobGriefing")) {
			float chance = (float)Math.random() * 100f;
			if(chance < (level * 10)) {
				victim.extinguish();
				stack.damageItem(6 - level, attacker);
				float damage = EnchantmentsUtility.CalculateDamageIgnoreSwipe(fEvent.getAmount(), 2.0f, 0.65f, 1.0f, attacker, EnchantmentRegistry.freezing);
				if(!(attacker instanceof EntityPlayer)) {
					EnchantmentsUtility.ImprovedKnockBack(attacker, 0.25f, attacker.posX - victim.posX, attacker.posZ - victim.posZ);
				}
				victim.getEntityWorld().playSound(null, victim.posX, victim.posY, victim.posZ, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.MASTER, 0.8f, -1f);
				BlockPos pos = new BlockPos(victim.posX, victim.posY, victim.posZ);
				BlockPos pos1 = new BlockPos(victim.posX, victim.posY + 1, victim.posZ);
				BlockPos pos2 = new BlockPos(victim.posX, victim.posY + 2, victim.posZ);
				BlockPos pos3 = new BlockPos(victim.posX + 1, victim.posY, victim.posZ);
				BlockPos pos4 = new BlockPos(victim.posX, victim.posY, victim.posZ + 1);
				BlockPos pos5 = new BlockPos(victim.posX - 1, victim.posY, victim.posZ);
				BlockPos pos6 = new BlockPos(victim.posX, victim.posY, victim.posZ - 1);
				if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
					victim.getEntityWorld().setBlockState(pos, Blocks.FROSTED_ICE.getDefaultState());
					victim.getEntityWorld().scheduleUpdate(pos, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
				}
				if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos1, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
					victim.getEntityWorld().setBlockState(pos1, Blocks.FROSTED_ICE.getDefaultState());
					victim.getEntityWorld().scheduleUpdate(pos1, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
				}
				if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos2, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
					victim.getEntityWorld().setBlockState(pos2, Blocks.FROSTED_ICE.getDefaultState());
					victim.getEntityWorld().scheduleUpdate(pos2, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
				}
				if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos3, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
					victim.getEntityWorld().setBlockState(pos3, Blocks.FROSTED_ICE.getDefaultState());
					victim.getEntityWorld().scheduleUpdate(pos3, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
				}
				if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos4, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
					victim.getEntityWorld().setBlockState(pos4, Blocks.FROSTED_ICE.getDefaultState());
					victim.getEntityWorld().scheduleUpdate(pos4, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
				}
				if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos5, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
					victim.getEntityWorld().setBlockState(pos5, Blocks.FROSTED_ICE.getDefaultState());
					victim.getEntityWorld().scheduleUpdate(pos5, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
				}
				if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos6, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
					victim.getEntityWorld().setBlockState(pos6, Blocks.FROSTED_ICE.getDefaultState());
					victim.getEntityWorld().scheduleUpdate(pos6, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
				}
				if(level >= 3) {
					damage += EnchantmentsUtility.CalculateDamageIgnoreSwipe(fEvent.getAmount(), 0.0f, 0.25f, 1.10f, attacker, EnchantmentRegistry.freezing);
					fEvent.setAmount(damage);
					BlockPos pos7 = new BlockPos(victim.posX - 1, victim.posY, victim.posZ - 1);
					BlockPos pos8 = new BlockPos(victim.posX + 1, victim.posY, victim.posZ + 1);
					BlockPos pos9 = new BlockPos(victim.posX - 1, victim.posY + 1, victim.posZ);
					BlockPos pos10 = new BlockPos(victim.posX + 1, victim.posY + 1, victim.posZ);
					BlockPos pos11 = new BlockPos(victim.posX, victim.posY + 1, victim.posZ - 1);
					BlockPos pos12 = new BlockPos(victim.posX, victim.posY + 1, victim.posZ + 1);
					BlockPos pos13 = new BlockPos(victim.posX + 2, victim.posY, victim.posZ);
					BlockPos pos14 = new BlockPos(victim.posX - 2, victim.posY, victim.posZ);
					BlockPos pos15 = new BlockPos(victim.posX, victim.posY, victim.posZ - 2);
					BlockPos pos16 = new BlockPos(victim.posX, victim.posY, victim.posZ + 2);
					BlockPos pos17 = new BlockPos(victim.posX + 1, victim.posY, victim.posZ - 1);
					BlockPos pos18 = new BlockPos(victim.posX - 1, victim.posY, victim.posZ + 1);
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos7, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos7, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos7, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos8, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos8, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos8, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos9, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos9, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos9, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos10, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos10, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos10, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos11, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos11, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos11, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos12, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos12, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos12, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos13, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos13, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos13, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos14, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos14, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos14, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos15, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos15, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos15, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos16, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos16, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos16, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos17, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos17, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos17, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
					if(victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos18, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos18, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos18, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
				}
				else fEvent.setAmount(damage);
			}
		}
	}
}
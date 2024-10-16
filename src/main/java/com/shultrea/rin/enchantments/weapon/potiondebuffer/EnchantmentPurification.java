package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.Interfaces.IPotionDebuffer;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;

public class EnchantmentPurification extends EnchantmentBase implements IPotionDebuffer {
	
	public EnchantmentPurification(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.purification;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.purification;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 18 + 12 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.purification;
	}
	
	@Override
	public boolean canApply(ItemStack fTest) {
		return super.canApply(fTest);
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !fTest.isCurse();
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(!(target instanceof EntityLivingBase)) return;
		if(user.world.isRemote) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.purification, stack);
		if(lvl <= 0) return;
		if(victim.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD || victim.isEntityUndead()) {
			victim.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, lvl * 20, lvl >= 5 ? 0 : 1));
			victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, lvl * 30));
		}
		if(victim.getRNG().nextInt(20) < lvl) {
			if(victim.isDead) return;
			repeat(victim);
		}
	}
	
	//TODO
	public boolean repeat(EntityLivingBase eBase) {
		if(eBase instanceof EntityZombieVillager) {
			eBase.setSilent(true);
			eBase.setDead();
			EntityVillager villager = new EntityVillager(eBase.world);
			villager.copyLocationAndAnglesFrom(eBase);
			villager.setProfession((((EntityZombieVillager)eBase).getForgeProfession()));
			villager.setLookingForHome();
			villager.finalizeMobSpawn(eBase.world.getDifficultyForLocation(new BlockPos(eBase)), null, false);
			if(villager.isChild()) villager.setGrowingAge(-24000);
			villager.setNoAI(villager.isAIDisabled());
			if(villager.hasCustomName()) {
				villager.setCustomNameTag(villager.getCustomNameTag());
				villager.setAlwaysRenderNameTag(villager.getAlwaysRenderNameTag());
			}
			villager.world.spawnEntity(villager);
			villager.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
			villager.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1));
			villager.world.playEvent(null, 1027, new BlockPos((int)villager.posX, (int)villager.posY, (int)villager.posZ), 0);
			villager.hurtResistantTime = 15;
			return true;
		}
		else if(eBase instanceof EntityPigZombie) {
			eBase.setSilent(true);
			eBase.setDead();
			//Mostly copied from #EntityPigZombie
			EntityPig pig = new EntityPig(eBase.world);
			pig.copyLocationAndAnglesFrom(eBase);
			if(pig.isChild()) pig.setGrowingAge(-24000);
			pig.world.removeEntity(eBase);
			pig.setNoAI(pig.isAIDisabled());
			if(pig.hasCustomName()) {
				pig.setCustomNameTag(pig.getCustomNameTag());
				pig.setAlwaysRenderNameTag(pig.getAlwaysRenderNameTag());
			}
			pig.world.spawnEntity(pig);
			pig.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
			pig.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1));
			pig.world.playEvent(null, 1027, new BlockPos((int)pig.posX, (int)pig.posY, (int)pig.posZ), 0);
			pig.hurtResistantTime = 15;
			return true;
		}
		else if(eBase instanceof EntityMagmaCube) {
			eBase.setSilent(true);
			eBase.setDead();
			//Mostly copied from #EntityPigZombie
			EntitySlime slime = new EntitySlime(eBase.world);
			slime.copyLocationAndAnglesFrom(eBase);
			NBTTagCompound nbt1 = eBase.serializeNBT();
			NBTTagCompound nbt2 = slime.serializeNBT();
			nbt2.setByte("Size", nbt1.getByte("Size"));
			slime.readEntityFromNBT(nbt2);
			eBase.setDead();
			slime.setNoAI(slime.isAIDisabled());
			if(slime.hasCustomName()) {
				slime.setCustomNameTag(slime.getCustomNameTag());
				slime.setAlwaysRenderNameTag(slime.getAlwaysRenderNameTag());
			}
			slime.world.spawnEntity(slime);
			slime.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1));
			slime.world.playEvent(null, 1027, new BlockPos((int)slime.posX, (int)slime.posY, (int)slime.posZ), 0);
			slime.hurtResistantTime = 15;
			return true;
		}
		return false;
	}
}
package com.shultrea.rin.enchantments.weapon.damagemultiplier;

import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCriticalStrike extends EnchantmentBase implements IDamageMultiplier {
	
	public EnchantmentCriticalStrike(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.criticalStrike;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.criticalStrike;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.criticalStrike;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IDamageMultiplier);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onCritical(CriticalHitEvent e) {
		if(e.getEntityPlayer() == null) return;
		EntityPlayer player = e.getEntityPlayer();
		if(player.getHeldItemMainhand().isEmpty()) return;
		ItemStack stack = player.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.criticalStrike, stack);
		if(level <= 0) return;
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		int counter = stack.getTagCompound().getInteger("failedCritCount");
		int maxReduction = level >= 4 ? 200 : 0;
		if(player.getRNG().nextInt(1000 - maxReduction) >= 32 * (counter + 1)) {
			stack.getTagCompound().setInteger("failedCritCount", counter + 1);
			return;
		}
		else stack.getTagCompound().setInteger("failedCritCount", 0);
		boolean bool = e.getTarget() instanceof EntityLivingBase;
		float crit = 1.2f + level * 0.2f + EnchantmentsUtility.RANDOM.nextFloat() * 0.5f;
		if(bool && !e.isVanillaCritical())
			if(stack.getItem() instanceof ItemSword && player.onGround && !player.isSprinting() && player.fallDistance <= 0) {
				float f = (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
				float f1;
				f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((EntityLivingBase)e.getTarget()).getCreatureAttribute());
				float f2 = player.getCooledAttackStrength(0.5F);
				f = f * (0.2F + f2 * f2 * 0.8F);
				f1 = f1 * f2;
				f = f + f1;
				f = f * crit;
				//System.out.println("Sweep Please");
				vanillaSweepAttack(player, f, (EntityLivingBase)e.getTarget());
			}
		e.setDamageModifier(crit);
		if(e.isVanillaCritical()) e.setDamageModifier(e.getDamageModifier() * 1.35f);
		else e.setResult(Result.ALLOW);
	}
	
	//TODO
	public void vanillaSweepAttack(EntityPlayer player, float damage, EntityLivingBase victim) {
		float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * damage;
		for(EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(EntityLivingBase.class, victim.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D))) {
			if(entitylivingbase != player && entitylivingbase != victim && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < 9.0D) {
				entitylivingbase.knockBack(player, 0.4F, MathHelper.sin(player.rotationYaw * 0.017453292F), -MathHelper.cos(player.rotationYaw * 0.017453292F));
				entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), f3);
			}
		}
		player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
		player.spawnSweepParticles();
	}
}
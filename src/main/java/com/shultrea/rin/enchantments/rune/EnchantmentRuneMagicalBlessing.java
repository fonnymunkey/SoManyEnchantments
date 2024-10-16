package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.Interfaces.IEnchantmentRune;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRuneMagicalBlessing extends EnchantmentBase implements IEnchantmentRune {
	
	public EnchantmentRuneMagicalBlessing(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runeMagicalBlessing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runeMagicalBlessing;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + 15 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runeMagicalBlessing;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return (0.75f * level);
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentRune);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		//System.out.println("Magical Blessing");
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		EntityLivingBase entity = (EntityLivingBase)fEvent.getEntity();
		if(entity == null) return;
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeMagicalBlessing, stack);
		if(level != 0) return;
		float LevelDamage = fEvent.getAmount();
		fEvent.setAmount(fEvent.getAmount() - (fEvent.getAmount() * (level * 0.25f)));
		LevelDamage = LevelDamage * (level * 0.25f);
		if(fEvent.getEntityLiving() instanceof EntityWitch) LevelDamage = LevelDamage * 0.15f;
		UtilityAccessor.damageTargetEvent(fEvent.getEntityLiving(), new EntityDamageSource("player", attacker).setMagicDamage(), LevelDamage);
		if(EnchantmentsUtility.RANDOM.nextBoolean()) {
			Potion negaPotion = EnchantmentsUtility.getNonInstantNegativePotion();
			if(negaPotion != null)
				fEvent.getEntityLiving().addPotionEffect(new PotionEffect(negaPotion, (MathHelper.clamp(EnchantmentsUtility.RANDOM.nextInt(6), 0, Integer.MAX_VALUE) + 1) * 20 * level, MathHelper.clamp(EnchantmentsUtility.RANDOM.nextInt(level) - 1, 0, Integer.MAX_VALUE)));
		}
		else {
			Potion negaIPotion = EnchantmentsUtility.getInstantNegativePotion();
			if(negaIPotion != null) {
				if(negaIPotion == MobEffects.INSTANT_DAMAGE && fEvent.getEntityLiving().isEntityUndead())
					negaIPotion = MobEffects.INSTANT_HEALTH;
				fEvent.getEntityLiving().addPotionEffect(new PotionEffect(negaIPotion, 1, MathHelper.clamp(EnchantmentsUtility.RANDOM.nextInt(level) - 1, 0, Integer.MAX_VALUE)));
			}
		}
	}
}
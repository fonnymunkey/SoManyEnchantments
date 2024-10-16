package com.shultrea.rin.enchantments.weapon.damagemultiplier;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.CompatUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentInstability extends EnchantmentCurse implements IDamageMultiplier {
	
	public EnchantmentInstability(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.instability;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.instability;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 30 + (par1 - 1) * 15;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.instability;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentDurability) && super.canApplyTogether(ench);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onLivingDamageEvent(LivingDamageEvent event) {
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getSource().getTrueSource() instanceof EntityPlayer && CompatUtil.isRLCombatLoaded()) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		if(!stack.isItemStackDamageable() || !stack.getItem().isDamageable()) return;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.instability, stack);
		if(level > 0 && event.getAmount() > 0) {
			float percentage = ((float)stack.getItemDamage() / (float)stack.getMaxDamage());
			percentage = 1.0F + percentage * (0.75F * (float)level);
			event.setAmount(event.getAmount() * percentage);
			stack.damageItem((int)(event.getAmount() * attacker.world.rand.nextFloat() / (float)(8 - level)) + 1, attacker);
		}
	}
	
	@Optional.Method(modid = "bettercombatmod")
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void modifyDamageEventPost(RLCombatModifyDamageEvent.Post event) {
		if(event.getEntityPlayer() == null || event.getTarget() == null || event.getStack().isEmpty() || !(event.getTarget() instanceof EntityLivingBase)) return;
		
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getStack();
		if(!stack.isItemStackDamageable() || !stack.getItem().isDamageable()) return;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.instability, stack);
		if(level > 0) {
			float percentage = ((float)stack.getItemDamage() / (float)stack.getMaxDamage()) * (0.75F * (float)level);
			float damage = (event.getBaseDamage() + event.getDamageModifier()) * percentage;
			if(damage > 0) {
				event.setDamageModifier(event.getDamageModifier() + damage);
				stack.damageItem((int)((event.getBaseDamage() + event.getDamageModifier()) * player.world.rand.nextFloat() / (float)(8 - level)) + 1, player);
			}
		}
	}
}
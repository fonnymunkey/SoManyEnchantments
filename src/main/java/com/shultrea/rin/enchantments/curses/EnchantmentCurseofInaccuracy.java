package com.shultrea.rin.enchantments.curses;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.CompatUtil;
import com.shultrea.rin.enchantments.weapon.EnchantmentTrueStrike;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment arrow inaccuracy handled in com.shultrea.rin.mixin.vanilla.ItemBowMixin
 */
public class EnchantmentCurseofInaccuracy extends EnchantmentCurse {
	
	public EnchantmentCurseofInaccuracy(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfInaccuracy;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfInaccuracy;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int ench) {
		return 15 + (ench - 1) * 15;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int ench) {
		return this.getMinEnchantability(ench) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfInaccuracy;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentTrueStrike) && super.canApplyTogether(ench);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingAttackEvent(LivingAttackEvent event) {
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getSource().getTrueSource() instanceof EntityPlayer && CompatUtil.isRLCombatLoaded()) return;
		EntityLivingBase entity = (EntityLivingBase)event.getSource().getTrueSource();
		if(entity == null) return;
		int level = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.curseOfInaccuracy, entity);
		if(level > 0 && entity.getRNG().nextFloat() < ((float)level * 0.20F)) {
			event.setCanceled(true);
		}
	}
	
	@Optional.Method(modid = "bettercombatmod")
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void modifyDamageEventPre(RLCombatModifyDamageEvent.Pre event) {
		if(event.getEntityPlayer() == null || event.getTarget() == null || event.getStack().isEmpty() || !(event.getTarget() instanceof EntityLivingBase)) return;
		
		EntityPlayer player = event.getEntityPlayer();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.curseOfInaccuracy, event.getStack());
		if(level > 0) {
			if(player.getRNG().nextFloat() < (float)level * 0.20F) {
				event.setDamageModifier(Math.min(-1 - event.getBaseDamage(), -1));
			}
		}
	}
}
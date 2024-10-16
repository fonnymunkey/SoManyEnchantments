package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.Interfaces.IEnchantmentRune;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment arrow pierce level setting handled in;
 * com.shultrea.rin.mixin.vanilla.ItemBowMixin
 * com.shultrea.rin.mixin.vanilla.EntityArrowMixin
 */
public class EnchantmentRuneArrowPiercing extends EnchantmentBase implements IEnchantmentRune {
	
	public EnchantmentRuneArrowPiercing(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runeArrowPiercing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runeArrowPiercing;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + (par1 - 1) * 15;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runeArrowPiercing;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	//TODO piercing compat with spartan weaponry piercing?
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onLivingHurtEvent(LivingHurtEvent event) {
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(!(event.getSource().getImmediateSource() instanceof EntityArrow)) return;
		EntityArrow arrow = (EntityArrow)event.getSource().getImmediateSource();
		IArrowProperties cap = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		if(cap == null) return;
		int pierceLevel = cap.getArmorPiercingLevel();
		if(pierceLevel > 0) {
			EntityLivingBase shooter = (EntityLivingBase)event.getSource().getTrueSource();
			float damage = event.getAmount() * 0.25F * pierceLevel;
			event.setAmount(event.getAmount() - damage);
			UtilityAccessor.damageTarget(event.getEntityLiving(), new EntityDamageSourceIndirect("arrow", arrow, shooter).setDamageBypassesArmor(), damage);
		}
	}
}
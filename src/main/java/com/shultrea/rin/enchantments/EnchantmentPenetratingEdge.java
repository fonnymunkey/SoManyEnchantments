package com.shultrea.rin.enchantments;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Interfaces.IEnchantmentRune;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_020;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentPenetratingEdge extends EnchantmentBase implements IEnchantmentDamage {
	
	public EnchantmentPenetratingEdge(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.penetratingEdge;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.penetratingEdge;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 14 + 12 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.penetratingEdge;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentRune);
	}
	//@Override
	//public boolean canApply(ItemStack fTest)
	//{
	//System.out.println(fTest.getItem() instanceof ItemAxe);
	//	return fTest.getItem() instanceof ItemAxe ? true : false;
	//}
	//@Override
	//public boolean canApplyAtEnchantingTable(ItemStack stack)
	//{
	//	return stack.getItem() instanceof ItemAxe;
	//}
	//@Override
	//public boolean canApply(ItemStack fTest)
	//{
	//	return fTest.getItem() instanceof ItemAxe;
	//}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack weapon = attacker.getHeldItemMainhand();
		if(weapon == null) return;
		if(!(fEvent.getEntity() instanceof EntityLivingBase)) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		float ArmorDetector;
		EntityLivingBase entity = (EntityLivingBase)fEvent.getEntity();
		float armor = fEvent.getEntityLiving().getTotalArmorValue();
		if(!(armor >= 3)) return;
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_020.PenetratingEdge, weapon);
		if(EnchantmentHelper.getEnchantmentLevel(Smc_020.PenetratingEdge, weapon) != 0) {
			fEvent.setAmount(fEvent.getAmount() + ((armor / 3 + 0.5f) + (level * 0.16f)));
		}
	}
}
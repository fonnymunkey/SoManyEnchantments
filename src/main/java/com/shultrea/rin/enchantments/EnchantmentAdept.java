package com.shultrea.rin.enchantments;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdept extends EnchantmentBase {
	
	public EnchantmentAdept(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.adept;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.adept;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 26 + (par1 - 1) * 12;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 40;
	}
	
	//TODO
	@Override
	public boolean canApply(ItemStack fTest) {
		return fTest.getItem() instanceof ItemAxe || super.canApply(fTest);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.adept;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != Enchantments.LOOTING && fTest != EnchantmentRegistry.advancedLooting;
	}
	
	//TODO
	@SubscribeEvent
	public void onDeath(LivingExperienceDropEvent fEvent) {
		EntityPlayer player = fEvent.getAttackingPlayer();
		if(player == null) return;
		ItemStack stack = player.getHeldItemMainhand();
		if(stack == null || stack.isEmpty()) {
			stack = player.getHeldItemOffhand();
			if(stack == null || stack.isEmpty()) return;
		}
		int lvl = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(lvl <= 0) return;
		//Don't add experience to drops that otherwise would have no experience
		if(fEvent.getOriginalExperience() <= 0) return;
		if(fEvent.getEntityLiving() != null && !fEvent.getEntityLiving().isNonBoss())
			fEvent.setDroppedExperience(2 + lvl + (int)(fEvent.getOriginalExperience() * (0.75f + 0.5f * lvl)));
		else fEvent.setDroppedExperience(2 + lvl + (int)(fEvent.getOriginalExperience() * (1.05f + 0.15f * lvl)));
		//System.out.println(fEvent.getDroppedExperience() + " - Altered EXP");
		//stack.damageItem(1, player);
		//System.out.println(fEvent.getOriginalExperience() + " - Orig EXP");
		//System.out.println(fEvent.getDroppedExperience() + " - Orig EXP");
	}
}
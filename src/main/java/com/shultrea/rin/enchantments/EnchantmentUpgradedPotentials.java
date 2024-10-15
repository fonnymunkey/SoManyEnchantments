package com.shultrea.rin.enchantments;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class EnchantmentUpgradedPotentials extends EnchantmentBase {
	
	public EnchantmentUpgradedPotentials(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.upgradedPotentials;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.upgradedPotentials;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 35;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 45;
	}
	
	@Override
	public boolean canApply(ItemStack fTest) {
		return false;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.upgradedPotentials;
	}
	
	@SubscribeEvent
	public void onAnvilAttach(AnvilUpdateEvent event) {
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		if(left.isEmpty() || right.isEmpty()) {
			//This isn't actually necessary? Doing it anyway
			return;
		}
		if(right.getItem() == Items.ENCHANTED_BOOK) {
			//Right is an enchanted book
			Map<Enchantment,Integer> enchantments = EnchantmentHelper.getEnchantments(right);
			if(enchantments.containsKey(this) && enchantments.get(this) >= 1) {
				//Right has Upgraded Potentials book
				if(left.isStackable()) {
					//Left is stackable, right slot has upgraded potentials, set output to empty and return
					event.setOutput(ItemStack.EMPTY);
					return;
				}
				//Left is currently not stackable
				if(EnchantmentHelper.getEnchantments(left).size() == 0) {
					//No enchantments on the left, exiting
					event.setOutput(ItemStack.EMPTY);
					return;
				}
				if(EnchantmentHelper.getEnchantmentLevel(this, left) >= 1) {
					//Left already has upgraded potentials, set output to empty and return
					event.setOutput(ItemStack.EMPTY);
					return;
				}
				//Left is currently not stackable and does not have upgraded potentials
				//Repair cost tweaking
				int cost = left.getRepairCost();
				cost = Math.max(0, (cost / 4) - 20);
				//Build a copy of the left with upgraded potentials and the modified repair cost
				ItemStack output = left.copy();
				output.setRepairCost(cost);
				output.addEnchantment(this, 1);
				//Set the result in the anvil
				event.setOutput(output);
				event.setCost(10);
			}
		}
	}
}
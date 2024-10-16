package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentUnsheathing extends EnchantmentBase {
	
	public EnchantmentUnsheathing(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.unsheathing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.unsheathing;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 30 + (par1 - 1) * 15;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.unsheathing;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAttack(LivingAttackEvent e) {
		if(!e.isCanceled() && e.getAmount() > 0) {
			if(e.getSource().getTrueSource() != null && e.getEntityLiving() != null && e.getEntityLiving() instanceof EntityPlayer) {
				EntityPlayer victim = (EntityPlayer)e.getEntityLiving();
				InventoryPlayer inv = victim.inventory;
				for(int x = 0; x < InventoryPlayer.getHotbarSize(); x++) {
					ItemStack stack = inv.getStackInSlot(x);
					if(EnchantmentHelper.getEnchantmentLevel(this, stack) <= 0) continue;
					if(EnchantmentsUtility.RANDOM.nextInt(2) <= -1 + EnchantmentHelper.getEnchantmentLevel(this, stack))
						continue;
					if(inv.getStackInSlot(inv.currentItem) == stack) continue;
					ItemStack s = inv.getStackInSlot(inv.currentItem);
					inv.setInventorySlotContents(inv.currentItem, stack);
					inv.setInventorySlotContents(x, s);
					stack.setAnimationsToGo(0);
				}
			}
		}
	}
}
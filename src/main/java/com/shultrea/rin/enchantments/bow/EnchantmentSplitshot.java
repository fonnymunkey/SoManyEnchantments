package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class EnchantmentSplitshot extends EnchantmentBase {
	
	public EnchantmentSplitshot(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.splitShot;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.splitShot;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 10 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.splitShot;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void onEvent(ArrowLooseEvent event) {
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.splitShot, event.getBow()) > 0) {
			int l = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.splitShot, event.getBow());
			for(int x = 0; x < l; x++) {
				EntityPlayer entityplayer = event.getEntityPlayer();
				float Yaw = new Random().nextFloat() * (float)Math.random() * 110f - 35;
				float Pitch = (float)Math.random() * entityplayer.getRNG().nextFloat() * 3.125f - 1.3525f;
				World worldIn = event.getWorld();
				ItemStack stack = event.getBow();
				boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
				ItemStack ammo = ItemStack.EMPTY;
				ammo = findAmmo(entityplayer);
				if(!ammo.isEmpty() || flag) {
					if(ammo.isEmpty()) {
						ammo = new ItemStack(Items.ARROW);
					}
					float f = ItemBow.getArrowVelocity(event.getCharge());
					if((double)f >= 0.1D) {
						boolean flag1 = entityplayer.capabilities.isCreativeMode || (ammo.getItem() instanceof ItemArrow && ((ItemArrow)ammo.getItem()).isInfinite(ammo, stack, entityplayer));
						if(!worldIn.isRemote) {
							ItemArrow itemarrow = (ItemArrow)(ammo.getItem() instanceof ItemArrow ? ammo.getItem() :
															  Items.ARROW);
							EntityArrow entityarrow = itemarrow.createArrow(worldIn, ammo, entityplayer);
							entityarrow.shoot(entityplayer, entityplayer.rotationPitch + Pitch, entityplayer.rotationYaw + Yaw, 0.0F, f * 3.0F, 1.0F + entityplayer.getRNG().nextFloat() * 2.0f);
							if(f == 1.0F) {
								entityarrow.setIsCritical(true);
							}
							int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
							if(j > 0) {
								entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
							}
							int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
							if(k > 0) {
								entityarrow.setKnockbackStrength(k);
							}
							if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
								entityarrow.setFire(100);
							}
							stack.damageItem(1, entityplayer);
							//if (flag1 || entityplayer.capabilities.isCreativeMode && (ammo.getItem() == Items.SPECTRAL_ARROW || ammo.getItem() == Items.TIPPED_ARROW))
							{
								entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
							}
							worldIn.spawnEntity(entityarrow);
						}
					}
				}
			}
		}
	}
	
	private ItemStack findAmmo(EntityPlayer player) {
		if(player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemArrow) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemArrow) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else {
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);
				if(itemstack.getItem() instanceof ItemArrow) {
					return itemstack;
				}
			}
			return ItemStack.EMPTY;
		}
	}
}
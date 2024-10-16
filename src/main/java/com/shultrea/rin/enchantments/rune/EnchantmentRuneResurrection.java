package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Prop_Sector.IPlayerProperties;
import com.shultrea.rin.Prop_Sector.PlayerPropertiesProvider;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.*;

public class EnchantmentRuneResurrection extends EnchantmentBase {
	
	private final Map<UUID,InventoryPlayer> INVENTORY_MAP = new HashMap<>();
	
	public EnchantmentRuneResurrection(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runeResurrection;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runeResurrection;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 30 * par1;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runeResurrection;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onWielderDeath(LivingDeathEvent e) {
		int level = 0;
		//System.out.println("IM DEAD SAD SO SAD");
		if(!(e.getEntityLiving() instanceof EntityPlayer)) return;
		//System.out.println("IM DEAD BUT IM PLAYER I LIVE");
		EntityPlayer diedPlayer = (EntityPlayer)e.getEntityLiving();
		if(diedPlayer.world.isRemote) return;
		List<ItemStack> list = diedPlayer.inventory.mainInventory;
		for(int x = 0; x < list.size(); x++) {
			level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeResurrection, list.get(x));
			//System.out.println("OH IM GETTINT THE LEVEL. IT IS ->" + " " + level);
			if(level > 0) {
				if(diedPlayer.world.getWorldInfo().isHardcoreModeEnabled() || diedPlayer.posY < 0) {
					diedPlayer.setHealth(diedPlayer.getMaxHealth() * 0.5f * level);
					e.setCanceled(true);
					list.get(x).shrink(1);
					diedPlayer.hurtResistantTime = 30;
					return;
				}
    			
    			/*
    			BlockPos bp = diedPlayer.getBedLocation();
    			if(bp != null){
    			BlockPos bedSpawnPos = diedPlayer.getBedSpawnLocation(diedPlayer.world, bp, false);
    			ItemStack stack = list.get(x);
    			NBTTagCompound nbt = stack.serializeNBT();
    			nbt.setInteger("blockPosX", bedSpawnPos.getX());
    			nbt.setInteger("blockPosY", bedSpawnPos.getY());
    			nbt.setInteger("blockPosZ", bedSpawnPos.getZ());
    			nbt.setInteger("dimension", diedPlayer.dimension);
    			stack.deserializeNBT(nbt);
    			}
    			
    			else {
    				BlockPos origSpawnPos = diedPlayer.world.getSpawnPoint();
    				ItemStack stack = list.get(x);
        			NBTTagCompound nbt = stack.serializeNBT();
        			nbt.setInteger("blockPosX", origSpawnPos.getX());
        			nbt.setInteger("blockPosY", origSpawnPos.getY());
        			nbt.setInteger("blockPosZ", origSpawnPos.getZ());
        			nbt.setInteger("dimension", 0);
        			stack.deserializeNBT(nbt);
    			}
    			*/
				diedPlayer.setSpawnChunk(diedPlayer.getPosition(), true, diedPlayer.dimension);
				diedPlayer.setSpawnPoint(diedPlayer.getPosition(), true);
				BlockPos bp = diedPlayer.getBedLocation();
				BlockPos bedSpawnPos = EntityPlayer.getBedSpawnLocation(diedPlayer.world, bp, false);
				if(bedSpawnPos != null && bp != null) {
					ItemStack stack = list.get(x);
					NBTTagCompound nbt = stack.serializeNBT();
					nbt.setInteger("blockPosX", bedSpawnPos.getX());
					nbt.setInteger("blockPosY", bedSpawnPos.getY());
					nbt.setInteger("blockPosZ", bedSpawnPos.getZ());
					nbt.setInteger("dimension", diedPlayer.dimension);
					stack.deserializeNBT(nbt);
				}
				else {
					BlockPos originalWorldSpawn = diedPlayer.world.getSpawnPoint();
					ItemStack stack = list.get(x);
					NBTTagCompound nbt = stack.serializeNBT();
					nbt.setInteger("blockPosX", originalWorldSpawn.getX());
					nbt.setInteger("blockPosY", originalWorldSpawn.getY());
					nbt.setInteger("blockPosZ", originalWorldSpawn.getZ());
					stack.deserializeNBT(nbt);
				}
				//ObfuscationReflectionHelper.setPrivateValue(LivingDeathEvent.class, e, new EntityDamageSource("revival", e.getSource().getTrueSource()), "source");
				//System.out.println(e.getSource().damageType);
				InventoryPlayer inv = new InventoryPlayer(null);
				if(diedPlayer.hasCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null)) {
					IPlayerProperties ar = diedPlayer.getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);
					ar.setResurrecting(true);
					if(!diedPlayer.world.getGameRules().getBoolean("keepInventory")) {
						keepAllArmor(diedPlayer, inv);
						keepOffHand(diedPlayer, inv);
						for(int i = 0; i < diedPlayer.inventory.mainInventory.size(); i++) {
							inv.mainInventory.set(i, diedPlayer.inventory.mainInventory.get(i));
							diedPlayer.inventory.mainInventory.set(i, ItemStack.EMPTY);
						}
						INVENTORY_MAP.put(diedPlayer.getUniqueID(), inv);
						//for(int i = 0; i < INVENTORY_MAP.get(diedPlayer.getUniqueID()).mainInventory.size(); i++)
						//System.out.println(INVENTORY_MAP.get(diedPlayer.getUniqueID()).mainInventory.get(i));
						//System.out.println(INVENTORY_MAP.get(diedPlayer.getUniqueID()).currentItem + " -> Slot: " + "The Inventory Map is Success");
						//list.get(x).shrink(1);
					}
				}
				else {
				}
				break;
			}
			if(x + 1 >= list.size()) {
				x = 0;
				list = diedPlayer.inventory.offHandInventory;
				continue;
			}
		}
	}
	
	@SubscribeEvent
	public void onItemDrop(PlayerDropsEvent e) {
		int level = 0;
		EntityPlayer player = e.getEntityPlayer();
		List<EntityItem> lista = e.getDrops();
		if(player.hasCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null)) {
			IPlayerProperties p = player.getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);
			if(p.isResurrecting()) {
				e.setCanceled(true);
			
			/*
			for(int x = 0; x < lista.size(); x++){
	    		EntityItem item = lista.get(x);
	    		ItemStack stack = item.getItem();
	    		boolean flag = player.inventory.addItemStackToInventory(stack);
	    		
	    		if(!flag){
	    			EntityItem entityItem = item;
	    		    entityItem.setOwner(player.getName());
	    		    entityItem.setNoPickupDelay();
	    		    entityItem.setEntityInvulnerable(true);
	    		}
	    	}
	    	*/
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerRessurect(Clone e) {
		if(!e.isWasDeath()) return;
		EntityPlayer player = e.getOriginal();
		if(player.hasCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null) && e.getEntityPlayer().hasCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null)) {
			IPlayerProperties p = player.getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);
			IPlayerProperties cap = e.getEntityPlayer().getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);
			if(p.isResurrecting()) {
				//e.getEntityPlayer().inventory = player.inventory;
				cap.setResurrecting(true);
				//p.setResurrecting(false);
			}
		}
	}
	
	@SubscribeEvent
	public void teleportLastDeathLocation(PlayerRespawnEvent e) {
		if(e.isEndConquered()) return;
		EntityPlayer player = e.player;
		regenerateItems(player);
		if(player.hasCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null)) {
			InventoryPlayer inv = null;
			List<ItemStack> list = new ArrayList();
			IPlayerProperties p = player.getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);
			inv = player.inventory;
			list = inv.mainInventory;
			boolean resurrect = p.isResurrecting();
			if(resurrect) p.setResurrecting(false);
			if(resurrect && inv != null) {
				if(!player.world.getGameRules().getBoolean("keepInventory")) {
					if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeResurrection, player.getHeldItemOffhand()) > 0)
						player.getHeldItemOffhand().shrink(1);
					else for(int x = 0; x < list.size(); x++) {
						int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeResurrection, list.get(x));
						if(level > 0) {
							player.addStat(StatList.DEATHS, -1);
							ItemStack stack = list.get(x);
							NBTTagCompound nbt = stack.serializeNBT();
							BlockPos pos = new BlockPos(nbt.getInteger("blockPosX"), nbt.getInteger("blockPosY"), nbt.getInteger("blockPosZ"));
							player.setSpawnChunk(pos, false, nbt.getInteger("dimension"));
							player.setSpawnPoint(pos, false);
							list.get(x).shrink(1);
							return;
						}
					}
				}
			}
		}
	}
	
	private InventoryPlayer regenerateItems(EntityPlayer player) {
		InventoryPlayer keepInventory = INVENTORY_MAP.remove(player.getUniqueID());
		if(keepInventory != null) {
			NonNullList<ItemStack> displaced = NonNullList.create();
			for(int i = 0; i < player.inventory.armorInventory.size(); i++) {
				ItemStack kept = keepInventory.armorInventory.get(i);
				if(!kept.isEmpty()) {
					ItemStack existing = player.inventory.armorInventory.set(i, kept);
					//System.out.println(existing + " - > Armor Inventory");
					if(!existing.isEmpty()) {
						displaced.add(existing);
					}
				}
			}
			for(int i = 0; i < player.inventory.offHandInventory.size(); i++) {
				ItemStack kept = keepInventory.offHandInventory.get(i);
				if(!kept.isEmpty()) {
					ItemStack existing = player.inventory.offHandInventory.set(i, kept);
					//System.out.println(existing + " - > Offhand Inventory");
					if(!existing.isEmpty()) {
						displaced.add(existing);
					}
				}
			}
			for(int i = 0; i < player.inventory.mainInventory.size(); i++) {
				ItemStack kept = keepInventory.mainInventory.get(i);
				if(!kept.isEmpty()) {
					ItemStack existing = player.inventory.mainInventory.set(i, kept);
					//System.out.println(existing + " - > Main Inventory");
					if(!existing.isEmpty()) {
						displaced.add(existing);
					}
				}
			}
			// try to give player any displaced items
			for(ItemStack extra : displaced) {
				ItemHandlerHelper.giveItemToPlayer(player, extra);
			}
		}
		return keepInventory;
	}
	
	private void keepAllArmor(EntityPlayer player, InventoryPlayer keepInventory) {
		for(int i = 0; i < player.inventory.armorInventory.size(); i++) {
			keepInventory.armorInventory.set(i, player.inventory.armorInventory.get(i).copy());
			player.inventory.armorInventory.set(i, ItemStack.EMPTY);
		}
	}
	
	private void keepOffHand(EntityPlayer player, InventoryPlayer keepInventory) {
		for(int i = 0; i < player.inventory.offHandInventory.size(); i++) {
			keepInventory.offHandInventory.set(i, player.inventory.offHandInventory.get(i).copy());
			player.inventory.offHandInventory.set(i, ItemStack.EMPTY);
		}
	}
}
    
	   
   

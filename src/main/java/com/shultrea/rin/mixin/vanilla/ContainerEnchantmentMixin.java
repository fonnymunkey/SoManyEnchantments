package com.shultrea.rin.mixin.vanilla;

import com.google.common.collect.Lists;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.config.ModConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

import static net.minecraftforge.common.ForgeHooks.getEnchantPower;

@Mixin(value = ContainerEnchantment.class, priority = 2000)
public abstract class ContainerEnchantmentMixin extends Container {
    @Shadow public IInventory tableInventory;
    @Final @Shadow private World world;
    @Final @Shadow private BlockPos position;
    @Shadow public int[] enchantLevels;
    @Shadow public int[] enchantClue;
    @Shadow public int[] worldClue;
    @Shadow @Final private Random rand;
    @Shadow public int xpSeed;

    @Unique EnchantmentData soManyEnchantments$currentEnch;
    @Unique EnchantmentData soManyEnchantments$upgradedEnch;
    @Unique EnchantmentData soManyEnchantments$cursedEnch;
    @Unique int soManyEnchantments$levelCost;
    @Unique int soManyEnchantments$upgradeSlot = 0;

    @Redirect(
            method = "<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at= @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ContainerEnchantment;addSlotToContainer(Lnet/minecraft/inventory/Slot;)Lnet/minecraft/inventory/Slot;")
    )
    Slot soManyEnchantments_init_addSlotToContainer(ContainerEnchantment instance, Slot slot){
        if(slot.getSlotIndex()==1 && slot.xPos==35 && slot.yPos==47) {
            Slot newSlot = new Slot(this.tableInventory, 1, 35, 47) {
                java.util.List<ItemStack> ores = net.minecraftforge.oredict.OreDictionary.getOres("gemLapis");

                public boolean isItemValid(ItemStack stack) {
                    for (ItemStack ore : ores)
                        if (net.minecraftforge.oredict.OreDictionary.itemMatches(ore, stack, false))
                            return true;
                    return soManyEnchantments$isUpgradeToken(stack);
                }
            };
            return addSlotToContainer(newSlot);
        }
        return addSlotToContainer(slot);
    }

    @Inject(
            method = "transferStackInSlot",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    void soManyEnchantments_transferStackInSlot_head(EntityPlayer playerIn, int index, CallbackInfoReturnable<ItemStack> cir) {
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            if (index != 0 && index != 1 && soManyEnchantments$isUpgradeToken(itemstack1)) {
                if (!this.mergeItemStack(itemstack1, 1, 2, true)) {
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            }
        }
    }

    @Inject(
            method = "onCraftMatrixChanged",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void soManyEnchantments_onCraftMatrixChanged_head(IInventory inventoryIn, CallbackInfo ci) {
        if (inventoryIn != this.tableInventory) return;
        if (this.world.isRemote) return;

        //Correct token?
        ItemStack itemstackToken = inventoryIn.getStackInSlot(1);
        if (itemstackToken.isEmpty()) return;
        if (!soManyEnchantments$isUpgradeToken(itemstackToken)) return;
        if (itemstackToken.getCount() < ModConfig.upgrade.upgradeTokenAmount) return;

        //Correct item?
        ItemStack itemstackTargetItem = inventoryIn.getStackInSlot(0);
        if (itemstackTargetItem.isEmpty()) return;
        boolean isBook = itemstackTargetItem.getItem() == Items.ENCHANTED_BOOK;
        Map<Enchantment, Integer> currentEnchants = EnchantmentHelper.getEnchantments(itemstackTargetItem);
        if (ModConfig.upgrade.onlyAllowOnBooks) {
            if (!isBook) return;
            if (currentEnchants.size() != 1) return;
        }
        if (currentEnchants.isEmpty()) return;

        //Enough bookshelves?
        float bookshelfPower = 0;
        for (int z = -1; z <= 1; ++z) {
            for (int x = -1; x <= 1; ++x) {
                if ((z != 0 || x != 0) && this.world.isAirBlock(this.position.add(x, 0, z)) && this.world.isAirBlock(this.position.add(x, 1, z))) {
                    bookshelfPower += getEnchantPower(world, position.add(x * 2, 0, z * 2));
                    bookshelfPower += getEnchantPower(world, position.add(x * 2, 1, z * 2));
                    if (x != 0 && z != 0) {
                        bookshelfPower += getEnchantPower(world, position.add(x * 2, 0, z));
                        bookshelfPower += getEnchantPower(world, position.add(x * 2, 1, z));
                        bookshelfPower += getEnchantPower(world, position.add(x, 0, z * 2));
                        bookshelfPower += getEnchantPower(world, position.add(x, 1, z * 2));
                    }
                }
            }
        }
        if (MathHelper.ceil(bookshelfPower) < ModConfig.upgrade.bookshelvesNeeded) return;

        //Get upgradeable enchants
        List<EnchantmentData> upgradeableEnchantments = Lists.<EnchantmentData>newArrayList();
        List<EnchantmentData> upgradedEnchantments = Lists.<EnchantmentData>newArrayList();
        for (Enchantment currEnch : currentEnchants.keySet()) {
            int enchLvl = currentEnchants.get(currEnch);

            Enchantment nextEnchantment = soManyEnchantments$getNextEnchInUpgradeOrder(currEnch);
            int newLvl = 0;

            if (ModConfig.upgrade.allowLevelIncrease && enchLvl < currEnch.getMaxLevel() && !currEnch.isCurse()) {
                nextEnchantment = currEnch;
                newLvl = enchLvl + 1;
            }
            if (nextEnchantment == null) continue;

            //If newLvl not already set by level increase
            if (newLvl == 0) {
                if (Objects.equals(ModConfig.upgrade.upgradedTierLevelMode, "MINLVL"))
                    newLvl = nextEnchantment.getMinLevel();
                if (Objects.equals(ModConfig.upgrade.upgradedTierLevelMode, "SUBTRACT"))
                    newLvl = MathHelper.clamp(enchLvl - ModConfig.upgrade.enchantLvlsReduced, nextEnchantment.getMinLevel(), nextEnchantment.getMaxLevel());
            }
            upgradeableEnchantments.add(new EnchantmentData(currEnch, enchLvl));
            upgradedEnchantments.add(new EnchantmentData(nextEnchantment, newLvl));
        }
        if (upgradeableEnchantments.isEmpty()) return;

        //Set RNG seed so the roll is always the same, no matter how often you input the same item
        this.rand.setSeed((long) this.xpSeed);

        //Which one of the upgradeable enchants to upgrade
        int upgradingIndex;
        switch (ModConfig.upgrade.selectionMode) {
            case "RANDOM":
                upgradingIndex = rand.nextInt(upgradeableEnchantments.size());
                break;
            case "LAST":
                upgradingIndex = upgradeableEnchantments.size() - 1;
                break;
            /* case "FIRST": */
            default:
                upgradingIndex = 0;
        }
        soManyEnchantments$currentEnch = upgradeableEnchantments.get(upgradingIndex);
        soManyEnchantments$upgradedEnch = upgradedEnchantments.get(upgradingIndex);

        //Get possible cursed enchant
        Enchantment curse = soManyEnchantments$getCurse(soManyEnchantments$currentEnch.enchantment);
        soManyEnchantments$cursedEnch = null;
        if (curse != null)
            soManyEnchantments$cursedEnch = new EnchantmentData(curse, soManyEnchantments$currentEnch.enchantmentLevel);

        //Get level cost
        soManyEnchantments$levelCost = 1;
        switch (ModConfig.upgrade.levelCostMode) {
            case "ANVIL":
                soManyEnchantments$levelCost = soManyEnchantments$getRarityMultiplier(soManyEnchantments$upgradedEnch.enchantment.getRarity(), isBook) * soManyEnchantments$upgradedEnch.enchantmentLevel;
                break;
            case "ENCHANTABILITY":
                soManyEnchantments$levelCost = soManyEnchantments$upgradedEnch.enchantment.getMinEnchantability(soManyEnchantments$upgradedEnch.enchantmentLevel);
                break;
            /*case "NONE":*/
            default:
                break;
        }
        soManyEnchantments$levelCost *= (int) ModConfig.upgrade.levelCostMultiplier;

        //Clue for GUI
        this.enchantLevels[soManyEnchantments$upgradeSlot] = soManyEnchantments$levelCost;
        this.enchantClue[soManyEnchantments$upgradeSlot] = -1;
        this.worldClue[soManyEnchantments$upgradeSlot] = -1;
        if (this.enchantLevels[soManyEnchantments$upgradeSlot] > 0) {
            this.enchantClue[soManyEnchantments$upgradeSlot] = Enchantment.getEnchantmentID(soManyEnchantments$upgradedEnch.enchantment);
            this.worldClue[soManyEnchantments$upgradeSlot] = soManyEnchantments$upgradedEnch.enchantmentLevel;
        }

        this.detectAndSendChanges();
        ci.cancel();
    }

    @Inject(
            method = "enchantItem",
            at = @At("HEAD"),
            cancellable = true
    )
    void soManyEnchantments_enchantItem_head(EntityPlayer playerIn, int id, CallbackInfoReturnable<Boolean> cir) {
        if (this.world.isRemote) return;

        ItemStack itemstackTargetItem = this.tableInventory.getStackInSlot(0);
        ItemStack itemstackToken = this.tableInventory.getStackInSlot(1);

        if (id != soManyEnchantments$upgradeSlot) return;

        //Correct token+amount?
        if ((itemstackToken.isEmpty() || itemstackToken.getCount() < ModConfig.upgrade.upgradeTokenAmount || !soManyEnchantments$isUpgradeToken(itemstackToken)) && !playerIn.capabilities.isCreativeMode)
            return;

        //Enough lvls?
        if (this.enchantLevels[soManyEnchantments$upgradeSlot] <= 0 || itemstackTargetItem.isEmpty() || (playerIn.experienceLevel < this.enchantLevels[soManyEnchantments$upgradeSlot] && !playerIn.capabilities.isCreativeMode))
            return;

        //Replace enchantment with upgraded or cursed version
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemstackTargetItem);
        boolean isCursed = rand.nextFloat() < ModConfig.upgrade.cursingChance;
        if (isCursed) {
            if (ModConfig.upgrade.cursesReplaceUpgrade)
                enchantments.remove(soManyEnchantments$currentEnch.enchantment);
            if (soManyEnchantments$cursedEnch != null)
                if(enchantments.containsKey(soManyEnchantments$cursedEnch.enchantment)) {
                    int newCurseLvl = Math.min(enchantments.get(soManyEnchantments$cursedEnch.enchantment) + 1, soManyEnchantments$cursedEnch.enchantment.getMaxLevel());
                    enchantments.put(soManyEnchantments$cursedEnch.enchantment, newCurseLvl);
                } else {
                    enchantments.put(soManyEnchantments$cursedEnch.enchantment, soManyEnchantments$cursedEnch.enchantmentLevel);
                }
        } else {
            enchantments.remove(soManyEnchantments$currentEnch.enchantment);
            enchantments.put(soManyEnchantments$upgradedEnch.enchantment, soManyEnchantments$upgradedEnch.enchantmentLevel);
        }

        //Clear Enchanted Book enchants bc setEnchantments is handled differently there
        if(itemstackTargetItem.getItem()==Items.ENCHANTED_BOOK && itemstackTargetItem.hasTagCompound()) {
            NBTTagCompound tags = itemstackTargetItem.getTagCompound();
            if (tags.hasKey("StoredEnchantments")) {
                tags.setTag("StoredEnchantments", new NBTTagList());
                itemstackTargetItem.setTagCompound(tags);
            }
        }

        //Set new enchants
        EnchantmentHelper.setEnchantments(enchantments, itemstackTargetItem);

        //Pay xp price and roll RNG seed
        playerIn.onEnchant(itemstackTargetItem, soManyEnchantments$levelCost);

        //Pay token price
        if (!playerIn.capabilities.isCreativeMode) {
            itemstackToken.shrink(ModConfig.upgrade.upgradeTokenAmount);

            if (itemstackToken.isEmpty()) {
                this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
            }
        }

        //Pay anvil repair price
        if (ModConfig.upgrade.increaseAnvilRepairCost) {
            int repairCost = itemstackTargetItem.getRepairCost();
            switch (ModConfig.upgrade.anvilRepairMode) {
                case "ADD":
                    repairCost = (int) (repairCost + ModConfig.upgrade.anvilRepairCostAmount);
                    break;
                case "MULT":
                    repairCost = (int) (repairCost * ModConfig.upgrade.anvilRepairCostAmount);
                    break;
                /* case "ANVIL": */
                default:
                    repairCost = 2 * repairCost + 1;
            }
            itemstackTargetItem.setRepairCost(repairCost);
        }

        this.tableInventory.markDirty();
        this.xpSeed = playerIn.getXPSeed();
        this.onCraftMatrixChanged(this.tableInventory);
        if(isCursed)
            this.world.playSound(null, this.position, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        else
            this.world.playSound(null, this.position, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        cir.setReturnValue(true);
    }

    @Unique
    private boolean soManyEnchantments$isUpgradeToken(ItemStack stack){
        return stack.getItem().getRegistryName().toString().equals(ModConfig.upgrade.upgradeToken);
    }

    @Unique
    private int soManyEnchantments$getRarityMultiplier(Enchantment.Rarity rarity, boolean isBook){
        switch(rarity){
            case COMMON: return 1;
            case UNCOMMON: return isBook ? 1 : 2;
            case RARE: return isBook ? 2 : 4;
            case VERY_RARE: return isBook ? 4 : 8;
            default: return 0;
        }
    }

    @Unique
    private Enchantment soManyEnchantments$getNextEnchInUpgradeOrder(Enchantment currEnch){
        String enchName = currEnch.getRegistryName().toString();
        if(enchName.contains("somanyenchantments:"))
            enchName = enchName.split(":")[1];
        for (String upgradeList : ModConfig.upgrade.enchantUpgradeOrder) {
            if (!upgradeList.contains(enchName)) continue;

            String[] order = upgradeList.split(" *, *"); //Removing optional whitespace
            int foundIndex = Arrays.asList(order).indexOf(enchName);
            //If we're already maxed, we don't need to upgrade further
            if (foundIndex >= order.length-1) continue;

            String nextEnchName = order[foundIndex+1];
            if(!nextEnchName.contains(":")) nextEnchName = "somanyenchantments:"+nextEnchName;
            Enchantment nextEnchantment = Enchantment.getEnchantmentByLocation(nextEnchName);
            if(nextEnchantment == null){
                SoManyEnchantments.LOGGER.info("Could not find upgraded enchantment {}", nextEnchName);
                return null;
            }
            return nextEnchantment;
        }
        return null;
    }

    @Unique
    private Enchantment soManyEnchantments$getCurse(Enchantment currEnch){
        String enchName = currEnch.getRegistryName().toString();
        if(enchName.contains("somanyenchantments:"))
            enchName = enchName.split(":")[1];
        for (String upgradeList : ModConfig.upgrade.enchantUpgradeCursing) {
            if (!upgradeList.contains(enchName)) continue;

            String[] order = upgradeList.split(" *, *"); //Removing optional whitespace
            int foundIndex = Arrays.asList(order).indexOf(enchName);
            //Do nothing if we're already at the last entry = curse
            if (foundIndex >= order.length-1) continue;

            String curseEnchName = order[order.length-1];
            if(curseEnchName.equals("none"))
                return null;
            else {
                if (!curseEnchName.contains(":")) curseEnchName = "somanyenchantments:" + curseEnchName;
                Enchantment curse = Enchantment.getEnchantmentByLocation(curseEnchName);
                if (curse == null) {
                    SoManyEnchantments.LOGGER.info("Could not find cursed enchantment {}", curseEnchName);
                    return null;
                }
                return curse;
            }

        }
        return null;
    }
}
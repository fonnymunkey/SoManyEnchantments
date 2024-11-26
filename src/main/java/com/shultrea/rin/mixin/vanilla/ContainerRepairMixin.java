package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.config.ModConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(value = ContainerRepair.class, priority = 2000)
public abstract class ContainerRepairMixin {
    @Shadow @Final private IInventory inputSlots;

    @Redirect(
            method = "updateRepairOutput",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setRepairCost(I)V")
    )
    void soManyEnchantments_updateRepairOutput_setRepairCost(ItemStack itemStack, int cost){
        //Default behavior
        itemStack.setRepairCost(cost);

        if(!ModConfig.miscellaneous.removeBookCombinationAnvilCost) return;

        ItemStack itemstackL = this.inputSlots.getStackInSlot(0);
        ItemStack itemstackR = this.inputSlots.getStackInSlot(1);
        //Both enchanted books
        if(itemstackL.getItem()!= Items.ENCHANTED_BOOK) return;
        if(itemstackR.getItem()!= Items.ENCHANTED_BOOK) return;

        //Only one enchant, same enchant, same level
        Map<Enchantment, Integer> enchsL = EnchantmentHelper.getEnchantments(itemstackL);
        Map<Enchantment, Integer> enchsR = EnchantmentHelper.getEnchantments(itemstackR);
        if(enchsL.size() != 1 || enchsR.size() != 1) return;
        if(!enchsL.keySet().toArray()[0].equals(enchsR.keySet().toArray()[0])) return;
        if(!enchsL.values().toArray()[0].equals(enchsR.values().toArray()[0])) return;

        //Repair cost action backwards, c*2+1 turns to (c-1)/2
        itemStack.setRepairCost((cost-1)/2);
    }
}
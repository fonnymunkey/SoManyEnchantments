package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.util.EnchantUtil;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(value = EnchantmentHelper.class, priority = 2000)
public abstract class EnchantmentHelperEnchantBlacklistMixin {

    @Redirect(
            method = "buildEnchantmentList",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantmentDatas(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;")
    )
    private static List<EnchantmentData> soManyEnchantments_vanillaEnchantmentHelper_buildEnchantmentList(int level, ItemStack itemStackIn, boolean allowTreasure) {
        List<EnchantmentData> list = EnchantmentHelper.getEnchantmentDatas(level, itemStackIn, allowTreasure);
        list.removeIf(enchantmentData -> !EnchantUtil.isNonBlackListedEnchant(enchantmentData.enchantment.getRegistryName()));
        return list;
    }
}
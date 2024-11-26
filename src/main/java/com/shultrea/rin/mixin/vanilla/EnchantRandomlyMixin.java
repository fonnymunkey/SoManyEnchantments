package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.util.EnchantUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.EnchantRandomly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = EnchantRandomly.class, priority = 2000)
public abstract class EnchantRandomlyMixin {

    @Final @Shadow
    private List<Enchantment> enchantments;

    @Inject(
            method = "<init>",
            at = @At(value = "TAIL")
    )
    private void soManyEnchantments_vanillaEnchantRandomly_init(LootCondition[] conditionsIn, List<Enchantment> enchantmentsIn, CallbackInfo ci) {
        this.enchantments.removeIf(e -> !EnchantUtil.isNonBlackListedEnchant(e.getRegistryName()));
    }

    @Redirect(
            method = "apply",
            at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z")
    )
    boolean soManyEnchantments_vanillaEnchantRandomly_apply(List<Enchantment> instance)
    {
        instance.removeIf(e -> !EnchantUtil.isNonBlackListedEnchant(e.getRegistryName()));
        return instance.isEmpty();
    }
}
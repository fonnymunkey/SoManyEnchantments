package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.util.EnchantUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(EntityVillager.ListEnchantedBookForEmeralds.class)
public abstract class EntityVillagerListEnchantedBookForEmeraldsMixin {

    @Unique
    private Random soManyEnchantments_random;

    @Inject(
            method = "addMerchantRecipe",
            at = @At(value = "HEAD")
    )
    public void soManyEnchantments_vanillaEntityVillagerListEnchantedBookForEmeralds_addMerchantRecipe_head(IMerchant merchant, MerchantRecipeList recipeList, Random random, CallbackInfo ci){
        this.soManyEnchantments_random = random;
    }

    @ModifyVariable(
            method = "addMerchantRecipe",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    public Enchantment soManyEnchantments_vanillaEntityVillagerListEnchantedBookForEmeralds_addMerchantRecipe_modify(Enchantment enchantment){
        ResourceLocation[] validEnchants = Enchantment.REGISTRY.getKeys()
                .stream().filter(EnchantUtil::isNonBlackListedEnchant)
                .toArray(ResourceLocation[]::new);

        if(validEnchants.length>0) {
            ResourceLocation chosenEnchant = validEnchants[soManyEnchantments_random.nextInt(validEnchants.length)];
            enchantment = Enchantment.REGISTRY.getObject(chosenEnchant);
        }
        return enchantment;
    }
}
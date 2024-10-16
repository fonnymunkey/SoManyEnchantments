package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;

public class EnchantmentSharperEdge extends EnchantmentBase implements IEnchantmentDamage {
	
	public EnchantmentSharperEdge(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.sharperEdge;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.sharperEdge;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 18 + 16 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.sharperEdge;
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(ModConfig.enabled.sharperEdge && target instanceof EntityLivingBase && user instanceof EntityPlayer) {
			EntityLivingBase victim = (EntityLivingBase)target;
			int x = victim.getTotalArmorValue();
			if(x > 20) x = 20;
			if(level >= 9) level = 9;
			if(!victim.isSilent()) {
				victim.setSilent(true);
				victim.hurtResistantTime = 0;
				victim.attackEntityFrom(new EntityDamageSource("player", user), (20 - x) / (10 - level));
				victim.setSilent(false);
			}
			else victim.attackEntityFrom(new EntityDamageSource("player", user), (20 - x) / (10 - level));
		}
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		if(ModConfig.enabled.sharperEdge) return (0.75f * level + 0.5f);
		return 0;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != EnchantmentRegistry.bluntness && !(fTest instanceof IEnchantmentDamage) && !(fTest instanceof EnchantmentDamage);
	}
}
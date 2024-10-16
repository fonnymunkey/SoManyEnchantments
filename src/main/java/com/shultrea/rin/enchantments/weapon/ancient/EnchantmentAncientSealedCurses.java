package com.shultrea.rin.enchantments.weapon.ancient;

import com.shultrea.rin.Interfaces.IAncientEnchantment;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentLister;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class EnchantmentAncientSealedCurses extends EnchantmentBase implements IAncientEnchantment {
	
	public EnchantmentAncientSealedCurses(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.ancientSealedCurses;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.ancientSealedCurses;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int ench) {
		return 240 * ench;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int ench) {
		return 720 * ench;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.ancientSealedCurses;
	}
	
	//TODO
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}
	
	//TODO
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	//TODO
	@Override
	public String getPrefix() {
		return TextFormatting.YELLOW.toString();
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stackIn, int level) {
		if(target instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase)target;
			Iterable<ItemStack> iter = entity.getEquipmentAndArmor();
			for(ItemStack stack : iter) {
				if(user.getRNG().nextInt(8) < 1) {
					List<Enchantment> list = EnchantmentLister.CURSE;
					int random = user.getRNG().nextInt(list.size());
					Enchantment ench = list.get(random);
					int randLevel = user.getRNG().nextInt(ench.getMaxLevel());
					if(!ench.canApply(stack)) continue;
					boolean isCompatible = true;
					NBTTagList nbt = stack.getEnchantmentTagList();
					for(int z = 0; z < nbt.tagCount(); z++) {
						NBTTagCompound tag = nbt.getCompoundTagAt(z);
						int enchId = tag.getShort("id");
						int currEnchLevel = tag.getShort("lvl");
						Enchantment enchantment = Enchantment.getEnchantmentByID(enchId);
						if(enchantment == null) continue;
						if(enchantment == this) continue;
						if(enchantment.isCompatibleWith(ench)) continue;
						else isCompatible = false;
						break;
					}
					if(EnchantmentHelper.getEnchantmentLevel(ench, stack) <= 0 && isCompatible)
						stack.addEnchantment(list.get(random), randLevel + 1);
				}
			}
		}
	}
}
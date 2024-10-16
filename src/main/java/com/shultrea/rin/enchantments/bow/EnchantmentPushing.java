package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EnchantmentPushing extends EnchantmentBase {
	
	public EnchantmentPushing(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.pushing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.pushing;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + 25 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.pushing;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return !(fTest instanceof EnchantmentStrafe) && super.canApplyTogether(fTest);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onEvent(LivingEntityUseItemEvent.Tick event) {
		EntityLivingBase entity = event.getEntityLiving();
		if(entity == null) return;
		ItemStack bow = event.getItem();
		if(bow.isEmpty()) return;
		int level = EnchantmentHelper.getEnchantmentLevel(this, bow);
		if(level <= 0) return;
		if(bow.getItem() instanceof ItemBow && (72000 - event.getDuration() <= 20 + level * 10)) {
			AxisAlignedBB axis = new AxisAlignedBB(entity.getPosition()).grow(4 + level * 2);
			repelEntitiesInAABBFromPoint(entity.world, axis, entity.posX, entity.posY, entity.posZ, level);
		}
	}
	
	public void repelEntitiesInAABBFromPoint(World world, AxisAlignedBB effectBounds, double x, double y, double z, int level) {
		List<Entity> list = world.getEntitiesWithinAABB(Entity.class, effectBounds);
		for(Entity ent : list) {
			if((ent instanceof EntityLiving) || (ent instanceof IProjectile)) {
				if(ent instanceof EntityArrow && ent.onGround) {
					continue;
				}
				Vec3d p = new Vec3d(x, y, z);
				Vec3d t = new Vec3d(ent.posX, ent.posY, ent.posZ);
				double distance = p.distanceTo(t) + 0.1D;
				distance = 10 / distance;
				Vec3d r = new Vec3d(t.x - p.x, t.y - p.y, t.z - p.z);
				ent.motionX += (r.x / ((3.75D - ((level - 1) * 1.5D)) / distance)) / (MathHelper.clamp(50 - level * 10, 1, Integer.MAX_VALUE));
				ent.motionY += (r.y / ((5D - ((level - 1) * 1.25D)) / distance)) / (MathHelper.clamp(50 - level * 10, 1, Integer.MAX_VALUE));
				ent.motionZ += (r.z / ((3.75D - ((level - 1) * 1.5D)) / distance)) / (MathHelper.clamp(50 - level * 10, 1, Integer.MAX_VALUE));
			}
		}
	}
}
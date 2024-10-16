package com.shultrea.rin.enchantments.rune;
/*
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.registry.Smc_040;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.ArrayList;

public class EnchantmentRuneStarFall extends Enchantment {
	//UNUSED
	
	public EnchantmentRuneStarFall() {
		super(Rarity.VERY_RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
		this.setName("rune_starfall");
		this.setRegistryName("rune_starfall");
	}
	
	@Override
	public int getMaxLevel() {
		return 2;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 10 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH, receiveCanceled = true)
	public void setDidStarFall(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow)event.getEntity();
			EntityLivingBase shooter = (EntityLivingBase)arrow.shootingEntity;
			if(shooter == null) return;
			ItemStack bow = shooter.getActiveItemStack();
			if(bow == ItemStack.EMPTY) {
				bow = shooter.getHeldItemOffhand();
				if(bow == ItemStack.EMPTY) {
					return;
				}
			}
			if(arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) {
				IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
				{
					if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.rune_starfall, bow) > 0) {
						int l = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.rune_starfall, shooter);
						properties.setLevel(l);
						properties.setDidStarFall(false);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void whenitsHigh(WorldTickEvent fEvent) {
		if(fEvent.world.getTotalWorldTime() % 30 != 0) return;
		ArrayList<Entity> entities = (ArrayList<Entity>)fEvent.world.loadedEntityList;
		int size = entities.size();
		//System.out.println(itr);
		EntityArrow arrow = null;
		for(int a = 0; a < size; a++) {
			Entity f = entities.get(a);
			if(f instanceof Entity) {
				if(f instanceof EntityArrow) {
					if(f instanceof EntityTippedArrow) {
						arrow = (EntityTippedArrow)f;
					}
					else arrow = (EntityArrow)f;
					//	System.out.println("CYCLE");
				}
			}
			else continue;
		}
		//	Entity existingArrow = fEvent.world.enti
		//EntityArrow arrow = null;
		if(arrow == null) return;
		if((arrow.motionX == 0 && arrow.motionZ == 0) && arrow.motionY == 0) return;
		//if(fEvent.getEntity() instanceof EntityArrow){
		//EntityArrow arrow = (EntityArrow) fEvent.getEntity();
		Entity shoote = arrow.shootingEntity;
		if(shoote == null) return;
		//	System.out.println("EARLY DDDDDDDDDDDDDDDDDDDDDDDDDDDD");
		if(!(shoote instanceof EntityLivingBase)) return;
		EntityLivingBase shooter = (EntityLivingBase)shoote;
		if(arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) {
			//	 System.out.println("tooooooooooooooooooMIDDLEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
			IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
			try {
				// System.out.println("MIDDLEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
				boolean flag = properties.didStarFall();
				//System.out.println(flag);
				//System.out.println(properties.getLevel());
				if(properties.getIsStarFallMade() == true) return;
				if(flag == false) {
					if(properties.getLevel() > 0) {
						//   System.out.println("ITS STARTINGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
						if(arrow.posY > 60 + arrow.shootingEntity.posY) {
							for(int i = 0; i < properties.getLevel() * 700; i++) {
								if(properties.didStarFall() == false) {
									properties.setDidStarFall(true);
								}
								// System.out.println("MULTIPLYINGGGGGGGGGGGGGGGGGGGGGGGGGGG");
								ItemArrow itemarrow = new ItemArrow();
								EntityArrow arrows = itemarrow.createArrow(shooter.getEntityWorld(), new ItemStack(Items.ARROW), shooter);
								arrow.setDead();
								arrows.shoot(shooter, -90f, (float)(shooter.getRNG().nextFloat() * 75f - Math.random() * 75f), 0.0F, properties.getLevel() * 7f * (float)Math.random(), 1.0F + shooter.getRNG().nextFloat() * 2.0f);
								//EntityArrow arrows = arrow;
								// arrows.setUniqueId(UUID.randomUUID());
								arrows.posX = arrow.posX + ((Math.random() * -12 + shooter.getRNG().nextDouble() * 12));
								arrows.posY = arrow.posY + 60 + (Math.random() * 2 + shooter.getRNG().nextDouble() * 16);
								arrows.posZ = arrow.posZ + ((Math.random() * -12 + shooter.getRNG().nextDouble() * 12));
								arrows.setIsCritical(true);
								IArrowProperties arrowsp = arrows.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
								arrowsp.setIsStarFallMade(true);
								arrowsp.setDidStarFall(true);
								//arrowsp.setLevel(0);
								shooter.getEntityWorld().spawnEntity(arrows);
							}
						}
					}
				}
			}
			catch(Exception e) {
			}
		}
	}
}
//}

 */

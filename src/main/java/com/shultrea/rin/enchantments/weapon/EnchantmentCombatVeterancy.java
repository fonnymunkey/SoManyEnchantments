package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCombatVeterancy extends EnchantmentBase {
	
	public EnchantmentCombatVeterancy(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.combatVeterancy;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.combatVeterancy;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 25;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.combatVeterancy;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onRegen(LivingHealEvent e) {
		EntityLivingBase en = e.getEntityLiving();
		if(en == null) return;
		if(en.getHeldItemMainhand().isEmpty()) return;
		int level = EnchantmentHelper.getEnchantmentLevel(this, en.getHeldItemMainhand());
		if(level > 0) e.setAmount(e.getAmount() * (1.05f + 0.15f * level));
	}
	/**
	 @SubscribeEvent(priority = EventPriority.NORMAL)
	 public void onPlayerTick(LivingUpdateEvent event){
	 
	 EntityLivingBase entity = event.getEntityLiving();
	 
	 ItemStack weapon = entity.getHeldItemMainhand();
	 
	 int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.CombatRegeneration, weapon);
	 
	 if(!(entity.getHeldItemMainhand()!= null))
	 return;
	 
	 
	 
	 if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.CombatRegeneration, weapon) > 0)
	 {
	 
	 
	 int levelRegen = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.CombatRegeneration, weapon);
	 int levelRegen1 = levelRegen * 10;
	 
	 if(levelRegen1 <= 80)
	 levelRegen1 = 80;
	 
	 float absorpPotion = 0.0f;
	 PotionEffect potion = null;
	 
	 if(entity.isPotionActive(MobEffects.ABSORPTION)){
	 potion = entity.getActivePotionEffect(MobEffects.ABSORPTION);
	 
	 
	 absorpPotion = potion.getAmplifier() + 1;
	 }
	 
	 float absorptionMax = (20.0f + ((levelRegen - 3) * 10)) + (absorpPotion * 4);
	 
	 
	 
	 if(entity.getEntityWorld().getTotalWorldTime() % (90 - (levelRegen1)) == 0){
	 
	 if(!(entity.getHealth() <= entity.getMaxHealth()))
	 return;
	 
	 entity.heal(0.15f * levelRegen + 0.05f);
	 if(entity instanceof EntityPlayer){
	 ((EntityPlayer) entity).getFoodStats().addStats(((EntityPlayer) entity).getFoodStats().getFoodLevel(), -0.05f);
	 }
		
	 }
	 if(levelRegen >= 3){
	 
	 
	 if(entity.getAbsorptionAmount() >= absorptionMax)
	 return;
	 
	 if(entity.getEntityWorld().getTotalWorldTime() % 40 == 0){
	 
	 if((entity.getHealth() == entity.getMaxHealth())){
	 
	 //System.out.print("Requirements Achieved!");
	 
	 
	 if((entity.getAbsorptionAmount() < 20 + ((levelRegen - 3) * 10))){
	 
	 //System.out.print("Absorption Increased");
	 
	 entity.setAbsorptionAmount(entity.getAbsorptionAmount() + 0.20f + (absorptionMax * 0.075f));
	 }
	 
	 if(entity.getAbsorptionAmount() > absorptionMax)
	 entity.setAbsorptionAmount(absorptionMax);
	 }
	 }
	 }
	 }
	 }
	 */
}
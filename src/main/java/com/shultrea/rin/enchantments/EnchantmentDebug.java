package com.shultrea.rin.enchantments;
/*
import java.util.ArrayList;
import java.util.ListIterator;

import com.shultrea.rin.registry.Smc_030;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;




public class EnchantmentDebug extends Enchantment
{
	//UNUSED
	
	public EnchantmentDebug()
	{
		super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setName("Debug");
		this.setRegistryName("Debug");
	}
	
	@Override
	public int getMaxLevel()
    {
        return 10;
    }
	
	@Override
    public int getMinEnchantability(int par1)
    {
		return 5 + 10 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1)
    {
        return super.getMinEnchantability(par1) + 30;
    }
    
    @Override
    public boolean canApplyTogether(Enchantment fTest)
    {    			
    	return super.canApplyTogether(fTest);
    }
    
    @Override
    public boolean canApply(ItemStack fTest)
    {
    	return super.canApply(fTest);
    }
    */
/**
 * float Damage = 0; float PRDCounter = 0; float NBDamage = 0; String Chance = null; for (EntityLivingBase
 * entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
 * fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D))) { if (entitylivingbase != attacker &&
 * entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) &&
 * attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D) { entitylivingbase.knockBack(attacker, 0.4F,
 * (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw *
 * 0.017453292F))); entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount()); } } for
 * (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
 * fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D))) { if (entitylivingbase != attacker &&
 * entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) &&
 * attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D) { entitylivingbase.knockBack(attacker, 0.4F,
 * (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw *
 * 0.017453292F))); entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount()); } } for
 * (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
 * fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D))) { if (entitylivingbase != attacker &&
 * entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) &&
 * attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D) { entitylivingbase.knockBack(attacker, 0.4F,
 * (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw *
 * 0.017453292F))); entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount()); } } for
 * (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
 * fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D))) { if (entitylivingbase != attacker &&
 * entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) &&
 * attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D) { entitylivingbase.knockBack(attacker, 0.4F,
 * (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw *
 * 0.017453292F))); entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount()); } } for
 * (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
 * fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D))) { if (entitylivingbase != attacker &&
 * entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) &&
 * attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D) { entitylivingbase.knockBack(attacker, 0.4F,
 * (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw *
 * 0.017453292F))); entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount()); } } for
 * (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
 * fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D))) { if (entitylivingbase != attacker &&
 * entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) &&
 * attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D) { entitylivingbase.knockBack(attacker, 0.4F,
 * (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw *
 * 0.017453292F))); entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount()); } } for
 * (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
 * fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D))) { if (entitylivingbase != attacker &&
 * entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) &&
 * attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D) { entitylivingbase.knockBack(attacker, 0.4F,
 * (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw *
 * 0.017453292F))); entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount()); } } for
 * (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
 * fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D))) { if (entitylivingbase != attacker &&
 * entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) &&
 * attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D) { entitylivingbase.knockBack(attacker, 0.4F,
 * (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw *
 * 0.017453292F))); entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount()); } } for
 * (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class,
 * fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D))) { if (entitylivingbase != attacker &&
 * entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) &&
 * attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D) { entitylivingbase.knockBack(attacker, 0.4F,
 * (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw *
 * 0.017453292F))); entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount()); } }
 */
	/*
    @SubscribeEvent
    public void HandleEnchant(LivingAttackEvent fEvent)
    {
    	if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob")
			return;
    	
    	if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase))
    		return;
    	
    	EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
    	if(attacker == null)
    		return;
    	
    	ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null)
			return;
		
		int levelCleave = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.Debug, dmgSource);
		
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.Debug, dmgSource) <= 0)
			return;
		
		*/
/**  for (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class, fEvent.getEntityLiving().getEntityBoundingBox().expand(2.0D, 1.00D, 2.0D)))
 {
 if (entitylivingbase != attacker && entitylivingbase != fEvent.getEntityLiving() && !attacker.isOnSameTeam(entitylivingbase) && attacker.getDistanceSqToEntity(entitylivingbase) < 9.0D)
 {
 entitylivingbase.knockBack(attacker, 0.4F, (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw * 0.017453292F)));
 entitylivingbase.attackEntityFrom(somanyenchantments.PhysicalDamage, fEvent.getAmount());
 }
 }
 */
		/*
		// We have a cleaving level, let's figure out our damage value.
		float splashDamage = fEvent.getAmount() * (levelCleave * 0.25f);
		
		// Next, find our entities to hit.
		AxisAlignedBB boundBox = new AxisAlignedBB(attacker.posX-5 - levelCleave, attacker.posY-5 - levelCleave, attacker.posZ-5 - levelCleave, attacker.posX+5 + levelCleave, attacker.posY+5 + levelCleave, attacker.posZ+5 + levelCleave);
		@SuppressWarnings("unchecked")
		ArrayList<Entity> targetEntities = new ArrayList<Entity>(attacker.getEntityWorld().getEntitiesWithinAABBExcludingEntity(fEvent.getEntity(), boundBox));
		
		// Let's remove all the entries that aren't within range of our attacker
		ListIterator<Entity> itr = targetEntities.listIterator();
		while(itr.hasNext())
		{
			Entity target = itr.next();
			
			if(!(target instanceof EntityLivingBase))
				continue;
			
			if(target == attacker)
				continue;
			
			if(target == fEvent.getEntityLiving())
				continue;
			
			if(target.getDistance(attacker) > 3.5F)
				continue;
			
			Vec3d attackerCheck = EnchantmentsUtility.Cleave(target.posX-attacker.posX, target.posY-attacker.posY, target.posZ-attacker.posZ);
			double angle = Math.toDegrees(Math.acos(attackerCheck.normalize().dotProduct(attacker.getLookVec())));
			
			if(angle < 60.0D + (levelCleave * 10))
			{
				// This is within our arc, let's deal our damage.
				DamageSource source = null;
				if(attacker instanceof EntityPlayer)
					source = new EntityDamageSource("player", attacker);
				    target.attackEntityFrom(SoManyEnchantments.Cleave, splashDamage);
				if(attacker instanceof EntityMob)
					source = new EntityDamageSource("mob", attacker);
				    target.attackEntityFrom(SoManyEnchantments.Cleave, splashDamage);
				
				if(source == null)
				{
					target.attackEntityFrom(DamageSource.GENERIC, splashDamage);
				}
				
				if(attacker instanceof EntityPlayer)
				{
					// Apply knockback
					int modKnockback = 1;
					modKnockback += EnchantmentHelper.getKnockbackModifier(attacker);
					if(attacker.isSprinting())
						modKnockback++;
					
					Entity victim = fEvent.getEntityLiving();
					
					if(modKnockback > 0)
						//target.setVelocity((double)(-MathHelper.sin(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)modKnockback * 0.85F), 0.2D, (double)(MathHelper.cos(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)modKnockback * 0.85F));
						fEvent.getEntityLiving().knockBack(attacker, 0.4F * modKnockback, attacker.posX - victim.posX, attacker.posZ - victim.posZ);
				}
			}
		}
	
		// Stop the player sprinting
		attacker.setSprinting(false);
    }
*/
    /*
     *  
	float Damage = 0;
	float PRDCounter = 0;
    float NBDamage = 0;
    String Chance = null;
   
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public void HandleEnchant(LivingAttackEvent fEvent)
    {
    	if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob")
			return;
    	
    	if(fEvent.getEntityLiving().getIsInvulnerable())
    		return;
    	
    	if(fEvent.getEntityLiving().attackable() == false)
    		return;
    	
    	if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase))
    		return;
    	
    	EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
    	if(attacker == null)
    		return;
    	
    	ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null)
			return;
		
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.CriticalStrike, dmgSource) <= 0)
			return;
		int levelcriticalStrike = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.CriticalStrike, dmgSource);
			
		if(!dmgSource.hasTagCompound())
			dmgSource.setTagCompound(new NBTTagCompound());
		
	    if((fEvent.getEntity().world.rand.nextInt(1000) <= 32 * (PRDCounter + 1))){
		Chance = "Critical!";
		dmgSource.getTagCompound().setFloat("Counter", 0);	
		PRDCounter = 0;
			}
	    else{
	    Chance = "Normal";
	    dmgSource.getTagCompound().setFloat("Counter", dmgSource.getTagCompound().getFloat("Counter") + 1);
	    PRDCounter = dmgSource.getTagCompound().getFloat("Counter");
	    
	    //System.out.println("Increasing" +  dmgSource.getTagCompound().getFloat("Counter"));
	    	}
	    	
			
	    
		if(Chance.equals("Critical!")){
			
		dmgSource.getTagCompound().setFloat("Counter", 0);	
		PRDCounter = 0;
		return;
		
				}
		else if(Chance.equals("Normal")){
		Damage = 0;
		//System.out.println(PRDCounter + " - Counter");
		return;
				}
				
		
		}
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public void ZafterMath(LivingHurtEvent fEvent)
    {
    	if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob")
			return;
    		
    	if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase))
    		return;
    	
    	EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
    	if(attacker == null)
    		return;
    	
    	ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null)
			return;
		
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.CriticalStrike, dmgSource) <= 0)
			return;
		int levelcriticalStrike = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.CriticalStrike, dmgSource);
    
		if(Chance.equals("Critical!")){
		
			float FDamage = EnchantmentsUtility.CalculateDamageIgnoreSwipe(fEvent.getAmount(), 0.0f, 0.00f, 1.2f + (levelcriticalStrike * 0.2f), attacker, EnchantmentRegistry.CriticalStrike);
		    fEvent.setAmount(FDamage);
		
		double X = fEvent.getEntity().posX;
		double Y = fEvent.getEntity().posY;
		double Z = fEvent.getEntity().posZ;
		Random random = fEvent.getEntity().world.rand;
		for (int i = 0; i < 20; ++i){
        
            double d0 = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            SMEnetwork.net.sendToAll(new MsgSP_Particle("magicCrit",  (double)((float)X + random.nextFloat() + 0.5f), (double)Y + ((double)random.nextFloat() * 2.5), (double)((float)Z + random.nextFloat() + 0.5f), d0, d1, d2));
            //fEvent.getEntityLiving().world.playSound(attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.MASTER, 4f, 0f, true);
		
		}
		
    }
   
    
    
    }
     * 
     */
/*
}

 */
//}
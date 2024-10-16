package com.shultrea.rin.enchantments.armor;
/*
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentBiomePresence extends Enchantment
{
	//UNUSED
	
	private static final String[] PRESENCE_NAMES = new String[] {"plains","iceplains", "desert", "forest", "mountain", "roofForest", "taiga", "ocean", "mushroom","swamp","mesa","savanna"};

	
    private static final int[] MIN_COST = new int[] {1, 120, 1, 120, 1, 120};

    
    private static final int[] LEVEL_COST = new int[] {4, 80, 5, 80, 4, 80};

    
    private static final int[] LEVEL_COST_SPAN = new int[] {20, 180, 20, 180, 20, 180};
    
    //Defines the type of presence of the enchantment, 0 = plains, 1 = iceplains, 2 = desert, 3 = forest, 4 = mountain, 5 = roofForest, 6 = taiga, 7 = ocean, 8 = mushroom, 9 = swamp, 10 = mesa, 11 = savanna
    public final int presenceType;

 
    
    public EnchantmentBiomePresence(Enchantment.Rarity rarityIn, int damageTypeIn, EntityEquipmentSlot... slots)
    {
        super(rarityIn, EnumEnchantmentType.ARMOR, slots);
        this.presenceType = damageTypeIn;
      
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return MIN_COST[this.presenceType] + (enchantmentLevel - 1) * LEVEL_COST[this.presenceType];
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + LEVEL_COST_SPAN[this.presenceType];
    }

    @Override
    public int getMaxLevel()
    {
    	//if(this.damageType == 5)
       // return 5;
    	
    	return 4;
    }


    @Override
    public String getName()
    {
        return "enchantment." + PRESENCE_NAMES[this.presenceType];
    }

    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        return !(ench instanceof EnchantmentBiomePresence);
    }
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
    	
        return super.canApplyAtEnchantingTable(stack);
    	
    	
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
        return super.canApply(stack);
    }

    @Override
    public void onUserHurt(EntityLivingBase user, Entity target, int level)
    {
    	if(!(target instanceof EntityLivingBase))
    		return;
    	
    	List<ItemStack> list = new ArrayList();
    	ItemStack nocure = ItemStack.EMPTY;
    	list.add(nocure);
    	
       if(this.presenceType == 0){
    	   if(EnchantmentsUtility.isInASpecificBiome(Biomes.PLAINS, user)){
    	   
    	   PotionEffect reg = new PotionEffect(MobEffects.SPEED, 30 + level * 30, 0);
    	   reg.setCurativeItems(list);   
    	   
    	   PotionEffect reg1 = new PotionEffect(MobEffects.SPEED, 20 + level * 20, level - 2);
    	   reg1.setCurativeItems(list);   
    	   
    	   if(level > 0 && level <= 2)		   
    	   user.addPotionEffect(reg);
    	   
    	   else if(level >= 3){
    	   user.addPotionEffect(reg1);
    	   }
       }
       }
       if(this.presenceType == 1){
           if(EnchantmentsUtility.isInASpecificBiome(Biomes.ICE_PLAINS, user)){
        	   
           PotionEffect fire = new PotionEffect(MobEffects.FIRE_RESISTANCE, 30 + level * 30, 0);
           fire.setCurativeItems(list);   
            	   
           PotionEffect fire1 = new PotionEffect(MobEffects.FIRE_RESISTANCE, 40 + level * 40, 0);
           fire1.setCurativeItems(list); 	   
        		   
           if(level > 0 && level <= 2)		   
           user.addPotionEffect(fire);
           else if(level >= 3){
           user.addPotionEffect(fire1); 
           user.extinguish();
        	   }
           }
    	   
       }
       if(this.presenceType == 2){
    	   if(EnchantmentsUtility.isInASpecificBiome(Biomes.DESERT, user) || EnchantmentsUtility.isInASpecificBiome(Biomes.DESERT_HILLS, user) || EnchantmentsUtility.isInASpecificBiome(Biomes.MUTATED_DESERT, user)){	   
    		PotionEffect reg = new PotionEffect(MobEffects.NAUSEA, 30 + level * 30, 0); 
        	PotionEffect reg1 = new PotionEffect(MobEffects.NAUSEA, 20 + level * 20, 0);
            reg1.setCurativeItems(list); 	
        	PotionEffect reg2 = new PotionEffect(MobEffects.POISON, 20 + level * 20, level - 3);
        	reg2.setCurativeItems(list); 
        	
    	   if(level > 0 && level <= 2)		   
    	   ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30 + level * 30, 0));
    	   else if(level >= 3){
    	   ((EntityLivingBase) target).addPotionEffect(new PotionEffect(reg1)); 
    	   ((EntityLivingBase) target).addPotionEffect(new PotionEffect(reg2)); 
    	   }
       }
	   
   }
       if(this.presenceType == 3){
    	   if(EnchantmentsUtility.isInASpecificBiome(Biomes.FOREST, user)){
    	   
    	   PotionEffect fire = new PotionEffect(MobEffects.REGENERATION, 30 + level * 30, 0);
        	   fire.setCurativeItems(list);   
        	  
        	   
           PotionEffect fire1 = new PotionEffect(MobEffects.REGENERATION, 20 + level * 20, level - 2);
        	   fire1.setCurativeItems(list); 	   
    		   
    	   if(level > 0 && level <= 2)		   
    	   user.addPotionEffect(fire);
    	   else if(level >= 3){
    	   user.addPotionEffect(fire1); 
    	  
    	   }
       }
	   
   }
       if(this.presenceType == 4){
    	   if(EnchantmentsUtility.IsInMountains(user)){
    	   
    	   PotionEffect fire = new PotionEffect(MobEffects.JUMP_BOOST, 30 + level * 30, 0);
        	   fire.setCurativeItems(list);   
        	  
        	   
           PotionEffect fire1 = new PotionEffect(MobEffects.JUMP_BOOST, 20 + level * 20, level - 2);
        	   fire1.setCurativeItems(list); 	   
    		   
    	   if(level > 0 && level <= 2)		   
    	   user.addPotionEffect(fire);
    	   else if(level >= 3){
    	   user.addPotionEffect(fire1); 
    	  
    	   }
       }
       }
    	   
    	   if(this.presenceType == 5){
        	   if(EnchantmentsUtility.isInBiomeRoofForest(user)){
        	   
        	   PotionEffect fire = new PotionEffect(MobEffects.NIGHT_VISION, 240 + level * 120, 0);
            	   fire.setCurativeItems(list);   
            		   
        	   user.addPotionEffect(fire);
        	   
           }
    	   }
    	   if(this.presenceType == 6){
        	   if(EnchantmentsUtility.IsInTaiga(user)){
        	   if((float)Math.random() > 0.95f - (level * 0.05f)){
        	   EntityWolf wolfSummoned = new EntityWolf(user.getEntityWorld());
        	   wolfSummoned.setHealth(6f);
        	   wolfSummoned.setOwnerId(user.getUniqueID());
        	   PotionEffect AmplifyingPotion = new PotionEffect(MobEffects.RESISTANCE, 1000000, -1);
        	   PotionEffect WitherPotion = new PotionEffect(MobEffects.WITHER, 1000000, 3);
        	   PotionEffect HealthRemover = new PotionEffect(MobEffects.HEALTH_BOOST, 1000000, -7);
        	   WitherPotion.setCurativeItems(list);
        	   AmplifyingPotion.setCurativeItems(list);
        	   HealthRemover.setCurativeItems(list);
        	   wolfSummoned.addPotionEffect(new PotionEffect(AmplifyingPotion));
        	   wolfSummoned.addPotionEffect(new PotionEffect(HealthRemover));
        	   wolfSummoned.addPotionEffect(new PotionEffect(WitherPotion));
        	   
        	   if(target instanceof EntityLivingBase){
        	   wolfSummoned.setAttackTarget((EntityLivingBase) target);
        	   user.getEntityWorld().spawnEntity(wolfSummoned);
        	   }
        	   }
            	  
            	   
               
        	   }
        	   }
        	   if(this.presenceType == 7){
            	   if(EnchantmentsUtility.isInBiomeOcean(user)){
	    	   
            		   PotionEffect fire = new PotionEffect(MobEffects.WATER_BREATHING, 240 + level * 120, 0);
                	   fire.setCurativeItems(list);   
                		   
            	       user.addPotionEffect(fire);

            	   }
    	   }
        	   
        	   if(this.presenceType == 9){
            	   if(EnchantmentsUtility.IsInSwamp(user)){
	    	   
            		   PotionEffect fire = new PotionEffect(MobEffects.WATER_BREATHING, 120 + level * 60, 0);
            		   PotionEffect fire1 = new PotionEffect(MobEffects.REGENERATION, 40 + level * 20, 1);
                	   fire.setCurativeItems(list);   
                	   fire1.setCurativeItems(list);    
                	   
            	       user.addPotionEffect(fire);
            	       user.addPotionEffect(fire1);
            	   }
        	   }
                if(this.presenceType == 10){
                 if(EnchantmentsUtility.IsInMesa(user)){
                	 target.setFire(4 * level);
                	 PotionEffect fire = new PotionEffect(MobEffects.HUNGER, 120 + level * 60, 0);
                	 if(level >= 3){
                	 PotionEffect fire1 = new PotionEffect(MobEffects.HUNGER, 60 + level * 30, level - 2);
                	 fire1.setCurativeItems(list);
                	 ((EntityLivingBase) target).addPotionEffect(fire1);
                	 }
                	 else {
                		 
                		 fire.setCurativeItems(list);
                		 ((EntityLivingBase) target).addPotionEffect(fire);
                	 
                	 }
                	 }
                
                	 
                 }
                
                if(this.presenceType == 11){
             	   if(EnchantmentsUtility.IsInSavanna(user)){
             	   
             	   PotionEffect fire = new PotionEffect(MobEffects.JUMP_BOOST, 30 + level * 30, 0);
                 	   fire.setCurativeItems(list);   
                 	  
                 	   
                    PotionEffect fire1 = new PotionEffect(MobEffects.JUMP_BOOST, 20 + level * 20, level - 2);
                 	   fire1.setCurativeItems(list); 	   
             		   
             	   if(level > 0 && level <= 2)		   
             	   user.addPotionEffect(fire);
             	   else if(level >= 3){
             	   user.addPotionEffect(fire1); 
             	  
             	   }
                }
                }
                
              
                }
    	   }
           */
           
    	   
   
       
            
        
    

  
        
        
    


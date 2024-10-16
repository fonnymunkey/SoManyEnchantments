package com.shultrea.rin;

import com.shultrea.rin.Enum.EnumList;
import com.shultrea.rin.Prop_Sector.*;
import com.shultrea.rin.Utility_Sector.*;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = SoManyEnchantments.MODID, name = SoManyEnchantments.NAME, version = SoManyEnchantments.VERSION,
		acceptedMinecraftVersions = "[1.12.0, 1.12.2]")
public class SoManyEnchantments {
	
	public static final String MODID = "somanyenchantments";
	public static final String NAME = "Rin's So Many Enchantments?";
	public static final String VERSION = "0.5.5";
	public static boolean hotbarOnly = true;
	@Instance(SoManyEnchantments.MODID)
	public static SoManyEnchantments instance;
	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static CommProxy proxy;
	/**
	 * Pure is a damage source added by this mod that can kill even creative players. It also bypasses several damage
	 * absorptions.
	 */
	public static DamageSource Pure = new DamageSource("Pure").setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();
	/**
	 * Ethereal is a damage source added by this mod that bypasses armor
	 */
	public static DamageSource Ethereal = new DamageSource("Ethereal").setDamageBypassesArmor();
	/**
	 * Cleave is a damage source added by this mod that is used to damage enemies in a certain angle from the original
	 * damage source.
	 */
	public static DamageSource Cleave = new DamageSource("Cleave");
	/**
	 * Deconstruct is a damage source added by this mod that is used when an enemy is damaged from a molecular force. It
	 * bypasses armor and is absolute.
	 */
	public static DamageSource Deconstruct = new DamageSource("Deconstruct").setDamageIsAbsolute().setDamageBypassesArmor();
	/**
	 * Culled is a damage source added by this mod that is used to finish off low health opponents. Like Deconstruct, it
	 * bypasses armor and is absolute.
	 */
	public static DamageSource Culled = new DamageSource("Culled").setDamageIsAbsolute().setDamageBypassesArmor();
	/**
	 * CounterAttack is a damage source added by this mod that is used by the counter attack enchantment.
	 */
	public static DamageSource CounterAttack = new DamageSource("CounterAttack");
	/**
	 * Silence is a damage source added by this mod that is used instead of a physical damage to deal damage to silenced
	 * mob.
	 */
	public static DamageSource Silence = new DamageSource("Silence");
	/**
	 * Physical Damage is a damage source added by this mod that is used to deal ordinary damage to a mob. Some
	 * enchantments in this mod make use of it.
	 */
	public static DamageSource PhysicalDamage = new DamageSource("PhysicalDamage");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent fEvent) {
		CapabilityManager.INSTANCE.register(IArrowProperties.class, new ArrowPropertiesStorage(), ArrowProperties::new);
		CapabilityManager.INSTANCE.register(IPlayerProperties.class, new PlayerPropertiesStorage(), PlayerProperties::new);
		proxy.preInit(fEvent);
		SMEsounds.registerSounds();
		EnchantmentRegistry.handleSubscribers();
		MinecraftForge.EVENT_BUS.register(new HurtPatchHandler());
		MinecraftForge.EVENT_BUS.register(new OtherHandler());
		MinecraftForge.EVENT_BUS.register(new ArrowPropertiesHandler());
		MinecraftForge.EVENT_BUS.register(new AdditionalProtectionEnchantmentsEffects());
		MinecraftForge.EVENT_BUS.register(new ExtraEvent());
	}
	
	@EventHandler
	public void load(FMLInitializationEvent fEvent) {
		proxy.onInit(fEvent);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent fEvent) {
		proxy.postInit(fEvent);
		PotionLister.Cycle();
		EnumList.initializeEnchantmentTab();
		EnchantmentLister.initEnchantmentList();
	}
}
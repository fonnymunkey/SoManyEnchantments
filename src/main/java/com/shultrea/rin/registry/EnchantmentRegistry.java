package com.shultrea.rin.registry;

import com.shultrea.rin.Enum.EnumList;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.enchantments.*;
import com.shultrea.rin.enchantments.armor.protection.*;
import com.shultrea.rin.enchantments.armor.thorns.EnchantmentAdvancedThorns;
import com.shultrea.rin.enchantments.bow.*;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLuckOfTheSea;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLure;
import com.shultrea.rin.enchantments.tool.EnchantmentAdvancedEfficiency;
import com.shultrea.rin.enchantments.tool.EnchantmentQuarrying;
import com.shultrea.rin.enchantments.tool.EnchantmentSmelter;
import com.shultrea.rin.enchantments.weapon.*;
import com.shultrea.rin.enchantments.weapon.ancient.EnchantmentAncientSealedCurses;
import com.shultrea.rin.enchantments.weapon.ancient.EnchantmentAncientSwordMastery;
import com.shultrea.rin.enchantments.armor.*;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.weapon.conditionaldamage.EnchantmentDarkShadows;
import com.shultrea.rin.enchantments.weapon.conditionaldamage.EnchantmentMortalitas;
import com.shultrea.rin.enchantments.weapon.conditionaldamage.EnchantmentViper;
import com.shultrea.rin.enchantments.curses.*;
import com.shultrea.rin.enchantments.weapon.damage.*;
import com.shultrea.rin.enchantments.weapon.damagemultiplier.*;
import com.shultrea.rin.enchantments.hoe.EnchantmentJaggedRake;
import com.shultrea.rin.enchantments.hoe.EnchantmentMoisturized;
import com.shultrea.rin.enchantments.hoe.EnchantmentPlowing;
import com.shultrea.rin.enchantments.weapon.potiondebuffer.*;
import com.shultrea.rin.enchantments.rune.*;
import com.shultrea.rin.enchantments.shield.EnchantmentBurningShield;
import com.shultrea.rin.enchantments.shield.EnchantmentEmpoweredDefence;
import com.shultrea.rin.enchantments.shield.EnchantmentNaturalBlocking;
import com.shultrea.rin.enchantments.armor.thorns.EnchantmentBurningThorns;
import com.shultrea.rin.enchantments.weapon.subject.EnchantmentSubjectEnchantments;
import com.shultrea.rin.enchantments.weapon.weather.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashSet;
import java.util.Set;

public class EnchantmentRegistry {
	
	private static final Set<EnchantmentBase> enchantmentSet = new LinkedHashSet<>();
	
	public static EnchantmentBase adept = register(new EnchantmentAdept("adept", Enchantment.Rarity.RARE, EnumList.COMBAT_WEAPON));
	public static EnchantmentBase ancientSealedCurses = register(new EnchantmentAncientSealedCurses("ancientsealedcurses", Enchantment.Rarity.VERY_RARE, EnumList.SWORD));
	public static EnchantmentBase ancientSwordMastery = register(new EnchantmentAncientSwordMastery("ancientswordmastery", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase arcSlash = register(new EnchantmentArcSlash("arcslash", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase ashDestroyer = register(new EnchantmentAshDestroyer("ashdestroyer", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase atomicDeconstructor = register(new EnchantmentAtomicDeconstructor("atomicdeconstructor", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	//public static EnchantmentBase biomePresence;
	public static EnchantmentBase blessedEdge = register(new EnchantmentBlessedEdge("blessededge", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase brutality = register(new EnchantmentBrutality("brutality", Enchantment.Rarity.RARE, EnumList.COMBAT_AXE));
	public static EnchantmentBase burningShield = register(new EnchantmentBurningShield("burningshield", Enchantment.Rarity.RARE, EnumList.SHIELD));
	public static EnchantmentBase burningThorns = register(new EnchantmentBurningThorns("burningthorns", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR));
	public static EnchantmentBase butchering = register(new EnchantmentButchering("butchering", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase clearSkiesFavor = register(new EnchantmentClearSkiesFavor("clearskiesfavor", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase combatVeterancy = register(new EnchantmentCombatVeterancy("combatveterancy", Enchantment.Rarity.RARE, EnumList.COMBAT_WEAPON));
	public static EnchantmentBase counterAttack = register(new EnchantmentCounterAttack("counterattack", Enchantment.Rarity.RARE, EnumList.SWORD));
	public static EnchantmentBase criticalStrike = register(new EnchantmentCriticalStrike("criticalstrike", Enchantment.Rarity.RARE, EnumList.COMBAT));
	public static EnchantmentBase culling = register(new EnchantmentCulling("culling", Enchantment.Rarity.VERY_RARE, EnumList.COMBAT_AXE));
	public static EnchantmentBase darkShadows = register(new EnchantmentDarkShadows("darkshadows", Enchantment.Rarity.RARE, EnumList.SWORD));
	//public static EnchantmentBase debug;
	public static EnchantmentBase defusion = register(new EnchantmentDefusion("defusion", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase desolator = register(new EnchantmentDesolator("desolator", Enchantment.Rarity.RARE, EnumList.COMBAT_AXE));
	public static EnchantmentBase difficultysEndowment = register(new EnchantmentDifficultysEndowment("difficultysendowment", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase disarmament = register(new EnchantmentDisarmament("disarmament", Enchantment.Rarity.RARE, EnumList.COMBAT_AXE));
	public static EnchantmentBase disorientatingBlade = register(new EnchantmentDisorientatingBlade("disorientatingblade", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase drag = register(new EnchantmentDrag("drag", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BOW));
	public static EnchantmentBase empoweredDefence = register(new EnchantmentEmpoweredDefence("empowereddefence", Enchantment.Rarity.RARE, EnumList.SHIELD));
	public static EnchantmentBase envenomed = register(new EnchantmentEnvenomed("envenomed", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase evasion = register(new EnchantmentEvasion("evasion", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR_LEGS));
	public static EnchantmentBase fieryEdge = register(new EnchantmentFieryEdge("fieryedge", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase flinging = register(new EnchantmentFlinging("flinging", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase freezing = register(new EnchantmentFreezing("freezing", Enchantment.Rarity.RARE, EnumList.SWORD));
	public static EnchantmentBase horsDeCombat = register(new EnchantmentHorsDeCombat("horsdecombat", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase inhumane = register(new EnchantmentInhumane("inhumane", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase innerBerserk = register(new EnchantmentInnerBerserk("innerberserk", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_CHEST));
	public static EnchantmentBase jaggedRake = register(new EnchantmentJaggedRake("jaggedrake", Enchantment.Rarity.RARE, EnumList.HOE));
	public static EnchantmentBase levitator = register(new EnchantmentLevitator("levitator", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase lifesteal = register(new EnchantmentLifesteal("lifesteal", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase lightWeight = register(new EnchantmentLightWeight("lightweight", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR_FEET));
	public static EnchantmentBase luckMagnification = register(new EnchantmentLuckMagnification("luckmagnification", Enchantment.Rarity.RARE, EnumList.COMBAT));
	public static EnchantmentBase lunarsBlessing = register(new EnchantmentLunarsBlessing("lunarsblessing", Enchantment.Rarity.RARE, EnumList.SWORD));
	public static EnchantmentBase magicProtection = register(new EnchantmentMagicProtection("magicprotection", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR));
	public static EnchantmentBase magmaWalker = register(new EnchantmentMagmaWalker("magmawalker", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_FEET));
	public static EnchantmentBase meltdown = register(new EnchantmentMeltdown("meltdown", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_CHEST));
	public static EnchantmentBase moisturized = register(new EnchantmentMoisturized("moisturized", Enchantment.Rarity.UNCOMMON, EnumList.HOE));
	//public static EnchantmentBase multiFisher;
	public static EnchantmentBase mortalitas = register(new EnchantmentMortalitas("mortalitas", Enchantment.Rarity.VERY_RARE, EnumList.COMBAT));
	public static EnchantmentBase naturalBlocking = register(new EnchantmentNaturalBlocking("naturalblocking", Enchantment.Rarity.RARE, EnumList.SHIELD));
	public static EnchantmentBase parry = register(new EnchantmentParry("parry", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase penetratingEdge = register(new EnchantmentPenetratingEdge("penetratingedge", Enchantment.Rarity.RARE, EnumList.COMBAT_AXE));
	public static EnchantmentBase physicalProtection = register(new EnchantmentPhysicalProtection("physicalprotection", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR));
	public static EnchantmentBase purgingBlade = register(new EnchantmentPurgingBlade("purgingblade", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase purification = register(new EnchantmentPurification("purification", Enchantment.Rarity.UNCOMMON, EnumList.COMBAT_AXE));
	public static EnchantmentBase pushing = register(new EnchantmentPushing("pushing", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BOW));
	public static EnchantmentBase quarrying = register(new EnchantmentQuarrying("quarrying", Enchantment.Rarity.VERY_RARE, EnumList.PICKAXE));
	public static EnchantmentBase rainsBestowment = register(new EnchantmentRainsBestowment("rainsbestowment", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase reviledBlade = register(new EnchantmentReviledBlade("reviledblade", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase sharperEdge = register(new EnchantmentSharperEdge("sharperedge", Enchantment.Rarity.RARE, EnumList.COMBAT_TOOL));
	public static EnchantmentBase smelter = register(new EnchantmentSmelter("smelter", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.DIGGER));
	public static EnchantmentBase solsBlessing = register(new EnchantmentSolsBlessing("solsblessing", Enchantment.Rarity.VERY_RARE, EnumList.SWORD));
	public static EnchantmentBase spellBreaker = register(new EnchantmentSpellBreaker("spellbreaker", Enchantment.Rarity.RARE, EnumList.COMBAT));
	public static EnchantmentBase splitShot = register(new EnchantmentSplitshot("splitshot", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BOW));
	public static EnchantmentBase strafe = register(new EnchantmentStrafe("strafe", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.BOW));
	public static EnchantmentBase strengthenedVitality = register(new EnchantmentStrengthenedVitality("strengthenedvitality", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_CHEST));
	public static EnchantmentBase swifterSlashes = register(new EnchantmentSwifterSlashes("swifterslashes", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase thunderstormsBestowment = register(new EnchantmentThunderstormsBestowment("thunderstormsbestowment", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase trueStrike = register(new EnchantmentTrueStrike("truestrike", Enchantment.Rarity.RARE, EnumList.SWORD));
	public static EnchantmentBase underwaterStrider = register(new EnchantmentUnderwaterStrider("underwaterstrider", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR_FEET));
	public static EnchantmentBase unpredictable = register(new EnchantmentUnpredictable("unpredictable", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase unreasonable = register(new EnchantmentUnreasonable("unreasonable", Enchantment.Rarity.RARE, EnumList.SWORD));
	public static EnchantmentBase unsheathing = register(new EnchantmentUnsheathing("unsheathing", Enchantment.Rarity.VERY_RARE, EnumList.SWORD));
	public static EnchantmentBase upgradedPotentials = register(new EnchantmentUpgradedPotentials("upgradedpotentials", Enchantment.Rarity.RARE, EnumList.NONE));
	public static EnchantmentBase viper = register(new EnchantmentViper("viper", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase waterAspect = register(new EnchantmentWaterAspect("wateraspect", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase plowing = register(new EnchantmentPlowing("plowing", Enchantment.Rarity.RARE, EnumList.HOE));
	public static EnchantmentBase wintersGrace = register(new EnchantmentWintersGrace("wintersgrace", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	
	//Curses
	public static EnchantmentBase bluntness = register(new EnchantmentBluntness("bluntness", Enchantment.Rarity.RARE, EnumList.COMBAT));
	public static EnchantmentBase cursedEdge = register(new EnchantmentCursedEdge("cursededge", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase curseOfDecay = register(new EnchantmentCurseofDecay("curseofdecay", Enchantment.Rarity.VERY_RARE, EnumList.ALL));
	public static EnchantmentBase curseOfHolding = register(new EnchantmentCurseofHolding("curseofholding", Enchantment.Rarity.VERY_RARE, EnumList.ALL));
	public static EnchantmentBase curseOfInaccuracy = register(new EnchantmentCurseofInaccuracy("curseofinaccuracy", Enchantment.Rarity.VERY_RARE, EnumList.COMBAT_WEAPON));
	public static EnchantmentBase curseOfPossession = register(new EnchantmentCurseofPossession("curseofpossession", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ALL));
	public static EnchantmentBase curseOfVulnerability = register(new EnchantmentCurseofVulnerability("curseofvulnerability", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR));
	public static EnchantmentBase heavyWeight = register(new EnchantmentHeavyWeight("heavyweight", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase inefficient = register(new EnchantmentInefficient("inefficient", Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER));
	public static EnchantmentBase instability = register(new EnchantmentInstability("instability", Enchantment.Rarity.VERY_RARE, EnumList.ALL_TOOL));
	public static EnchantmentBase pandorasCurse = register(new EnchantmentPandorasCurse("pandorascurse", Enchantment.Rarity.VERY_RARE, EnumList.ALL));
	public static EnchantmentBase powerless = register(new EnchantmentPowerless("powerless", Enchantment.Rarity.RARE, EnumEnchantmentType.BOW));
	public static EnchantmentBase rusted = register(new EnchantmentRusted("rusted", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE));
	
	//Rune
	public static EnchantmentBase runeArrowPiercing = register(new EnchantmentRuneArrowPiercing("rune_arrowpiercing", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BOW));
	public static EnchantmentBase runeMagicalBlessing = register(new EnchantmentRuneMagicalBlessing("rune_magicalblessing", Enchantment.Rarity.VERY_RARE, EnumList.SWORD));
	public static EnchantmentBase runePiercingCapabilities = register(new EnchantmentRunePiercingCapabilities("rune_piercingcapabilities", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase runeResurrection = register(new EnchantmentRuneResurrection("rune_resurrection", Enchantment.Rarity.VERY_RARE, EnumList.COMBAT_GOLDEN_APPLE));
	public static EnchantmentBase runeRevival = register(new EnchantmentRuneRevival("rune_revival", Enchantment.Rarity.VERY_RARE, EnumList.DAMAGEABLE));
	//public static EnchantmentBase runeStarfall;
	
	//Subject
	public static EnchantmentBase subjectMathematics = register(new EnchantmentSubjectEnchantments("subjectmathematics",Enchantment.Rarity.VERY_RARE,EnumList.SWORD,0));
	public static EnchantmentBase subjectScience = register(new EnchantmentSubjectEnchantments("subjectscience",Enchantment.Rarity.VERY_RARE,EnumList.SWORD,1));
	public static EnchantmentBase subjectHistory = register(new EnchantmentSubjectEnchantments("subjecthistory",Enchantment.Rarity.VERY_RARE,EnumList.SWORD,2));
	//public static Enchantment subjectPhysics = register(new EnchantmentSubjectEnchantments("subjectphysics",Enchantment.Rarity.VERY_RARE,EnumList.SWORD,3));
	public static EnchantmentBase subjectEnglish = register(new EnchantmentSubjectEnchantments("subjectenglish",Enchantment.Rarity.VERY_RARE,EnumList.SWORD,4));
	public static EnchantmentBase subjectPE = register(new EnchantmentSubjectEnchantments("subjectpe",Enchantment.Rarity.VERY_RARE,EnumList.SWORD,5));

	//Lesser
	public static EnchantmentBase lesserBaneOfArthropods = register(new EnchantmentTierDamage("lesserbaneofarthropods", Enchantment.Rarity.COMMON,EnumEnchantmentType.WEAPON,4));
	//public static EnchantmentBase lesserBlastProtection;
	//public static EnchantmentBase lesserEfficiency;
	//public static EnchantmentBase lesserFeatherFalling;
	public static EnchantmentBase lesserFireAspect = register(new EnchantmentTierFA("lesserfireaspect", Enchantment.Rarity.COMMON,EnumEnchantmentType.WEAPON,0));
	//public static EnchantmentBase lesserFireProtection;
	//TODO, Nischhelm: why is this and the other Flame tiers WEAPON and not BOW? How does that even work in game? Vanilla Flame got BOW
	public static EnchantmentBase lesserFlame = register(new EnchantmentTierFlame("lesserflame", Enchantment.Rarity.COMMON,EnumEnchantmentType.WEAPON,0));
	//public static EnchantmentBase lesserFortune;
	//public static EnchantmentBase lesserKnockback;
	//public static EnchantmentBase lesserLooting;
	//public static EnchantmentBase lesserLuckOfTheSea;
	//public static EnchantmentBase lesserLure;
	//public static EnchantmentBase lesserMending;
	//public static EnchantmentBase lesserPower;
	//public static EnchantmentBase lesserProjectileProtection;
	//public static EnchantmentBase lesserProtection;
	//public static EnchantmentBase lesserPunch;
	//public static EnchantmentBase lesserRespiration;
	public static EnchantmentBase lesserSharpness = register(new EnchantmentTierDamage("lessersharpness", Enchantment.Rarity.COMMON,EnumEnchantmentType.WEAPON,0));
	public static EnchantmentBase lesserSmite = register(new EnchantmentTierDamage("lessermite", Enchantment.Rarity.COMMON,EnumEnchantmentType.WEAPON,2));
	//public static EnchantmentBase lesserThorns;

	//Advanced
	public static EnchantmentBase advancedBaneOfArthropods = register(new EnchantmentAdvancedBaneOfArthropods("advancedbaneofarthropods", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase advancedBlastProtection = register(new EnchantmentAdvancedBlastProtection("advancedblastprotection", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR));
	public static EnchantmentBase advancedEfficiency = register(new EnchantmentAdvancedEfficiency("advancedefficiency", Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER));
	public static EnchantmentBase advancedFeatherFalling = register(new EnchantmentAdvancedFeatherFalling("advancedfeatherfalling", Enchantment.Rarity.VERY_RARE,EnumEnchantmentType.ARMOR_FEET));
	public static EnchantmentBase advancedFireAspect = register(new EnchantmentTierFA("advancedfireaspect", Enchantment.Rarity.RARE,EnumEnchantmentType.WEAPON,1));
	public static EnchantmentBase advancedFireProtection = register(new EnchantmentAdvancedFireProtection("advancedfireprotection", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR));
	public static EnchantmentBase advancedFlame = register(new EnchantmentTierFlame("advancedflame", Enchantment.Rarity.VERY_RARE,EnumEnchantmentType.WEAPON,1));
	//public static EnchantmentBase advancedFortune;
	public static EnchantmentBase advancedKnockback = register(new EnchantmentAdvancedKnockback("advancedknockback", Enchantment.Rarity.VERY_RARE,EnumEnchantmentType.WEAPON));
	public static EnchantmentBase advancedLooting = register(new EnchantmentAdvancedLooting("advancedlooting", Enchantment.Rarity.VERY_RARE,EnumEnchantmentType.WEAPON));
	public static EnchantmentBase advancedLuckOfTheSea = register(new EnchantmentAdvancedLuckOfTheSea("advancedluckofthesea",Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.FISHING_ROD));
	public static EnchantmentBase advancedLure = register(new EnchantmentAdvancedLure("advancedlure", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.FISHING_ROD));
	public static EnchantmentBase advancedMending = register(new EnchantmentAdvancedMending("advancedmending", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BREAKABLE));
	public static EnchantmentBase advancedPower = register(new EnchantmentAdvancedPower("advancedpower", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BOW));
	public static EnchantmentBase advancedProjectileProtection = register(new EnchantmentAdvancedProjectileProtection("advancedprojectileprotection", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR));
	public static EnchantmentBase advancedProtection = register(new EnchantmentAdvancedProtection("advancedprotection", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR));
	public static EnchantmentBase advancedPunch = register(new EnchantmentAdvancedPunch("advancedpunch", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BOW));
	//public static EnchantmentBase advancedRespiration;
	public static EnchantmentBase advancedSharpness = register(new EnchantmentAdvancedSharpness("advancedsharpness", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase advancedSmite = register(new EnchantmentAdvancedSmite("advancedsmite", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON));
	public static EnchantmentBase advancedThorns = register(new EnchantmentAdvancedThorns("advancedthorns", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR));
	//Supreme
	public static EnchantmentBase supremeBaneOfArthropods = register(new EnchantmentTierDamage("supremebaneofarthropods", Enchantment.Rarity.VERY_RARE,EnumEnchantmentType.WEAPON,5));
	//public static EnchantmentBase supremeBlastProtection;
	//public static EnchantmentBase supremeEfficiency;
	//public static EnchantmentBase supremeFeatherFalling;
	public static EnchantmentBase supremeFireAspect = register(new EnchantmentTierFA("supremefireaspect", Enchantment.Rarity.VERY_RARE,EnumEnchantmentType.WEAPON,2));
	//public static EnchantmentBase supremeFireProtection;
	public static EnchantmentBase supremeFlame = register(new EnchantmentTierFlame("supremeflame", Enchantment.Rarity.VERY_RARE,EnumEnchantmentType.WEAPON,2));
	//public static EnchantmentBase supremeFortune;
	//public static EnchantmentBase supremeKnockback;
	//public static EnchantmentBase supremeLooting;
	//public static EnchantmentBase supremeLuckOfTheSea;
	//public static EnchantmentBase supremeLure;
	//public static EnchantmentBase supremeMending;
	//public static EnchantmentBase supremePower;
	//public static EnchantmentBase supremeProjectileProtection;
	//public static EnchantmentBase supremeProtection;
	//public static EnchantmentBase supremePunch;
	//public static EnchantmentBase supremeRespiration;
	public static EnchantmentBase supremeSharpness = register(new EnchantmentTierDamage("supremesharpness", Enchantment.Rarity.VERY_RARE,EnumEnchantmentType.WEAPON,1));
	public static EnchantmentBase supremeSmite = register(new EnchantmentTierDamage("supremesmite", Enchantment.Rarity.VERY_RARE,EnumEnchantmentType.WEAPON,3));;
	//public static EnchantmentBase supremeThorns;
	
	private static EnchantmentBase register(EnchantmentBase enchantment) {
		if(!ModConfig.miscellaneous.dontRegisterDisabledEnchants || enchantment.isEnabled()) {
			enchantmentSet.add(enchantment);
		}
		return enchantment;
	}
	
	public static void handleSubscribers() {
		for(EnchantmentBase enchantment : enchantmentSet) {
			if(enchantment.isEnabled() && enchantment.hasSubscriber()) MinecraftForge.EVENT_BUS.register(enchantment);
		}
	}
	
	@Mod.EventBusSubscriber(modid = SoManyEnchantments.MODID)
	public static class EventSubscriber {
		
		@SubscribeEvent
		public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
			IForgeRegistry<Enchantment> registry = event.getRegistry();
			for(EnchantmentBase enchant : enchantmentSet) {
				registry.register(enchant);
			}
		}
	}
}
package com.shultrea.rin.config;

import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;

import java.util.ArrayList;

public class IncompatibleConfig {
	
	@Config.Comment("List groupings of enchantments that should be incompatible with each other")
	@Config.Name("Incompatible Enchantment Groups")
	@Config.RequiresMcRestart
	public String[] incompatibleGroups = {
			"minecraft:feather_falling, advancedfeatherfalling",
			"minecraft:depth_strider, underwaterstrider",
			"minecraft:frost_walker, magma_walker",
			"heavyweight, swifterslashes",
			"minecraft:unbreaking, rusted",
			"minecraft:unbreaking, instability",
			"minecraft:sweeping, arcslash, ancientswordmastery",
			"minecraft:silk_touch, smelter",
			"minecraft:luck_of_the_sea, advancedluckofthesea",
			"minecraft:lure, advancedlure",
			"minecraft:mending, advancedmending, minecraft:infinity",
			"curseofpossession, curseofdecay",
			"truestrike, curseofinaccuracy",
			"minecraft:thorns, advancedthorns, burningthorns, meltdown",
			"minecraft:efficiency, advancedefficiency, inefficient",
			"minecraft:knockback, advancedknockback, flinging, mujmajnkraftsbettersurvival:fling",
			"fieryedge, ashdestroyer",
			"minecraft:looting, advancedlooting, mujmajnkraftsbettersurvival:education",
			"blessededge, lifesteal",
			"rune_piercingcapabilities, rune_arrowpiercing, penetratingedge, rune_magicalblessing, rune_revival, rune_resurrection",
			"viper, darkshadows, mortalitas",
			"minecraft:infinity, strafe",
			"minecraft:power, advancedpower, powerless",
			"minecraft:punch, advancedpunch, dragging, pushing",
			"subjectpe, subjectenglish, subjectscience, subjectmathematics, subjecthistory",
			"minecraft:fire_aspect, lesserfireaspect, advancedfireaspect, supremefireaspect, wateraspect",
			"minecraft:flame, lesserflame, advancedflame, supremeflame",
			"reviledblade, criticalstrike, ashdestroyer, luckmagnification, instability, difficultysendowment, cursededge",
			"clearskiesfavor, rainsbestowment, thunderstormsbestowment, wintersgrace, solsblessing, lunarsblessing",
			"purification, levitator, desolator, disorientatingblade, envenomed, horsdecombat, freezing",
			"minecraft:protection, minecraft:fire_protection, minecraft:blast_protection, minecraft:projectile_protection, magicprotection, physicalprotection, advancedprotection, advancedfireprotection, advancedblastprotection, advancedprojectileprotection, curseofvulnerability",
			"lessersharpness, minecraft:sharpness, advancedsharpness, supremesharpness, reinforcedsharpness, bluntness",
			"lessersmite, minecraft:smite, advancedsmite, supremesmite, blessededge, reinforcedsharpness, bluntness",
			"lesserbaneofarthropods, minecraft:bane_of_arthropods, advancedbaneofarthropods, supremebaneofarthropods, reinforcedsharpness, bluntness",
			"lessersharpness, lessersmite, lesserbaneofarthropods",
			"advancedsharpness, advancedsmite, advancedbaneofarthropods, supremesharpness, supremesmite, supremebaneofarthropods, spellbreaker",
			"defusingedge, inhumane, butchering",
			"splitshot, mujmajnkraftsbettersurvival:multishot",
			"adept, mujmajnkraftsbettersurvival:education"
	};

	public ArrayList<Enchantment> getIncompatibleEnchantmentsString(Enchantment thisEnch) {
		ArrayList<Enchantment> incompatEnchs = new ArrayList<>();

		ResourceLocation regName = thisEnch.getRegistryName();
		if(regName == null) return incompatEnchs;
		
		for(String s : incompatibleGroups) {
			if(s.contains(regName.getPath())) {
				//Assumes that config lines are enchantments separated by comma
				String[] enchsInList = s.split(",");
				for(String s1 : enchsInList) {
					s1 = s1.trim();
					if(s1.isEmpty()) continue;
					//assumes that the config uses modid:enchantment if its not an SME enchant
					if(!s1.contains(":")) s1 = SoManyEnchantments.MODID + ":" + s1;
					Enchantment incompatEnch = Enchantment.getEnchantmentByLocation(s1);
					if(incompatEnch == null) SoManyEnchantments.LOGGER.info("SME: could not find incompatible enchantment {}", s1);
					else incompatEnchs.add(incompatEnch);
				}
			}
		}
		// remove duplicates of the calling enchant
		// every enchantment is incompatible with itself, this is handled by Enchantment.java though
		// and thus doesnt need to be in this list
		while(incompatEnchs.contains(thisEnch)) {
			incompatEnchs.remove(thisEnch);
		}

		return incompatEnchs;
	}
}
package com.shultrea.rin.config;

import net.minecraftforge.common.config.Config;

public class UpgradeConfig {
	@Config.Comment("Enchantments will be upgraded in this order")
	@Config.Name("Enchantment upgrade order")
	public String[] enchantUpgradeOrder = {
			"lessersharpness, minecraft:sharpness, advancedsharpness, supremesharpness",
			"lessersmite, minecraft:smite, advancedsmite, supremesmite",
			"lesserbaneofarthropods, minecraft:bane_of_arthropods, advancedbaneofarthropods, supremebaneofarthropods",
			"lesserfireaspect, minecraft:fire_aspect, advancedfireaspect, supremefireaspect",
			"minecraft:knockback, advancedknockback",
			"minecraft:looting, advancedlooting",
			"minecraft:efficiency, advancedefficiency",
			"minecraft:luck_of_the_sea, advancedluckofthesea",
			"minecraft:lure, advancedlure",
			"minecraft:mending, advancedmending",
			"lesserflame, minecraft:flame, advancedflame, supremeflame",
			"minecraft:punch, advancedpunch",
			"minecraft:power, advancedpower",
			"minecraft:feather_falling, advancedfeatherfalling",
			"minecraft:blast_protection, advancedblastprotection",
			"minecraft:fire_protection, advancedfireprotection",
			"minecraft:projectile_protection, advancedprojectileprotection",
			"minecraft:protection, advancedprotection",
			"minecraft:thorns, burningthorns, advancedthorns"
	};

	@Config.Comment("Enchantments will be turned into their curse form. Curse is last in list. none means it will be removed instead")
	@Config.Name("Enchantment upgrade cursing")
	public String[] enchantUpgradeCursing = {
			"lessersharpness, minecraft:sharpness, advancedsharpness, bluntness",
			"lessersmite, minecraft:smite, advancedsmite, supremesmite, bluntness",
			"lesserbaneofarthropods, minecraft:bane_of_arthropods, advancedbaneofarthropods, supremebaneofarthropods, bluntness",
			"lesserfireaspect, minecraft:fire_aspect, advancedfireaspect, supremefireaspect, extinguish",
			"minecraft:knockback, advancedknockback, dragging",
			"minecraft:looting, advancedlooting, ascetic",
			"minecraft:efficiency, advancedefficiency, inefficient",
			"minecraft:luck_of_the_sea, advancedluckofthesea, ascetic",
			"minecraft:lure, advancedlure, none",
			"minecraft:mending, advancedmending, rusted",
			"lesserflame, minecraft:flame, advancedflame, supremeflame, extinguish",
			"minecraft:punch, advancedpunch, dragging",
			"powerless, minecraft:power, advancedpower, powerless",
			"minecraft:feather_falling, advancedfeatherfalling, curseofvulnerability",
			"minecraft:blast_protection, advancedblastprotection, curseofvulnerability",
			"minecraft:fire_protection, advancedfireprotection, curseofvulnerability",
			"minecraft:projectile_protection, advancedprojectileprotection, curseofvulnerability",
			"minecraft:protection, advancedprotection, curseofvulnerability",
			"minecraft:thorns, burningthorns, advancedthorns, meltdown"
	};

	@Config.Comment("Upgrading enchantments will use up this material")
	@Config.Name("Upgrade Token")
	public String upgradeToken = "minecraft:nether_star";

	@Config.Comment("Upgrading enchantments will use up this amount of the token material")
	@Config.Name("Upgrade Token Amount")
	public int upgradeTokenAmount = 1;

	@Config.Comment("Mode how many XP levels are used while upgrading enchants: NONE=no xp cost, ANVIL=how much the resulting enchant would cost on anvil, ENCHANTABILITY=minimum enchantability of the resulting enchant")
	@Config.Name("Level Cost Mode")
	public String levelCostMode = "ANVIL";

	@Config.Comment("By how much the resulting level cost will be multiplied")
	@Config.Name("Level Cost Multiplier")
	public float levelCostMultiplier = 2.0F;

	@Config.Comment("Set to true to allow upgrading enchants only on enchanted books with one enchantment")
	@Config.Name("Only allow upgrading for single enchant books")
	public boolean onlyAllowOnBooks = false;

	@Config.Comment("Chance to turn into curse or to remove entirely instead of upgrading")
	@Config.Name("Cursing Chance")
	public float cursingChance = 0.1F;

	@Config.Comment("If set to true, randomly rolled curses will replace the upgraded enchant. If false, they will be an additional enchant")
	@Config.Name("Curses Replace Upgrade")
	public boolean cursesReplaceUpgrade = false;

	@Config.Comment("If upgrading is allowed on items with multiple enchants, upgrade them randomly (RANDOM), top to bottom (FIRST) or bottom to top (LAST)")
	@Config.Name("Selection mode")
	public String selectionMode = "RANDOM";

	@Config.Comment("Upgraded Enchant will have the current level minus x levels set in config (SUBTRACT), or it will have the min possible level of the enchant, usually 1 (MINLVL)")
	@Config.Name("Upgraded tier level mode")
	public String upgradedTierLevelMode = "MINLVL";

	@Config.Comment("How many lvls of the enchant to reduce by while upgrading in SUBTRACT mode. For example 1 means Prot 4 turns to Adv Prot 3. Does not apply for cursing")
	@Config.Name("Upgraded tier level reduction")
	public int enchantLvlsReduced = 1;

	@Config.Comment("Whether you can also upgrade the level of the enchants without changing the tier. This makes upgrading tier only possible at max lvl. Prot 3 -> Prot 4 -> Adv Prot x")
	@Config.Name("Allow level increase")
	public boolean allowLevelIncrease = true;

	@Config.Comment("Whether to increase anvil repair cost when upgrading")
	@Config.Name("Anvil repair cost increases")
	public boolean increaseAnvilRepairCost = false;

	@Config.Comment("Mode of how anvil repair cost is increased. ANVIL= normal exponential anvil behavior, ADD= add a flat amount, MULT= multiply by a number")
	@Config.Name("Anvil repair cost increase mode")
	public String anvilRepairMode = "ADD";

	@Config.Comment("How many levels to add or multiply anvil repair cost by when upgrading")
	@Config.Name("Anvil repair cost amount")
	public float anvilRepairCostAmount = 10.0F;

	@Config.Comment("How many bookshelves are needed to be able to upgrade")
	@Config.Name("Amount of bookshelves needed")
	public int bookshelvesNeeded = 30;
}
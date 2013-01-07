package uk.co.jacekk.bukkit.infiniteplots;

import java.util.Arrays;

import uk.co.jacekk.bukkit.baseplugin.v7.config.PluginConfigKey;

public class PlotConfig {
	
	public static final PluginConfigKey ADMIN_NAME			= new PluginConfigKey("admin-name", 		"Steve");
	public static final PluginConfigKey BUILDER_NAMES		= new PluginConfigKey("builder-names", 		Arrays.asList("Steve"));
	
	public static final PluginConfigKey FLAG_WATER_FLOW		= new PluginConfigKey("flag.water-flow", 	true);
	public static final PluginConfigKey FLAG_LAVA_FLOW		= new PluginConfigKey("flag.lava-flow", 	true);
	public static final PluginConfigKey FLAG_ICE_MELT		= new PluginConfigKey("flag.ice-melt", 		true);
	public static final PluginConfigKey FLAG_REDSTONE		= new PluginConfigKey("flag.redstone", 		true);
	public static final PluginConfigKey FLAG_BLOCK_FALL		= new PluginConfigKey("flag.block-fall", 	true);
	public static final PluginConfigKey FLAG_MOB_SPAWN		= new PluginConfigKey("flag.mob-spawn", 	true);
	
	public static final PluginConfigKey PROTECTION_BUILD	= new PluginConfigKey("protection.build", 	true);
	public static final PluginConfigKey PROTECTION_ENTER	= new PluginConfigKey("protection.enter", 	false);
	
}

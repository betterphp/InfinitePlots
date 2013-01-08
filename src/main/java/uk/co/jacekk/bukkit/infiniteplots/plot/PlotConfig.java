package uk.co.jacekk.bukkit.infiniteplots.plot;

import java.util.Arrays;

import uk.co.jacekk.bukkit.baseplugin.v8.config.PluginConfigKey;

public class PlotConfig {
	
	public static final PluginConfigKey LOCATION_WORLD_NAME	= new PluginConfigKey("location.world-name",	"");
	public static final PluginConfigKey LOCATION_X			= new PluginConfigKey("location.x",				0);
	public static final PluginConfigKey LOCATION_Z			= new PluginConfigKey("location.z",				0);
	
	public static final PluginConfigKey AUTH_ADMIN_NAME		= new PluginConfigKey("auth.admin-name", 		"");
	public static final PluginConfigKey AUTH_BUILDER_NAMES	= new PluginConfigKey("auth.builder-names", 	Arrays.asList());
	
	public static final PluginConfigKey FLAG_WATER_FLOW		= new PluginConfigKey("flag.water-flow", 		true);
	public static final PluginConfigKey FLAG_LAVA_FLOW		= new PluginConfigKey("flag.lava-flow", 		true);
	public static final PluginConfigKey FLAG_ICE_MELT		= new PluginConfigKey("flag.ice-melt", 			true);
	public static final PluginConfigKey FLAG_REDSTONE		= new PluginConfigKey("flag.redstone", 			true);
	public static final PluginConfigKey FLAG_BLOCK_FALL		= new PluginConfigKey("flag.block-fall", 		true);
	public static final PluginConfigKey FLAG_MOB_SPAWN		= new PluginConfigKey("flag.mob-spawn", 		true);
	
	public static final PluginConfigKey PROTECTION_BUILD	= new PluginConfigKey("protection.build", 		true);
	public static final PluginConfigKey PROTECTION_ENTER	= new PluginConfigKey("protection.enter", 		false);
	
}

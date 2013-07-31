package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfigKey;

/**
 * The config options stored in the main plugin config file.
 */
public class Config {
	
	public static final PluginConfigKey GENERATOR_ONLY			= new PluginConfigKey("generator-only",			false);
	public static final PluginConfigKey GRID_SIZE				= new PluginConfigKey("grid-size",				32);
	public static final PluginConfigKey GRID_HEIGHT				= new PluginConfigKey("grid-height",			20);
	public static final PluginConfigKey USE_SIGNS				= new PluginConfigKey("use-signs",				true);
	public static final PluginConfigKey TRACK_STATS				= new PluginConfigKey("track-stats",			true);
	public static final PluginConfigKey OWNER_PREFIX			= new PluginConfigKey("owner-prefix",			"Plot Owner");
	public static final PluginConfigKey RESET_DELAY				= new PluginConfigKey("reset.delay",			5);
	public static final PluginConfigKey RESET_PERTICK			= new PluginConfigKey("reset.pertick",			2000);
	public static final PluginConfigKey CLAIM_PROTECT_PATHS		= new PluginConfigKey("claim.protect-paths",	false);
	public static final PluginConfigKey CLAIM_MAX				= new PluginConfigKey("claim.max",				25);
	public static final PluginConfigKey CLAIM_MAX_UNUSED		= new PluginConfigKey("claim.max-unused",		4);
	public static final PluginConfigKey CLAIM_COST				= new PluginConfigKey("claim.cost",				0.0d);
	
	public static final PluginConfigKey BLOCKS_PATH				= new PluginConfigKey("blocks.path",				Material.DOUBLE_STEP.getId());
	public static final PluginConfigKey BLOCKS_PATH_DATA		= new PluginConfigKey("blocks.path-data", 			0);
	public static final PluginConfigKey BLOCKS_LOWER_WALL		= new PluginConfigKey("blocks.lower-wall",			Material.SMOOTH_BRICK.getId());
	public static final PluginConfigKey BLOCKS_LOWER_WALL_DATA	= new PluginConfigKey("blocks.lower-wall-data",		0);
	public static final PluginConfigKey BLOCKS_UPPER_WALL		= new PluginConfigKey("blocks.upper-wall",			Material.FENCE.getId());
	public static final PluginConfigKey BLOCKS_UPPER_WALL_DATA	= new PluginConfigKey("blocks.upper-wall-data",		0);
	public static final PluginConfigKey BLOCKS_SURFACE			= new PluginConfigKey("blocks.surface",				Material.GRASS.getId());
	public static final PluginConfigKey BLOCKS_GROUND			= new PluginConfigKey("blocks.ground",				Material.DIRT.getId());
	
}
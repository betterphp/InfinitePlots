package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.baseplugin.v8.config.PluginConfigKey;

/**
 * the config options stored in the main plugin config file.
 */
public class Config {
	
	public static final PluginConfigKey CLASSIC_MODE		= new PluginConfigKey("classic-mode",		false);
	
	public static final PluginConfigKey GRID_SIZE			= new PluginConfigKey("plots.size",			32);
	public static final PluginConfigKey GRID_HEIGHT			= new PluginConfigKey("plots.height",		20);
	
	public static final PluginConfigKey BLOCKS_BASE			= new PluginConfigKey("blocks_base",		Material.STONE.getId());
	public static final PluginConfigKey BLOCKS_SURFACE		= new PluginConfigKey("blocks.surface",		Material.GRASS.getId());
	public static final PluginConfigKey BLOCKS_PATH			= new PluginConfigKey("blocks.path",		Material.STEP.getId());
	public static final PluginConfigKey BLOCKS_LOWER_WALL	= new PluginConfigKey("blocks.lower-wall",	Material.SMOOTH_BRICK.getId());
	public static final PluginConfigKey BLOCKS_UPPER_WALL	= new PluginConfigKey("blocks.upper-wall",	Material.FENCE.getId());
	
	public static final PluginConfigKey BIOMES_PLOTS		= new PluginConfigKey("biomes.plots",		"PLAINS");
	public static final PluginConfigKey BIOMES_PATHS		= new PluginConfigKey("biomes.paths",		"PLAINS");
	
}
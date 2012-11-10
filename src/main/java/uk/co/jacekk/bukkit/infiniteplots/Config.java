package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.baseplugin.v5.config.PluginConfigKey;

public class Config {
	
	public static final PluginConfigKey PLOTS_SIZE				= new PluginConfigKey("plots.size",					64);
	public static final PluginConfigKey PLOTS_HEIGHT				= new PluginConfigKey("plots.height",				20);
	public static final PluginConfigKey PLOTS_RESTRICT_SPAWNING	= new PluginConfigKey("plors.restrict-spawning",	true);
	
	public static final PluginConfigKey BLOCKS_BASE				= new PluginConfigKey("blocks_base",				Material.STONE.getId());
	public static final PluginConfigKey BLOCKS_SURFACE			= new PluginConfigKey("blocks.surface",				Material.GRASS.getId());
	public static final PluginConfigKey BLOCKS_PATH				= new PluginConfigKey("blocks.path",				Material.STEP.getId());
	public static final PluginConfigKey BLOCKS_LOWER_WALL			= new PluginConfigKey("blocks.lower-wall",			Material.SMOOTH_BRICK.getId());
	public static final PluginConfigKey BLOCKS_UPPER_WALL			= new PluginConfigKey("blocks.upper-wall",			Material.FENCE.getId());
	
	public static final PluginConfigKey BIOMES_PLOTS				= new PluginConfigKey("biomes.plots",				"PLAINS");
	public static final PluginConfigKey BIOMES_PATHS				= new PluginConfigKey("biomes.paths",				"PLAINS");
	
}
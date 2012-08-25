package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfigKey;

public enum Config implements PluginConfigKey {
	
	PLOTS_SIZE(					"plots.size",				64),
	PLOTS_HEIGHT(				"plots.height",				20),
	PLOTS_RESTRICT_SPAWNING(	"plors.restrict-spawning",	true),
	
	BLOCKS_BASE(		"blocks_base",			Material.STONE.getId()),
	BLOCKS_SURFACE(		"blocks.surface",		Material.GRASS.getId()),
	BLOCKS_PATH(		"blocks.path",			Material.STEP.getId()),
	BLOCKS_LOWER_WALL(	"blocks.lower-wall",	Material.SMOOTH_BRICK.getId()),
	BLOCKS_UPPER_WALL(	"blocks.upper-wall",	Material.FENCE.getId());
	
	private String key;
	private Object defaultValue;
	
	private Config(String key, Object defaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public Object getDefault(){
		return this.defaultValue;
	}
	
}
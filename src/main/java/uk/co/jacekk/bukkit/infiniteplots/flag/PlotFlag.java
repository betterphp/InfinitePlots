package uk.co.jacekk.bukkit.infiniteplots.flag;

import java.util.HashMap;

import uk.co.jacekk.bukkit.baseplugin.v9.config.PluginConfigKey;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotConfig;

/**
 * A list of flags that can be applied to a plot, each of this is a boolean
 * value only. An enabled flag will allow the event it describes to take place,
 */
public enum PlotFlag {
	
	WATER_FLOW(PlotConfig.FLAG_WATER_FLOW),
	LAVA_FLOW(PlotConfig.FLAG_LAVA_FLOW),
	
	ICE_MELT(PlotConfig.FLAG_ICE_MELT),
	SNOW_FORM(PlotConfig.FLAG_SNOW_FORM),
	
	REDSTONE(PlotConfig.FLAG_REDSTONE),
	
	BLOCK_PHYSICS(PlotConfig.FLAG_BLOCK_PHYSICS),
	
	MONSTER_SPAWN(PlotConfig.FLAG_MONSTER_SPAWN),
	ANIMAL_SPAWN(PlotConfig.FLAG_ANIMAL_SPAWN);
	
	private PluginConfigKey configKey;
	private String name;
	
	private static final HashMap<String, PlotFlag> nameMap;
	
	private PlotFlag(PluginConfigKey configKey){
		this.configKey = configKey;
		
		String key = this.configKey.getKey();
		this.name = key.substring(key.lastIndexOf('.') + 1);
	}
	
	static{
		nameMap = new HashMap<String, PlotFlag>();
		
		for (PlotFlag flag : values()){
			nameMap.put(flag.getName(), flag);
		}
	}
	
	/**
	 * Gets the {@link PluginConfigKey} that defines the value of this flag.
	 * 
	 * @return The config key.
	 */
	public PluginConfigKey getConfigKey(){
		return this.configKey;
	}
	
	/**
	 * Gets the name of this flag.
	 * 
	 * @return The flags name.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets the plot flag from its name.
	 * 
	 * @param flag The name of the flag
	 * @return The flag or null if an invalid name was used.
	 */
	public static PlotFlag getFromName(String name){
		return nameMap.get(name);
	}
	
}

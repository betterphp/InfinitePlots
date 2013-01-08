package uk.co.jacekk.bukkit.infiniteplots.plot;

import uk.co.jacekk.bukkit.baseplugin.v8.BaseObject;
import uk.co.jacekk.bukkit.baseplugin.v8.config.PluginConfig;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;

public class Plot extends BaseObject<InfinitePlots> {
	
	private final PluginConfig config;
	private final PlotLocation location;
	
	public Plot(InfinitePlots plugin, PluginConfig config){
		super(plugin);
		
		this.config = config;
		this.location = new PlotLocation(config.getString(PlotConfig.LOCATION_WORLD_NAME), config.getInt(PlotConfig.LOCATION_X), config.getInt(PlotConfig.LOCATION_Z));
	}
	
	/**
	 * Gets the location of this plot.
	 * 
	 * @return The {@link PlotLocation} of this plot.
	 */
	public PlotLocation getLocation(){
		return this.location;
	}
	
}

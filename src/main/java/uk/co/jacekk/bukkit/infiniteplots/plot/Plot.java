package uk.co.jacekk.bukkit.infiniteplots.plot;

import java.io.File;

import uk.co.jacekk.bukkit.baseplugin.v8.BaseObject;
import uk.co.jacekk.bukkit.baseplugin.v8.config.PluginConfig;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;

public class Plot extends BaseObject<InfinitePlots> {
	
	private PlotLocation location;
	
	private File configFile;
	private PluginConfig config;
	
	public Plot(InfinitePlots plugin, PlotLocation location){
		super(plugin);
		
		this.location = location;
		
		this.configFile = new File(plugin.getBaseDirPath() + File.separator + "plots" + File.separator + location.getWorldName() + "_" + location.getX() + "_" + location.getZ() + ".yml");
		this.config = new PluginConfig(this.configFile, PlotConfig.class, plugin.log);
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

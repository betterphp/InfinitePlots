package uk.co.jacekk.bukkit.infiniteplots.plot;

import java.io.File;
import java.util.HashMap;

import uk.co.jacekk.bukkit.baseplugin.v8.BaseObject;
import uk.co.jacekk.bukkit.baseplugin.v8.config.PluginConfig;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;

public class PlotManager extends BaseObject<InfinitePlots> {
	
	private HashMap<PlotLocation, Plot> plots;
	
	public PlotManager(InfinitePlots plugin){
		super(plugin);
		
		this.plots = new HashMap<PlotLocation, Plot>();
		
		for (File configFile : plugin.getPlotsDir().listFiles()){
			if (configFile.isFile() && configFile.getName().endsWith(".yml")){
				Plot plot = new Plot(plugin, new PluginConfig(configFile, PlotConfig.class, plugin.log));
				
				this.plots.put(plot.getLocation(), plot);
			}
		}
	}
	
	/**
	 * Gets the total number of plots being managed.
	 * 
	 * @return The number of plots.
	 */
	public int getTotal(){
		return this.plots.size();
	}
	
	/**
	 * Checks to see if a plot is claimed at the given location.
	 * 
	 * @param location A {@link PlotLocation} to check for
	 * @return True if the plot is claimed.
	 */
	public boolean plotExistsAt(PlotLocation location){
		return this.plots.containsKey(location);
	}
	
	/**
	 * Gets the {@link Plot} at a location.
	 * 
	 * @param location A {@link PlotLocation} to get the plot for.
	 * @return The {@link Plot} or null if one does not exist.
	 */
	public Plot getPlotAt(PlotLocation location){
		return this.plots.get(location);
	}
	
	/**
	 * Creates a new plot, note that this is not a physical plot in
	 * the world but rather an interface to the claim system.
	 * 
	 * @param location The {@link PlotLocation} of the plot.
	 * @param force If set to true existing plots will be replaced.
	 * @return The {@link Plot} that was created or null on failure.
	 */
	public Plot createPlotAt(PlotLocation location, boolean force){
		if (!force && this.plotExistsAt(location)){
			return null;
		}
		
		String worldName = location.getWorldName();
		int x = location.getX();
		int z = location.getZ();
		
		File configFile = new File(plugin.getBaseDirPath() + File.separator + "plots" + File.separator + worldName + "_" + x + "_" + z + ".yml");
		PluginConfig config = new PluginConfig(configFile, PlotConfig.class, plugin.log);
		
		config.set(PlotConfig.LOCATION_WORLD_NAME, worldName);
		config.set(PlotConfig.LOCATION_X, x);
		config.set(PlotConfig.LOCATION_Z, z);
		
		Plot plot = new Plot(plugin, config);
		
		this.plots.put(location, plot);
		
		return plot;
	}
	
	/**
	 * Removes a plot.
	 * 
	 * @param location The {@link PlotLocation} of the plot.
	 */
	public void removePlotAt(PlotLocation location){
		this.plots.remove(location);
	}
	
}

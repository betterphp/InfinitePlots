package uk.co.jacekk.bukkit.infiniteplots.plot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.jacekk.bukkit.baseplugin.v9.BaseObject;
import uk.co.jacekk.bukkit.baseplugin.v9.config.PluginConfig;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;

/**
 * Used to manage the plots that are defined on the server.
 */
public class PlotManager extends BaseObject<InfinitePlots> {
	
	private HashMap<PlotLocation, Plot> plots;
	
	public PlotManager(InfinitePlots plugin){
		super(plugin);
		
		this.plots = new HashMap<PlotLocation, Plot>();
		
		for (File configFile : plugin.getPlotsDir().listFiles()){
			if (configFile.isFile() && configFile.getName().endsWith(".yml")){
				Plot plot = new Plot(plugin, configFile, new PluginConfig(configFile, PlotConfig.class, plugin.log));
				
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
	 * Gets all of the plots that a player is the owner of.
	 * 
	 * @param playerName The name of the player.
	 * @return The list of plots they own.
	 */
	public List<Plot> getOwnedPlots(String playerName){
		ArrayList<Plot> plots = new ArrayList<Plot>();
		
		for (Plot plot : this.plots.values()){
			if (plot.getAdmin().equalsIgnoreCase(playerName)){
				plots.add(plot);
			}
		}
		
		return plots;
	}
	
	/**
	 * Gets all of the plots that a player is the owner of in a world.
	 * 
	 * @param playerName The name of the player.
	 * @param worldName The name of the world.
	 * @return The list of plots they own in that world.
	 */
	public List<Plot> getOwnedPlots(String playerName, String worldName){
		ArrayList<Plot> plots = new ArrayList<Plot>();
		
		for (Plot plot : this.plots.values()){
			if (plot.getAdmin().equalsIgnoreCase(playerName) && plot.getLocation().getWorldName().equals(worldName)){
				plots.add(plot);
			}
		}
		
		return plots;
	}
	
	/**
	 * Gets all of the plots that a player can build in.
	 * 
	 * @param playerName The name of the player.
	 * @return The list of plots where they can build.
	 */
	public List<Plot> getBuildablePlots(String playerName){
		ArrayList<Plot> plots = new ArrayList<Plot>();
		
		for (Plot plot : this.plots.values()){
			if (plot.canBuild(playerName)){
				plots.add(plot);
			}
		}
		
		return plots;
	}
	
	/**
	 * Gets a plot by its name
	 * 
	 * @param playerName The name of the player owning the plot
	 * @param plotName The name of the plot
	 * @return The {@link Plot} or null if one was not found
	 */
	public Plot getPlotByName(String playerName, String plotName){
		for (Plot plot : this.plots.values()){
			if (plot.getAdmin().equalsIgnoreCase(playerName) && plot.getName().equals(plotName)){
				return plot;
			}
		}
		
		return null;
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
		
		Plot plot = new Plot(plugin, configFile, config);
		
		this.plots.put(location, plot);
		
		return plot;
	}
	
	/**
	 * Removes a plot.
	 * 
	 * @param location The {@link PlotLocation} of the plot.
	 */
	public void removePlotAt(PlotLocation location){
		Plot plot = this.plots.get(location);
		
		if (plot != null){
			plot.getConfigFile().delete();
			this.plots.remove(location);
		}
	}
	
}

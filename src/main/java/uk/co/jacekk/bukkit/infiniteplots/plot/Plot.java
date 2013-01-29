package uk.co.jacekk.bukkit.infiniteplots.plot;

import java.util.Arrays;
import java.util.List;

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
	
	/**
	 * Gets the player that is the admin of this plot.
	 * 
	 * @return The player name.
	 */
	public String getAdmin(){
		return this.config.getString(PlotConfig.AUTH_ADMIN_NAME);
	}
	
	/**
	 * Sets the admin of this plot.
	 * 
	 * @param admin The name of the player.
	 */
	public void setAdmin(String admin){
		this.config.set(PlotConfig.AUTH_ADMIN_NAME, admin);
	}
	
	/**
	 * Gets all of the players (not including the plot admin) that are allowed to build in tis plot.
	 * 
	 * @return The list of player names.
	 */
	public List<String> getBuilders(){
		return this.config.getStringList(PlotConfig.AUTH_BUILDER_NAMES);
	}
	
	/**
	 * Adds a builder to this plot.
	 * 
	 * @param playerName The name of the player to add.
	 */
	public void addBuilder(String playerName){
		List<String> builders = this.getBuilders();
		builders.add(playerName.toLowerCase());
		this.config.set(PlotConfig.AUTH_BUILDER_NAMES, builders);
	}
	
	/**
	 * Remvoed a builder from this plot.
	 * 
	 * @param playerName The name of the player to remove.
	 */
	public void removeBuilder(String playerName){
		List<String> builders = this.getBuilders();
		builders.remove(playerName.toLowerCase());
		this.config.set(PlotConfig.AUTH_BUILDER_NAMES, builders);
	}
	
	/**
	 * Removes all builders from this plot.
	 */
	public void removeAllBuilders(){
		this.config.set(PlotConfig.AUTH_BUILDER_NAMES, Arrays.asList(new String[0]));
	}
	
	/**
	 * Checks to see if a player can build in this plot.
	 * 
	 * <p>
	 * 	A player that is not able to build should not be able to
	 * 	interact with the environment in any way at all inside
	 * 	the plot area.
	 * <p>
	 * 
	 * @param playerName The name of the player to test.
	 * @return True if the player can build, false if not.
	 */
	public boolean canBuild(String playerName){
		return this.getAdmin().equalsIgnoreCase(playerName) || this.getBuilders().contains(playerName);
	}
	
}

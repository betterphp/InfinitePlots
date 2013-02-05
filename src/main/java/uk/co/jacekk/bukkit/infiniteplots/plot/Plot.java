package uk.co.jacekk.bukkit.infiniteplots.plot;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import uk.co.jacekk.bukkit.baseplugin.v8.BaseObject;
import uk.co.jacekk.bukkit.baseplugin.v8.config.PluginConfig;
import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.flag.PlotFlag;

/**
 * Represents a plot in the world.
 */
public class Plot extends BaseObject<InfinitePlots> {
	
	private final File configFile;
	private final PluginConfig config;
	private final PlotLocation location;
	
	public Plot(InfinitePlots plugin, File configFile, PluginConfig config){
		super(plugin);
		
		this.configFile = configFile;
		this.config = config;
		this.location = new PlotLocation(config.getString(PlotConfig.LOCATION_WORLD_NAME), config.getInt(PlotConfig.LOCATION_X), config.getInt(PlotConfig.LOCATION_Z));
	}
	
	/**
	 * Gets the file used to store this plots config.
	 * 
	 * @return The {@link File}
	 */
	public File getConfigFile(){
		return this.configFile;
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
	 * Gets the limits of the plot.
	 * 
	 * @return An array of X,Z coordinates [x1, z1, x2, z2]
	 */
	public int[] getBuildLimits(){
		int x1 = (int) (Math.floor(((this.location.getX() * InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE)) / InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE)) * InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE)) + 4);
		int z1 = (int) (Math.floor(((this.location.getZ() * InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE)) / InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE)) * InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE)) + 4);
		int x2 = x1 + InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE) - 8;
		int z2 = z1 + InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE) - 8;
		int[] ret = {x1, z1, x2, z2};
		return ret;
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
	 * @param admin
	 *            The name of the player.
	 */
	public void setAdmin(String admin){
		this.config.set(PlotConfig.AUTH_ADMIN_NAME, admin);
	}
	
	/**
	 * Gets all of the players (not including the plot admin) that are allowed
	 * to build in this plot.
	 * 
	 * @return The list of player names.
	 */
	public List<String> getBuilders(){
		return this.config.getStringList(PlotConfig.AUTH_BUILDER_NAMES);
	}
	
	/**
	 * Adds a builder to this plot.
	 * 
	 * @param playerName
	 *            The name of the player to add.
	 */
	public void addBuilder(String playerName){
		List<String> builders = this.getBuilders();
		builders.add(playerName.toLowerCase());
		this.config.set(PlotConfig.AUTH_BUILDER_NAMES, builders);
	}
	
	/**
	 * Removes a builder from this plot.
	 * 
	 * @param playerName
	 *            The name of the player to remove.
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
	 * A player that is not able to build should not be able to interact with
	 * the environment in any way at all inside the plot area.
	 * <p>
	 * 
	 * @param playerName
	 *            The name of the player to test.
	 * @return True if the player can build, false if not.
	 */
	public boolean canBuild(String playerName){
		return this.getAdmin().equalsIgnoreCase(playerName) || this.getBuilders().contains(playerName);
	}
	
	/**
	 * Checks to see if a specific flag is enabled for this plot.
	 * 
	 * <p>
	 * An enabled flag will allow the event it describes to take place,
	 * </p>
	 * 
	 * @param flag
	 * @return
	 */
	public boolean isFlagEnabled(PlotFlag flag){
		return this.config.getBoolean(flag.getConfigKey());
	}
	
	public void setFlag(PlotFlag flag, boolean value){
		this.config.set(flag.getConfigKey(), value);
	}
}

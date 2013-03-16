package uk.co.jacekk.bukkit.infiniteplots.plot;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v9_1.BaseObject;
import uk.co.jacekk.bukkit.baseplugin.v9_1.config.PluginConfig;
import uk.co.jacekk.bukkit.infiniteplots.BlockChangeTask;
import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.flag.PlotFlag;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation.Direction;

/**
 * Represents a plot in the world.
 */
public class Plot extends BaseObject<InfinitePlots> {
	
	private final File configFile;
	private final PluginConfig config;
	private final PlotLocation location;
	
	private int[] plotLimits;
	private int[] buildLimits;
	
	public Plot(InfinitePlots plugin, File configFile, PluginConfig config){
		super(plugin);
		
		this.configFile = configFile;
		this.config = config;
		this.location = new PlotLocation(config.getString(PlotConfig.LOCATION_WORLD_NAME), config.getInt(PlotConfig.LOCATION_X), config.getInt(PlotConfig.LOCATION_Z));
		
		int x1 = (int) Math.floor(((this.location.getX() * plugin.config.getInt(Config.GRID_SIZE)) / plugin.config.getInt(Config.GRID_SIZE)) * plugin.config.getInt(Config.GRID_SIZE));
		int z1 = (int) Math.floor(((this.location.getZ() * plugin.config.getInt(Config.GRID_SIZE)) / plugin.config.getInt(Config.GRID_SIZE)) * plugin.config.getInt(Config.GRID_SIZE));
		int x2 = x1 + plugin.config.getInt(Config.GRID_SIZE);
		int z2 = z1 + plugin.config.getInt(Config.GRID_SIZE);
		
		this.plotLimits = new int[]{x1, z1, x2, z2};
		this.buildLimits = new int[]{x1 + 4, z1 + 4, x2 - 4, z2 - 4};
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
	 * Gets the name of this plot
	 * 
	 * @return The plot name
	 */
	public String getName(){
		return this.config.getString(PlotConfig.INFO_NAME);
	}
	
	/**
	 * Sets the name of this plot
	 * 
	 * @param name The new name for the plot
	 */
	public void setName(String name){
		this.config.set(PlotConfig.INFO_NAME, name);
	}
	
	/**
	 * Gets the limits of the plot.
	 * 
	 * @return An array of X,Z coordinates [x1, z1, x2, z2]
	 */
	public int[] getBuildLimits(){
		return this.buildLimits;
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
	 * Gets all of the players (not including the plot admin) that are allowed
	 * to build in this plot.
	 * 
	 * @return The list of player names.
	 */
	public List<String> getBuilders(){
		return this.config.getStringList(PlotConfig.AUTH_BUILDER_NAMES);
	}
	
	/**
	 * Checks to see if a player can build in this plot.
	 * 
	 * <p>
	 * A player that is not able to build should not be able to interact with
	 * the environment in any way at all inside the plot area.
	 * <p>
	 * 
	 * @param playerName The name of the player to test.
	 * @return True if the player can build, false if not.
	 */
	public boolean canBuild(String playerName){
		return (this.getAdmin().equalsIgnoreCase(playerName) || this.getBuilders().contains(playerName.toLowerCase()));
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
	 * Removes a builder from this plot.
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
	 * Checks to see if a player is within the buildable area of a plot.
	 *
	 * @param player The {@link Player} to check.
	 * @param location The {@link Location} to check.
	 * @return True if the player is in the area false if not.
	 */
	public boolean withinBuildableArea(Player player, Location location){
		String playerName = player.getName();
		int x = location.getBlockX();
		int z = location.getBlockZ();
		
		if (plugin.config.getBoolean(Config.CLAIM_PROTECT_PATHS)){
			return (x >= this.buildLimits[0] && z >= this.buildLimits[1] && x <= this.buildLimits[2] && z <= this.buildLimits[3]);
		}
		
		if (x < this.plotLimits[0] || z < this.plotLimits[1] || x > this.plotLimits[2] || z > this.plotLimits[3]){
			return false;
		}
		
		Plot plot0 = plugin.getPlotManager().getPlotAt(this.location.getRelative(Direction.SOUTH));
		Plot plot1 = plugin.getPlotManager().getPlotAt(this.location.getRelative(Direction.WEST));
		Plot plot2 = plugin.getPlotManager().getPlotAt(this.location.getRelative(Direction.NORTH));
		Plot plot3 = plugin.getPlotManager().getPlotAt(this.location.getRelative(Direction.EAST));
		
		if ((plot0 == null || !plot0.canBuild(playerName)) && x < this.buildLimits[0]){
			return false;
		}
		
		if ((plot1 == null || !plot1.canBuild(playerName)) && z < this.buildLimits[1]){
			return false;
		}
		
		if ((plot2 == null || !plot2.canBuild(playerName)) && x > this.buildLimits[2]){
			return false;
		}
		
		if ((plot3 == null || !plot3.canBuild(playerName)) && z > this.buildLimits[3]){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks to see if a specific flag is enabled for this plot.
	 * 
	 * <p>
	 * An enabled flag will allow the event it describes to take place,
	 * </p>
	 * 
	 * @param flag The flag to check
	 * @return The value of the flag for this plot
	 */
	public boolean isFlagEnabled(PlotFlag flag){
		return this.config.getBoolean(flag.getConfigKey());
	}
	
	/**
	 * Sets a flag for this plot.
	 * 
	 * <p>
	 * An enabled flag will allow the event it describes to take place,
	 * </p>
	 * 
	 * @param flag The flag to set.
	 * @param value The value to set this flag to.
	 */
	public void setFlag(PlotFlag flag, boolean value){
		this.config.set(flag.getConfigKey(), value);
	}
	
	/**
	 * Sets the biome for the plot.
	 * 
	 * @param biome The biome to set.
	 */
	public void setBiome(Biome biome){
		World world = plugin.server.getWorld(this.location.getWorldName());
		
		for (int x = this.buildLimits[0]; x <= this.buildLimits[2]; ++x){
			for (int z = this.buildLimits[1]; z <= this.buildLimits[3]; ++z){
				world.setBiome(x, z, biome);
			}
		}
	}
	
	/**
	 * Gets the biome for this plot
	 * 
	 * @return The {@link Biome} used
	 */
	public Biome getBiome(){
		World world = plugin.server.getWorld(this.location.getWorldName());
		return world.getBiome(this.buildLimits[0], this.buildLimits[1]);
	}
	
	/**
	 * Regenerates the buildable region of this plot.
	 */
	public void regenerate(){
		World world = plugin.server.getWorld(this.location.getWorldName());
		
		int worldHeight = world.getMaxHeight();
		int gridHeight = plugin.config.getInt(Config.GRID_HEIGHT);
		
		BlockChangeTask task = new BlockChangeTask(plugin);
		
		Material surface = Material.getMaterial(plugin.config.getInt(Config.BLOCKS_SURFACE));
		Material ground = Material.getMaterial(plugin.config.getInt(Config.BLOCKS_GROUND));
		
		for (int x = this.buildLimits[0]; x <= this.buildLimits[2]; ++x){
			for (int z = this.buildLimits[1]; z <= this.buildLimits[3]; ++z){
				task.setBlockType(world.getBlockAt(x, 0, z), Material.BEDROCK);
				
				for (int y = 1; y < gridHeight; ++y){
					task.setBlockType(world.getBlockAt(x, y, z), ground);
				}
				
				task.setBlockType(world.getBlockAt(x, gridHeight, z), surface);
				
				for (int y = gridHeight + 1; y < worldHeight; ++y){
					task.setBlockType(world.getBlockAt(x, y, z), Material.AIR);
				}
				
				world.setBiome(x, z, Biome.PLAINS);
			}
		}
		
		task.start(plugin.config.getInt(Config.RESET_DELAY), plugin.config.getInt(Config.RESET_PERTICK));
	}
	
	/**
	 * Places a sign at all corners of the plot with the owners name.
	 */
	public void createSigns(){
		int[] buildLimits = this.getBuildLimits();
		
		int x3 = buildLimits[0];
		int z3 = buildLimits[1] + (InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE) - 7);
		int x4 = buildLimits[2];
		int z4 = buildLimits[3] - (InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE) - 7);
		int y = plugin.config.getInt(Config.GRID_HEIGHT);
		World world = plugin.getServer().getWorld(this.getLocation().getWorldName());
		
		Block cornerOne = world.getBlockAt(buildLimits[0] - 1, y + 2, buildLimits[1] - 1);
		Block cornerTwo = world.getBlockAt(buildLimits[2] + 1, y + 2, buildLimits[3] + 1);
		Block cornerThree = world.getBlockAt(x3 - 1, y + 2, z3);
		Block cornerFour = world.getBlockAt(x4 + 1, y + 2, z4);
		
		cornerOne.setType(Material.SIGN_POST);
		cornerTwo.setType(Material.SIGN_POST);
		cornerThree.setType(Material.SIGN_POST);
		cornerFour.setType(Material.SIGN_POST);
		
		// north west
		Sign signOne = (Sign) cornerOne.getState();
		// south east
		Sign signTwo = (Sign) cornerTwo.getState();
		// north east
		Sign signThree = (Sign) cornerThree.getState();
		// south west
		Sign signFour = (Sign) cornerFour.getState();
		
		signOne.setRawData((byte) 0x6);
		signTwo.setRawData((byte) 0xE);
		signThree.setRawData((byte) 0x2);
		signFour.setRawData((byte) 0xA);
		
		signOne.setLine(1, plugin.config.getString(Config.OWNER_PREFIX));
		signOne.setLine(2, this.getAdmin());
		signTwo.setLine(1, plugin.config.getString(Config.OWNER_PREFIX));
		signTwo.setLine(2, this.getAdmin());
		signThree.setLine(1, plugin.config.getString(Config.OWNER_PREFIX));
		signThree.setLine(2, this.getAdmin());
		signFour.setLine(1, plugin.config.getString(Config.OWNER_PREFIX));
		signFour.setLine(2, this.getAdmin());
		
		signOne.update();
		signTwo.update();
		signThree.update();
		signFour.update();
	}
	
	/**
	 * Removes the signs from the plot corners.
	 */
	public void removeSigns(){
		int[] buildLimits = this.getBuildLimits();
		int x3 = buildLimits[0];
		int z3 = buildLimits[1] + (InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE) - 7);
		int x4 = buildLimits[2];
		int z4 = buildLimits[3] - (InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE) - 7);
		int y = plugin.config.getInt(Config.GRID_HEIGHT);
		World world = plugin.getServer().getWorld(this.getLocation().getWorldName());
		
		Block cornerOne = world.getBlockAt(buildLimits[0] - 1, y + 2, buildLimits[1] - 1);
		Block cornerTwo = world.getBlockAt(buildLimits[2] + 1, y + 2, buildLimits[3] + 1);
		Block cornerThree = world.getBlockAt(x3 - 1, y + 2, z3);
		Block cornerFour = world.getBlockAt(x4 + 1, y + 2, z4);
		
		cornerOne.setType(Material.AIR);
		cornerTwo.setType(Material.AIR);
		cornerThree.setType(Material.AIR);
		cornerFour.setType(Material.AIR);
	}
	
}

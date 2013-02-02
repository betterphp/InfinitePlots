package uk.co.jacekk.bukkit.infiniteplots;

import java.io.File;

import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import uk.co.jacekk.bukkit.baseplugin.v8.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.v8.config.PluginConfig;
import uk.co.jacekk.bukkit.infiniteplots.command.AddBuilderCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.ClaimCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.flag.RestrictSpawningListener;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotManager;
import uk.co.jacekk.bukkit.infiniteplots.protection.BuildListener;

public class InfinitePlots extends BasePlugin {
	
	private static InfinitePlots instance;
	
	private File plotsDir;
	private PlotManager plotManager;
	
	public void onEnable(){
		super.onEnable(true);
		
		instance = this;
		
		this.plotsDir =  new File(this.baseDirPath + File.separator + "plots");
		
		if (!this.plotsDir.exists()){
			this.plotsDir.mkdirs();
		}
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		this.plotManager = new PlotManager(this);
		
		if (this.config.getBoolean(Config.RESTRICT_SPAWNING)){
			this.pluginManager.registerEvents(new RestrictSpawningListener(this), this);
		}
		
		this.pluginManager.registerEvents(new WorldInitListener(this), this);
		
		this.commandManager.registerCommandExecutor(new ClaimCommandExecutor(this));
		this.commandManager.registerCommandExecutor(new AddBuilderCommandExecutor(this));
		
		this.pluginManager.registerEvents(new BuildListener(this), this);
	}
	
	public void onDisable(){
		instance = null;
	}
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		int size = this.config.getInt(Config.GRID_SIZE);
		int height = this.config.getInt(Config.GRID_HEIGHT);
		
		byte pathId = (byte) this.config.getInt(Config.BLOCKS_PATH);
		byte pathData = (byte) this.config.getInt(Config.BLOCKS_PATH_DATA);
		byte wallLowerId = (byte) this.config.getInt(Config.BLOCKS_LOWER_WALL);
		byte wallLowerData = (byte) this.config.getInt(Config.BLOCKS_LOWER_WALL_DATA);
		byte wallUpperId = (byte) this.config.getInt(Config.BLOCKS_UPPER_WALL);
		byte wallUpperData = (byte) this.config.getInt(Config.BLOCKS_UPPER_WALL_DATA);
		
		return new PlotsGenerator(size, height, pathId, pathData, wallLowerId, wallLowerData, wallUpperId, wallUpperData);
	}
	
	public static InfinitePlots getInstance(){
		return instance;
	}
	
	/**
	 * Gets the folder used for plot data files.
	 * 
	 * @return The {@link File} for the folder.
	 */
	public File getPlotsDir(){
		return this.plotsDir;
	}
	
	/**
	 * Gets the plot manager.
	 * 
	 * @return The {@link PlotManager}.
	 */
	public PlotManager getPlotManager(){
		return this.plotManager;
	}
	
}
package uk.co.jacekk.bukkit.infiniteplots;

import java.io.File;

import org.bukkit.generator.ChunkGenerator;

import uk.co.jacekk.bukkit.baseplugin.v8.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.v8.config.PluginConfig;
import uk.co.jacekk.bukkit.infiniteplots.flag.RestrictSpawningListener;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotManager;

public class InfinitePlots extends BasePlugin {
	
	private PlotManager plotManager;
	
	public void onEnable(){
		super.onEnable(true);
		
		File plotDir =  new File(this.baseDirPath + File.separator + "plots");
		
		if (!plotDir.exists()){
			plotDir.mkdirs();
		}
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		this.plotManager = new PlotManager(this);
		
		this.pluginManager.registerEvents(new RestrictSpawningListener(this), this);
		this.pluginManager.registerEvents(new WorldInitListener(this), this);
	}
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		int size = this.config.getInt(Config.GRID_SIZE);
		int height = this.config.getInt(Config.GRID_HEIGHT);
		
		return new PlotsGenerator(size, height);
	}
	
	public PlotManager getPlotManager(){
		return this.plotManager;
	}
	
}
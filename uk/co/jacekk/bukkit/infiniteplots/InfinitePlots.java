package uk.co.jacekk.bukkit.infiniteplots;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class InfinitePlots extends JavaPlugin {
	
	protected Server server;
	protected PluginManager pluginManager;
	protected InfinitePlotsLogger log;
	
	protected InfinitePlotsConfig config;
	
	public void onEnable(){
		this.server = this.getServer();
		this.pluginManager = this.server.getPluginManager();
		this.log = new InfinitePlotsLogger("Minecraft", this);
		
		this.config = new InfinitePlotsConfig(new File(this.getDataFolder().getAbsolutePath() + File.separator + "config.yml"));
		
		if (this.config.getBoolean("plots.restrict-spawning")){
			this.pluginManager.registerEvents(new InfinitePlotsEntityListener(), this);
		}
		
		this.pluginManager.registerEvents(new InfinitePlotsWorldListener(this), this);
		
		this.log.info("Enabled.");
	}
	
	public void onDisable(){
		this.log.info("Disabled.");
		
		this.log = null;
		
		this.config = null;
	}
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		int size = (Integer) ((id != null && id.matches("[-+]?\\d+(\\.\\d+)?")) ? Integer.parseInt(id) : this.config.getInt("plots.size"));
		int height = this.config.getInt("plots.height");
		
		byte baseId = (byte) this.config.getInt("blocks.base");
		byte surfaceId = (byte) this.config.getInt("blocks.surface");
		byte pathId = (byte) this.config.getInt("blocks.path");
		byte wallLowerId = (byte) this.config.getInt("blocks.lower-wall");
		byte wallUpperId = (byte) this.config.getInt("blocks.upper-wall");
		
		return new InfinitePlotsGenerator(this, size, height, baseId, surfaceId, pathId, wallLowerId, wallUpperId);
	}
	
}

package uk.co.jacekk.bukkit.infiniteplots;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;

public class InfinitePlotsLogger {
	
	private InfinitePlots plugin;
	private Logger logger;
	
	public InfinitePlotsLogger(String logger, InfinitePlots plugin){
		this.plugin = plugin;
		this.logger = Logger.getLogger(logger);
	}
	
	private String buildString(String msg){
		PluginDescriptionFile pdFile = plugin.getDescription();
		
		return pdFile.getName() + " " + pdFile.getVersion() + ": " + msg;
	}
	
	public void info(String msg){
		this.logger.info(this.buildString(msg));
	}
	
	public void warn(String msg){
		this.logger.warning(this.buildString(msg));
	}
	
	public void severe(String msg){
		this.logger.severe(this.buildString(msg));
	}
	
}

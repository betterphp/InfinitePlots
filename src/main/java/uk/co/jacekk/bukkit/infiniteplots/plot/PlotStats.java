package uk.co.jacekk.bukkit.infiniteplots.plot;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;

/**
 * Represents a collection of statistics for this plot.
 */
public class PlotStats {
	
	private final Plot plot;
	private final PluginConfig config;
	
	public PlotStats(Plot plot, PluginConfig config){
		this.plot = plot;
		this.config = config;
	}
	
	/**
	 * Gets the plot that these stats are for.
	 * 
	 * @return The plot.
	 */
	public Plot getPlot(){
		return this.plot;
	}
	
	/**
	 * Gets the number of blocks that have been broken in this plot.
	 * 
	 * @return The number.
	 */
	public int getBlocksBroken(){
		return this.config.getInt(PlotConfig.STATS_BLOCKS_BROKEN);
	}
	
	/**
	 * Adds one to the number of blocks that have been broken.
	 */
	public void addBlockBreak(){
		this.config.set(PlotConfig.STATS_BLOCKS_BROKEN, this.getBlocksBroken() + 1);
	}
	
	/**
	 * Gets the number of blocks that have been placed in this plot.
	 * 
	 * @return The number.
	 */
	public int getBlocksPlaced(){
		return this.config.getInt(PlotConfig.STATS_BLOCKS_PLACED);
	}
	
	/**
	 * Adds one to the number of blocks that have been placed.
	 */
	public void addBlockPlace(){
		this.config.set(PlotConfig.STATS_BLOCKS_PLACED, this.getBlocksPlaced() + 1);
	}
	
}

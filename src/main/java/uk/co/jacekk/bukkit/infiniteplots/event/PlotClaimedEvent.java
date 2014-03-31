package uk.co.jacekk.bukkit.infiniteplots.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;

public class PlotClaimedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Plot plot;
	private Player player;
	
	/**
	 * Event that occurs when a player claims a plot.
	 * 
	 * @param plot The plot being claimed
	 * @param player The player claiming the plot
	 */
	public PlotClaimedEvent(Plot plot, Player player){
		this.plot = plot;
		this.player = player;
	}
	
	/**
	 * Gets the plot being claimed.
	 * 
	 * @return Plot The plot
	 */
	public Plot getPlot(){
		return plot;
	}
	
	/**
	 * Gets the player claiming the plot.
	 * 
	 * @return Player The player.
	 */
	public Player getPlayer(){
		return player;
	}
	
	@Override
    public HandlerList getHandlers(){
        return handlers;
    }
	
    public static HandlerList getHandlerList(){
        return handlers;
    }
    
}
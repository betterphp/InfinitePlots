package uk.co.jacekk.bukkit.infiniteplots.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;

public class PlotUnclaimedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Plot plot;
	private Player player;
	
	/**
	 * Event that occurs when a player forfeits their claim to a plot.
	 * 
	 * @param plot The plot being unclaimed
	 * @param player The player unclaiming the plot
	 */
	public PlotUnclaimedEvent(Plot plot, Player player){
		this.plot = plot;
		this.player = player;
	}
	
	/**
	 * Gets the plot being unclaimed
	 * 
	 * @return Plot The plot.
	 */
	public Plot getPlot(){
		return plot;
	}
	
	/**
	 * Gets the player unclaiming the plot.
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
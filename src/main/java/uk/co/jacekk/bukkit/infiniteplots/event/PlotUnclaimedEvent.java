package uk.co.jacekk.bukkit.infiniteplots.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;

public class PlotUnclaimedEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	Plot plot;
	Player player;
	
	/**
	 * Event that occurs when a player forfeits their claim to a plot.
	 * 
	 * @param plot The plot being unclaimed
	 * @param player The player unclaiming the plot
	 */
	public PlotUnclaimedEvent(Plot plot, Player player) {
		this.plot = plot;
		this.player = player;
	}
	
	/**
	 * Gets the plot being unclaimed
	 */
	public Plot getPlot() {
		return plot;
	}
	
	/**
	 * Gets the player unclaiming the plot.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/** 
	 * HandlerList for FlagChangedEvent
	 * 
	 * @return A list of event handlers, stored per-event. Based on lahwran's fevents
	 */
	@Override
    public HandlerList getHandlers() {
        return handlers;
    }
	
	/** 
	 * Static HandlerList for FlagChangedEvent
	 * 
	 * @return A list of event handlers, stored per-event. Based on lahwran's fevents
	 */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

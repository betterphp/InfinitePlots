package uk.co.jacekk.bukkit.infiniteplots.plot.decorator;

import uk.co.jacekk.bukkit.baseplugin.BaseObject;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;

/**
 * Represents a plot decorator, these build the terrain of the plots.
 */
public abstract class PlotDecorator extends BaseObject<InfinitePlots> {
	
	public PlotDecorator(InfinitePlots plugin){
		super(plugin);
	}
	
	/**
	 * Apply the decoration to a plot.
	 * 
	 * @param plot The plot.
	 */
	public abstract void decorate(Plot plot);
	
}

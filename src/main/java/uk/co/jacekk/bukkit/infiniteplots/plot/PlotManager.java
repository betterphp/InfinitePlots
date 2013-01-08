package uk.co.jacekk.bukkit.infiniteplots.plot;

import java.util.HashMap;

import uk.co.jacekk.bukkit.baseplugin.v8.BaseObject;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;

public class PlotManager extends BaseObject<InfinitePlots> {
	
	private HashMap<PlotLocation, Plot> plots;
	
	public PlotManager(InfinitePlots plugin){
		super(plugin);
		
		this.plots = new HashMap<PlotLocation, Plot>();
	}
	
}

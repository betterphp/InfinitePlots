package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class PlotStatsListener extends BaseListener<InfinitePlots> {
	
	public PlotStatsListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		if (event.getBlock().getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(event.getBlock().getLocation()));
			
			if (plot != null){
				plot.getStats().addBlockBreak();
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreak(BlockPlaceEvent event){
		if (event.getBlock().getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(event.getBlock().getLocation()));
			
			if (plot != null){
				plot.getStats().addBlockPlace();
			}
		}
	}
	
}

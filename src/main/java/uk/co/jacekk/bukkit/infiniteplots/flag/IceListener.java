package uk.co.jacekk.bukkit.infiniteplots.flag;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;

import uk.co.jacekk.bukkit.baseplugin.v8.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class IceListener extends BaseListener<InfinitePlots> {
	
	public IceListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockMelt(BlockFadeEvent event){
		Block block = event.getBlock();
		
		if (block.getType() == Material.ICE && block.getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(block.getLocation()));
			
			if (plot == null){
				return;
			}
			
			if (!plot.isFlagEnabled(PlotFlag.ICE_MELT)){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockForm(BlockFormEvent event){
		Block block = event.getBlock();
		
		if (block.getType() == Material.SNOW && block.getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(block.getLocation()));
			
			if (plot == null){
				return;
			}
			
			if (!plot.isFlagEnabled(PlotFlag.SNOW_FORM)){
				event.setCancelled(true);
			}
		}
	}
	
}

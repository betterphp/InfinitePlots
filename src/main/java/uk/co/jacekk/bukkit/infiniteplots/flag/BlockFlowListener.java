package uk.co.jacekk.bukkit.infiniteplots.flag;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFromToEvent;

import uk.co.jacekk.bukkit.baseplugin.v8.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class BlockFlowListener extends BaseListener<InfinitePlots> {
	
	public BlockFlowListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockFlow(BlockFromToEvent event){
		if (event.getBlock().getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(event.getToBlock().getLocation()));
			
			if (plot == null){
				return;
			}
			
			Material type = event.getBlock().getType();
			
			if (type == Material.WATER || type == Material.STATIONARY_WATER){
				if (!plot.isFlagEnabled(PlotFlag.WATER_FLOW)){
					event.setCancelled(true);
				}
			}else if (type == Material.LAVA || type == Material.STATIONARY_LAVA){
				if (!plot.isFlagEnabled(PlotFlag.LAVA_FLOW)){
					event.setCancelled(true);
				}
			}
		}
	}
	
}

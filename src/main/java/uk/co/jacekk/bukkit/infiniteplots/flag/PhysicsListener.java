package uk.co.jacekk.bukkit.infiniteplots.flag;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

import uk.co.jacekk.bukkit.baseplugin.v8.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class PhysicsListener extends BaseListener<InfinitePlots> {
	
	public PhysicsListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onRedstoneUpdate(BlockRedstoneEvent event){
		Block block = event.getBlock();
		
		if (block.getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(block.getLocation()));
			
			if (plot == null){
				event.setNewCurrent(0);
				return;
			}
			
			if (!plot.isFlagEnabled(PlotFlag.REDSTONE)){
				event.setNewCurrent(0);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent event){
		Block block = event.getBlock();
		
		if (block.getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(block.getLocation()));
			
			if (plot == null){
				event.setCancelled(true);
				return;
			}
			
			if (!plot.isFlagEnabled(PlotFlag.BLOCK_PHYSICS)){
				event.setCancelled(true);
			}
		}
	}
	
}

package uk.co.jacekk.bukkit.infiniteplots.protection;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import uk.co.jacekk.bukkit.baseplugin.v9.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class BuildListener extends BaseListener<InfinitePlots> {
	
	public BuildListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event){
		if (event.getBlock().getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(event.getBlock().getLocation()));
			
			if (plot == null || !plot.canBuild(event.getPlayer().getName())){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		if (event.getBlock().getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(event.getBlock().getLocation()));
			
			if (plot == null || !plot.canBuild(event.getPlayer().getName())){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onInteractBlock(PlayerInteractEvent event){
		if (event.getPlayer().getWorld().getGenerator() instanceof PlotsGenerator){
			Player player = event.getPlayer();
			Action action = event.getAction();
			
			if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK){
				Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(event.getClickedBlock().getLocation()));
				
				if (plot == null || !plot.canBuild(player.getName())){
					event.setCancelled(true);
				}
			}else{
				Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(player.getLocation()));
				
				if (plot == null || !plot.canBuild(player.getName())){
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onInteractEntity(PlayerInteractEntityEvent event){
		if (event.getPlayer().getWorld().getGenerator() instanceof PlotsGenerator){
			Player player = event.getPlayer();
			Entity target = event.getRightClicked();
			
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(target.getLocation()));
			
			if (plot == null || !plot.canBuild(player.getName())){
				event.setCancelled(true);
			}
		}
	}
	
}

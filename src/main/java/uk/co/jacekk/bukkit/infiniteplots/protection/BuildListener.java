package uk.co.jacekk.bukkit.infiniteplots.protection;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class BuildListener extends BaseListener<InfinitePlots> {
	
	public BuildListener(InfinitePlots plugin){
		super(plugin);
	}
	
	private boolean canBuild(Player player, Location location){
		if (!(location.getWorld().getGenerator() instanceof PlotsGenerator)){
			return true;
		}
		
		if (Permission.PLOT_BUILD_ALL.has(player)){
			return true;
		}
		
		Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(location));
		
		if (plot == null || !plot.canBuild(player)){
			return false;
		}
		
		return plot.withinBuildableArea(player, location);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event){
		if (!this.canBuild(event.getPlayer(), event.getBlock().getLocation())){
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		if (!this.canBuild(event.getPlayer(), event.getBlock().getLocation())){
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onInteractBlock(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action action = event.getAction();
		Location location = (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) ? event.getClickedBlock().getLocation() : player.getLocation();
		
		if (!this.canBuild(event.getPlayer(), location)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onInteractEntity(PlayerInteractEntityEvent event){
		if (!this.canBuild(event.getPlayer(), event.getRightClicked().getLocation())){
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event){
		if (event.getLocation().getWorld().getGenerator() instanceof PlotsGenerator){
			event.blockList().clear();
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPistonExtend(BlockPistonExtendEvent event){
		Block piston = event.getBlock();
		if (!PlotLocation.isInPlotWorld(piston.getLocation())){
			return;
		}
			
		Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(piston.getLocation()));
		
		if (plot == null){
			event.setCancelled(true);
			return;
		}
		
		for (Block block : event.getBlocks()){
			Plot blockPlot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(block.getLocation()));
			
			if (blockPlot == null || (blockPlot.isBuildProtected() && !plot.getLocation().equals(blockPlot.getLocation()) && !plot.getAdmin().equals(blockPlot.getAdmin()))){
				event.setCancelled(true);
				return;
			}
		}
	}
	
}

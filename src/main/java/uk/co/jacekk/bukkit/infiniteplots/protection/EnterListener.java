package uk.co.jacekk.bukkit.infiniteplots.protection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class EnterListener extends BaseListener<InfinitePlots> {
	
	public EnterListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event){
		Location from = event.getFrom();
		Location to = event.getTo();
		
		if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()){
			return;
		}
		
		if (!(to.getWorld().getGenerator() instanceof PlotsGenerator)){
			return;
		}
		
		Player player = event.getPlayer();
		
		if (Permission.PLOT_BUILD_ALL.has(player)){
			return;
		}
		
		Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(to));
		
		if (plot != null && !plot.canEnter(player.getName()) && plot.withinBuildableArea(player, to)){
			event.setCancelled(true);
		}
	}
	
}

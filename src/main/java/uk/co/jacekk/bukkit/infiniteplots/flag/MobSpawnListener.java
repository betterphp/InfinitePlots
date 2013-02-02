package uk.co.jacekk.bukkit.infiniteplots.flag;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.co.jacekk.bukkit.baseplugin.v8.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class MobSpawnListener extends BaseListener<InfinitePlots>{

	public MobSpawnListener(InfinitePlots plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.getLocation().getWorld().getGenerator() instanceof PlotsGenerator)
		{
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(event.getLocation()));
			
			if (plot == null) {
				return;
			}
			
			if (!plot.canSpawnMobs()){
				event.setCancelled(true);
			}
		}
	}
}

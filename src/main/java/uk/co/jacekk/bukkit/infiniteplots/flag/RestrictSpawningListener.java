package uk.co.jacekk.bukkit.infiniteplots.flag;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.co.jacekk.bukkit.baseplugin.v7.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.PlotsGenerator;

public class RestrictSpawningListener extends BaseListener<InfinitePlots> {
	
	public RestrictSpawningListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		if (event.getLocation().getWorld().getGenerator() instanceof PlotsGenerator){
			event.setCancelled(true);
		}
	}
	
}

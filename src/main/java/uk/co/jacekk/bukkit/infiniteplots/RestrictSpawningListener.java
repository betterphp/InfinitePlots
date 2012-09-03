package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.co.jacekk.bukkit.baseplugin.v1.event.BaseListener;

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

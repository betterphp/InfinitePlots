package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class InfinitePlotsEntityListener implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		if (event.getLocation().getWorld().getGenerator() instanceof InfinitePlotsGenerator){
			event.setCancelled(true);
		}
	}
	
}

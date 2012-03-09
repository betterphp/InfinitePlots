package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class InfinitePlotsEntityListener implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		if (event.isCancelled()) return;
		
		if (event.getLocation().getWorld().getGenerator() instanceof InfinitePlotsGenerator){
			event.setCancelled(true);
		}
	}
	
}

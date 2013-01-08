package uk.co.jacekk.bukkit.infiniteplots.flag;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.co.jacekk.bukkit.baseplugin.v8.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;

public class RestrictSpawningListener extends BaseListener<InfinitePlots> {
	
	public RestrictSpawningListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		
	}
	
}

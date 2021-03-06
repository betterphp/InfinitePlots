package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.world.WorldInitEvent;

import net.minecraft.server.v1_9_R1.WorldServer;
import net.minecraft.server.v1_9_R1.WorldType;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;

public class WorldInitListener extends BaseListener<InfinitePlots> {
	
	public WorldInitListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onWorldInit(WorldInitEvent event){
		World world = event.getWorld();
		
		if (world.getGenerator() instanceof PlotsGenerator){
			this.plugin.getPlotManager().loadPlotsFor(world);
			
			WorldServer worldServer = ((CraftWorld) world).getHandle();
			worldServer.worldData.a(WorldType.FLAT);
			
			plugin.log.info("Changed the world type of '" + world.getName() + "' to flat (this makes the void blue down to y = 0).");
		}
	}
	
}

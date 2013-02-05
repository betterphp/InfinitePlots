package uk.co.jacekk.bukkit.infiniteplots.flag;

import java.util.ArrayList;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import uk.co.jacekk.bukkit.baseplugin.v9.event.BaseListener;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class MobSpawnListener extends BaseListener<InfinitePlots>{
	
	private ArrayList<EntityType> monsterTypes;
	private ArrayList<EntityType> animalTypes;
	
	public MobSpawnListener(InfinitePlots plugin) {
		super(plugin);
		
		this.monsterTypes = new ArrayList<EntityType>();
		this.animalTypes = new ArrayList<EntityType>();
		
		this.monsterTypes.add(EntityType.ZOMBIE);
		this.monsterTypes.add(EntityType.SKELETON);
		this.monsterTypes.add(EntityType.ENDERMAN);
		this.monsterTypes.add(EntityType.SPIDER);
		this.monsterTypes.add(EntityType.CAVE_SPIDER);
		this.monsterTypes.add(EntityType.ENDER_DRAGON);
		this.monsterTypes.add(EntityType.SLIME);
		this.monsterTypes.add(EntityType.GHAST);
		this.monsterTypes.add(EntityType.GIANT);
		this.monsterTypes.add(EntityType.MAGMA_CUBE);
		this.monsterTypes.add(EntityType.CREEPER);
		this.monsterTypes.add(EntityType.PIG_ZOMBIE);
		this.monsterTypes.add(EntityType.WITCH);
		this.monsterTypes.add(EntityType.WITHER);
		this.monsterTypes.add(EntityType.BLAZE);
		this.monsterTypes.add(EntityType.SILVERFISH);
		
		this.animalTypes.add(EntityType.SHEEP);
		this.animalTypes.add(EntityType.COW);
		this.animalTypes.add(EntityType.BAT);
		this.animalTypes.add(EntityType.PIG);
		this.animalTypes.add(EntityType.WOLF);
		this.animalTypes.add(EntityType.IRON_GOLEM);
		this.animalTypes.add(EntityType.CHICKEN);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		if (event.getLocation().getWorld().getGenerator() instanceof PlotsGenerator){
			Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(event.getLocation()));
			
			if (plot == null){
				event.setCancelled(true);
				return;
			}
			
			if (event.getSpawnReason() == SpawnReason.NATURAL){
				EntityType type = event.getEntityType();
				
				if (this.monsterTypes.contains(type)){
					if (!plot.isFlagEnabled(PlotFlag.MONSTER_SPAWN)){
						event.setCancelled(true);
					}
				}else if (this.animalTypes.contains(type)){
					if (!plot.isFlagEnabled(PlotFlag.ANIMAL_SPAWN)){
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
}

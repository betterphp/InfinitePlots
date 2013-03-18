package uk.co.jacekk.bukkit.infiniteplots.converters;

import java.io.File;
import java.net.URISyntaxException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/**
 * Responsible for converting InfiniteClaims plots to the new plot system.
 */
public class IClaimsConverter {
	
	private InfinitePlots plugin;
	private File iClaimsPlotFile;
	private World world;
	
	public IClaimsConverter(InfinitePlots instance, World world){
		this.plugin = instance;
		this.world = world;
		this.iClaimsPlotFile = new File(this.getInfiniteClaimsDirectory() + File.separator + world.getName() + File.separator + "plots.yml");
	}
	
	private String getInfiniteClaimsDirectory(){
		File jarLocation;
		try{
			jarLocation = new File(this.plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		}catch (URISyntaxException e){
			jarLocation = new File(this.plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		}
		
		return jarLocation.getParent() + File.separator + "InfiniteClaims";
	}
	
	public void start(){
		YamlConfiguration plotsFile = YamlConfiguration.loadConfiguration(iClaimsPlotFile);
		MemorySection plots = (MemorySection) plotsFile.get("plots");
		
		for (String key : plots.getKeys(false)){
			String plotOwner = key;
			MemorySection plotData = (MemorySection) plots.get(plotOwner);
			for (String plotKey : plotData.getKeys(false)){
				String plotName = plotKey;
				int x = plotData.getInt(plotName + ".x");
				int y = plugin.config.getInt(Config.GRID_HEIGHT);
				int z = plotData.getInt(plotName + ".z");
				
				Plot plot = plugin.getPlotManager().createPlotAt(PlotLocation.fromWorldLocation(new Location(this.world, x, y, z)), true);
				
				plot.setAdmin(plotOwner);
				plot.setName(plotName);
				plot.createSigns();
				
				Plugin wgPlugin = plugin.pluginManager.getPlugin("WorldGuard");
				if (wgPlugin != null && wgPlugin instanceof WorldGuardPlugin){
					// plugin.log.info("WorldGuard detected, attempting to add builders to plots");
					WorldGuardPlugin wg = (WorldGuardPlugin) plugin.pluginManager.getPlugin("WorldGuard");
					
					RegionManager mgr = wg.getRegionManager(world);
					
					ProtectedRegion plotRegion = mgr.getRegion(plotOwner + plotName);
					
					if (plotRegion.getMembers().size() > 0){
						DefaultDomain plotBuilders = plotRegion.getMembers();
						for (String builder : plotBuilders.getPlayers()){
							// plugin.log.info("Adding " + builder + "to plot "
							// + plot.toString());
							plot.addBuilder(builder);
						}
					}
				}
			}
		}
	}
}

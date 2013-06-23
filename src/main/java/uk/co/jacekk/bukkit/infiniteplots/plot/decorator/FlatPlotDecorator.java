package uk.co.jacekk.bukkit.infiniteplots.plot.decorator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

import uk.co.jacekk.bukkit.infiniteplots.BlockChangeTask;
import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;

/**
 * Creates a flat plot, the default.
 */
public class FlatPlotDecorator extends PlotDecorator {
	
	private Material groundBlock;
	private Material surfaceBlock;
	private byte groundData;
	private byte surfaceData;
	
	/**
	 * @param plugin The InfinitePlots instance.
	 * @param groundBlock The block type to use for the ground.
	 * @param surfaceBlock The block type to use for the surface.
	 */
	public FlatPlotDecorator(InfinitePlots plugin, Material groundBlock, Material surfaceBlock, byte groundData, byte surfaceData){
		super(plugin);
		
		this.groundBlock = groundBlock;
		this.surfaceBlock = surfaceBlock;
		this.groundData = groundData;
		this.surfaceData = surfaceData;
	}
	
	@Override
	public void decorate(Plot plot){
		World world = plugin.server.getWorld(plot.getLocation().getWorldName());
		
		int worldHeight = world.getMaxHeight();
		int gridHeight = plugin.config.getInt(Config.GRID_HEIGHT);
		
		BlockChangeTask task = new BlockChangeTask(plugin, world);
		
		int[] buildLimits = plot.getBuildLimits();
		
		for (int x = buildLimits[0]; x <= buildLimits[2]; ++x){
			for (int z = buildLimits[1]; z <= buildLimits[3]; ++z){
				task.setBlockType(world.getBlockAt(x, 0, z), Material.BEDROCK);
				
				for (int y = 1; y < gridHeight; ++y){
					task.setBlockType(world.getBlockAt(x, y, z), this.groundBlock, this.groundData);
				}
				
				task.setBlockType(world.getBlockAt(x, gridHeight, z), this.surfaceBlock, this.surfaceData);
				
				for (int y = gridHeight + 1; y < worldHeight; ++y){
					task.setBlockType(world.getBlockAt(x, y, z), Material.AIR);
				}
				
				world.setBiome(x, z, Biome.PLAINS);
			}
		}
		
		task.start(plugin.config.getInt(Config.RESET_DELAY), plugin.config.getInt(Config.RESET_PERTICK));
	}
	
}

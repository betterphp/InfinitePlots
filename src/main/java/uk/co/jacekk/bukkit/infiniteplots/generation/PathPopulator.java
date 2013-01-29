package uk.co.jacekk.bukkit.infiniteplots.generation;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 * Responsible for decorating a world with structures such as paths.
 */
public class PathPopulator extends BlockPopulator {
	
	private int size;
	private int height;
	
	public PathPopulator(int size, int height){
		this.size = size;
		this.height = height;
	}
	
	@Override
	public void populate(World world, Random random, Chunk chunk){
		int chunkX = chunk.getX() * 16;
		int chunkZ = chunk.getZ() * 16;
		
		for (int cx = 0; cx < 16; ++cx){
			for (int cz = 0; cz < 16; ++cz){
				int x = chunkX + cx;
				int y = this.height;
				int z = chunkZ + cz;
				
				if (x % this.size == 0 && ((z - 1) % this.size != 0 && z % this.size != 0 && (z + 1) % this.size != 0)){
					// North - South
					world.getBlockAt(x - 2, y, z).setTypeIdAndData(Material.LOG.getId(), (byte) 9, false);
					world.getBlockAt(x - 1, y, z).setTypeIdAndData(Material.LOG.getId(), (byte) 9, false);
					world.getBlockAt(x, y, z).setTypeIdAndData(Material.LOG.getId(), (byte) 9, false);
					world.getBlockAt(x + 1, y, z).setTypeIdAndData(Material.LOG.getId(), (byte) 9, false);
					world.getBlockAt(x + 2, y, z).setTypeIdAndData(Material.LOG.getId(), (byte) 9, false);
					
					world.getBlockAt(x - 2, y + 1, z).setTypeIdAndData(Material.WOOD_STEP.getId(), (byte) 1, false);
					world.getBlockAt(x + 2, y + 1, z).setTypeIdAndData(Material.WOOD_STEP.getId(), (byte) 1, false);
				}
				
				if (z % this.size == 0 && ((x - 1) % this.size != 0 && x % this.size != 0 && (x + 1) % this.size != 0)){
					// East - West
					world.getBlockAt(x, y, z - 2).setTypeIdAndData(Material.LOG.getId(), (byte) 5, false);
					world.getBlockAt(x, y, z - 1).setTypeIdAndData(Material.LOG.getId(), (byte) 5, false);
					world.getBlockAt(x, y, z).setTypeIdAndData(Material.LOG.getId(), (byte) 5, false);
					world.getBlockAt(x, y, z + 1).setTypeIdAndData(Material.LOG.getId(), (byte) 5, false);
					world.getBlockAt(x, y, z + 2).setTypeIdAndData(Material.LOG.getId(), (byte) 5, false);
					
					world.getBlockAt(x, y + 1, z - 2).setTypeIdAndData(Material.WOOD_STEP.getId(), (byte) 1, false);
					world.getBlockAt(x, y + 1, z + 2).setTypeIdAndData(Material.WOOD_STEP.getId(), (byte) 1, false);
				}
				
				if (x % this.size == 0 && z % this.size == 0){
					// Junction
					world.getBlockAt(x, y - 1, z).setType(Material.REDSTONE_TORCH_ON);
					
					world.getBlockAt(x, y, z).setType(Material.REDSTONE_LAMP_ON);
					world.getBlockAt(x + 1, y, z).setType(Material.REDSTONE_LAMP_ON);
					world.getBlockAt(x - 1, y, z).setType(Material.REDSTONE_LAMP_ON);
					world.getBlockAt(x, y, z + 1).setType(Material.REDSTONE_LAMP_ON);
					world.getBlockAt(x, y, z - 1).setType(Material.REDSTONE_LAMP_ON);
					
					world.getBlockAt(x + 1, y, z + 1).setType(Material.GLOWSTONE);
					world.getBlockAt(x + 1, y, z - 1).setType(Material.GLOWSTONE);
					world.getBlockAt(x - 1, y, z + 1).setType(Material.GLOWSTONE);
					world.getBlockAt(x - 1, y, z - 1).setType(Material.GLOWSTONE);
				}
			}
		}
	}
	
}

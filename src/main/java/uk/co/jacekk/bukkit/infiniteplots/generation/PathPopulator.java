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
	private byte pathId;
	private byte pathData;
	private byte wallLowerId;
	private byte wallLowerData;
	private byte wallUpperId;
	private byte wallUpperData;
	
	public PathPopulator(int size, int height, byte pathId, byte pathData, byte wallLowerId, byte wallLowerData, byte wallUpperId, byte wallUpperData){
		this.size = size;
		this.height = height;
		this.pathId = pathId;
		this.pathData = pathData;
		this.wallLowerId = wallLowerId;
		this.wallLowerData = wallLowerData;
		this.wallUpperId = wallUpperId;
		this.wallUpperData = wallUpperData;
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
				
				if (x % this.size == 0 && ((z - 2) % this.size != 0 && (z - 1) % this.size != 0 && z % this.size != 0 && (z + 1) % this.size != 0 && (z + 2) % this.size != 0)){
					// North - South
					world.getBlockAt(x - 2, y, z).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x - 1, y, z).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x, y, z).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x + 1, y, z).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x + 2, y, z).setTypeIdAndData(this.pathId, this.pathData, false);
					//Lower wall
					world.getBlockAt(x - 3, y, z).setTypeIdAndData(this.wallLowerId, this.wallLowerData, false);
					world.getBlockAt(x + 3, y, z).setTypeIdAndData(this.wallLowerId, this.wallLowerData, false);
					//Upper wall
					world.getBlockAt(x - 3, y + 1, z).setTypeIdAndData(this.wallUpperId, this.wallUpperData, false);
					world.getBlockAt(x + 3, y + 1, z).setTypeIdAndData(this.wallUpperId, this.wallUpperData, false);
				}
				
				if (z % this.size == 0 && ((x - 2) % this.size != 0 && (x - 1) % this.size != 0 && x % this.size != 0 && (x + 1) % this.size != 0 && (x + 2) % this.size != 0)){
					// East - West
					world.getBlockAt(x, y, z - 2).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x, y, z - 1).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x, y, z).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x, y, z + 1).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x, y, z + 2).setTypeIdAndData(this.pathId, this.pathData, false);
					//Lower wall
					world.getBlockAt(x, y, z - 3).setTypeIdAndData(this.wallLowerId, this.wallLowerData, false);
					world.getBlockAt(x, y, z + 3).setTypeIdAndData(this.wallLowerId, this.wallLowerData, false);
					//Upper wall
					world.getBlockAt(x, y + 1, z - 3).setTypeIdAndData(this.wallUpperId, this.wallUpperData, false);
					world.getBlockAt(x, y + 1, z + 3).setTypeIdAndData(this.wallUpperId, this.wallUpperData, false);
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
					
					world.getBlockAt(x + 2, y, z + 2).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x + 2, y, z - 2).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x - 2, y, z + 2).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x - 2, y, z - 2).setTypeIdAndData(this.pathId, this.pathData, false);
					
					world.getBlockAt(x + 1, y, z + 2).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x + 1, y, z - 2).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x + 2, y, z + 1).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x + 2, y, z - 1).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x - 1, y, z + 2).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x - 1, y, z - 2).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x - 2, y, z + 1).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x - 2, y, z - 1).setTypeIdAndData(this.pathId, this.pathData, false);
					
					world.getBlockAt(x + 2, y, z).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x - 2, y, z).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x, y, z + 2).setTypeIdAndData(this.pathId, this.pathData, false);
					world.getBlockAt(x, y, z - 2).setTypeIdAndData(this.pathId, this.pathData, false);
				}
			}
		}
	}
	
}

package uk.co.jacekk.bukkit.infiniteplots.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

/**
 * Responsible for building the ground of a world 
 */
public class PlotsGenerator extends ChunkGenerator {
	
	private int size;
	private int height;
	
	private byte pathId;
	private byte pathData;
	private byte wallLowerId;
	private byte wallLowerData;
	private byte wallUpperId;
	private byte wallUpperData;
	private byte surfaceId;
	private byte groundId;
	
	public PlotsGenerator(int size, int height, byte pathId, byte pathData, byte wallLowerId, byte wallLowerData, byte wallUpperId, byte wallUpperData, byte surfaceId, byte groundId){
		this.size = size;
		this.height = height;
		
		this.pathId = pathId;
		this.pathData = pathData;
		this.wallLowerId = wallLowerId;
		this.wallLowerData = wallLowerData;
		this.wallUpperId = wallUpperId;
		this.wallUpperData = wallUpperData;
		this.surfaceId = surfaceId;
		this.groundId = groundId;
	}
	
	/**
	 * Gets the grid size used by this generator
	 * 
	 * @return The grid size
	 */
	public int getGridSize(){
		return this.size;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world){
		ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
		
		populators.add(new PathPopulator(this.size, this.height, this.pathId, this.pathData, this.wallLowerId, this.wallLowerData, this.wallUpperId, this.wallUpperData));
		
		return populators;
	}
	
	@Override
	public Location getFixedSpawnLocation(World world, Random rand){
		return new Location(world, 0, this.height + 1, 0);
	}
	
	private void setBlockAt(byte[][] chunk, int x, int y, int z, byte typeId){
		chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = typeId;
	}
	
	@Override
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes){
		byte[][] chunk = new byte[8][4096];
		
		for (int x = 0; x < 16; ++x){
			for (int z = 0; z < 16; ++z){
				this.setBlockAt(chunk, x, 0, z, (byte) Material.BEDROCK.getId());
				
				for (int y = 1; y < this.height; ++y){
					this.setBlockAt(chunk, x, y, z, this.groundId);
				}
				
				this.setBlockAt(chunk, x, this.height, z, this.surfaceId);
				
				biomes.setBiome(x, z, Biome.PLAINS);
			}
		}
		
		return chunk;
	}
	
}

package uk.co.jacekk.bukkit.infiniteplots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class InfinitePlotsGenerator extends ChunkGenerator {
	
	private int plotSize;
	private int plotSizeBy2;
	
	private int plotHeight;
	
	private byte bedId;
	private byte baseId;
	private byte surfaceId;
	private byte hiddenId;
	private byte pathId;
	private byte wallLowerId;
	private byte wallUpperId;
	
	public InfinitePlotsGenerator(InfinitePlots instance, int size, int height, byte baseId, byte surfaceId, byte pathId, byte wallLowerId, byte wallUpperId){
		this.plotSize = size + 7;
		this.plotSizeBy2 = this.plotSize / 2;
		
		this.plotHeight = height;
		
		this.bedId = (byte) Material.BEDROCK.getId();
		this.baseId = (baseId == (byte) Material.GRASS.getId()) ? (byte) Material.DIRT.getId() : baseId;
		this.surfaceId = surfaceId;
		this.hiddenId = (this.surfaceId == (byte) Material.GRASS.getId()) ? (byte) Material.DIRT.getId() : this.surfaceId;
		this.pathId = pathId;
		this.wallLowerId = wallLowerId;
		this.wallUpperId = wallUpperId;
	}
	
	public int getPlotSize(){
		return this.plotSize - 7;
	}
	
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return new ArrayList<BlockPopulator>();
	}
	
	public Location getFixedSpawnLocation(World world, Random rand){
		return new Location(world, 0, 18, 0);
	}
	
	public int coordsToByte(int x, int y, int z){
		return (x * 16 + z) * 128 + y;
	}
	
	private boolean isPathBlock(int x, int z){
		if ((x % this.plotSize == 0) || (z % this.plotSize == 0)) return true;
		
		if (((x + 1) % this.plotSize == 0) || ((z + 1) % this.plotSize == 0)) return true;
		
		if (((x - 1) % this.plotSize == 0) || ((z - 1) % this.plotSize == 0)) return true;
		
		if (((x + 2) % this.plotSize == 0) || ((z + 2) % this.plotSize == 0)) return true;
		
		if (((x - 2) % this.plotSize == 0) || ((z - 2) % this.plotSize == 0)) return true;
		
		return false;
	}
	
	private boolean isWallBlock(int x, int z){
		if (((x + 3) % this.plotSize == 0) || ((z + 3) % this.plotSize == 0)) return true;
		
		if (((x - 3) % this.plotSize == 0) || ((z - 3) % this.plotSize == 0)) return true;
		
		return false;
	}
	
	private boolean isGateBlock(int x, int z){
		double pos = this.plotSizeBy2 + 0.5;
		x += this.plotSizeBy2;
		
		if ((x % pos == 0) && ((z + 3) % this.plotSize == 0)) return true;
		
		if (((x + 1) % pos == 0) && ((z + 3) % this.plotSize == 0)) return true;
		
		if (((x - 1) % pos == 0) && ((z + 3) % this.plotSize == 0)) return true;
		
		return false;
	}
	
	public byte[] generate(World world, Random random, int chunkX, int chunkZ){
		byte[] blocks = new byte[32768];
		int x, y, z;
		
		int worldChunkX = chunkX * 16;
		int worldChunkZ = chunkZ * 16;
		
		for (x = 0; x < 16; ++x){
			for (z = 0; z < 16; ++z){
				blocks[this.coordsToByte(x, 0, z)] = this.bedId;
				
				for (y = 1; y < this.plotHeight; ++y){
					blocks[this.coordsToByte(x, y, z)] = this.baseId;
				}
				
				if (this.isGateBlock(worldChunkX + x, worldChunkZ + z) || this.isPathBlock(worldChunkX + x, worldChunkZ + z)){
					blocks[this.coordsToByte(x, this.plotHeight, z)] = this.hiddenId;
					blocks[this.coordsToByte(x, this.plotHeight + 1, z)] = this.pathId;
				}else if (this.isWallBlock(worldChunkX + x, worldChunkZ + z)){
					blocks[this.coordsToByte(x, this.plotHeight, z)] = (byte) ((this.surfaceId == Material.GRASS.getId()) ? Material.DIRT.getId() : this.surfaceId);
					blocks[this.coordsToByte(x, this.plotHeight + 1, z)] = this.wallLowerId;
					blocks[this.coordsToByte(x, this.plotHeight + 2, z)] = this.wallUpperId;
				}else{
					blocks[this.coordsToByte(x, this.plotHeight, z)] = this.surfaceId;
				}
			}
		}
		
		return blocks;
	}

}

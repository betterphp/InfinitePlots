package uk.co.jacekk.bukkit.infiniteplots.nms;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.minecraft.server.v1_9_R1.Block;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.Blocks;
import net.minecraft.server.v1_9_R1.Chunk;
import net.minecraft.server.v1_9_R1.EntitySlice;
import net.minecraft.server.v1_9_R1.IBlockData;
import net.minecraft.server.v1_9_R1.World;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;

public class ChunkWrapper extends Chunk {
	
	private Chunk chunk;
	private int[] buildLimits;
	
	public ChunkWrapper(Chunk chunk, int[] buildLimits){
		super(chunk.world, chunk.locX, chunk.locZ);
		
		this.chunk = chunk;
		this.buildLimits = buildLimits;
        
        try {
	        ReflectionUtils.setFieldValue(Chunk.class, "sections", this, this.chunk.getSections());
	        this.a(this.chunk.getBiomeIndex()); // Sets this.f
	        ReflectionUtils.setFieldValue(Chunk.class, "g", this, ReflectionUtils.getFieldValue(Chunk.class, "g", int[].class, this.chunk));
	        ReflectionUtils.setFieldValue(Chunk.class, "h", this, ReflectionUtils.getFieldValue(Chunk.class, "h", boolean[].class, this.chunk));
	        ReflectionUtils.setFieldValue(Chunk.class, "tileEntities", this, ReflectionUtils.getFieldValue(Chunk.class, "tileEntities", Map.class, this.chunk));
	        ReflectionUtils.setFieldValue(Chunk.class, "w", this, ReflectionUtils.getFieldValue(Chunk.class, "w", int.class, this.chunk));
	        ReflectionUtils.setFieldValue(Chunk.class, "x", this, ReflectionUtils.getFieldValue(Chunk.class, "x", ConcurrentLinkedQueue.class, this.chunk));
	        ReflectionUtils.setFieldValue(Chunk.class, "entitySlices", this, ReflectionUtils.getFieldValue(Chunk.class, "entitySlices", EntitySlice[].class, this.chunk));
	        ReflectionUtils.setFieldValue(Chunk.class, "world", this, ReflectionUtils.getFieldValue(Chunk.class, "world", World.class, this.chunk));
	        ReflectionUtils.setFieldValue(Chunk.class, "locX", this, ReflectionUtils.getFieldValue(Chunk.class, "locX", int.class, this.chunk));
	        ReflectionUtils.setFieldValue(Chunk.class, "locZ", this, ReflectionUtils.getFieldValue(Chunk.class, "locZ", int.class, this.chunk));
	        ReflectionUtils.setFieldValue(Chunk.class, "heightMap", this, ReflectionUtils.getFieldValue(Chunk.class, "heightMap", int[].class, this.chunk));
        }catch (NoSuchFieldException e){
        	e.printStackTrace();
        }
        
        this.bukkitChunk = this.chunk.bukkitChunk;
	}
	
	// Check the build limits when setting blocks in this chunk
	public IBlockData a(BlockPosition blockposition, IBlockData iblockdata){
		int wx = (this.chunk.locX * 16) + blockposition.getX();
		int wz = (this.chunk.locZ * 16) + blockposition.getZ();
		
		if (wx >= this.buildLimits[0] && wz >= this.buildLimits[1] && wx <= this.buildLimits[2] && wz <= this.buildLimits[3]){
			return this.chunk.a(blockposition, iblockdata);
		}
		
		return null;
	}
	
	// Return air for everything outside the build limits
	public IBlockData a(final int x, final int y, final int z){
		int wx = (this.chunk.locX * 16) + x;
		int wz = (this.chunk.locZ * 16) + z;
		
		if (wx >= this.buildLimits[0] && wz >= this.buildLimits[1] && wx <= this.buildLimits[2] && wz <= this.buildLimits[3]){
			return this.chunk.a(x, y, z);
		}
		
		return Blocks.AIR.getBlockData();
	}
	
}

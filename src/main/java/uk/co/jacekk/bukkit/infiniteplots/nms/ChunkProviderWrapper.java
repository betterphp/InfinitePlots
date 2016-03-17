package uk.co.jacekk.bukkit.infiniteplots.nms;

import net.minecraft.server.v1_9_R1.Chunk;
import net.minecraft.server.v1_9_R1.IChunkProvider;

public class ChunkProviderWrapper implements IChunkProvider {
	
	private IChunkProvider chunkProvider;
	private int[] buildLimits;
	
	public ChunkProviderWrapper(IChunkProvider chunkProvider, int[] buildLimits){
		this.chunkProvider = chunkProvider;
		this.buildLimits = buildLimits;
	}
	
	@Override
	public Chunk getLoadedChunkAt(int i, int j){
		return this.getLoadedChunkAt(i, j);
	}
	
	@Override
	public Chunk getChunkAt(int arg0, int arg1){
		return this.chunkProvider.getChunkAt(arg0, arg1);
	}
	
	@Override
	public String getName(){
		return this.chunkProvider.getName();
	}
	
	@Override
	public boolean unloadChunks(){
		return this.chunkProvider.unloadChunks();
	}
	
	@Override
	public Chunk getOrCreateChunkFast(int x, int z){
		return new ChunkWrapper(this.chunkProvider.getOrCreateChunkFast(x, z), this.buildLimits);
	}

}

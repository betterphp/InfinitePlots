package uk.co.jacekk.bukkit.infiniteplots.nms;

import java.util.List;

import net.minecraft.server.v1_7_R2.Chunk;
import net.minecraft.server.v1_7_R2.ChunkPosition;
import net.minecraft.server.v1_7_R2.EnumCreatureType;
import net.minecraft.server.v1_7_R2.IChunkProvider;
import net.minecraft.server.v1_7_R2.IProgressUpdate;
import net.minecraft.server.v1_7_R2.World;

public class ChunkProviderWrapper implements IChunkProvider {
	
	private IChunkProvider chunkProvider;
	private int[] buildLimits;
	
	public ChunkProviderWrapper(IChunkProvider chunkProvider, int[] buildLimits){
		this.chunkProvider = chunkProvider;
		this.buildLimits = buildLimits;
	}
	
	@Override
	public void c(){
		this.chunkProvider.c();
	}
	
	@Override
	public boolean canSave(){
		return this.chunkProvider.canSave();
	}
	
	@Override
	public ChunkPosition findNearestMapFeature(World arg0, String arg1, int arg2, int arg3, int arg4){
		return this.chunkProvider.findNearestMapFeature(arg0, arg1, arg2, arg3, arg4);
	}
	
	@Override
	public Chunk getChunkAt(int arg0, int arg1){
		return this.chunkProvider.getChunkAt(arg0, arg1);
	}
	
	@Override
	public void getChunkAt(IChunkProvider arg0, int arg1, int arg2){
		this.chunkProvider.getChunkAt(arg0, arg1, arg2);
	}
	
	@Override
	public int getLoadedChunks(){
		return this.chunkProvider.getLoadedChunks();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getMobsFor(EnumCreatureType arg0, int arg1, int arg2, int arg3){
		return this.chunkProvider.getMobsFor(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public String getName(){
		return this.chunkProvider.getName();
	}
	
	@Override
	public Chunk getOrCreateChunk(int x, int z){
		return new ChunkWrapper(this.chunkProvider.getOrCreateChunk(x, z), this.buildLimits);
	}
	
	@Override
	public boolean isChunkLoaded(int arg0, int arg1){
		return this.chunkProvider.isChunkLoaded(arg0, arg1);
	}
	
	@Override
	public void recreateStructures(int arg0, int arg1){
		this.chunkProvider.recreateStructures(arg0, arg1);
	}
	
	@Override
	public boolean saveChunks(boolean arg0, IProgressUpdate arg1){
		return this.chunkProvider.saveChunks(arg0, arg1);
	}
	
	@Override
	public boolean unloadChunks(){
		return this.chunkProvider.unloadChunks();
	}
	
}

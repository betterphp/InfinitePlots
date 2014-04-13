package uk.co.jacekk.bukkit.infiniteplots.nms;

import net.minecraft.server.v1_7_R3.Block;
import net.minecraft.server.v1_7_R3.Blocks;
import net.minecraft.server.v1_7_R3.Chunk;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;

public class ChunkWrapper extends Chunk {
	
	private Chunk chunk;
	private int[] buildLimits;
	
	public ChunkWrapper(Chunk chunk, int[] buildLimits){
		super(chunk.world, chunk.locX, chunk.locZ);
		
		this.chunk = chunk;
		this.buildLimits = buildLimits;
		
		this.a(this.chunk.i());
		this.a(this.chunk.m());
		this.b = this.chunk.b;
		this.c = this.chunk.c;
		this.d = this.chunk.d;
		this.world = this.chunk.world;
		this.heightMap = this.chunk.heightMap;
		this.tileEntities = this.chunk.tileEntities;
		this.entitySlices = this.chunk.entitySlices;
		this.done = this.chunk.done;
		this.lit = this.chunk.lit;
		this.m = this.chunk.m;
		this.n = this.chunk.n;
		this.o = this.chunk.o;
		this.p = this.chunk.p;
		this.q = this.chunk.q;
		this.r = this.chunk.r;
		this.s = this.chunk.s;
		
		try{
			ReflectionUtils.setFieldValue(Chunk.class, "w", this, ReflectionUtils.getFieldValue(Chunk.class, "w", Boolean.class, this.chunk));
			ReflectionUtils.setFieldValue(Chunk.class, "x", this, ReflectionUtils.getFieldValue(Chunk.class, "x", Integer.class, this.chunk));
		}catch (NoSuchFieldException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public int b(int x, int y, int z){
		return this.getType(x, y, z).k();
	}
	
	@Override
	public Block getType(int x, int y, int z){
		int wx = (this.chunk.locX * 16) + x;
		int wz = (this.chunk.locZ * 16) + z;
		
		if (wx >= this.buildLimits[0] && wz >= this.buildLimits[1] && wx <= this.buildLimits[2] && wz <= this.buildLimits[3]){
			return this.chunk.getType(x, y, z);
		}
		
		return Blocks.AIR;
	}
	
	@Override
	public int getData(int x, int y, int z){
		int wx = (this.chunk.locX * 16) + x;
		int wz = (this.chunk.locZ * 16) + z;
		
		if (wx >= this.buildLimits[0] && wz >= this.buildLimits[1] && wx <= this.buildLimits[2] && wz <= this.buildLimits[3]){
			return this.chunk.getData(x, y, z);
		}
		
		return 0;
	}
	
	@Override
	public boolean a(int x, int y, int z, Block block, int data){
		int wx = (this.chunk.locX * 16) + x;
		int wz = (this.chunk.locZ * 16) + z;
		
		if (wx >= this.buildLimits[0] && wz >= this.buildLimits[1] && wx <= this.buildLimits[2] && wz <= this.buildLimits[3]){
			return this.chunk.a(x, y, z, block, data);
		}
		
		return false;
	}
	
}

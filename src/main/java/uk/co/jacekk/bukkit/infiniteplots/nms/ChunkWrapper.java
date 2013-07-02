package uk.co.jacekk.bukkit.infiniteplots.nms;

import net.minecraft.server.v1_6_R1.Chunk;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;

public class ChunkWrapper extends Chunk {
	
	private Chunk chunk;
	private int[] buildLimits;
	
	public ChunkWrapper(Chunk chunk, int[] buildLimits){
		super(chunk.world, chunk.x, chunk.z);
		
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
		this.l = this.chunk.l;
		this.m = this.chunk.m;
		this.n = this.chunk.n;
		this.seenByPlayer = this.chunk.seenByPlayer;
		this.p = this.chunk.p;
		this.q = this.chunk.q;
		
		try{
			ReflectionUtils.setFieldValue(Chunk.class, "t", this, ReflectionUtils.getFieldValue(Chunk.class, "t", Boolean.class, this.chunk));
			ReflectionUtils.setFieldValue(Chunk.class, "u", this, ReflectionUtils.getFieldValue(Chunk.class, "u", Integer.class, this.chunk));
		}catch (NoSuchFieldException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public int getTypeId(int x, int y, int z){
		int wx = (this.chunk.x * 16) + x;
		int wz = (this.chunk.z * 16) + z;
		
		if (wx >= buildLimits[0] && wz >= buildLimits[1] && wx <= buildLimits[2] && wz <= buildLimits[3]){
			return this.chunk.getTypeId(x, y, z);
		}
		
		return 0;
	}
	
	@Override
	public int getData(int x, int y, int z){
		int wx = (this.chunk.x * 16) + x;
		int wz = (this.chunk.z * 16) + z;
		
		if (wx >= buildLimits[0] && wz >= buildLimits[1] && wx <= buildLimits[2] && wz <= buildLimits[3]){
			return this.chunk.getData(x, y, z);
		}
		
		return 0;
	}
	
	@Override
	public boolean a(int x, int y, int z, int l, int i1){
		int wx = (this.chunk.x * 16) + x;
		int wz = (this.chunk.z * 16) + z;
		
		if (wx >= buildLimits[0] && wz >= buildLimits[1] && wx <= buildLimits[2] && wz <= buildLimits[3]){
			return this.chunk.a(x, y, z, l, i1);
		}
		
		return false;
	}
	
}

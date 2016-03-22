package uk.co.jacekk.bukkit.infiniteplots.plot.decorator;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.server.v1_9_R1.BiomeBase;
import net.minecraft.server.v1_9_R1.Block;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.BlockSand;
import net.minecraft.server.v1_9_R1.ChunkProviderGenerate;
import net.minecraft.server.v1_9_R1.ChunkSnapshot;
import net.minecraft.server.v1_9_R1.IBlockData;
import net.minecraft.server.v1_9_R1.IChunkProvider;
import net.minecraft.server.v1_9_R1.MinecraftKey;
import net.minecraft.server.v1_9_R1.World;
import net.minecraft.server.v1_9_R1.WorldGenCanyon;
import net.minecraft.server.v1_9_R1.WorldGenCaves;
import net.minecraft.server.v1_9_R1.WorldServer;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;

import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;
import uk.co.jacekk.bukkit.infiniteplots.BlockChangeTask;
import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.nms.ChunkProviderWrapper;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;

/**
 * Created a random vanilla-style world using a specific biome.
 */
public class BiomePlotDecorator extends PlotDecorator {
	
	private Biome biome;
	private BiomeBase biomeBase;
	private long seed;
	
	public BiomePlotDecorator(InfinitePlots plugin, Biome biome){
		super(plugin);
		
		this.biome = biome;
		this.seed = (new Random()).nextLong();
		
		this.biomeBase = BiomeBase.REGISTRY_ID.get(new MinecraftKey(biome.name().toLowerCase()));
	}
	
	private ChunkSnapshot createChunk(ChunkProviderGenerate generator, World world, int x, int z, BiomeBase biomeBase){
		ChunkSnapshot chunk = new ChunkSnapshot();
		BiomeBase[] biomeBases = new BiomeBase[256];
		Arrays.fill(biomeBases, biomeBase);
		
		generator.a(x, z, chunk);
		generator.a(x, z, chunk, biomeBases);
		
		try{
			ReflectionUtils.getFieldValue(ChunkProviderGenerate.class, "v", WorldGenCaves.class, generator).a(world, x, z, chunk);
			ReflectionUtils.getFieldValue(ChunkProviderGenerate.class, "A", WorldGenCanyon.class, generator).a(world, x, z, chunk);
		}catch (NoSuchFieldException e){
			e.printStackTrace();
		}
		
		return chunk;
	}
	
	@Override
	public void decorate(Plot plot){
		CraftWorld craftWorld = (CraftWorld) plugin.getServer().getWorld(plot.getLocation().getWorldName());
		final WorldServer world = craftWorld.getHandle();
		final ChunkProviderGenerate generator = new ChunkProviderGenerate(world, this.seed, false, "");
		
		int worldHeight = craftWorld.getMaxHeight();
		int seaHeight = craftWorld.getSeaLevel();
		int gridHeight = plugin.config.getInt(Config.GRID_HEIGHT);
		final int[] buildLimits = plot.getBuildLimits();
		
		BlockChangeTask task = new BlockChangeTask(plugin, craftWorld);
		
		for (int x = buildLimits[0]; x <= buildLimits[2]; x += 16){
			for (int z = buildLimits[1]; z <= buildLimits[3]; z += 16){
				ChunkSnapshot chunk = this.createChunk(generator, world, x >> 4, z >> 4, this.biomeBase);
				
				for (int cx = 0; cx < 16; ++cx){
					for (int cz = 0; cz < 16; ++cz){
						int wx = x + cx;
						int wz = z + cz;
						
						if (wx >= buildLimits[0] && wz >= buildLimits[1] && wx <= buildLimits[2] && wz <= buildLimits[3]){
							craftWorld.setBiome(wx, wz, this.biome);
							
							for (int y = 0; y < 256; ++y){
								int wy = y - (seaHeight - gridHeight);
								
								if (wy > 0 && wy < worldHeight){
									IBlockData blockData = chunk.a(cx, y, cz);
									
									//TODO: How do we do this without the ID?
									Material type = Material.getMaterial(Block.REGISTRY_ID.getId(blockData));
									
									if (blockData == null){
										type = Material.AIR;
									}
									
									task.setBlockType(craftWorld.getBlockAt(wx, wy, wz), type);
								}
							}
							
							task.setBlockType(craftWorld.getBlockAt(wx, 0, wz), Material.BEDROCK);
						}
					}
				}
			}
		}
		
		BlockSand.instaFall = true;
		
		task.start(plugin.config.getInt(Config.RESET_DELAY), plugin.config.getInt(Config.RESET_PERTICK));
		
		task.setOnComplete(new Runnable(){
			
			@Override
			public void run(){
				IChunkProvider currentChunkProvider = world.getChunkProvider();
				
				try{
					ChunkProviderWrapper wrappedChunkProvider = new ChunkProviderWrapper(currentChunkProvider, buildLimits);
					ReflectionUtils.setFieldValue(World.class, "chunkProvider", world, wrappedChunkProvider);
					Random random = ReflectionUtils.getFieldValue(ChunkProviderGenerate.class, "i", Random.class, generator);

					for (int x = buildLimits[0]; x <= buildLimits[2]; x += 16){
						for (int z = buildLimits[1]; z <= buildLimits[3]; z += 16){
							biomeBase.a(world, random, new BlockPosition((x >> 4) * 16, 0, (z >> 4) * 16));
						}
					}
					
					ReflectionUtils.setFieldValue(World.class, "chunkProvider", world, currentChunkProvider);
				}catch (NoSuchFieldException e){
					e.printStackTrace();
				}
				
				BlockSand.instaFall = false;
			}
			
		});
	}
	
}

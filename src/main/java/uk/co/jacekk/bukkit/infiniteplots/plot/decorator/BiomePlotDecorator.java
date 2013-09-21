package uk.co.jacekk.bukkit.infiniteplots.plot.decorator;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.BlockSand;
import net.minecraft.server.v1_6_R3.ChunkProviderGenerate;
import net.minecraft.server.v1_6_R3.IChunkProvider;
import net.minecraft.server.v1_6_R3.World;
import net.minecraft.server.v1_6_R3.WorldGenCanyon;
import net.minecraft.server.v1_6_R3.WorldGenCaves;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;

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
		
		try{
			this.biomeBase = ReflectionUtils.getFieldValue(BiomeBase.class, biome.name(), BiomeBase.class, null);
		}catch (NoSuchFieldException e){
			e.printStackTrace();
		}
	}
	
	private byte[] createChunk(ChunkProviderGenerate generator, World world, int x, int z, BiomeBase biomeBase){
		BiomeBase[] biomeBases = new BiomeBase[256];
		Arrays.fill(biomeBases, biomeBase);
		
		byte[] blocks = new byte[32768];
		
		generator.a(x, z, blocks);
		generator.a(x, z, blocks, biomeBases);
		
		try{
			ReflectionUtils.getFieldValue(ChunkProviderGenerate.class, "t", WorldGenCaves.class, generator).a(generator, world, x, z, blocks);
			ReflectionUtils.getFieldValue(ChunkProviderGenerate.class, "y", WorldGenCanyon.class, generator).a(generator, world, x, z, blocks);
		}catch (NoSuchFieldException e){
			e.printStackTrace();
		}
		
		return blocks;
	}
	
	@Override
	public void decorate(Plot plot){
		CraftWorld craftWorld = (CraftWorld) plugin.server.getWorld(plot.getLocation().getWorldName());
		final World world = craftWorld.getHandle();
		final ChunkProviderGenerate generator = new ChunkProviderGenerate(world, this.seed, false);
		
		int worldHeight = craftWorld.getMaxHeight();
		int seaHeight = craftWorld.getSeaLevel();
		int gridHeight = plugin.config.getInt(Config.GRID_HEIGHT);
		final int[] buildLimits = plot.getBuildLimits();
		
		BlockChangeTask task = new BlockChangeTask(plugin, craftWorld);
		
		for (int x = buildLimits[0]; x <= buildLimits[2]; x += 16){
			for (int z = buildLimits[1]; z <= buildLimits[3]; z += 16){
				byte[] chunk = this.createChunk(generator, world, x >> 4, z >> 4, this.biomeBase);
				
				for (int cx = 0; cx < 16; ++cx){
					for (int cz = 0; cz < 16; ++cz){
						int wx = x + cx;
						int wz = z + cz;
						
						if (wx >= buildLimits[0] && wz >= buildLimits[1] && wx <= buildLimits[2] && wz <= buildLimits[3]){
							craftWorld.setBiome(wx, wz, this.biome);
							
							for (int y = 0; y < 128; ++y){
								int wy = y - (seaHeight - gridHeight);
								
								if (wy > 0 && wy < worldHeight){
									task.setBlockType(craftWorld.getBlockAt(wx, wy, wz), Material.getMaterial(chunk[(cx * 16 + cz) * 128 + y]));
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
				IChunkProvider currentChunkProvider = world.chunkProvider;
				
				for (int x = buildLimits[0]; x <= buildLimits[2]; x += 16){
					for (int z = buildLimits[1]; z <= buildLimits[3]; z += 16){
						world.chunkProvider = new ChunkProviderWrapper(currentChunkProvider, buildLimits);
						
						try{
							biomeBase.a(world, ReflectionUtils.getFieldValue(ChunkProviderGenerate.class, "k", Random.class, generator), (x >> 4) * 16, (z >> 4) * 16);
						}catch (NoSuchFieldException e){
							e.printStackTrace();
						}
					}
				}
				
				world.chunkProvider = currentChunkProvider;
				
				BlockSand.instaFall = false;
			}
			
		});
	}
	
}

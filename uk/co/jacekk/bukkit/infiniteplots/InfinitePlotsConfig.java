package uk.co.jacekk.bukkit.infiniteplots;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class InfinitePlotsConfig {
	
	private YamlConfiguration config;
	private HashMap<String, Object> configDefaults = new HashMap<String, Object>();
	
	public InfinitePlotsConfig(File configFile){
		this.config = new YamlConfiguration();
		
		this.configDefaults.put("plots.size", 64);
		this.configDefaults.put("plots.height", 20);
		this.configDefaults.put("plots.restrict-spawning", true);
		
		this.configDefaults.put("blocks.base", Material.STONE.getId());
		this.configDefaults.put("blocks.surface", Material.GRASS.getId());
		this.configDefaults.put("blocks.path", Material.STEP.getId());
		this.configDefaults.put("blocks.lower-wall", Material.SMOOTH_BRICK.getId());
		this.configDefaults.put("blocks.upper-wall", Material.FENCE.getId());
		
		if (configFile.exists() == false){
			for (String key : this.configDefaults.keySet()){
				this.config.set(key, this.configDefaults.get(key));
			}
			
			try {
				this.config.save(configFile);
			} catch (IOException e){
				e.printStackTrace();
			}
		}else{
			try {
				this.config.load(configFile);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public boolean getBoolean(String key){
		if (this.configDefaults.containsKey(key) == false){
			return false;
		}
		
		return this.config.getBoolean(key, (Boolean) this.configDefaults.get(key));
	}
	
	public int getInt(String key){
		if (this.configDefaults.containsKey(key) == false){
			return 0;
		}
		
		return this.config.getInt(key, (Integer) this.configDefaults.get(key));
	}
	
	public double getDouble(String key){
		if (this.configDefaults.containsKey(key) == false){
			return 0.0;
		}
		
		return this.config.getDouble(key, (Double) this.configDefaults.get(key));
	}
	
}

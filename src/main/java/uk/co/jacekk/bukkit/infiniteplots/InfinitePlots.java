package uk.co.jacekk.bukkit.infiniteplots;

import java.io.File;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.RegisteredServiceProvider;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.infiniteplots.command.AddBuilderCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.AutoCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.ClaimCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.DecorateCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.FlagCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.InfoCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.ListCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.NameCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.PlotCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.ProtectionCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.ResetCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.SetBiomeCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.command.TeleportCommandExecutor;
import uk.co.jacekk.bukkit.infiniteplots.flag.BlockFlowListener;
import uk.co.jacekk.bukkit.infiniteplots.flag.IceListener;
import uk.co.jacekk.bukkit.infiniteplots.flag.MobSpawnListener;
import uk.co.jacekk.bukkit.infiniteplots.flag.PhysicsListener;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotManager;
import uk.co.jacekk.bukkit.infiniteplots.protection.BuildListener;
import uk.co.jacekk.bukkit.infiniteplots.protection.EnterListener;

public class InfinitePlots extends BasePlugin {
	
	private static InfinitePlots instance;
	
	private File plotsDir;
	private PlotManager plotManager;
	private Economy economy;
	
	@Override
	public void onEnable(){
		super.onEnable(true);
		
		instance = this;
		
		this.plotsDir = new File(this.baseDirPath + File.separator + "plots");
		
		if (!this.plotsDir.exists()){
			this.plotsDir.mkdirs();
		}
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		if (!this.config.getBoolean(Config.GENERATOR_ONLY)){
			this.plotManager = new PlotManager(this);
			
			for (World world : this.server.getWorlds()){
				this.plotManager.loadPlotsFor(world);
			}
			
			this.permissionManager.registerPermissions(Permission.class);
			
			this.pluginManager.registerEvents(new WorldInitListener(this), this);
			
			this.pluginManager.registerEvents(new BuildListener(this), this);
			this.pluginManager.registerEvents(new EnterListener(this), this);
			
			this.pluginManager.registerEvents(new MobSpawnListener(this), this);
			this.pluginManager.registerEvents(new BlockFlowListener(this), this);
			this.pluginManager.registerEvents(new IceListener(this), this);
			this.pluginManager.registerEvents(new PhysicsListener(this), this);
			
			if (this.config.getBoolean(Config.TRACK_STATS)){
				this.pluginManager.registerEvents(new PlotStatsListener(this), this);
			}
			
			if (this.config.getDouble(Config.CLAIM_COST) > 0.0d){
				RegisteredServiceProvider<Economy> economyProvider = this.server.getServicesManager().getRegistration(Economy.class);
				
				if (economyProvider == null){
					this.log.warn("Vault not found, players will not be charged to claim plots.");
					this.log.warn("Download it from http://dev.bukkit.org/bukkit-plugins/vault/");
				}else{
					this.economy = economyProvider.getProvider();
				}
			}
			
			this.commandManager.registerCommandExecutor(new PlotCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new InfoCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new ClaimCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new AutoCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new AddBuilderCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new FlagCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new SetBiomeCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new ResetCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new ListCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new NameCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new TeleportCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new DecorateCommandExecutor(this));
			this.commandManager.registerCommandExecutor(new ProtectionCommandExecutor(this));
		}
	}
	
	@Override
	public void onDisable(){
		instance = null;
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		int size = this.config.getInt(Config.GRID_SIZE);
		
		if (id != null && !id.isEmpty()){
			try{
				size = Integer.parseInt(id);
			}catch(NumberFormatException e){  }
		}
		
		int height = this.config.getInt(Config.GRID_HEIGHT);
		
		byte pathId = (byte) this.config.getInt(Config.BLOCKS_PATH);
		byte pathData = (byte) this.config.getInt(Config.BLOCKS_PATH_DATA);
		byte wallLowerId = (byte) this.config.getInt(Config.BLOCKS_LOWER_WALL);
		byte wallLowerData = (byte) this.config.getInt(Config.BLOCKS_LOWER_WALL_DATA);
		byte wallUpperId = (byte) this.config.getInt(Config.BLOCKS_UPPER_WALL);
		byte wallUpperData = (byte) this.config.getInt(Config.BLOCKS_UPPER_WALL_DATA);
		byte surfaceId = (byte) this.config.getInt(Config.BLOCKS_SURFACE);
		byte groundId = (byte) this.config.getInt(Config.BLOCKS_GROUND);
		
		return new PlotsGenerator(size, height, pathId, pathData, wallLowerId, wallLowerData, wallUpperId, wallUpperData, surfaceId, groundId);
	}
	
	public static InfinitePlots getInstance(){
		return instance;
	}
	
	/**
	 * Gets the folder used for plot data files.
	 * 
	 * @return The {@link File} for the folder.
	 */
	public File getPlotsDir(){
		return this.plotsDir;
	}
	
	/**
	 * Gets the plot manager.
	 * 
	 * @return The {@link PlotManager}.
	 */
	public PlotManager getPlotManager(){
		return this.plotManager;
	}
	
	/**
	 * Gets the economy instance.
	 * 
	 * @return The instance.
	 */
	public Economy getEconomy(){
		return this.economy;
	}
	
}
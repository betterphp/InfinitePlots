package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v9.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v9.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class PlotResetCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public PlotResetCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	private void setBlockType(Block block, Material type){
		if (block.getType() != type){
			block.setType(type);
		}
	}
	
	@SubCommandHandler(parent = "iplot", name = "reset")
	public void plotReset(CommandSender sender, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		Player player = (Player) sender;
		
		Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(player.getLocation()));
		
		if (plot == null){
			player.sendMessage(ChatColor.RED + "There is no plot at this location");
			return;
		}
		
		if (!plot.getAdmin().equalsIgnoreCase(player.getName())){
			player.sendMessage(ChatColor.RED + "You do not own this plot");
		}
		
		World world = player.getWorld();
		
		int worldHeight = world.getMaxHeight();
		int gridHeight = plugin.config.getInt(Config.GRID_HEIGHT);
		
		int[] buildLimits = plot.getBuildLimits();
		
		for (int x = buildLimits[0]; x <= buildLimits[2]; ++x){
			for (int z = buildLimits[1]; z <= buildLimits[3]; ++z){
				this.setBlockType(world.getBlockAt(x, 0, z), Material.BEDROCK);
				
				for (int y = 1; y < gridHeight; ++y){
					this.setBlockType(world.getBlockAt(x, y, z), Material.DIRT);
				}
				
				this.setBlockType(world.getBlockAt(x, gridHeight, z), Material.GRASS);
				
				for (int y = gridHeight + 1; y < worldHeight; ++y){
					this.setBlockType(world.getBlockAt(x, y, z), Material.AIR);
				}
			}
		}
	}
	
}

package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
		
		if (!(plot.getAdmin().equalsIgnoreCase(player.getName()))){
			player.sendMessage(ChatColor.RED + "You do not own this plot");
		}
		
		for (int x = plot.getBuildLimits()[0]; x <= plot.getBuildLimits()[2]; ++x){
			for (int z = plot.getBuildLimits()[1]; z <= plot.getBuildLimits()[3]; ++z){
				player.getLocation().getWorld().getBlockAt(x, 0, z).setType(Material.BEDROCK);
				
				for (int y = 1; y <= plugin.config.getInt(Config.GRID_HEIGHT); ++y){
					player.getLocation().getWorld().getBlockAt(x, y, z).setType(Material.DIRT);
					if (y == plugin.config.getInt(Config.GRID_HEIGHT)){
						player.getLocation().getWorld().getBlockAt(x, y, z).setType(Material.GRASS);
					}
				}
				
				for (int y = plugin.config.getInt(Config.GRID_HEIGHT) + 1; y < player.getLocation().getWorld().getMaxHeight(); ++y){
					if (player.getLocation().getWorld().getBlockAt(x, y, z).getType() == Material.AIR){
						continue;
					}
					
					player.getLocation().getWorld().getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
	}
	
}

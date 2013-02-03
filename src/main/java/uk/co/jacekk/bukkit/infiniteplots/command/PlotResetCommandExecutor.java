package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v8.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v8.command.CommandHandler;
import uk.co.jacekk.bukkit.baseplugin.v8.command.CommandTabCompletion;
import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class PlotResetCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public PlotResetCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"reset"}, description = "Resets the plot you are standing in")
	@CommandTabCompletion({"<online_player>"})
	public void reset(CommandSender sender, String label, String[] args){
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
		
		int highest = player.getLocation().getWorld().getHighestBlockYAt(plot.getLocation().getX(), plot.getLocation().getZ());
		
		plugin.log.info("Highest Y is: " + highest);
		
		for (int y = highest; y > plugin.config.getInt(Config.GRID_HEIGHT); y--){
			for (int x = 0; x < 16; x++){
				for (int z = 0; z < 16; z++){
					player.getLocation().getWorld().getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
	}
	
}

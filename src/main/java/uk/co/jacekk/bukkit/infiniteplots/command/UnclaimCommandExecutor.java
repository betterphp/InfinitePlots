package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v9.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v9.command.CommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class UnclaimCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public UnclaimCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"unclaim"}, description = "Unclaims the plot you are standing in.", usage = "")
	public void unclaim(CommandSender sender, String label, String args[]){
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
		
		plugin.getPlotManager().removePlotAt(PlotLocation.fromWorldLocation(player.getLocation()));
		player.sendMessage(ChatColor.GREEN + "Successfully unclaimed plot");
	}
}

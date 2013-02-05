package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v9.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v9.command.CommandHandler;
import uk.co.jacekk.bukkit.baseplugin.v9.command.CommandTabCompletion;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class AddBuilderCommandExecutor extends BaseCommandExecutor<InfinitePlots>{

	public AddBuilderCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"addmember"}, description = "Adds a member to the plot you are standing in.", usage = "[player_name]")
	@CommandTabCompletion({"<online_player>"})
	public void addbuilder(CommandSender sender, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		if (args.length == 0){
			sender.sendMessage(ChatColor.RED + "Must supply a players name");
			return;
		}
		
		Player player = (Player)sender;
		
		Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(player.getLocation()));
		
		if (plot == null){
			player.sendMessage(ChatColor.RED + "There is no plot at this location");
			return;
		}
		
		if (!plot.getAdmin().equalsIgnoreCase(player.getName())){
			player.sendMessage(ChatColor.RED + "You do not own this plot");
		}
		
		if (plot.getBuilders().contains(args[0])){
			player.sendMessage(ChatColor.RED + args[0] + " is already a builder on your plot");
			return;
		}
		
		plot.addBuilder(args[0]);
		
		player.sendMessage(ChatColor.GREEN + "Added " + args[0] + " as a builder to your plot");
	}
	
}

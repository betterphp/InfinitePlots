package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v8.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v8.command.CommandHandler;
import uk.co.jacekk.bukkit.baseplugin.v8.command.CommandTabCompletion;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class ClaimCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public ClaimCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"claim"}, description = "Claims the plot you are standing in", usage = "[player_name]")
	@CommandTabCompletion({"<online_player>"})
	public void claim(CommandSender sender, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		Player player = (Player) sender;
		
		Plot plot = plugin.getPlotManager().createPlotAt(PlotLocation.fromWorldLocation(player.getLocation()), true);
		
		if (plot == null){
			player.sendMessage(ChatColor.RED + "This plot has already been claimed.");
			return;
		}
		
		plot.setAdmin(player.getName());
		
		player.sendMessage(ChatColor.GREEN + "Plot claimed");
	}
	
}

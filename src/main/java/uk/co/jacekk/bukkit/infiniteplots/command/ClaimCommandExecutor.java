package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v9.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v9.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class ClaimCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public ClaimCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@SubCommandHandler(parent = "iplot", name = "claim")
	public void plotClaim(CommandSender sender, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		Player player = (Player) sender;
		
		Plot plot = plugin.getPlotManager().createPlotAt(PlotLocation.fromWorldLocation(player.getLocation()), false);
		
		if (plot == null){
			player.sendMessage(ChatColor.RED + "This plot has already been claimed.");
			return;
		}
		
		plot.setAdmin(player.getName());
		
		player.sendMessage(ChatColor.GREEN + "Plot claimed");
	}
	
}

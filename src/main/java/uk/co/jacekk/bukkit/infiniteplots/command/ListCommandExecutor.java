package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v9.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v9.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;

public class ListCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public ListCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@SubCommandHandler(parent = "iplot", name = "list")
	public void plotList(CommandSender sender, String label, String[] args){
		if (!Permission.PLOT_LIST.has(sender)){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return;
		}
		
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		Player player = (Player) sender;
		String playerName = player.getName();
		String worldName = player.getWorld().getName();
		
		player.sendMessage(ChatColor.GREEN + "Owned Plots:");
		
		for (Plot plot : plugin.getPlotManager().getOwnedPlots(playerName, worldName)){
			player.sendMessage(ChatColor.GREEN + "  " + plot.getLocation().getX() + "," + plot.getLocation().getZ());
		}
	}
	
}

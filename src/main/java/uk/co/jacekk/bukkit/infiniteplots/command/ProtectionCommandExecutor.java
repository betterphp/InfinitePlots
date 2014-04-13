package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class ProtectionCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public ProtectionCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}

	@SubCommandHandler(parent = "iplot", name = "protection")
	public void plotProtection(CommandSender sender, String label, String[] args){
		if (!Permission.PLOT_PROTECTION.has(sender)){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return;
		}
		
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		if (args.length != 2){
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " protection <protection> <true/false>");
			sender.sendMessage(ChatColor.RED + "Example: /" + label + " protection build true");
			sender.sendMessage(ChatColor.RED + "Example: /" + label + " protection enter false");
			return;
		}
		
		Player player = (Player) sender;
		
		if (!(player.getWorld().getGenerator() instanceof PlotsGenerator)){
			player.sendMessage(ChatColor.RED + "You must be in a plot world");
			return;
		}
		
		Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(player.getLocation()));
		
		if (plot == null){
			player.sendMessage(ChatColor.RED + "There is no plot at this location");
			return;
		}
		
		if (!Permission.PLOT_PROTECTION_OTHER.has(player) && !plot.getAdmin().getUniqueId().equals(player.getUniqueId())){
			player.sendMessage(ChatColor.RED + "You do not own this plot");
			return;
		}
		
		if (args[0].equalsIgnoreCase("build")){
			plot.setBuildProtection(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("deny"));
		}else if (args[0].equalsIgnoreCase("enter")){
			plot.setEnterProtection(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("deny"));
		}else{
			sender.sendMessage(ChatColor.RED + "Invalid protection.");
		}
		
		sender.sendMessage(ChatColor.GREEN + "Protection updated");
	}
	
	
	
}

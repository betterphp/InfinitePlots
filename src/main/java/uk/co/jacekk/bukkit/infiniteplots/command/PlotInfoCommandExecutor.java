package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v8.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v8.command.CommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class PlotInfoCommandExecutor extends BaseCommandExecutor<InfinitePlots>{

	public PlotInfoCommandExecutor(InfinitePlots plugin) {
		super(plugin);
	}

	@CommandHandler(names = {"info"}, description = "Gives you information about your plot", usage = "")
	public void info(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		Player player = (Player)sender;
		
		Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(player.getLocation()));
		
		if (plot == null) {
			player.sendMessage(ChatColor.RED + "There is no plot at this location");
			return;
		}
		
		if (!(plot.getAdmin().equalsIgnoreCase(player.getName()))) {
			player.sendMessage(ChatColor.RED + "You do not own this plot");
			return;
		}
		
		player.sendMessage(ChatColor.YELLOW + "Plot Information");
		player.sendMessage(ChatColor.BLUE + "Location: " + ChatColor.RESET + plot.getLocation().getX() + ", " + plot.getLocation().getZ());

		StringBuilder builders = new StringBuilder();
		for(String builder : plot.getBuilders())
		{
			builders.append(builder + " ");
		}
		
		if(builders.length() < 1) builders.append("None");
		
		player.sendMessage(ChatColor.LIGHT_PURPLE + "Builders: " + ChatColor.RESET + builders.toString());
		
		StringBuilder flags = new StringBuilder();
		for(String flag : plot.getFlags())
		{
			flags.append(flag + ChatColor.RESET + " ");
		}
		
		player.sendMessage("Flags: " + flags.toString());
	}
}

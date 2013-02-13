package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v9.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v9.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.flag.PlotFlag;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class InfoCommandExecutor extends BaseCommandExecutor<InfinitePlots>{

	public InfoCommandExecutor(InfinitePlots plugin) {
		super(plugin);
	}

	@SubCommandHandler(parent = "iplot", name = "info")
	public void plotInfo(CommandSender sender, String label, String[] args){
		if (!Permission.PLOT_INFO.has(sender)){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return;
		}
		
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		Player player = (Player)sender;
		
		Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(player.getLocation()));
		
		if (plot == null){
			player.sendMessage(ChatColor.RED + "There is no plot at this location");
			return;
		}
		
		player.sendMessage(ChatColor.YELLOW + "Plot Information");
		player.sendMessage(ChatColor.BLUE + "Location: " + ChatColor.RESET + plot.getLocation().getX() + ", " + plot.getLocation().getZ());

		StringBuilder builders = new StringBuilder();
		
		for (String builder : plot.getBuilders()){
			builders.append(builder + " ");
		}
		
		if (builders.length() < 1){
			builders.append("None");
		}
		
		player.sendMessage(ChatColor.LIGHT_PURPLE + "Builders: " + ChatColor.RESET + builders.toString());
		
		StringBuilder flags = new StringBuilder();
		
		for (PlotFlag flag : PlotFlag.values()){
			flags.append((plot.isFlagEnabled(flag) ? ChatColor.GREEN : ChatColor.RED) + flag.getName() + ChatColor.RESET + " ");
		}
		
		player.sendMessage("Flags: " + flags.toString());
	}
	
}

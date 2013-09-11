package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import uk.co.jacekk.bukkit.baseplugin.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;

public class PurgeCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public PurgeCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@SubCommandHandler(name = "purge", parent = "iplot")
	public void plotPurge(CommandSender sender, String label, String[] args){
		if (!Permission.PLOT_PURGE.has(sender)){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return;
		}
		
		if (args.length != 1){
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " purge <days>");
			return;
		}
		
		// 1 day
		long time = 86400000l;
		
		try{
			time *= Integer.parseInt(args[0]);
		}catch (NumberFormatException e){
			sender.sendMessage(ChatColor.RED + "Invalid number of days '" + args[0] + "'");
			return;
		}
		
		int removed = 0;
		
		for (Plot plot : this.plugin.getPlotManager().getDeadPlots(time)){
			if (!this.plugin.config.getStringList(Config.PURGE_SAFE_LIST).contains(plot.getAdmin())){
				plot.regenerate();
				plot.removeSigns();
				
				this.plugin.getPlotManager().removePlotAt(plot.getLocation());
				
				++removed;
			}
		}
		
		sender.sendMessage(ChatColor.GREEN + "Removed " + removed + " plots");
	}
	
}

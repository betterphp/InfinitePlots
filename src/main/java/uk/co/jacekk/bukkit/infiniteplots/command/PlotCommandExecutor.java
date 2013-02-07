package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import uk.co.jacekk.bukkit.baseplugin.v9.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v9.command.CommandHandler;
import uk.co.jacekk.bukkit.baseplugin.v9.command.CommandTabCompletion;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;

public class PlotCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public PlotCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"iplot", "plot"}, description = "Manages the current plot.", usage = "[action] <args>")
	@CommandTabCompletion({"info|claim|unclaim|addbuilder|removebuilder|flag|reset"})
	public void iplot(CommandSender sender, String label, String[] args){
		sender.sendMessage(ChatColor.RED + "Usage: /" + label + " [action] <args>");
		sender.sendMessage(ChatColor.RED + "Actions:");
		sender.sendMessage(ChatColor.RED + "  info - View the plots info");
		sender.sendMessage(ChatColor.RED + "  claim - Claims this plot");
		sender.sendMessage(ChatColor.RED + "  unclaim - Releases this plot");
		sender.sendMessage(ChatColor.RED + "  addbuilder - Allows a player to build");
		sender.sendMessage(ChatColor.RED + "  removebuilder - Removes a builder");
		sender.sendMessage(ChatColor.RED + "  flag - Manage the plot flags");
		sender.sendMessage(ChatColor.RED + "  reset - Resets the plot");
	}
	
}

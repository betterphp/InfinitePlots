package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v9.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v9.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.converters.IClaimsConverter;

public class ConvertCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public ConvertCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@SubCommandHandler(name = "convert", parent = "iplot")
	public void convert(CommandSender sender, String label, String args[]){
		if (!(Permission.CONVERT.has(sender))){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return;
		}
		
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		if (args.length == 0){
			sender.sendMessage(ChatColor.RED + "Must supply a world name");
			return;
		}
		
		World plotWorld = plugin.getServer().getWorld(args[0]);
		
		if (plotWorld == null){
			sender.sendMessage(ChatColor.RED + "World does not exist");
			return;
		}
		
		IClaimsConverter icConverter = new IClaimsConverter(this.plugin, plotWorld);
		
		icConverter.start();
	}
}

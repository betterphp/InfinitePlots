package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class TeleportCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public TeleportCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@SubCommandHandler(parent = "iplot", name = "tp")
	public void plotTp(CommandSender sender, String label, String[] args){
		if (!Permission.PLOT_TELEPORT.has(sender)){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return;
		}
		
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		Player player = (Player) sender;
		Plot plot = null;
		
		if (args.length == 1){
			plot = plugin.getPlotManager().getPlotByName(player.getName(), args[0]);
		}else if (args.length == 2){
			int x = 0;
			int z = 0;
			
			try{
				x = Integer.parseInt(args[0]);
				z = Integer.parseInt(args[1]);
			}catch (NumberFormatException e){
				sender.sendMessage(ChatColor.RED + "The plot coordinates must be numeric");
				return;
			}
			
			plot = plugin.getPlotManager().getPlotAt(new PlotLocation(player.getWorld().getName(), x, z));
		}else{
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + "<plot_name>");
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + "<x> <z>");
			return;
		}
		
		if (plot == null){
			player.sendMessage(ChatColor.RED + "There is no plot at this location");
			return;
		}
		
		if (!Permission.PLOT_TELEPORT_OTHER.has(sender) && !plot.getAdmin().equalsIgnoreCase(player.getName())){
			player.sendMessage(ChatColor.RED + "You do not own this plot");
			return;
		}
		
		player.teleport(plot.getLocation().getWorldLocation());
		player.sendMessage(ChatColor.GREEN + "Welcome to " + plot.getName());
	}
	
}

package uk.co.jacekk.bukkit.infiniteplots.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;

public class AutoCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public AutoCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@SubCommandHandler(parent = "iplot", name = "auto")
	public void plotAuto(CommandSender sender, String label, String[] args){
		if (!Permission.PLOT_CLAIM.has(sender)){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return;
		}
		
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		Player player = (Player) sender;
		
		if (!(player.getWorld().getGenerator() instanceof PlotsGenerator)){
			player.sendMessage(ChatColor.RED + "You must be in a plot world");
			return;
		}
		
		if (!Permission.PLOT_BYPASS_CLAIM_LIMIT.has(player)){
			List<Plot> plots = plugin.getPlotManager().getOwnedPlots(player.getName());
			int max = plugin.config.getInt(Config.CLAIM_MAX);
			int maxUnused = plugin.config.getInt(Config.CLAIM_MAX_UNUSED);
			
			if (maxUnused > 0 && plugin.config.getBoolean(Config.TRACK_STATS)){
				int unused = 0;
				
				for (Plot plot : plots){
					if (plot.getStats().getBlocksBroken() == 0 || plot.getStats().getBlocksPlaced() == 0){
						if (++unused > maxUnused){
							player.sendMessage(ChatColor.RED + "You have too many claimed plots that have not been used");
							return;
						}
					}
				}
			}
			
			if (max > 0 && plots.size() >= max){
				player.sendMessage(ChatColor.RED + "You have already claimed the maximum number of plots");
				return;
			}
		}
		
		Plot plot = plugin.getPlotManager().createFirstAvailablePlot(player.getWorld());
		
		plot.setAdmin(player.getName());
		plot.createSigns();
		
		if (args.length == 1){
			plot.setName(args[0]);
		}
		
		player.teleport(plot.getLocation().getWorldLocation());
		
		player.sendMessage(ChatColor.GREEN + "Plot claimed");
	}
	
}

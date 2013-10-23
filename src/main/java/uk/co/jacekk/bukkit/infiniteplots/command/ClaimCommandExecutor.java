package uk.co.jacekk.bukkit.infiniteplots.command;

import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.event.PlotClaimedEvent;
import uk.co.jacekk.bukkit.infiniteplots.event.PlotUnclaimedEvent;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class ClaimCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public ClaimCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@SubCommandHandler(parent = "iplot", name = "claim")
	public void plotClaim(CommandSender sender, String label, String[] args){
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
		
		Economy economy = this.plugin.getEconomy();
		
		if (economy != null && economy.getBalance(player.getName()) < this.plugin.config.getDouble(Config.CLAIM_COST)){
			player.sendMessage(ChatColor.RED + "You need " + this.plugin.config.getDouble(Config.CLAIM_COST) + " " + economy.currencyNamePlural() + " to claim a new plot.");
			return;
		}
		
		if (!Permission.PLOT_BYPASS_CLAIM_LIMIT.has(player)){
			List<Plot> plots = plugin.getPlotManager().getOwnedPlots(player.getName());
			int max = plugin.config.getInt(Config.CLAIM_MAX);
			int maxUnused = plugin.config.getInt(Config.CLAIM_MAX_UNUSED);
			
			if (max > 0 && plots.size() >= max){
				player.sendMessage(ChatColor.RED + "You have already claimed the maximum number of plots");
				return;
			}
			
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
		}
		
		Plot plot = plugin.getPlotManager().createPlotAt(PlotLocation.fromWorldLocation(player.getLocation()), false);
		
		if (plot == null){
			player.sendMessage(ChatColor.RED + "This plot has already been claimed.");
			return;
		}
		
		plot.setAdmin(player.getName());
		plot.createSigns();
		
		if (args.length == 1){
			plot.setName(args[0]);
		}
		
		if (economy != null){
			economy.withdrawPlayer(player.getName(), this.plugin.config.getDouble(Config.CLAIM_COST));
		}
		
        Bukkit.getServer().getPluginManager().callEvent(new PlotClaimedEvent(plot, player));
		player.sendMessage(ChatColor.GREEN + "Plot claimed");
	}
	
	@SubCommandHandler(parent = "iplot", name = "unclaim")
	public void plotUnclaim(CommandSender sender, String label, String args[]){
		if (!Permission.PLOT_UNCLAIM.has(sender)){
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
		
		Plot plot = plugin.getPlotManager().getPlotAt(PlotLocation.fromWorldLocation(player.getLocation()));
		
		if (plot == null){
			player.sendMessage(ChatColor.RED + "There is no plot at this location");
			return;
		}
		
		if (!Permission.PLOT_UNCLAIM_OTHERS.has(sender) && !plot.getAdmin().equalsIgnoreCase(player.getName())){
			player.sendMessage(ChatColor.RED + "You do not own this plot");
			return;
		}
		
        Bukkit.getServer().getPluginManager().callEvent(new PlotUnclaimedEvent(plot, player));
		
		plot.removeSigns();
		plot.regenerate();
		
		plugin.getPlotManager().removePlotAt(PlotLocation.fromWorldLocation(player.getLocation()));
		
		player.sendMessage(ChatColor.GREEN + "Successfully unclaimed plot");
	}
	
}

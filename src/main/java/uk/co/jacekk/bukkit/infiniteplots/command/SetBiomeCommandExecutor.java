package uk.co.jacekk.bukkit.infiniteplots.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.command.CommandTabCompletion;
import uk.co.jacekk.bukkit.baseplugin.command.SubCommandHandler;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;
import uk.co.jacekk.bukkit.infiniteplots.Permission;
import uk.co.jacekk.bukkit.infiniteplots.generation.PlotsGenerator;
import uk.co.jacekk.bukkit.infiniteplots.plot.Plot;
import uk.co.jacekk.bukkit.infiniteplots.plot.PlotLocation;

public class SetBiomeCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public SetBiomeCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	public List<String> getBiomeList(CommandSender sender, String[] args){
		ArrayList<String> biomes = new ArrayList<String>();
		
		for (Biome biome : Biome.values()){
			biomes.add(biome.name().toLowerCase());
		}
		
		return biomes;
	}
	
	@SubCommandHandler(parent = "iplot", name = "setbiome")
	@CommandTabCompletion({"[getBiomeList]"})
	public void plotFlag(CommandSender sender, String label, String[] args){
		if (!Permission.PLOT_SET_BIOME.has(sender)){
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
		
		if (!Permission.PLOT_SET_BIOME_OTHERS.has(sender) && !plot.getAdmin().equalsIgnoreCase(player.getName())){
			player.sendMessage(ChatColor.RED + "You do not own this plot");
			return;
		}
		
		if (args.length != 1){
			player.sendMessage(ChatColor.RED + "Usage: /" + label + " setbiome <biome>");
			return;
		}
		
		Biome biome = Biome.PLAINS;
		
		try{
			biome = Biome.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e){
			player.sendMessage(ChatColor.RED + args[0] + " is not a valid biome");
			player.sendMessage(ChatColor.RED + "Valid values:");
			
			for (Biome valid : Biome.values()){
				player.sendMessage(ChatColor.RED + "  " + valid.name().toLowerCase());
			}
			
			return;
		}
		
		plot.setBiome(biome);
		
		player.sendMessage(ChatColor.GREEN + "Biome set to " + biome.name().toLowerCase());
	}
	
}

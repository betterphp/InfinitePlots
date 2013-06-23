package uk.co.jacekk.bukkit.infiniteplots.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import uk.co.jacekk.bukkit.infiniteplots.plot.decorator.BiomePlotDecorator;
import uk.co.jacekk.bukkit.infiniteplots.plot.decorator.FlatPlotDecorator;

public class DecorateCommandExecutor extends BaseCommandExecutor<InfinitePlots> {
	
	public DecorateCommandExecutor(InfinitePlots plugin){
		super(plugin);
	}
	
	@SubCommandHandler(parent = "iplot", name = "decorate")
	@CommandTabCompletion({"flat"})
	public void plotFlag(CommandSender sender, String label, String[] args){
		if (!Permission.PLOT_DECORATE.has(sender)){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return;
		}
		
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command can only be used in game");
			return;
		}
		
		if (args.length == 0){
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " decorate <decorator> [args]");
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
		
		if (!Permission.PLOT_FLAG_OTHER.has(sender) && !plot.getAdmin().equalsIgnoreCase(player.getName())){
			player.sendMessage(ChatColor.RED + "You do not own this plot");
			return;
		}
		
		if (args[0].equalsIgnoreCase("flat")){
			if (args.length != 3){
				sender.sendMessage(ChatColor.RED + "Usage: /" + label + " decorate flat <ground_block> <surface_block>");
				return;
			}
			
			Material groundBlock = Material.DIRT;
			Material surfaceBlock = Material.GRASS;
			byte groundData = 0;
			byte surfaceData = 0;
			
			String[] groundParts = args[1].split(":");
			String[] surfaceParts = args[2].split(":");
			
			try{
				groundBlock = Material.valueOf(groundParts[0].toUpperCase());
			}catch (IllegalArgumentException e){
				sender.sendMessage(ChatColor.RED + groundParts[0] + " is not a valid block name");
				return;
			}
			
			try{
				surfaceBlock = Material.valueOf(surfaceParts[0].toUpperCase());
			}catch (IllegalArgumentException e){
				sender.sendMessage(ChatColor.RED + surfaceParts[0] + " is not a valid block name");
				return;
			}
			
			if (!groundBlock.isBlock()){
				sender.sendMessage(ChatColor.RED + groundParts[0] + " is not a valid block name");
				return;
			}
			
			if (!surfaceBlock.isBlock()){
				sender.sendMessage(ChatColor.RED + surfaceParts[0] + " is not a valid block name");
				return;
			}
			
			if (groundParts.length == 2){
				try{
					groundData = Byte.parseByte(groundParts[1]);
				}catch (IllegalArgumentException e){
					sender.sendMessage(ChatColor.RED + groundParts[1] + " is not a valid data value");
					return;
				}
			}
			
			if (surfaceParts.length == 2){
				try{
					surfaceData = Byte.parseByte(surfaceParts[1]);
				}catch (IllegalArgumentException e){
					sender.sendMessage(ChatColor.RED + surfaceParts[1] + " is not a valid data value");
					return;
				}
			}
			
			(new FlatPlotDecorator(plugin, groundBlock, surfaceBlock, groundData, surfaceData)).decorate(plot);
		}else if (args[0].equalsIgnoreCase("biome")){
			if (args.length != 2){
				sender.sendMessage(ChatColor.RED + "Usage: /" + label + " decorate biome <biome_name>");
				return;
			}
			
			Biome biome = Biome.PLAINS;
			
			try{
				biome = Biome.valueOf(args[1].toUpperCase());
			}catch (IllegalArgumentException e){
				sender.sendMessage(ChatColor.RED + args[1] + " is not a valid biome");
				return;
			}
			
			(new BiomePlotDecorator(plugin, biome)).decorate(plot);
		}
	}
	
}

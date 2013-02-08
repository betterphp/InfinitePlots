package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.permissions.PermissionDefault;

import uk.co.jacekk.bukkit.baseplugin.v9.permissions.PluginPermission;

public class Permission {
	
	public static final PluginPermission PLOT_CLAIM				= new PluginPermission("infiniteplots.plot.claim",			PermissionDefault.TRUE,	"Allows the player to claim plots");
	public static final PluginPermission PLOT_UNCLAIM			= new PluginPermission("infiniteplots.plot.unclaim",		PermissionDefault.TRUE,	"Allows the player to unclaim plots");
	public static final PluginPermission PLOT_ADD_BUILDER		= new PluginPermission("infiniteplots.plot.add-builder",	PermissionDefault.TRUE,	"Allows the player to add builders to their plots");
	public static final PluginPermission PLOT_REMOVE_BUILDER	= new PluginPermission("infiniteplots.plot.remove-builder",	PermissionDefault.TRUE,	"Allows the player to remove builders from their plots");
	public static final PluginPermission PLOT_FLAG				= new PluginPermission("infiniteplots.plot.flag",			PermissionDefault.TRUE,	"Allows the player to modify their plots flags");
	public static final PluginPermission PLOT_FLAG_OTHER		= new PluginPermission("infiniteplots.plot.flag.others",	PermissionDefault.OP,	"Allows the player to modify other players plot flags");
	public static final PluginPermission PLOT_INFO				= new PluginPermission("infiniteplots.plot.info",			PermissionDefault.TRUE,	"Allows the player to view plot info");
	public static final PluginPermission PLOT_RESET				= new PluginPermission("infiniteplots.plot.reset",			PermissionDefault.TRUE,	"Allows the player to regenerate their plots");
	public static final PluginPermission PLOT_RESET_OTHER		= new PluginPermission("infiniteplots.plot.reset.others",	PermissionDefault.OP,	"Allows the player to regenerate other players plots");
	
	public static final PluginPermission PLOT_BUILD_ALL			= new PluginPermission("infiniteplots.plot.build-all",		PermissionDefault.OP,	"Allows the player to build in plots they do not own");
	
}

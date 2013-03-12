package uk.co.jacekk.bukkit.infiniteplots;

import org.bukkit.permissions.PermissionDefault;

import uk.co.jacekk.bukkit.baseplugin.v9.permissions.PluginPermission;

/**
 * The {@link PluginPermission} used to control access to various features.
 */
public class Permission {
	
	public static final PluginPermission PLOT_CLAIM					= new PluginPermission("infiniteplots.plot.claim",				PermissionDefault.TRUE,	"Allows the player to claim plots");
	public static final PluginPermission PLOT_UNCLAIM				= new PluginPermission("infiniteplots.plot.unclaim",			PermissionDefault.TRUE,	"Allows the player to unclaim plots");
	public static final PluginPermission PLOT_UNCLAIM_OTHERS		= new PluginPermission("infiniteplots.plot.unclaim.others",		PermissionDefault.OP, 	"Allows the player to unclaim other players plots");
	public static final PluginPermission PLOT_ADD_BUILDER			= new PluginPermission("infiniteplots.plot.add-builder",		PermissionDefault.TRUE,	"Allows the player to add builders to their plots");
	public static final PluginPermission PLOT_REMOVE_BUILDER		= new PluginPermission("infiniteplots.plot.remove-builder",		PermissionDefault.TRUE,	"Allows the player to remove builders from their plots");
	public static final PluginPermission PLOT_FLAG					= new PluginPermission("infiniteplots.plot.flag",				PermissionDefault.TRUE,	"Allows the player to modify their plots flags");
	public static final PluginPermission PLOT_FLAG_OTHER			= new PluginPermission("infiniteplots.plot.flag.others",		PermissionDefault.OP,	"Allows the player to modify other players plot flags");
	public static final PluginPermission PLOT_SET_BIOME				= new PluginPermission("infiniteplots.plot.set-biome",			PermissionDefault.TRUE,	"Allows the player to set their plots biome");
	public static final PluginPermission PLOT_SET_BIOME_OTHERS		= new PluginPermission("infiniteplots.plot.set-biome.others",	PermissionDefault.OP,	"Allows the player to set biome of other player plots");
	public static final PluginPermission PLOT_INFO					= new PluginPermission("infiniteplots.plot.info",				PermissionDefault.TRUE,	"Allows the player to view plot info");
	public static final PluginPermission PLOT_RESET					= new PluginPermission("infiniteplots.plot.reset",				PermissionDefault.TRUE,	"Allows the player to regenerate their plots");
	public static final PluginPermission PLOT_RESET_OTHER			= new PluginPermission("infiniteplots.plot.reset.others",		PermissionDefault.OP,	"Allows the player to regenerate other players plots");
	public static final PluginPermission PLOT_LIST					= new PluginPermission("infiniteplots.plot.list",				PermissionDefault.TRUE,	"Allows the player to list their plots");
	public static final PluginPermission PLOT_LIST_OTHER			= new PluginPermission("infiniteplots.plot.list.others",		PermissionDefault.OP,	"Allows the player to list another players plots");
	public static final PluginPermission PLOT_TELEPORT				= new PluginPermission("infiniteplots.plot.teleport",			PermissionDefault.TRUE,	"Allows the player to teleport to their plots");
	public static final PluginPermission PLOT_TELEPORT_OTHER		= new PluginPermission("infiniteplots.plot.teleport.others",	PermissionDefault.OP,	"Allows the player to teleport to another players plots");
	
	public static final PluginPermission PLOT_BUILD_ALL				= new PluginPermission("infiniteplots.plot.build-all",			PermissionDefault.OP,	"Allows the player to build in plots they do not own");
	public static final PluginPermission PLOT_BYPASS_CLAIM_LIMIT	= new PluginPermission("infiniteplots.plot.bypass-claim-limit",	PermissionDefault.OP,	"Allows the player to claim more plots than the limit");
	
}

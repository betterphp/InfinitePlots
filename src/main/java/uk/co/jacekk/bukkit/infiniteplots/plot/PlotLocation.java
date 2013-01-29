package uk.co.jacekk.bukkit.infiniteplots.plot;

import org.bukkit.Location;

import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;

/**
 * Represents the location of a plot in plot space, this start from 0,0
 * at the 0,0 block coordinate and move out in all directions, 
 */
public class PlotLocation {
	
	private String worldName;
	private int x, z;
	
	public PlotLocation(String worldName, int x, int z){
		this.worldName = worldName;
		this.x = x;
		this.z = z;
	}
	
	/**
	 * Creates a new PlotLocation based on the location in block space.
	 * 
	 * @param worldLocation The {@link Location} in block space.
	 * @return The {@link PlotLocation} in plot space.
	 */
	public static PlotLocation fromWorldLocation(Location worldLocation){
		int x = worldLocation.getBlockX() / InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE);
		int z = worldLocation.getBlockZ() / InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE);
		
		return new PlotLocation(worldLocation.getWorld().getName(), x, z);
	}
	
	/**
	 * Gets the name of the world that this location is in.
	 * 
	 * @return The name of the world.
	 */
	public String getWorldName(){
		return this.worldName;
	}
	
	/**
	 * Gets the X coordinate of this location.
	 * 
	 * @return The X coordinate, note that this is in plot space not block space.
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Gets the Z coordinate of this location.
	 * 
	 * @return The Z coordinate, note that this is in plot space not block space.
	 */
	public int getZ(){
		return this.z;
	}
	
	@Override
	public int hashCode(){
		return (37 * this.worldName.hashCode()) + ((this.x * 29) ^  this.z);
	}
	
	@Override
	public boolean equals(Object object){
		if (!(object instanceof PlotLocation)){
			return false;
		}
		
		PlotLocation compare = (PlotLocation) object;
		
		return (this.x == compare.x && this.z == compare.z && this.worldName.equals(compare.worldName));
	}
	
	@Override
	public String toString(){
		return "PlotLocation(worldName=" + this.worldName + ", x=" + this.x + ", z=" + this.z + ")";
	}
	
}

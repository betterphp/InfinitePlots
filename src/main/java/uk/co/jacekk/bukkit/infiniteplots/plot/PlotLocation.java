package uk.co.jacekk.bukkit.infiniteplots.plot;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import uk.co.jacekk.bukkit.infiniteplots.Config;
import uk.co.jacekk.bukkit.infiniteplots.InfinitePlots;

/**
 * Represents the location of a plot in plot space, this start from 0,0 
 * at the 0,0 block coordinate and move out in all directions,
 */
public class PlotLocation {
	
	/**
	 * Represents a direction in plot space, north is the +x direction.
	 */
	public enum Direction {
		
		NORTH(1, 0),
		EAST(0, 1),
		SOUTH(-1, 0),
		WEST(0, -1);
		
		private int dx;
		private int dz;
		
		private Direction(int dx, int dz){
			this.dx = dx;
			this.dz = dz;
		}
		
		public int getDx(){
			return this.dx;
		}
		
		public int getDz(){
			return this.dz;
		}
		
	}
	
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
		int x = (int) Math.floor((double) worldLocation.getBlockX() / (double) InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE));
		int z = (int) Math.floor((double) worldLocation.getBlockZ() / (double) InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE));
		
		return new PlotLocation(worldLocation.getWorld().getName(), x, z);
	}
	
	/**
	 * Gets the location in the world.
	 * 
	 * @return The {@link Location}
	 */
	public Location getWorldLocation(){
		int x = (int) Math.floor((double) this.x * (double) InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE));
		int z = (int) Math.floor((double) this.z * (double) InfinitePlots.getInstance().config.getInt(Config.GRID_SIZE));
		
		return new Location(Bukkit.getWorld(this.worldName), x, InfinitePlots.getInstance().config.getInt(Config.GRID_HEIGHT) + 2, z);
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
	
	/**
	 * Gets a new plot location relative to this one.
	 * 
	 * @param direction The direction, north is the +x direction
	 * @param distance The number of plots away from this one to get the location for
	 * @return The new {@link PlotLocation}
	 */
	public PlotLocation getRelative(Direction direction, int distance){
		return new PlotLocation(this.worldName, this.x + direction.getDx() * distance, this.z + direction.getDz() * distance);
	}
	
	/**
	 * Gets a new plot location relative to this one.
	 * 
	 * @param direction The direction, north is the +x direction
	 * @return The new {@link PlotLocation}
	 */
	public PlotLocation getRelative(Direction direction){
		return this.getRelative(direction, 1);
	}
	
	@Override
	public int hashCode(){
		return (37 * this.worldName.hashCode()) + ((this.x * 29) ^ this.z);
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

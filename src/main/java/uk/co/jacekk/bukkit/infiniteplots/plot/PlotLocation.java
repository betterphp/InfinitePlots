package uk.co.jacekk.bukkit.infiniteplots.plot;

public class PlotLocation {
	
	private String worldName;
	private int x, z;
	
	public PlotLocation(String worldName, int x, int z){
		this.worldName = worldName;
		this.x = x;
		this.z = z;
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
	
}

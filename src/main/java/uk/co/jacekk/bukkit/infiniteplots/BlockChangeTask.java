package uk.co.jacekk.bukkit.infiniteplots;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.Block;

import uk.co.jacekk.bukkit.baseplugin.scheduler.BaseTask;

/**
 * Handles making large block changes over a period of time.
 * 
 * Care should be taken when using this for structures as any blocks
 * affected by physics will be allowed to fall/pop-off before its
 * supporting blocks are changed.  
 */
public class BlockChangeTask extends BaseTask<InfinitePlots> {
	
	private int taskID;
	private int perTick;
	
	private LinkedHashMap<Block, Material> materials;
	private LinkedHashMap<Block, Byte> dataValues;
	
	public BlockChangeTask(InfinitePlots plugin){
		super(plugin);
		
		this.materials = new LinkedHashMap<Block, Material>();
		this.dataValues = new LinkedHashMap<Block, Byte>();
	}
	
	/**
	 * Adds a block changes to the schedule list.
	 * 
	 * @param block The block to change
	 * @param type The type to set it to
	 * @param data The data value to set
	 * @param force Whether to force the change even if the block is already the same type 
	 */
	public void setBlockType(Block block, Material type, byte data, boolean force){
		if (force || (block.getType() != type || block.getData() != data)){
			this.materials.put(block, type);
			this.dataValues.put(block, data);
		}
	}
	
	/**
	 * Adds a block changes to the schedule list.
	 * 
	 * @param block The block to change
	 * @param type The type to set it to
	 * @param data The data value to set
	 */
	public void setBlockType(Block block, Material type, byte data){
		this.setBlockType(block, type, data, false);
	}
	
	/**
	 * Adds a block changes to the schedule list.
	 * 
	 * @param block The block to change
	 * @param type The type to set it to
	 * @param force Whether to force the change even if the block is already the same type
	 */
	public void setBlockType(Block block, Material type, boolean force){
		this.setBlockType(block, type, (byte) 0, force);
	}
	
	/**
	 * Adds a block changes to the schedule list.
	 * 
	 * @param block The block to change
	 * @param type The type to set it to
	 */
	public void setBlockType(Block block, Material type){
		this.setBlockType(block, type, false);
	}
	
	/**
	 * Starts the scheduler running.
	 * 
	 * @param delay How many ticks to wait between operations
	 * @param perTick How many blocks to change per operation
	 */
	public void start(int delay, int perTick){
		this.perTick = perTick;
		this.taskID = plugin.scheduler.scheduleSyncRepeatingTask(plugin, this, 0, delay);
	}
	
	@Override
	public void run(){
		Iterator<Entry<Block, Material>> materialIterator = this.materials.entrySet().iterator();
		Iterator<Entry<Block, Byte>> dataIterator = this.dataValues.entrySet().iterator();
		
		for (int i = 0; i < this.perTick && materialIterator.hasNext() && dataIterator.hasNext(); ++i){
			Entry<Block, Material> materialEntry = materialIterator.next();
			Entry<Block, Byte> dataEntry = dataIterator.next();
			
			materialIterator.remove();
			dataIterator.remove();
			
			materialEntry.getKey().setType(materialEntry.getValue());
			dataEntry.getKey().setData(dataEntry.getValue());
		}
		
		if (this.materials.isEmpty() || this.dataValues.isEmpty()){
			plugin.scheduler.cancelTask(this.taskID);
		}
	}
	
}

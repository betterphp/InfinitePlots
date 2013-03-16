package uk.co.jacekk.bukkit.infiniteplots;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.Block;

import uk.co.jacekk.bukkit.baseplugin.v9_1.scheduler.BaseTask;

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
	
	private LinkedHashMap<Block, Material> changes;
	
	public BlockChangeTask(InfinitePlots plugin){
		super(plugin);
		
		this.changes = new LinkedHashMap<Block, Material>();
	}
	
	/**
	 * Adds a block changes to the schedule list.
	 * 
	 * @param block The block to change
	 * @param type The type to set it to
	 * @param force Whether to force the change even if the block is already the same type 
	 */
	public void setBlockType(Block block, Material type, boolean force){
		if (force || block.getType() != type){
			this.changes.put(block, type);
		}
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
		Iterator<Entry<Block, Material>> iterator = this.changes.entrySet().iterator();
		
		for (int i = 0; i < this.perTick && iterator.hasNext(); ++i){
			Entry<Block, Material> entry = iterator.next();
			iterator.remove();
			
			entry.getKey().setType(entry.getValue());
		}
		
		if (this.changes.isEmpty()){
			plugin.scheduler.cancelTask(this.taskID);
		}
	}
	
}

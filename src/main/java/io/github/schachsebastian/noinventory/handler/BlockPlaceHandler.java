package io.github.schachsebastian.noinventory.handler;

import io.github.schachsebastian.noinventory.inventory.InventoryBlocker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceHandler implements Listener {
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (InventoryBlocker.isBlockItem(event.getItemInHand())) {
			event.setCancelled(true);
		}
	}
}

package io.github.schachsebastian.noinventory.handler;

import io.github.schachsebastian.noinventory.inventory.InventoryBlocker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class InventoryHandler implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (InventoryBlocker.isBlockItem(event.getCurrentItem())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		if (InventoryBlocker.isBlockItem(event.getCursor())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onInventoryClick(InventoryMoveItemEvent event) {
		if (InventoryBlocker.isBlockItem(event.getItem())) {
			event.setCancelled(true);
		}
	}
}

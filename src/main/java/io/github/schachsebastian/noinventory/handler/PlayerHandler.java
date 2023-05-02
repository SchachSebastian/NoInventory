package io.github.schachsebastian.noinventory.handler;

import io.github.schachsebastian.noinventory.inventory.InventoryBlocker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerHandler implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		InventoryBlocker.blockInventory(event.getPlayer());
	}
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (InventoryBlocker.isBlockItem(event.getItemDrop().getItemStack())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		InventoryBlocker.blockInventory(event.getPlayer());
	}
	@EventHandler
	public void onHandItemsSwap(PlayerSwapHandItemsEvent event) {
		if (InventoryBlocker.isBlockItem(event.getOffHandItem())) {
			event.setCancelled(true);
		}
	}
}

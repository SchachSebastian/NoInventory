package io.github.schachsebastian.noinventory.main;

import io.github.schachsebastian.noinventory.commands.SlotLimitCommand;
import io.github.schachsebastian.noinventory.handler.BlockPlaceHandler;
import io.github.schachsebastian.noinventory.handler.InventoryHandler;
import io.github.schachsebastian.noinventory.handler.PlayerHandler;
import io.github.schachsebastian.noinventory.inventory.InventoryBlocker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginInitializer extends JavaPlugin {
	private static PluginInitializer instance;
	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(InventoryBlocker::unblockInventory);
	}
	@Override
	public void onEnable() {
		instance = this;
		getCommand("slotlimit").setExecutor(new SlotLimitCommand());
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new InventoryHandler(), this);
		pluginManager.registerEvents(new PlayerHandler(), this);
		pluginManager.registerEvents(new BlockPlaceHandler(), this);
		try {
			InventoryBlocker.slotLimit = Integer.parseInt(readValue("slotLimit"));
		} catch (NumberFormatException e) {
			InventoryBlocker.slotLimit = 9;
		}
		Bukkit.getOnlinePlayers().forEach(InventoryBlocker::blockInventory);
	}
	public static PluginInitializer getInstance() {
		return instance;
	}
	public void saveValue(String key, String value) {
		getConfig().set(key, value);
		saveConfig();
	}
	public String readValue(String key) {
		return getConfig().getString(key);
	}
}

package io.github.schachsebastian.noinventory.commands;

import io.github.schachsebastian.noinventory.inventory.InventoryBlocker;
import io.github.schachsebastian.noinventory.main.PluginInitializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SlotLimitCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!commandSender.hasPermission("noinventory.slotlimit")) return false;
		if (strings.length > 1) {
			commandSender.sendMessage("§cSyntax: slotlimit [§oplayer§c]");
			return false;
		}
		if (strings.length == 0) {
			commandSender.sendMessage("The current slot limit is §a" + InventoryBlocker.slotLimit);
			return false;
		}
		try {
			int newSlotLimit = Integer.parseInt(strings[0]);
			if (newSlotLimit < 0 || newSlotLimit > 36) {
				commandSender.sendMessage("§cThe slot limit must be a positive number between 0 and 36!");
				return false;
			}
			InventoryBlocker.slotLimit = newSlotLimit;
			PluginInitializer.getInstance().saveValue("slotLimit", String.valueOf(newSlotLimit));
			commandSender.sendMessage("The slot limit was set to §a" + InventoryBlocker.slotLimit);
			Bukkit.getOnlinePlayers().forEach(InventoryBlocker::unblockInventory);
			Bukkit.getOnlinePlayers().forEach(InventoryBlocker::blockInventory);
		} catch (NumberFormatException e) {
			commandSender.sendMessage("§cInvalid number!");
			return false;
		}
		return true;
	}
}

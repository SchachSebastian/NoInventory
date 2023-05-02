package io.github.schachsebastian.noinventory.inventory;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InventoryBlocker {
	public static int slotLimit = 9;
	private static final String identifier = "no-inventory-blocker";
	private static final ItemStack blockerItem;
	static {
		blockerItem = new ItemStack(Material.BARRIER, 1);
		ItemMeta meta = blockerItem.getItemMeta();
		meta.setDisplayName(" ");
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addAttributeModifier(Attribute.GENERIC_LUCK, new AttributeModifier(identifier, 0, AttributeModifier.Operation.ADD_NUMBER));
		blockerItem.setItemMeta(meta);
	}
	private InventoryBlocker() {
	}
	public static void blockInventory(Player player) {
		PlayerInventory inventory = player.getInventory();
		List<ItemStack> items = Arrays.stream(inventory.getStorageContents()).limit(slotLimit).collect(Collectors.toList());
		List<ItemStack> itemsToDrop = Arrays.stream(inventory.getStorageContents()).skip(slotLimit).collect(Collectors.toList());
		itemsToDrop.forEach(c -> items.add(blockerItem));
		inventory.setStorageContents(items.toArray(new ItemStack[0]));
		itemsToDrop.stream().filter(Objects::nonNull).filter(i -> !isBlockItem(i)).forEach(
				c -> player.getWorld().dropItem(player.getLocation(), c));
	}
	public static void unblockInventory(Player player) {
		PlayerInventory inventory = player.getInventory();
		inventory.setStorageContents(Arrays.stream(inventory.getStorageContents()).filter(c -> !isBlockItem(c)).toArray(ItemStack[]::new));
	}
	public static boolean isBlockItem(ItemStack item) {
		if (item == null) return false;
		ItemMeta meta = item.getItemMeta();
		if (meta == null) return false;
		if (!meta.hasAttributeModifiers()) return false;
		AttributeModifier modifier = meta.getAttributeModifiers().get(Attribute.GENERIC_LUCK).stream().findFirst().orElse(null);
		if (modifier == null) return false;
		return modifier.getName().equals(identifier);
	}
}

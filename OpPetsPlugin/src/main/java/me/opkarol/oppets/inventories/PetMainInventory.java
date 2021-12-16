package me.opkarol.oppets.inventories;

import me.opkarol.oppets.inventories.holders.PetMainInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import static me.opkarol.oppets.utils.ConfigUtils.*;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

public class PetMainInventory {
    public String guiTitle = getMessage("PetMainInventory.title");

    private final Inventory inventory = Bukkit.createInventory(new PetMainInventoryHolder(), 27, guiTitle);

    public PetMainInventory() {
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setupInventory() {
        String path = "PetMainInventory.items.";
        inventory.setItem(10, itemCreator(Material.valueOf(getString(path + "level.material")), getMessage(path + "level.name"), getListString(path + "level.lore"), null, true, getBoolean(path + "level.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(12, itemCreator(Material.valueOf(getString(path + "name.material")), getMessage(path + "name.name"), getListString(path + "name.lore"), null, true, getBoolean(path + "name.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(14, itemCreator(Material.valueOf(getString(path + "settings.material")), getMessage(path + "settings.name"), getListString(path + "settings.lore"), null, true, getBoolean(path + "settings.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(16, itemCreator(Material.valueOf(getString(path + "respawn.material")), getMessage(path + "respawn.name"), getListString(path + "respawn.lore"), null, true, getBoolean(path + "respawn.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);
    }
}

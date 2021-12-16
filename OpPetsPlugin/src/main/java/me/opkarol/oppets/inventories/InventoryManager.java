package me.opkarol.oppets.inventories;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    private final List<Inventory> list = new ArrayList<>();

    public Inventory getInventoryByIndex(int index) {
        return list.get(index);
    }

    public void setupList() {
        list.add(new PetMainInventory().getInventory());
        /*
        list.add(1, new LevelInventory().getInventory());
        -> Not used due to its own placeholders that need to be generated every time it is being open
        -> SettingsInventory same as LevelInventory
         */
    }
}

package me.opkarol.oppets.inventory;

import org.bukkit.inventory.Inventory;

public abstract class InventoryAccessor implements IInventoryAccess {
    public abstract Inventory buildInventory();

    public OpInventoryBuilder getEmptyBuilder() {
        return new OpInventoryBuilder();
    }
}

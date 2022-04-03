package me.opkarol.oppets.addons;

import me.opkarol.oppets.interfaces.IHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * The type Addons inventory holder.
 */
public class AddonsInventoryHolder implements InventoryHolder, IHolder {
    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}

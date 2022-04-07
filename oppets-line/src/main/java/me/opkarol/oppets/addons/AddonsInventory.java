package me.opkarol.oppets.addons;

import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.interfaces.IAddon;
import me.opkarol.oppets.interfaces.IInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static me.opkarol.oppets.utils.InventoryUtils.*;

/**
 * The type Addons inventory.
 */
public class AddonsInventory implements IInventory {
    /**
     * The Inventory.
     */
    private final Inventory inventory;

    /**
     * Instantiates a new Guest inventory.
     */
    public AddonsInventory() {
        inventory = Bukkit.createInventory(new AddonsInventoryHolder(), 27, InventoriesCache.addonsInventoryTitle);
        setupInventory();
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets inventory.
     */
    private void setupInventory() {
        int i = 0;
        for (IAddon addon : AddonManager.getAddons()) {
            if (addon.canBeLaunched()) {
                Logger logger = addon.getPlugin().getLogger();
                inventory.setItem(i, itemCreator(Material.ANVIL, addon.getName(), addon.getDescription(), this));
                logger.info("Enabled " + addon.getName() + ", version: " + addon.getVersion() + " from [OpPets]!");
                i++;
            }
        }
        if (i == 0) {
            inventory.setItem(i, itemCreator(Material.BARRIER, "There are no addons", Collections.singletonList(""), this));
        }
        fillInventory(inventory);
    }

    /**
     * Sets place holders.
     *
     * @param lore the lore
     * @return the place holders
     */
    @Override
    @NotNull
    public List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore;
    }
}

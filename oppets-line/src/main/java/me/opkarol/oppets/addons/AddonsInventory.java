package me.opkarol.oppets.addons;

import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.cache.PageCache;
import me.opkarol.oppets.interfaces.IAddon;
import me.opkarol.oppets.interfaces.IInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Addons inventory.
 */
public class AddonsInventory implements IInventory {
    /**
     * The Inventory.
     */
    private final Inventory inventory;
    /**
     * The Cache.
     */
    public PageCache<IAddon> cache;

    /**
     * Instantiates a new Addons inventory.
     *
     * @param page the page
     */
    public AddonsInventory(int page) {
        inventory = Bukkit.createInventory(new AddonsInventoryHolder(), 54, InventoriesCache.addonsInventoryTitle);
        cache = new PageCache<>(inventory, AddonManager.getAddons().stream()
                .filter(IAddon::canBeLaunched).collect(Collectors.toList()), 28);
        cache.setupInventory(page);
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

package me.opkarol.oppets.addons;

import me.opkarol.oppets.cache.InventoryCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.exceptions.InvalidAddonException;
import me.opkarol.oppets.interfaces.IAddon;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Addon manager.
 */
public class AddonManager {
    /**
     * The constant verifiedAddons.
     */
    public static List<String> verifiedAddons = new ArrayList<>(Arrays.asList("OpPetsDiscord", "OpPetsRecipes"));
    /**
     * The constant addons.
     */
    private static final List<IAddon> addons = new ArrayList<>();

    /**
     * Add addon.
     *
     * @param addon the addon
     */
    public static void addAddon(@NotNull IAddon addon) throws InvalidAddonException {
        if (addon.canBeLaunched()) {
            if (addon.getPlugin().getName().equals(addon.getName())) {
                addons.add(addon);
                setCache();
            } else {
                throw new InvalidAddonException("Provided addon contains invalid name: " + addon.getPlugin().getName() + " should be " + addon.getName() + ".");
            }
        } else {
            throw new InvalidAddonException("Provided addon " + addon.getName() + " can't be launched, contains invalid specification.");
        }
    }

    /**
     * Gets addons.
     *
     * @return the addons
     */
    public static List<IAddon> getAddons() {
        return addons;
    }

    /**
     * The constant cache.
     */
    private static InventoryCache cache = new InventoryCache();

    /**
     * Gets addon.
     *
     * @param name the name
     * @return the addon
     */
    public static @NotNull Optional<IAddon> getAddon(String name) {
        return addons.stream()
                .filter(iAddon -> iAddon.getName().equals(name)).findFirst();
    }

    public static void setCache() {
        if (cache == null) {
            cache = new InventoryCache();
        }
        cache.setInventory(getCachedInventory());
    }

    /**
     * Gets cache.
     *
     * @return the cache
     */
    public static Inventory getCachedInventory() {
        if (cache == null) {
            cache = new InventoryCache();
        }
        if (cache.getInventory() == null) {
            cache.setInventory(new AddonsInventory().getInventory());
        }
        return cache.getInventory();
    }


    public static List<String> getStringAddons() {
        return addons.stream().map(IAddon::getName).collect(Collectors.toList());
    }

    /**
     * Test.
     */
    @TestOnly
    public void test() {
        // Method 1 - using a constructor parameter
        AddonConfig addon2 = new AddonConfig("YourAddonName", "v. 1.0.1", Collections.singletonList("This plugin allows you to be successful in live and have amazing feelings."), Database.getInstance());
        AddonManager.addAddon(addon2);

        // Method 2 - using AddonConfig methods
        AddonConfig addon = new AddonConfig();
        addon.setName("YourAddonName");
        addon.setVersion("v. 1.0.1");
        addon.setDescription("This plugin allows you to be successful in live and have amazing feelings.");
        addon.setDescription(new ArrayList<>(Arrays.asList("First line of description", "Second line!")));
        addon.setPlugin(Database.getInstance());
        AddonManager.addAddon(addon);
    }
}

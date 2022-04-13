package me.opkarol.oppets.addons;

import me.opkarol.oppets.cache.InventoryCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.exceptions.InvalidAddonException;
import me.opkarol.oppets.interfaces.IAddon;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
     * The constant database.
     */
    private static Database database = null;

    /**
     * Add addon database.
     *
     * @param addon the addon
     * @return the database
     * @throws InvalidAddonException the invalid addon exception
     */
    public static Database addAddon(@NotNull IAddon addon) throws InvalidAddonException {
        if (addon.canBeLaunched()) {
            if (addon.getPlugin().getName().equals(addon.getName())) {
                addons.add(addon);
                return database;
            } else {
                throw new InvalidAddonException("Provided addon contains invalid name: " + addon.getName() + ", plugin name should be equal to " + addon.getPlugin().getName() + ".");
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
     * The Cache.
     */
    private static List<InventoryCache> cache;

    /**
     * Gets cache.
     *
     * @param page the page
     * @return the cache
     */
    public static @NotNull InventoryCache getCache(int page) {
        if (cache == null) {
            cache = new ArrayList<>();
        }
        if (cache.size() <= page) {
            cache.add(page, new InventoryCache());
        }
        InventoryCache current = cache.get(page);
        if (current.getInventory() == null) {
            current.setInventory(new AddonsInventory(page).getInventory());
        }
        return current;
    }

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

    /**
     * Gets string addons.
     *
     * @return the string addons
     */
    public static List<String> getStringAddons() {
        return addons.stream().map(IAddon::getName).collect(Collectors.toList());
    }

    /**
     * Sets database.
     *
     * @param database the database
     */
    public static void setDatabase(Database database) {
        if (AddonManager.database == null) {
            AddonManager.database = database;
        }
    }

    //@TestOnly
    //public void test() {
    //    // Method 1 - using a constructor parameter
    //    AddonConfig addon2 = new AddonConfig("YourAddonName", "v. 1.0.1", Collections.singletonList("This plugin allows you to be successful in live and have amazing feelings."), database.getPlugin());
    //    AddonManager.addAddon(addon2);
    //
    //    // Method 2 - using AddonConfig methods
    //    AddonConfig addon = new AddonConfig();
    //    addon.setName("YourAddonName");
    //    addon.setVersion("v. 1.0.1");
    //    addon.setDescription("This plugin allows you to be successful in live and have amazing feelings.");
    //    addon.setDescription(new ArrayList<>(Arrays.asList("First line of description", "Second line!")));
    //    addon.setPlugin(database.getPlugin());
    //    AddonManager.addAddon(addon);
    //}
}

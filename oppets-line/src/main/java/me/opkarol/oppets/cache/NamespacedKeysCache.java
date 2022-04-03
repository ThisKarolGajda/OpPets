package me.opkarol.oppets.cache;

import me.opkarol.oppets.databases.Database;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

/**
 * The type Namespaced keys cache.
 */
public class NamespacedKeysCache {
    /**
     * The constant plugin.
     */
    private static final Plugin plugin = Database.getInstance();
    /**
     * The constant typeKey.
     */
    public static NamespacedKey typeKey = new NamespacedKey(plugin, "oppets-shop-type");
    /**
     * The constant priceKey.
     */
    public static NamespacedKey priceKey = new NamespacedKey(plugin, "oppets-shop-price");
    /**
     * The constant petKey.
     */
    public static NamespacedKey petKey = new NamespacedKey(plugin, "oppets-entity-key");
}

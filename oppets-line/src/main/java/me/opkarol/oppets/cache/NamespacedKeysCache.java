package me.opkarol.oppets.cache;

import me.opkarol.oppets.databases.APIDatabase;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class NamespacedKeysCache {
    private static final Plugin plugin = APIDatabase.getInstance().getPlugin();
    public static NamespacedKey typeKey = new NamespacedKey(plugin, "oppets-shop-type");
    public static NamespacedKey priceKey = new NamespacedKey(plugin, "oppets-shop-price");
    public static NamespacedKey petKey = new NamespacedKey(plugin, "oppets-entity-key");
    public static NamespacedKey summonItemKey = new NamespacedKey(plugin, "oppets-summon-item-key");
    public static String noPetsString = "<NO-PETS>" ;
}

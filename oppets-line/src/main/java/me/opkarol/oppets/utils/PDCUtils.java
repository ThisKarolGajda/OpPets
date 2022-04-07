package me.opkarol.oppets.utils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * The type Pdc utils.
 */
@SuppressWarnings("unused")
public class PDCUtils {

    /**
     * Gets nbt.
     *
     * @param item the item
     * @param key  the key
     * @return the nbt
     */
    public static @Nullable String getNBT(@NotNull ItemStack item, NamespacedKey key) {
        if (!item.hasItemMeta()) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (pdc.has(key, PersistentDataType.STRING)) {
            return pdc.get(key, PersistentDataType.STRING);
        }
        return null;
    }

    /**
     * Gets nbt.
     *
     * @param entity the entity
     * @param key    the key
     * @return the nbt
     */
    public static @Nullable String getNBT(@NotNull Entity entity, NamespacedKey key) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        if (pdc.has(key, PersistentDataType.STRING)) {
            return pdc.get(key, PersistentDataType.STRING);
        }
        return null;
    }

    /**
     * Add nbt.
     *
     * @param item  the item
     * @param key   the key
     * @param value the value
     */
    public static void addNBT(@NotNull ItemStack item, NamespacedKey key, String value) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        if (meta == null) {
            return;
        }
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(key, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    /**
     * Add nbt.
     *
     * @param entity the entity
     * @param key    the key
     * @param value  the value
     */
    public static void addNBT(@NotNull Entity entity, NamespacedKey key, String value) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.set(key, PersistentDataType.STRING, value);
    }

    /**
     * Has nbt boolean.
     *
     * @param item the item
     * @param key  the key
     * @return the boolean
     */
    public static boolean hasNBT(@NotNull ItemStack item, NamespacedKey key) {
        if (!item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.has(key, PersistentDataType.STRING);
    }

    /**
     * Has nbt boolean.
     *
     * @param entity the entity
     * @param key    the key
     * @return the boolean
     */
    public static boolean hasNBT(@NotNull Entity entity, NamespacedKey key) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        return pdc.has(key, PersistentDataType.STRING);
    }

    /**
     * Remove nbt.
     *
     * @param item the item
     * @param key  the key
     */
    public static void removeNBT(@NotNull ItemStack item, NamespacedKey key) {
        if (!item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.remove(key);
        item.setItemMeta(meta);
    }

    /**
     * Remove nbt.
     *
     * @param entity the entity
     * @param key    the key
     */
    public static void removeNBT(@NotNull Entity entity, NamespacedKey key) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.remove(key);
    }

    /**
     * Gets all values.
     *
     * @param item the item
     * @return the all values
     */
    public static @NotNull HashMap<String, String> getAllValues(@NotNull ItemStack item) {
        HashMap<String, String> map = new HashMap<>();
        if (!item.hasItemMeta()) {
            return map;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return map;
        }
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        for (NamespacedKey key : pdc.getKeys()) {
            map.put(key.toString(), pdc.get(key, PersistentDataType.STRING));
        }
        return map;
    }
}

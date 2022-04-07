package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IInventory;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.cache.NamespacedKeysCache.priceKey;
import static me.opkarol.oppets.cache.NamespacedKeysCache.typeKey;
import static me.opkarol.oppets.utils.FormatUtils.formatList;
import static me.opkarol.oppets.utils.FormatUtils.formatMessage;

/**
 * The type Inventory utils.
 */
public class InventoryUtils {
    private static FileConfiguration config = Database.getInstance().getConfig();


    /**
     * Item creator shop item stack.
     *
     * @param type      the type
     * @param price     the price
     * @param path      the path
     * @param inventory the inventory
     * @return the item stack
     */
    public static @Nullable ItemStack itemCreatorShop(String type, int price, String path, @NotNull IInventory inventory) {
        ItemStack item = itemCreator(path, inventory);
        if (item == null) {
            return null;
        }
        PDCUtils.addNBT(item, priceKey, String.valueOf(price));
        PDCUtils.addNBT(item, typeKey, type);
        return item;
    }

    /**
     * Gets value from key.
     *
     * @param namespacedKey the namespaced key
     * @param meta          the meta
     * @param type          the type
     * @return the value from key
     * @see PDCUtils#getNBT(ItemStack, NamespacedKey)
     * @since 0.8.3.2
     */
    @Deprecated
    public static @Nullable Object getValueFromKey(NamespacedKey namespacedKey, @NotNull ItemMeta meta, ItemTagType type) {
        CustomItemTagContainer tagContainer = meta.getCustomTagContainer();
        if (tagContainer.hasCustomTag(namespacedKey, type)) {
            return tagContainer.getCustomTag(namespacedKey, type);
        }
        return null;
    }

    /**
     * Item creator lamp item stack.
     *
     * @param path      the path
     * @param lights    the lights
     * @param inventory the inventory
     * @return the item stack
     */
    public static @Nullable ItemStack itemCreatorLamp(String path, boolean lights, @NotNull IInventory inventory) {
        Material material;
        switch (String.valueOf(lights)) {
            case "true": {
                material = Material.RED_CONCRETE;
                break;
            }
            case "false": {
                material = Material.BLACK_CONCRETE;
                break;
            }
            default: {
                material = Material.YELLOW_CONCRETE;
                break;
            }
        }
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        meta.setDisplayName(formatMessage(config.getString(path + "name")));
        meta.setLore(formatList(inventory.setPlaceHolders(config.getStringList(path + "lore"))));
        meta.setAttributeModifiers(null);
        meta.setUnbreakable(true);
        if (config.getBoolean(path + "glow")) meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Gets blank glass panes.
     *
     * @param material the material
     * @return the blank glass panes
     * @since 0.8.3.4
     * @see InventoryUtils#getEmptyItemStack(Material)
     */
    @Deprecated
    public static @Nullable ItemStack getBlankGlassPanes(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        meta.setDisplayName(formatMessage("&k"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Sets empty glass panes.
     *
     * @param material  the material
     * @param inventory the inventory
     * @since 0.8.3.4
     * @see InventoryUtils#fillInventory(Inventory, Material...)
     */
    @Deprecated
    public static void setupEmptyGlassPanes(Material material, @NotNull Inventory inventory) {
        ItemStack glass = getBlankGlassPanes(material);
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
    }
    
    public static void fillInventory(Inventory inventory, Material... material) {
        List<Material> materials = Arrays.stream(material).collect(Collectors.toList());
        if (materials.isEmpty()) {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) == null) {
                    inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                }
            }
            return;
        }
        int size = material.length;
        int currentValue = 0;
        for (int itemNumber = 0; itemNumber < inventory.getSize(); itemNumber++) {
            if ((size - 1) == currentValue) {
                currentValue = 0;
            } else {
                currentValue++;
            }
            if (inventory.getItem(itemNumber) == null) {
                inventory.setItem(itemNumber, getEmptyItemStack(materials.get(currentValue)));
            }
        }
    }

    public static @NotNull ItemStack getEmptyItemStack(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }
        meta.setDisplayName(formatMessage("&l"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.addUnsafeEnchantment(Enchantment.LUCK, 1);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Item creator item stack.
     *
     * @param path      the path
     * @param inventory the inventory
     * @return the item stack
     */
    public static @Nullable ItemStack itemCreator(String path, @NotNull IInventory inventory) {
        ItemStack item = new ItemStack(Material.valueOf(config.getString(path + "material")));
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        meta.setDisplayName(formatMessage(config.getString(path + "name")));
        meta.setLore(formatList(inventory.setPlaceHolders(config.getStringList(path + "lore"))));
        meta.setAttributeModifiers(null);
        meta.setUnbreakable(true);
        if (config.getBoolean(path + "glow")) meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static @NotNull ItemStack itemCreator(Material material, String name, List<String> lore, @NotNull IInventory inventory) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return new ItemStack(material);
        }
        meta.setDisplayName(formatMessage(name));
        meta.setLore(formatList(inventory.setPlaceHolders(lore)));
        meta.setAttributeModifiers(null);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}

package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
import java.util.HashMap;
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
    /**
     * The constant config.
     */
    private static FileConfiguration config = ConfigUtils.getConfig();
    /**
     * The constant styles.
     */
    private static final FillStyles styles = new FillStyles();

    /**
     * Item creator shop item stack.
     *
     * @param type      the type
     * @param price     the price
     * @param path      the path
     * @param inventory the inventory
     * @return the item stack
     */
    public static @NotNull ItemStack itemCreatorShop(String type, int price, String path, @NotNull IInventory inventory) {
        ItemStack item = itemCreator(path, inventory);
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
     */
    @Deprecated
    @SuppressWarnings("all")
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

    /**
     * Fill inventory.
     *
     * @param inventory the inventory
     * @param material  the material
     */
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

    /**
     * Gets empty item stack.
     *
     * @param material the material
     * @return the empty item stack
     */
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
     * Fill styled inventory.
     *
     * @param inventory the inventory
     * @param style     the style
     */
    public static void fillStyledInventory(Inventory inventory, @NotNull FillStyles.INVENTORY_STYLE style) {
        switch (style) {
            case BOOK: {
                ItemStack item = getEmptyItemStack(Material.BLACK_STAINED_GLASS_PANE);
                int size = inventory.getSize();
                for (int i : styles.getMap().get(size)) {
                    inventory.setItem(i, item);
                }
                break;
            }
            case SQUARE: {
                ItemStack item = getEmptyItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemStack corner = getEmptyItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                int size = inventory.getSize();
                for (int i : styles.getMap().get(size)) {
                    inventory.setItem(i, item);
                }
                for (int i : styles.getCorners().get(size)) {
                    inventory.setItem(i, corner);
                }
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + style);
        }
    }

    /**
     * Item creator item stack.
     *
     * @param path      the path
     * @param inventory the inventory
     * @return the item stack
     */
    public static @NotNull ItemStack itemCreator(String path, @NotNull IInventory inventory) {
        ItemStack item = new ItemStack(Material.valueOf(config.getString(path + "material")));
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
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
     * Item creator item stack.
     *
     * @param material  the material
     * @param name      the name
     * @param lore      the lore
     * @param inventory the inventory
     * @return the item stack
     */
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

    /**
     * Sets config.
     *
     * @param config the config
     */
    public static void setConfig(FileConfiguration config) {
        InventoryUtils.config = config;
    }

    /**
     * The type Fill styles.
     */
    public static class FillStyles {
        /**
         * The Sizes.
         */
        private HashMap<Integer, List<Integer>> sizes = new HashMap<>();
        /**
         * The Corners.
         */
        private HashMap<Integer, List<Integer>> corners = new HashMap<>();

        /**
         * The enum Inventory style.
         */
        public enum INVENTORY_STYLE {
            /**
             * Book inventory style.
             */
            BOOK,
            /**
             * Square inventory style.
             */
            SQUARE
        }

        /**
         * Gets map.
         *
         * @return the map
         */
        public HashMap<Integer, List<Integer>> getMap() {
            if (sizes == null) {
                sizes = new HashMap<>();
            }
            if (sizes.isEmpty()) {
                //TODO fill the gaps
                sizes.put(9, Arrays.asList(0, 8));
                sizes.put(18, Arrays.asList(0, 8, 9, 17));
                sizes.put(27, Arrays.asList(0, 8, 9, 17, 18, 26));
                sizes.put(36, Arrays.asList(0, 8, 9, 17, 18, 26, 27, 35));
                sizes.put(45, Arrays.asList(0, 8, 9, 17, 18, 26, 27, 35, 36, 44));
                sizes.put(54, Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53));
            }
            return sizes;
        }

        /**
         * Gets corners.
         *
         * @return the corners
         */
        public HashMap<Integer, List<Integer>> getCorners() {
            if (corners == null) {
                corners = new HashMap<>();
            }
            if (corners.isEmpty()) {
                corners.put(9, Arrays.asList(0, 8));
                corners.put(18, Arrays.asList(0, 8, 9, 17));
                corners.put(27, Arrays.asList(0, 8, 18, 26));
                corners.put(36, Arrays.asList(0, 8, 27, 35));
                corners.put(45, Arrays.asList(0, 8, 36, 44));
                corners.put(54, Arrays.asList(0, 8, 45, 53));
            }
            return corners;
        }
    }
}
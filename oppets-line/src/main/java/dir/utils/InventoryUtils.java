package dir.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.interfaces.IInventory;
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

import static dir.utils.FormatUtils.formatList;
import static dir.utils.FormatUtils.formatMessage;

public class InventoryUtils {

    public static NamespacedKey typeKey = new NamespacedKey(Database.getInstance(), "oppets-shop-type");
    public static NamespacedKey priceKey = new NamespacedKey(Database.getInstance(), "oppets-shop-price");

    public static @NotNull ItemStack itemCreatorShop(String type, int price, String path, @NotNull IInventory inventory) {
        ItemStack item = itemCreator(path, inventory);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.getCustomTagContainer().setCustomTag(typeKey, ItemTagType.STRING, type);
        meta.getCustomTagContainer().setCustomTag(priceKey, ItemTagType.INTEGER, price);
        item.setItemMeta(meta);
        return item;
    }

    public static @Nullable Object getValueFromKey(NamespacedKey namespacedKey, @NotNull ItemMeta meta, ItemTagType type) {
        CustomItemTagContainer tagContainer = meta.getCustomTagContainer();

        if (tagContainer.hasCustomTag(namespacedKey, type)) {
            return tagContainer.getCustomTag(namespacedKey, type);
        }
        return null;
    }

    public static @NotNull ItemStack itemCreatorLamp(String path, boolean lights, @NotNull IInventory inventory) {
        FileConfiguration config = Database.getInstance().getConfig();
        Material material;
        switch (String.valueOf(lights)) {
            case "true" -> material = Material.RED_CONCRETE;
            case "false" -> material = Material.BLACK_CONCRETE;
            default -> material = Material.YELLOW_CONCRETE;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(formatMessage(config.getString(path + "name")));
        meta.setLore(formatList(inventory.setPlaceHolders(config.getStringList(path + "lore"))));
        meta.setAttributeModifiers(null);
        meta.setUnbreakable(true);
        if (config.getBoolean(path + "glow")) meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static @NotNull ItemStack getBlankGlassPanes(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(formatMessage("&k"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        item.setItemMeta(meta);
        return item;
    }

    public static void setupEmptyGlassPanes(Material material, @NotNull Inventory inventory) {
        ItemStack glass = getBlankGlassPanes(material);
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
    }


    public static @NotNull ItemStack itemCreator(String path, @NotNull IInventory inventory) {
        FileConfiguration config = Database.getInstance().getConfig();
        ItemStack item = new ItemStack(Material.valueOf(config.getString(path + "material")));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(formatMessage(config.getString(path + "name")));
        meta.setLore(formatList(inventory.setPlaceHolders(config.getStringList(path + "lore"))));
        meta.setAttributeModifiers(null);
        meta.setUnbreakable(true);
        if (config.getBoolean(path + "glow")) meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}

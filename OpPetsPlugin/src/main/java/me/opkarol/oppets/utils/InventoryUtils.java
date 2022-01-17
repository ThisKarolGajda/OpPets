package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import com.google.common.collect.Multimap;
import me.opkarol.oppets.OpPets;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static me.opkarol.oppets.utils.FormatUtils.formatList;
import static me.opkarol.oppets.utils.FormatUtils.formatMessage;

public class InventoryUtils {

    public static @NotNull ItemStack itemCreator(Material material, String displayName, List<String> lore, Multimap<Attribute, AttributeModifier> attributeModifierMultiMap, boolean unbreakable, boolean glow, Map<Enchantment, Integer> enchantmentIntegerMap, ItemFlag... itemFlags) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(formatMessage(displayName));
        meta.setLore(formatList(lore));
        meta.setAttributeModifiers(attributeModifierMultiMap);
        meta.setUnbreakable(unbreakable);
        if (glow) meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(itemFlags);
        if (enchantmentIntegerMap != null) item.addUnsafeEnchantments(enchantmentIntegerMap);
        item.setItemMeta(meta);
        return item;
    }

    public static NamespacedKey typeKey = new NamespacedKey(OpPets.getInstance(), "oppets-shop-type");
    public static NamespacedKey priceKey = new NamespacedKey(OpPets.getInstance(), "oppets-shop-price");
    public static @NotNull ItemStack itemCreatorShop(String type, int price, Material material, String displayName, List<String> lore, Multimap<Attribute, AttributeModifier> attributeModifierMultiMap, boolean unbreakable, boolean glow, Map<Enchantment, Integer> enchantmentIntegerMap, ItemFlag... itemFlags) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(formatMessage(displayName));
        meta.setLore(formatList(lore));
        meta.setAttributeModifiers(attributeModifierMultiMap);
        meta.setUnbreakable(unbreakable);
        if (glow) meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(itemFlags);
        if (enchantmentIntegerMap != null) item.addUnsafeEnchantments(enchantmentIntegerMap);
        meta.getCustomTagContainer().setCustomTag(typeKey, ItemTagType.STRING, type);
        meta.getCustomTagContainer().setCustomTag(priceKey, ItemTagType.INTEGER, price);
        item.setItemMeta(meta);
        return item;
    }

    public static @Nullable Object getValueFromKey(NamespacedKey namespacedKey, @NotNull ItemMeta meta, ItemTagType type) {
        CustomItemTagContainer tagContainer = meta.getCustomTagContainer();

        if(tagContainer.hasCustomTag(namespacedKey, type)) {
            return tagContainer.getCustomTag(namespacedKey, type);
        }
        return null;
    }

    public static @NotNull ItemStack itemCreatorLamp(String displayName, List<String> lore, Multimap<Attribute, AttributeModifier> attributeModifierMultiMap, boolean unbreakable, boolean glow, Map<Enchantment, Integer> enchantmentIntegerMap, boolean lights, ItemFlag... itemFlags) {
        Material material;
        if (lights) material = Material.RED_CONCRETE;
        else material = Material.BLACK_CONCRETE;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(formatMessage(displayName));
        meta.setLore(formatList(lore));
        meta.setAttributeModifiers(attributeModifierMultiMap);
        meta.setUnbreakable(unbreakable);
        if (glow) meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(itemFlags);
        if (enchantmentIntegerMap != null) item.addUnsafeEnchantments(enchantmentIntegerMap);
        item.setItemMeta(meta);
        return item;
    }

    public static @NotNull ItemStack getBlankGlassPanes(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(formatMessage("&9"));
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
}

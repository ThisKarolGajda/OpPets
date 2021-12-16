package me.opkarol.oppets.utils;

import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

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

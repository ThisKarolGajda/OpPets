package me.opkarol.oppets.utils.external;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.map.OpMap;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PDCUtils {

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

    public static @Nullable String getNBT(@NotNull Entity entity, NamespacedKey key) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        if (pdc.has(key, PersistentDataType.STRING)) {
            return pdc.get(key, PersistentDataType.STRING);
        }
        return null;
    }

    public static void addNBT(@NotNull ItemStack item, NamespacedKey key, String value) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        if (meta == null) {
            return;
        }
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(key, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    public static void addNBT(@NotNull Entity entity, NamespacedKey key, String value) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.set(key, PersistentDataType.STRING, value);
    }

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

    public static boolean hasNBT(@NotNull Entity entity, NamespacedKey key) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        return pdc.has(key, PersistentDataType.STRING);
    }

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

    public static void removeNBT(@NotNull Entity entity, NamespacedKey key) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.remove(key);
    }

    public static @NotNull OpMap<String, String> getAllValues(@NotNull ItemStack item) {
        OpMap<String, String> map = new OpMap<>();
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

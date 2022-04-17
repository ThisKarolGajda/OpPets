package me.opkarol.oppets.graphic;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.InventoryUtils;
import me.opkarol.oppets.utils.PDCUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphicItemData {
    private String name;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private int amount;
    private Material material;
    private boolean glow;
    private HashMap<String, String> pdc;
    private ItemStack item;
    private int slot;

    public GraphicItemData(@NotNull ItemStack item, int slot) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        this.name = meta.getDisplayName();
        this.lore = meta.getLore();
        this.enchantments = meta.getEnchants();
        this.amount = item.getAmount();
        this.material = item.getType();
        this.pdc = PDCUtils.getAllValues(item);
        this.item = item;
        this.slot = slot;
    }

    public GraphicItemData setName(String name) {
        this.name = name;
        return this;
    }

    public GraphicItemData setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public GraphicItemData setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public GraphicItemData setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public GraphicItemData setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public GraphicItemData setGlow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public String getName() {
        if (name == null) {
            return "default";
        }
        return FormatUtils.formatMessage(name);
    }

    public List<String> getLore() {
        if (lore == null) {
            return new ArrayList<>();
        }
        return FormatUtils.formatList(lore);
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        if (material == null) {
            return Material.STONE;
        }
        return material;
    }

    public HashMap<String, String> getPdc() {
        return pdc;
    }

    public void setPdc(HashMap<String, String> pdc) {
        this.pdc = pdc;
    }

    public boolean isGlow() {
        return glow;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setItem() {
        setItem(InventoryUtils.itemCreator(getMaterial(), getName(), getLore(), isGlow(), getPdc(), null));
    }

    public ItemStack getItem() {
        return item == null ? InventoryUtils.itemCreator(getMaterial(), getName(), getLore(), isGlow(), getPdc(), null) : item;
    }
}

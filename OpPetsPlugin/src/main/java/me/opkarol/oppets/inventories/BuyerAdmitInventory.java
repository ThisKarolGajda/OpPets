package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.inventories.holders.BuyerAdmitInventoryHolder;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.opkarol.oppets.utils.ConfigUtils.*;
import static me.opkarol.oppets.utils.InventoryUtils.*;

public class BuyerAdmitInventory {
    private final Inventory inventory;
    private final ItemStack itemStack;
    private final ItemMeta meta;
    private final String price;
    private final String type;
    private final String finalType;

    public BuyerAdmitInventory(ItemStack item) {
        this.itemStack = item;
        meta = itemStack.getItemMeta();
        price = String.valueOf(getValueFromKey(priceKey, meta, ItemTagType.INTEGER));
        type = (String) getValueFromKey(typeKey, meta, ItemTagType.STRING);
        finalType = type != null ? type : "ERROR";
        inventory = Bukkit.createInventory(new BuyerAdmitInventoryHolder(), 27, getMessage("BuyerAdmitInventory.title"));
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void setupInventory() {
        String path = "BuyerAdmitInventory.items.";
        inventory.setItem(10, itemCreator(Material.valueOf(getString(path + "decline.material")), getMessage(path + "decline.name"), setPlaceHolders(getListString(path + "decline.lore")), null, true, getBoolean(path + "decline.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(13, itemCreator(Material.valueOf(getString(path + "informationBook.material")), getMessage(path + "informationBook.name"), setPlaceHolders(getListString(path + "informationBook.lore")), null, true, getBoolean(path + "informationBook.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(16, itemCreatorShop(type, Integer.parseInt(price), Material.valueOf(getString(path + "confirm.material")), getMessage(path + "confirm.name"), setPlaceHolders(getListString(path + "confirm.lore")), null, true, getBoolean(path + "confirm.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }

    @Contract(pure = true)
    private @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        List<String> list = new ArrayList<>();
        if (meta == null) return lore;

        for (String sI : lore) {
            list.add(FormatUtils.formatMessage(sI.replace("%price%", price).replace("%type%", finalType)));
        }
        return list;
    }
}

package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.inventories.holders.ShopInventoryHolder;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.opkarol.oppets.utils.ConfigUtils.*;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreatorShop;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

public class ShopInventory {

    final String guiTitle = getString("ShopInventory.title");
    final Inventory inventory;

    public ShopInventory() {
        inventory = Bukkit.createInventory(new ShopInventoryHolder(), 54, FormatUtils.formatMessage(guiTitle));
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void setupInventory() {
        String path = "ShopInventory.items.";
        ConfigurationSection sec = OpPets.getInstance().getConfig().getConfigurationSection("ShopInventory.items");
        if (sec == null) return;

        int i = 0;
        for (String key : sec.getKeys(false)) {
            if (i > 53) break;
            key = key + ".";
            inventory.setItem(i, itemCreatorShop(getString(path + key + "options.type"), getInt(path + key + "options.price"), Material.valueOf(getString(path + key + "material")), getMessage(path + key + "name"), setPlaceHolders(getListString(path + key + "lore"), path + key), null, true, getBoolean(path + key + "glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
            i++;
        }

        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }

    @Contract(pure = true)
    private @NotNull List<String> setPlaceHolders(@NotNull List<String> lore, String path) {
        List<String> list = new ArrayList<>();
        String type = getString(path + ".options.type");
        String price = String.valueOf(getInt(path + ".options.price"));

        for (String sI : lore) {
            list.add(FormatUtils.formatMessage(sI.replace("%type%", type).replace("%price%", price)));
        }
        return list;
    }
}

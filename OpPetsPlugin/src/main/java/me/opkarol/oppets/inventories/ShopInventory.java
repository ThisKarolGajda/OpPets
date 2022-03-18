package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.inventories.holders.ShopInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.ConfigUtils.getInt;
import static me.opkarol.oppets.utils.ConfigUtils.getString;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreatorShop;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

public class ShopInventory implements IInventory {

    final String guiTitle = getString("ShopInventory.title");
    final Inventory inventory;
    String path;

    public ShopInventory() {
        inventory = Bukkit.createInventory(new ShopInventoryHolder(), 54, FormatUtils.formatMessage(guiTitle));
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void setupInventory() {
        String path = "ShopInventory.items.";
        ConfigurationSection sec = Database.getInstance().getConfig().getConfigurationSection("ShopInventory.items");
        if (sec == null) return;

        int i = 0;
        for (String key : sec.getKeys(false)) {
            if (i > 53) break;
            key = key + ".";
            this.path = path + key;
            inventory.setItem(i, itemCreatorShop(getString(path + key + "options.type"), getInt(path + key + "options.price"), path + key, this));
            i++;
        }

        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }

    @Override
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        String type = getString(path + ".options.type");
        String price = String.valueOf(getInt(path + ".options.price"));
        return lore.stream().map(s -> FormatUtils.formatMessage(s
                .replace("%type%", type)
                .replace("%price%", price)))
                .collect(Collectors.toList());
    }
}

package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.interfaces.IInventory;
import me.opkarol.oppets.inventories.holders.BuyerAdmitInventoryHolder;
import dir.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static dir.utils.ConfigUtils.getMessage;
import static dir.utils.InventoryUtils.*;

public class BuyerAdmitInventory implements IInventory {
    private Inventory inventory;
    private String price;
    private String type;

    public BuyerAdmitInventory(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        price = String.valueOf(getValueFromKey(priceKey, meta, ItemTagType.INTEGER));
        type = (String) getValueFromKey(typeKey, meta, ItemTagType.STRING);
        inventory = Bukkit.createInventory(new BuyerAdmitInventoryHolder(), 27, getMessage("BuyerAdmitInventory.title"));
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void setupInventory() {
        String path = "BuyerAdmitInventory.items.";
        inventory.setItem(10, itemCreator(path + "decline.", this));
        inventory.setItem(13, itemCreator(path + "informationBook.", this));
        inventory.setItem(16, itemCreatorShop(type, Integer.parseInt(price), path + "confirm.", this));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);
    }

    @Override
    @Contract(pure = true)
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore.stream().map(s -> FormatUtils.formatMessage(s
                .replace("%type%", type)
                .replace("%price%", price)))
                .collect(Collectors.toList());
    }
}

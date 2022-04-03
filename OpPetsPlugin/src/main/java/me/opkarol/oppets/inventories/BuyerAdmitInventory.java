package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.inventories.holders.BuyerAdmitInventoryHolder;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.PDCUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.cache.NamespacedKeysCache.priceKey;
import static me.opkarol.oppets.cache.NamespacedKeysCache.typeKey;
import static me.opkarol.oppets.utils.InventoryUtils.*;

/**
 * The type Buyer admit inventory.
 */
public class BuyerAdmitInventory implements IInventory {
    /**
     * The Inventory.
     */
    private final Inventory inventory;
    /**
     * The Price.
     */
    private final String price;
    /**
     * The Type.
     */
    private final String type;

    /**
     * Instantiates a new Buyer admit inventory.
     *
     * @param item the item
     */
    public BuyerAdmitInventory(@NotNull ItemStack item) {
        price = PDCUtils.getNBT(item, priceKey);
        type = PDCUtils.getNBT(item, typeKey);
        inventory = Bukkit.createInventory(new BuyerAdmitInventoryHolder(), 27, InventoriesCache.buyerAdmitInventoryTitle);
        setupInventory();
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets inventory.
     */
    private void setupInventory() {
        String path = "BuyerAdmitInventory.items.";
        inventory.setItem(10, itemCreator(path + "decline.", this));
        inventory.setItem(13, itemCreator(path + "informationBook.", this));
        inventory.setItem(16, itemCreatorShop(type, Integer.parseInt(price), path + "confirm.", this));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);
    }

    /**
     * Sets place holders.
     *
     * @param lore the lore
     * @return the place holders
     */
    @Override
    @Contract(pure = true)
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore.stream().map(s -> FormatUtils.formatMessage(s
                .replace("%type%", type)
                .replace("%price%", price)))
                .collect(Collectors.toList());
    }
}

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
import me.opkarol.oppets.inventories.holders.PrestigeInventoryHolder;
import me.opkarol.oppets.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

/**
 * The type Prestige inventory.
 */
public class PrestigeInventory implements IInventory {
    /**
     * The Inventory.
     */
    private final Inventory inventory;

    /**
     * Instantiates a new Prestige inventory.
     */
    public PrestigeInventory() {
        inventory = Bukkit.createInventory(new PrestigeInventoryHolder(), 27, InventoriesCache.prestigeInventoryTitle);
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
        String path = "PrestigeInventory.items.";
        inventory.setItem(11, itemCreator(path + "informationBook.", this));
        inventory.setItem(15, itemCreator(path + "prestige.", this));
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE);
    }

    /**
     * Sets place holders.
     *
     * @param lore the lore
     * @return the place holders
     */
    @Override
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore;
    }
}

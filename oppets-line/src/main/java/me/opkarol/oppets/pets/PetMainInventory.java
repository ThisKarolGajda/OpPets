package me.opkarol.oppets.pets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

/**
 * The type Pet main inventory.
 */
public class PetMainInventory implements IInventory {
    /**
     * The Inventory.
     */
    private final Inventory inventory = Bukkit.createInventory(new PetMainInventoryHolder(), 27, InventoriesCache.mainInventoryTitle);

    /**
     * Instantiates a new Pet main inventory.
     */
    public PetMainInventory() {
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
    public void setupInventory() {
        String path = "PetMainInventory.items.";
        inventory.setItem(10, itemCreator(path + "level.", this));
        inventory.setItem(12, itemCreator(path + "name.", this));
        inventory.setItem(14, itemCreator(path + "settings.", this));
        inventory.setItem(16, itemCreator(path + "respawn.", this));
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

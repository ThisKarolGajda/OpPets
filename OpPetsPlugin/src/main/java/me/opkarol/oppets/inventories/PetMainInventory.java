package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.inventories.holders.PetMainInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import static me.opkarol.oppets.utils.ConfigUtils.getMessage;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

public class PetMainInventory implements IInventory {
    public String guiTitle = getMessage("PetMainInventory.title");

    private final Inventory inventory = Bukkit.createInventory(new PetMainInventoryHolder(), 27, guiTitle);

    public PetMainInventory() {
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setupInventory() {
        String path = "PetMainInventory.items.";
        inventory.setItem(10, itemCreator(path + "level.", this));
        inventory.setItem(12, itemCreator(path + "name.", this));
        inventory.setItem(14, itemCreator(path + "settings.", this));
        inventory.setItem(16, itemCreator(path + "respawn.", this));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);
    }
}

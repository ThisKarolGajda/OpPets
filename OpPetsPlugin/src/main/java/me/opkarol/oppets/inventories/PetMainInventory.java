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
import org.bukkit.inventory.ItemFlag;

import static me.opkarol.oppets.utils.ConfigUtils.*;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

public class PetMainInventory {
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
        inventory.setItem(10, itemCreator(Material.valueOf(getString(path + "level.material")), getMessage(path + "level.name"), getListString(path + "level.lore"), null, true, getBoolean(path + "level.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(12, itemCreator(Material.valueOf(getString(path + "name.material")), getMessage(path + "name.name"), getListString(path + "name.lore"), null, true, getBoolean(path + "name.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(14, itemCreator(Material.valueOf(getString(path + "settings.material")), getMessage(path + "settings.name"), getListString(path + "settings.lore"), null, true, getBoolean(path + "settings.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(16, itemCreator(Material.valueOf(getString(path + "respawn.material")), getMessage(path + "respawn.name"), getListString(path + "respawn.lore"), null, true, getBoolean(path + "respawn.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);
    }
}

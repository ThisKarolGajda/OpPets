package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.cache.GraphicCache;
import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.graphic.GraphicInterface;
import me.opkarol.oppets.graphic.GraphicItem;
import me.opkarol.oppets.graphic.GraphicItemData;
import me.opkarol.oppets.interfaces.IGraphicInventoryData;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.inventories.anvil.RenameAnvilInventory;
import me.opkarol.oppets.inventories.holders.PetMainInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

/**
 * The type Pet main inventory.
 */
public class PetMainInventory implements IInventory {
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());
    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    @Override
    public Inventory getInventory() {
        GraphicCache cache = GraphicCache.getInstance();
        Inventory cached = cache.getInventory(this);
        if (cached == null) {
            loadButtons();
            cache.addInventory(this, GraphicInterface.getInventory(this, new IGraphicInventoryData() {
                @Override
                public InventoryHolder getHolder() {
                    return new PetMainInventoryHolder();
                }

                @Override
                public int getSize() {
                    return 27;
                }

                @Override
                public String getTitle() {
                    return InventoriesCache.mainInventoryTitle;
                }
            }, inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE)));
            return cache.getInventory(this);
        }
        return cached;
    }

    @Override
    public void loadButtons() {
        String path = "PetMainInventory.items.";
        GraphicInterface graphicInterface = GraphicInterface.getInstance();
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "level.", this), 10), player -> {
            Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
            player.openInventory(new LevelInventory(pet).getInventory());
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "name.", this), 12), player -> {
            Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
            new RenameAnvilInventory(pet, player);
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "settings.", this), 14), player -> {
            Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
            player.openInventory(new SettingsInventory(pet).getInventory());
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "respawn.", this), 16), player -> {
            Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
            database.getUtils().respawnPet(pet, player);
        }));
    }

    @Override
    public String getHolderName() {
        return "PetMainInventory";
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

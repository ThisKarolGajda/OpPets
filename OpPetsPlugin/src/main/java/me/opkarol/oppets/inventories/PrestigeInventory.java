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
import me.opkarol.oppets.graphic.*;
import me.opkarol.oppets.interfaces.IGraphicInventoryData;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.inventories.anvil.PrestigeConfirmAnvilInventory;
import me.opkarol.oppets.inventories.holders.PrestigeInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.InventoryUtils;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

public class PrestigeInventory implements IInventory {
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());

    @Override
    public Inventory getInventory() {
        GraphicCache cache = GraphicCache.getInstance();
        Inventory cached = cache.getInventory(this);
        if (cached == null) {
            loadButtons();
            cache.addInventory(this, GraphicInterface.getInventory(this, new IGraphicInventoryData() {
                @Override
                public InventoryHolder getHolder() {
                    return new PrestigeInventoryHolder();
                }

                @Override
                public int getSize() {
                    return 27;
                }

                @Override
                public String getTitle() {
                    return InventoriesCache.prestigeInventoryTitle;
                }
            }, inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE)));
            return cache.getInventory(this);
        }
        return cached;
    }

    @Override
    public void loadButtons() {
        String path = "PrestigeInventory.items.";
        GraphicInterface graphicInterface = GraphicInterface.getInstance();
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "informationBook.", this), 11)));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "prestige.", this), 15), player -> {
            Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
            if (!OpUtils.canPrestige(pet)) {
                returnMessage(player, database.getOpPets().getMessages().getMessagesAccess().stringMessage("cantPrestige").replace("%more_levels%", String.valueOf(OpUtils.getLevelsForPrestige(pet))));
            } else {
                player.closeInventory();
                new PrestigeConfirmAnvilInventory(pet, player);
            }
        }));
    }

    @Override
    public String getHolderName() {
        return "PrestigeInventory";
    }

    @Override
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore;
    }
}

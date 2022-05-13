package me.opkarol.oppets.graphic;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GraphicInventoryCreator {
    private final GraphicItem[] list;
    private final Inventory inventory;

    public GraphicInventoryCreator(GraphicItem[] list, @NotNull IGraphicInventoryData inventoryData, Consumer<Inventory> action) {
        this.list = list;
        inventory = Bukkit.createInventory(inventoryData.getHolder(), inventoryData.getSize(), inventoryData.getTitle());
        setupItems();
        if (action != null) {
            action.accept(inventory);
        }
    }

    private void setupItems() {
        if (getList() != null) {
            for (GraphicItem graphicItem : getList()) {
                if (graphicItem == null) {
                    continue;
                }
                inventory.setItem(graphicItem.getSlot(), graphicItem.getItem());
            }
        }
    }

    private GraphicItem[] getList() {
        return list;
    }

    public Inventory getInventory() {
        return inventory;
    }
}

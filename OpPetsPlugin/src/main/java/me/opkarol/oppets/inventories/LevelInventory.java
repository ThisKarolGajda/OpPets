package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.graphic.GraphicInterface;
import me.opkarol.oppets.graphic.GraphicItem;
import me.opkarol.oppets.graphic.GraphicItemData;
import me.opkarol.oppets.interfaces.IGraphicInventoryData;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.inventories.holders.PrestigeInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.InventoryUtils;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

public record LevelInventory(Pet pet) implements IInventory {
    public LevelInventory(@NotNull Pet pet) {
        this.pet = pet;
    }

    @Override
    public Inventory getInventory() {
        loadButtons();
        return GraphicInterface.getInventory(this, new IGraphicInventoryData() {
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
                return InventoriesCache.levelInventoryTitle.replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName()));
            }
        }, inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE));
    }

    @Override
    public void loadButtons() {
        String path = "LevelInventory.items.";
        GraphicInterface graphicInterface = GraphicInterface.getInstance();
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "informationBook.", this), 10)));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "level.", this), 13)));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "abilities.", this), 16)));
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getHolderName() {
        return "LevelInventory";
    }

    @Override
    public @NotNull
    List<String> setPlaceHolders(@NotNull List<String> lore) {
        String maxLevel = String.valueOf(OpUtils.getMaxLevel(pet));
        String percentageOfNext = OpUtils.getPercentageOfNextLevel(pet);
        String petExperience = String.valueOf(OpUtils.getPetLevelExperience(pet));
        return lore.stream().map(s -> FormatUtils.formatMessage(s
                        .replace("%max_pet_level%", maxLevel)
                        .replace("%percentage_of_next_experience%", percentageOfNext)
                        .replace("%pet_experience_next%", petExperience)
                        .replace("%pet_level%", String.valueOf(OpUtils.getLevel(pet)))))
                .collect(Collectors.toList());
    }
}

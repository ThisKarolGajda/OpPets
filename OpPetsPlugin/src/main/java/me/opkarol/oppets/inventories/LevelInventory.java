package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.Pet;
import me.opkarol.oppets.inventories.holders.LevelInventoryHolder;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.opkarol.oppets.utils.ConfigUtils.getMessage;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

public class LevelInventory implements IInventory {
    final Pet pet;
    private final Inventory inventory;

    public LevelInventory(@NotNull Pet pet) {
        this.pet = pet;
        inventory = Bukkit.createInventory(new LevelInventoryHolder(), 27, getMessage("LevelInventory.title").replace("%pet_name%", FormatUtils.formatMessage(Objects.requireNonNull(pet.getPetName()))));
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void setupInventory() {
        String path = "LevelInventory.items.";
        inventory.setItem(10, itemCreator(path + "informationBook.", this));
        inventory.setItem(13, itemCreator(path + "level.", this));
        inventory.setItem(16, itemCreator(path + "abilities.", this));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }

    @Override
    @Contract(pure = true)
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        List<String> list = new ArrayList<>();

        String maxLevel = String.valueOf(OpUtils.getMaxLevel(pet));
        String percentageOfNext = OpUtils.getPercentageOfNextLevel(pet);
        String petExperience = String.valueOf(OpUtils.getPetLevelExperience(pet));

        for (String sI : lore) {
            list.add(FormatUtils.formatMessage(sI.replace("%max_pet_level%", maxLevel).replace("%percentage_of_next_experience%", percentageOfNext).replace("%pet_experience_next%", petExperience).replace("%pet_level%", String.valueOf(OpUtils.getLevel(pet)))));
        }
        return list;
    }


}

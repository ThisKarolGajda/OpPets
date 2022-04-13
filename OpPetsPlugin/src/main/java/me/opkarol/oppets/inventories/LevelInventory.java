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
import me.opkarol.oppets.inventories.holders.LevelInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.InventoryUtils;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

/**
 * The type Level inventory.
 */
public class LevelInventory implements IInventory {
    /**
     * The Pet.
     */
    final Pet pet;
    /**
     * The Inventory.
     */
    private final Inventory inventory;

    /**
     * Instantiates a new Level inventory.
     *
     * @param pet the pet
     */
    public LevelInventory(@NotNull Pet pet) {
        this.pet = pet;
        inventory = Bukkit.createInventory(new LevelInventoryHolder(), 27, InventoriesCache.levelInventoryTitle.replace("%pet_name%", FormatUtils.formatMessage(Objects.requireNonNull(pet.getPetName()))));
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
        String path = "LevelInventory.items.";
        inventory.setItem(10, itemCreator(path + "informationBook.", this));
        inventory.setItem(13, itemCreator(path + "level.", this));
        inventory.setItem(16, itemCreator(path + "abilities.", this));
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE);

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

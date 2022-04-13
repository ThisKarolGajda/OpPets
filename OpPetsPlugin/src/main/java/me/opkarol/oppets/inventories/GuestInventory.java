package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.inventories.holders.GuestInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.InventoryUtils;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

/**
 * The type Guest inventory.
 */
public class GuestInventory implements IInventory {
    /**
     * The Pet.
     */
    private final Pet pet;
    /**
     * The Inventory.
     */
    private final Inventory inventory;
    /**
     * The Database.
     */
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());

    /**
     * Instantiates a new Guest inventory.
     *
     * @param pet the pet
     */
    public GuestInventory(@NotNull Pet pet) {
        this.pet = pet;
        inventory = Bukkit.createInventory(new GuestInventoryHolder(), 27, InventoriesCache.guestInventoryTitle.replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName())));
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
        String path = "GuestInventory.items.";
        inventory.setItem(11, itemCreator(path + "informationBook.", this));
        inventory.setItem(15, itemCreator(path + "level.", this));
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE);
    }

    /**
     * Sets place holders.
     *
     * @param lore the lore
     * @return the place holders
     */
    @Override
    @NotNull
    public List<String> setPlaceHolders(@NotNull List<String> lore) {
        String owner = FormatUtils.formatMessage(OpUtils.getNameFromUUID(pet.getOwnerUUID()));
        String petName = FormatUtils.formatMessage(pet.getPetName());
        String petType = pet.getPetType().name();
        String skillName = pet.getSkillName();
        String maxLevel = String.valueOf(OpUtils.getMaxLevel(pet));
        String level = String.valueOf(OpUtils.getLevel(pet));
        String percentageOfNext = OpUtils.getPercentageOfNextLevel(pet);
        String petExperience = String.valueOf(OpUtils.getPetLevelExperience(pet));
        String prestige = database.getOpPets().getPrestigeManager().getFilledPrestige(pet.getPrestige());
        return lore.stream().map(s -> FormatUtils.formatMessage(s
                .replace("%current_prestige%", prestige)
                .replace("%max_pet_level%", maxLevel)
                .replace("%percentage_of_next_experience%", percentageOfNext)
                .replace("%pet_experience_next%", petExperience)
                .replace("%pet_owner%", owner)
                .replace("%pet_name%", petName)
                .replace("%pet_experience%", petExperience)
                .replace("%pet_level%", level)
                .replace("%pet_type%", petType)
                .replace("%pet_skill%", skillName)))
                .collect(Collectors.toList());
    }

}

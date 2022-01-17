package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.Pet;
import me.opkarol.oppets.inventories.holders.GuestInventoryHolder;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.inventories.LevelInventory.getAdderPoints;
import static me.opkarol.oppets.utils.ConfigUtils.*;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

public class GuestInventory {
    final String guiTitle = getString("GuestInventory.title");
    final Pet pet;
    final Inventory inventory;

    public GuestInventory(@NotNull Pet pet) {
        this.pet = pet;
        inventory = Bukkit.createInventory(new GuestInventoryHolder(), 27, FormatUtils.formatMessage(guiTitle.replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName()))));
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void setupInventory() {
        String path = "GuestInventory.items.";
        inventory.setItem(11, itemCreator(Material.valueOf(getString(path + "informationBook.material")), getMessage(path + "informationBook.name"), setPlaceHolders(getListString(path + "informationBook.lore")), null, true, getBoolean(path + "informationBook.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(15, itemCreator(Material.valueOf(getString(path + "level.material")), getMessage(path + "level.name"), setPlaceHolders(getListString(path + "level.lore")), null, true, getBoolean(path + "level.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }

    @Contract(pure = true)
    private @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        List<String> list = new ArrayList<>();
        String owner = FormatUtils.formatMessage(getPlayerName(pet.getOwnerUUID()));
        String petName = FormatUtils.formatMessage(pet.getPetName());
        String petType = pet.getPetType().name();
        String skillName = pet.getSkillName();

        int petLevel = pet.getLevel();
        double adderPoints = getAdderPoints(skillName, true, pet);

        String maxLevel = String.valueOf(petLevel + 1);
        String percentageOfNext = (adderPoints / getAdderPoints(skillName, false, pet)) + "%";
        String petExperience = String.valueOf(Math.round(adderPoints - pet.getPetExperience()));

        for (String sI : lore) {
            list.add(FormatUtils.formatMessage(sI.replace("%max_pet_level%", maxLevel).replace("%percentage_of_next_experience%", percentageOfNext).replace("%pet_experience_next%", petExperience)).replace("%pet_owner%", owner).replace("%pet_name%", petName).replace("%pet_experience%", petExperience).replace("%pet_level%", String.valueOf(petLevel)).replace("%pet_type%", petType).replace("%pet_skill%", skillName));
        }
        return list;
    }

    private String getPlayerName(UUID uuid) {
        String name;
        try {
            name = Bukkit.getPlayer(uuid).getName();
        } catch (Exception ignore) {
            name = Bukkit.getOfflinePlayer(uuid).getName();
        }

        return name;
    }

}

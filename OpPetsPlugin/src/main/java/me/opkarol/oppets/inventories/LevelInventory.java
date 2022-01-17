package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.inventories.holders.LevelInventoryHolder;
import me.opkarol.oppets.skills.Adder;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.opkarol.oppets.utils.ConfigUtils.*;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

public class LevelInventory {
    private final Inventory inventory;
    final Pet pet;

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
        inventory.setItem(10, itemCreator(Material.valueOf(getString(path + "informationBook.material")), getMessage(path + "informationBook.name"), setPlaceHolders(getListString(path + "informationBook.lore")), null, true, getBoolean(path + "informationBook.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(13, itemCreator(Material.valueOf(getString(path + "level.material")), getMessage(path + "level.name"), setPlaceHolders(getListString(path + "level.lore")), null, true, getBoolean(path + "level.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(16, itemCreator(Material.valueOf(getString(path + "abilities.material")), getMessage(path + "abilities.name"), setPlaceHolders(getListString(path + "abilities.lore")), null, true, getBoolean(path + "abilities.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }

    @Contract(pure = true)
    private @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        List<String> list = new ArrayList<>();

        String skillName = pet.getSkillName();
        int petLevel = pet.getLevel();
        double adderPoints = getAdderPoints(skillName, true, pet);
        String maxLevel = String.valueOf(petLevel + 1);
        String percentageOfNext = (adderPoints / getAdderPoints(skillName, false, pet)) + "%";
        String petExperience = String.valueOf(Math.round(adderPoints - pet.getPetExperience()));

        for (String sI : lore) {
            list.add(FormatUtils.formatMessage(sI.replace("%max_pet_level%", maxLevel).replace("%percentage_of_next_experience%", percentageOfNext).replace("%pet_experience_next%", petExperience).replace("%pet_level%", String.valueOf(petLevel))));
        }
        return list;
    }

    public static double getAdderPoints(String skillName, boolean lowestOutput, Pet pet) {
        double number = 0D;
        List<Adder> list = OpPets.getSkillDatabase().getSkillFromMap(skillName).getE();
        for (Adder adder : list) {
            double calculated = adder.calculateMaxCurrent(pet.getLevel());
            if (number == 0) {
                number = calculated;
            }

            if (lowestOutput) {
                if (calculated < number) {
                    number = calculated;
                }
            } else {
                if (calculated > number) {
                    number = calculated;
                }
            }

        }
        return number;
    }
}

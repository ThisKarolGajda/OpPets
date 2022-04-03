package me.opkarol.oppets.leaderboards;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.utils.ConfigUtils;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

/**
 * The type Leaderboard inventory.
 */
public class LeaderboardInventory implements IInventory {
    /**
     * The Inventory.
     */
    private final Inventory inventory;
    /**
     * The Current path.
     */
    private String currentPath;

    /**
     * Instantiates a new Leaderboard inventory.
     */
    public LeaderboardInventory() {
        inventory = Bukkit.createInventory(new LeaderboardInventoryHolder(), 27, InventoriesCache.leaderboardInventoryTitle);
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
        String path = "LeaderboardInventory.items.";
        ConfigurationSection section = Database.getInstance().getConfig().getConfigurationSection("LeaderboardInventory.items");
        if (section == null) {
            return;
        }
        for (String key : section.getKeys(false)) {
            currentPath = path + key + ".";
            inventory.setItem(ConfigUtils.getInt(currentPath + "slot"), itemCreator(currentPath, this));
        }
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);
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
        LeaderboardCounter counter = Database.getOpPets().getLeaderboard();
        Leaderboard leaderboard = counter.getLeaderboardsFromName(ConfigUtils.getString(currentPath + "leaderboardName")).get(0);
        if (leaderboard == null) {
            return lore;
        }
        List<Pet> pets = new ArrayList<>(leaderboard.getPlaces());
        List<String> list = new ArrayList<>();
        lore.forEach(lambdaString -> {
            String toAdd = lambdaString;
            String[] strings = lambdaString.split(" ");
            for (String string : strings) {
                if (!string.startsWith("%") || !string.endsWith("%")) {
                    continue;
                }
                String replaced = string.replace("%", "");
                int number = Integer.parseInt(replaced.replaceAll("[A-z]", ""));
                if (pets.size() == 0) {
                    return;
                }
                if (pets.size() <= number - 1) {
                    continue;
                }
                Pet pet = pets.get(number - 1);
                if (pet == null) {
                    continue;
                }
                if (replaced.substring(1).equals("_player_name")) {
                    toAdd = lambdaString.replace("%" + number + "_player_name%", OpUtils.getNameFromUUID(pet.getOwnerUUID()));
                } else {
                    switch (leaderboard.getType()) {
                        case TOP_LEVEL:
                            toAdd = lambdaString.replace("%" + number + "_player_object%", String.valueOf(pet.getLevel()));
                            break;
                        case TOP_PRESTIGE:
                            toAdd = lambdaString.replace("%" + number + "_player_object%", new PrestigeManager().getFilledPrestige(pet.getPrestige()));
                            break;
                        case TOP_EXPERIENCE:
                            toAdd = lambdaString.replace("%" + number + "_player_object%", String.valueOf(pet.getPetExperience()));
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + leaderboard.getType());
                    }
                }
            }
            list.add(FormatUtils.formatMessage(toAdd));
        });
        return list;
    }
}

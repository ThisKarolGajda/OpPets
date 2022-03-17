package dir.leaderboards;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.interfaces.IInventory;
import dir.pets.Pet;
import dir.prestiges.PrestigeManager;
import dir.utils.ConfigUtils;
import dir.utils.FormatUtils;
import dir.utils.OpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dir.utils.ConfigUtils.getMessage;
import static dir.utils.InventoryUtils.itemCreator;
import static dir.utils.InventoryUtils.setupEmptyGlassPanes;

public class LeaderboardInventory implements IInventory {
    private final Inventory inventory;
    private String currentPath;

    public LeaderboardInventory() {
        inventory = Bukkit.createInventory(new LeaderboardInventoryHolder(), 27, getMessage("LeaderboardInventory.title"));
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

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
            final String[] toAdd = new String[1];
            toAdd[0] = lambdaString;
            String[] strings = lambdaString.split(" ");
            for (String string : strings) {
                if (!string.startsWith("%") || !string.endsWith("%")) {
                    continue;
                }
                String replaced = string.replace("%", "");
                int number = Integer.parseInt(replaced.replaceAll("[A-z]", ""));
                Pet pet = pets.get(number - 1);
                if (replaced.substring(1).equals("_player_name")) {
                    toAdd[0] = toAdd[0].replace("%" + number + "_player_name%", OpUtils.getNameFromUUID(pet.getOwnerUUID()));
                } else {
                    switch (leaderboard.getType()) {
                        case TOP_LEVEL:
                            toAdd[0] = toAdd[0].replace("%" + number + "_player_object%", String.valueOf(pet.getLevel()));
                            break;
                        case TOP_PRESTIGE:
                            toAdd[0] = toAdd[0].replace("%" + number + "_player_object%", new PrestigeManager().getFilledPrestige(pet.getPrestige()));
                            break;
                        case TOP_EXPERIENCE:
                            toAdd[0] = toAdd[0].replace("%" + number + "_player_object%", String.valueOf(pet.getPetExperience()));
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + leaderboard.getType());
                    }
                }
            }
            list.add(FormatUtils.formatMessage(toAdd[0]));
        });
        return list;
    }
}

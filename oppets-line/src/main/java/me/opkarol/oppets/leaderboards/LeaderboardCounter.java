package me.opkarol.oppets.leaderboards;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoryCache;
import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.exceptions.ExceptionLogger;
import me.opkarol.oppets.inventory.OpInventories;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardCounter {
    private final HashSet<Leaderboard> activeLeaderboards = new HashSet<>();
    private final Database database;
    private InventoryCache cache;

    public LeaderboardCounter(Database database) {
        this.database = database;
        setupLeaderboards();
        updateTick();
        cache = new InventoryCache();
    }

    private void setupLeaderboards() {
        addLeaderboard("top_level", Leaderboard.LEADERBOARD_TYPE.TOP_LEVEL);
        addLeaderboard("top_prestige", Leaderboard.LEADERBOARD_TYPE.TOP_PRESTIGE);
        addLeaderboard("top_experience", Leaderboard.LEADERBOARD_TYPE.TOP_EXPERIENCE);
    }

    private void updateTick() {
        new BukkitRunnable() {
            @Override
            public void run() {
                updateLeaderboards();
            }
        }.runTaskTimerAsynchronously(database.getPlugin(), 0, 1200);
    }

    private void updateLeaderboards() {
        new BukkitRunnable() {
            @Override
            public void run() {
                OpMap<UUID, Pet> pets = database.getDatabase().getActivePetMap();
                if (pets == null) {
                    return;
                }
                activeLeaderboards.forEach(leaderboard -> {
                    Collection<Pet> value = pets.getValues();
                    if (value != null) {
                        List<Pet> places = null;
                        switch (leaderboard.getType()) {
                            case TOP_LEVEL:
                                places = value.stream()
                                        .sorted(Comparator.comparing(Pet::getLevel))
                                        .collect(Collectors.toList());
                                break;
                            case TOP_PRESTIGE:
                                places = value.stream()
                                        .sorted(Comparator.comparing(Pet::getPrestige))
                                        .collect(Collectors.toList());
                                break;
                            case TOP_EXPERIENCE:
                                places = value.stream()
                                        .sorted(Comparator.comparing(Pet::getPetExperience))
                                        .collect(Collectors.toList());
                                break;
                            default:
                                ExceptionLogger.getInstance().throwException("Unexpected value: " + leaderboard.getType());
                        }
                        Collections.reverse(places);
                        leaderboard.setPlaces(places);
                    }
                });
                cache.setInventory(new OpInventories.LeaderboardInventory().buildInventory());
            }
        }.runTaskAsynchronously(database.getPlugin());
    }

    public void addLeaderboard(String name, Leaderboard.LEADERBOARD_TYPE type) {
        activeLeaderboards.add(new Leaderboard(name, new ArrayList<>(), type));
    }

    public List<Leaderboard> getLeaderboardsFromName(String name) {
        return activeLeaderboards.stream().filter(leaderboard -> leaderboard.getName().equals(name)).collect(Collectors.toList());
    }

    public @NotNull InventoryCache getCache() {
        if (cache == null) {
            cache = new InventoryCache();
        }
        if (cache.getInventory() == null) {
            cache.setInventory(new OpInventories.LeaderboardInventory().buildInventory());
        }
        return cache;
    }

    public void setCache(InventoryCache cache) {
        this.cache = cache;
    }
}

package me.opkarol.oppets.leaderboards;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoryCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Leaderboard counter.
 */
public class LeaderboardCounter {
    /**
     * The Active leaderboards.
     */
    private final HashSet<Leaderboard> activeLeaderboards = new HashSet<>();
    /**
     * The Database.
     */
    private final Database database;
    /**
     * The Cache.
     */
    private InventoryCache cache;

    /**
     * Instantiates a new Leaderboard counter.
     *
     * @param database the database
     */
    public LeaderboardCounter(Database database) {
        this.database = database;
        setupLeaderboards();
        updateTick();
        cache = new InventoryCache();
    }

    /**
     * Sets leaderboards.
     */
    private void setupLeaderboards() {
        addLeaderboard("top_level", Leaderboard.LEADERBOARD_TYPE.TOP_LEVEL);
        addLeaderboard("top_prestige", Leaderboard.LEADERBOARD_TYPE.TOP_PRESTIGE);
        addLeaderboard("top_experience", Leaderboard.LEADERBOARD_TYPE.TOP_EXPERIENCE);
    }

    /**
     * Update tick.
     */
    private void updateTick() {
        updateLeaderboards();
        new BukkitRunnable() {
            @Override
            public void run() {
                updateLeaderboards();
            }
        }.runTaskTimerAsynchronously(database.getPlugin(), 1200, 1200);
    }

    /**
     * Update leaderboards.
     */
    private void updateLeaderboards() {
        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<UUID, Pet> pets = database.getDatabase().getActivePetMap();
                activeLeaderboards.forEach(leaderboard -> {
                    Collection<Pet> value = pets.values();
                    List<Pet> places;
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
                            throw new IllegalStateException("Unexpected value: " + leaderboard.getType());
                    }
                    Collections.reverse(places);
                    leaderboard.setPlaces(places);
                });
                cache.setInventory(new LeaderboardInventory().getInventory());
            }
        }.runTaskAsynchronously(database.getPlugin());
    }

    /**
     * Add leaderboard.
     *
     * @param name the name
     * @param type the type
     */
    public void addLeaderboard(String name, Leaderboard.LEADERBOARD_TYPE type) {
        activeLeaderboards.add(new Leaderboard(name, new ArrayList<>(), type));
    }

    /**
     * Gets leaderboards from name.
     *
     * @param name the name
     * @return the leaderboards from name
     */
    public List<Leaderboard> getLeaderboardsFromName(String name) {
        return activeLeaderboards.stream().filter(leaderboard -> leaderboard.getName().equals(name)).collect(Collectors.toList());
    }

    /**
     * Gets cache.
     *
     * @return the cache
     */
    public @NotNull InventoryCache getCache() {
        if (cache == null) {
            cache = new InventoryCache();
        }
        if (cache.getInventory() == null) {
            cache.setInventory(new LeaderboardInventory().getInventory());
        }
        return cache;
    }

    /**
     * Sets cache.
     *
     * @param cache the cache
     */
    public void setCache(InventoryCache cache) {
        this.cache = cache;
    }
}

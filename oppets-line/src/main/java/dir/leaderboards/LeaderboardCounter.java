package dir.leaderboards;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.cache.LeaderboardCache;
import dir.databases.Database;
import dir.pets.Pet;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardCounter {
    private final HashSet<Leaderboard> activeLeaderboards = new HashSet<>();
    private final LeaderboardCache cache;

    public LeaderboardCounter() {
        setupLeaderboards();
        updateTick();
        cache = new LeaderboardCache();
    }

    private void setupLeaderboards() {
        addLeaderboard("top_level", Leaderboard.LEADERBOARD_TYPE.TOP_LEVEL);
        addLeaderboard("top_prestige", Leaderboard.LEADERBOARD_TYPE.TOP_PRESTIGE);
        addLeaderboard("top_experience", Leaderboard.LEADERBOARD_TYPE.TOP_EXPERIENCE);
    }

    private void updateTick() {
        updateLeaderboards();
        new BukkitRunnable() {
            @Override
            public void run() {
                updateLeaderboards();
            }
        }.runTaskTimerAsynchronously(Database.getInstance(), 20 * 60, 20 * 60);
    }

    private void updateLeaderboards() {
        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<UUID, Pet> pets = Database.getOpPets().getDatabase().getActivePetMap();
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
        }.runTaskAsynchronously(Database.getInstance());
    }

    public void addLeaderboard(String name, Leaderboard.LEADERBOARD_TYPE type) {
        activeLeaderboards.add(new Leaderboard(name, new ArrayList<>(), type));
    }

    public List<Leaderboard> getLeaderboardsFromName(String name) {
        return activeLeaderboards.stream().filter(leaderboard -> leaderboard.getName().equals(name)).collect(Collectors.toList());
    }

    public LeaderboardCache getCache() {
        return cache;
    }
}

package me.opkarol.oppets.boosters;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.StringsCache;
import me.opkarol.oppets.databases.Database;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Booster provider.
 */
public class BoosterProvider {
    /**
     * The Database.
     */
    private final Database database;
    /**
     * The Boosters.
     */
    private final HashSet<Booster> boosters = new HashSet<>();

    /**
     * Instantiates a new Booster provider.
     *
     * @param database the database
     */
    public BoosterProvider(Database database) {
        this.database = database;
    }

    /**
     * Create new booster.
     *
     * @param name       the name
     * @param multiplier the multiplier
     * @param timeToEnd  the time to end
     * @param type       the type
     */
    public void createNewBooster(String name, double multiplier, long timeToEnd, Booster.BOOSTER_TYPE type) {
        Booster booster = new Booster(name, multiplier, timeToEnd, false, type, database);
        booster.run();
        boosters.add(booster);
    }

    /**
     * Create new booster.
     *
     * @param name       the name
     * @param multiplier the multiplier
     * @param timeToEnd  the time to end
     * @param type       the type
     * @param owner      the owner
     */
    public void createNewBooster(String name, double multiplier, long timeToEnd, Booster.BOOSTER_TYPE type, UUID owner) {
        Booster booster = new Booster(name, multiplier, timeToEnd, false, type, owner, database);
        booster.run();
        boosters.add(booster);
    }

    /**
     * Remove booster.
     *
     * @param name the name
     */
    public void removeBooster(String name) {
        boosters.removeIf(booster -> booster.getName().equals(name));
    }

    /**
     * Gets booster.
     *
     * @param name the name
     * @return the booster
     */
    public Booster getBooster(String name) {
        return boosters.stream().filter(booster1 -> booster1.getName().equals(name)).collect(Collectors.toList()).get(0);
    }

    /**
     * Gets boosters.
     *
     * @return the boosters
     */
    public HashSet<Booster> getBoosters() {
        return boosters;
    }

    /**
     * Gets active boosters.
     *
     * @return the active boosters
     */
    public List<Booster> getActiveBoosters() {
        return boosters.stream().filter(Booster::isRunning).collect(Collectors.toList());
    }

    /**
     * Gets multiplier.
     *
     * @param owner the owner
     * @return the multiplier
     */
    public double getMultiplier(@NotNull String owner) {
        double value = getActiveBoosters().stream().filter(booster -> booster.getOwner().equals(StringsCache.defaultServerValue) || booster.getOwner().equals(owner)).mapToDouble(Booster::getMultiplier).sum();
        return Math.max(value, 1.0);
    }

}
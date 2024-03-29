package me.opkarol.oppets.boosters;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.api.files.MessagesHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BoosterProvider {
    private final Database database;
    private final HashSet<Booster> boosters = new HashSet<>();
    private final String defaultOwner = MessagesHolder.getInstance().getMessages().getString("Formats.defaultServerValue");

    public BoosterProvider(Database database) {
        this.database = database;
    }

    public void createNewBooster(String name, double multiplier, long timeToEnd, Booster.BOOSTER_TYPE type) {
        Booster booster = new Booster(name, multiplier, timeToEnd, false, type, database);
        booster.run();
        boosters.add(booster);
    }

    public void createNewBooster(String name, double multiplier, long timeToEnd, Booster.BOOSTER_TYPE type, UUID owner) {
        Booster booster = new Booster(name, multiplier, timeToEnd, false, type, owner, database);
        booster.run();
        boosters.add(booster);
    }

    public void removeBooster(String name) {
        boosters.removeIf(booster -> booster.getName().equals(name));
    }

    public Booster getBooster(String name) {
        return boosters.stream().filter(booster1 -> booster1.getName().equals(name)).collect(Collectors.toList()).get(0);
    }

    public HashSet<Booster> getBoosters() {
        return boosters;
    }

    public List<Booster> getActiveBoosters() {
        return boosters.stream().filter(Booster::isRunning).collect(Collectors.toList());
    }

    public double getMultiplier(@NotNull String owner) {
        double value = getActiveBoosters().stream().filter(booster -> booster.getOwner().equals(defaultOwner) || booster.getOwner().equals(owner)).mapToDouble(Booster::getMultiplier).sum();
        return Math.max(value, 1.0);
    }

}
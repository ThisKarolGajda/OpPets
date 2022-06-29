package me.opkarol.oppets.interfaces.database;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.external.APIDatabase;
import me.opkarol.oppets.api.misc.CooldownModule;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class IDatabase {
    private final Plugin plugin = APIDatabase.getInstance().getPlugin();
    private final CooldownModule<Integer> cooldownModule = new CooldownModule<>();

    public abstract IDatabase setupDatabase();

    public abstract void closeConnection();

    public abstract void insertPet(@NotNull Pet pet);

    public void asyncInsertPet(@NotNull Pet pet) {
        int id = pet.petUUID.getDatabaseId();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!cooldownModule.hasActiveCooldown(id)) {
                    insertPet(pet);
                    cooldownModule.addCustomCooldown(id, 1);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public abstract void deletePet(@NotNull Pet pet);

    public abstract List<Pet> getPetsFromDatabase();

    public String getConnectionError() {
        return "Database connection isn't stable and/or connected, make sure you are connected to a working database, which is provided in a config.yml file.";
    }

    public Plugin getPlugin() {
        return plugin;
    }
}

package me.opkarol.oppets.cache;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * The type Last id cache.
 */
public class LastIDCache {
    /**
     * The Last id.
     */
    private int lastID;

    /**
     * Instantiates a new Last id cache.
     *
     * @param database the database
     */
    public LastIDCache(Database database) {
        new BukkitRunnable() {
            int largestID = 0;
            @Override
            public void run() {
                for (List<Pet> list : database.getDatabase().getPetsMap().values()) {
                    for (Pet pet : list) {
                        int id = pet.getPetUUID().getID();
                        if (id > largestID) {
                            largestID = id;
                        }
                    }
                }
                lastID = largestID;
            }
        }.runTaskAsynchronously(database.getPlugin());
    }

    /**
     * Gets last id.
     *
     * @return the last id
     */
    public int getLastID() {
        return lastID;
    }

    /**
     * Sets last id.
     *
     * @param lastID the last id
     */
    public void setLastID(int lastID) {
        this.lastID = lastID;
    }
}
package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.misc.PetDatabaseObject;
import me.opkarol.oppets.pets.OpPetsEntityTypes;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

/**
 * The type Pets database.
 */
public class PetsDatabase {
    /**
     * The Map.
     */
    private final HashMap<OpPetsEntityTypes.TypeOfEntity, PetDatabaseObject> map = new HashMap<>();

    /**
     * Instantiates a new Pets database.
     */
    public PetsDatabase() {
        String iPath;
        Database database = Database.getInstance();
        FileConfiguration config = database.getPlugin().getConfig();
        for (OpPetsEntityTypes.TypeOfEntity type : OpPetsEntityTypes.TypeOfEntity.values()) {
            iPath = "Pets." + type.toString() + ".";
            List<String> abilityEnabled = config.getStringList(iPath + "special_ability");
            float entitySpeed = (float) config.getDouble(iPath + "speed");
            float entityDistance = (float) config.getDouble(iPath + "distance");
            map.put(type, new PetDatabaseObject(abilityEnabled, entitySpeed, entityDistance));
        }
    }

    /**
     * Gets object from database.
     *
     * @param entityType the entity type
     * @return the object from database
     */
    public PetDatabaseObject getObjectFromDatabase(OpPetsEntityTypes.TypeOfEntity entityType) {
        return map.get(entityType);
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    public HashMap<OpPetsEntityTypes.TypeOfEntity, PetDatabaseObject> getMap() {
        return map;
    }
}

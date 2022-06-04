package me.opkarol.oppets.databases.external;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.map.OpMap;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.misc.external.api.PetDatabaseObject;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class PetsDatabase {
    //TODO change
    private final OpMap<TypeOfEntity, PetDatabaseObject> map = new OpMap<>();

    public PetsDatabase() {
        String iPath;
        Database database = Database.getInstance();
        FileConfiguration config = database.getPlugin().getConfig();
        for (TypeOfEntity type : TypeOfEntity.values()) {
            iPath = "Pets." + type.toString() + ".";
            List<String> abilityEnabled = config.getStringList(iPath + "special_ability");
            float entitySpeed = (float) config.getDouble(iPath + "speed");
            float entityDistance = (float) config.getDouble(iPath + "distance");
            map.put(type, new PetDatabaseObject(abilityEnabled, entitySpeed, entityDistance));
        }
    }

    public PetDatabaseObject getObjectFromDatabase(TypeOfEntity entityType) {
        return map.getOrDefault(entityType, null);
    }

    public OpMap<TypeOfEntity, PetDatabaseObject> getMap() {
        return map;
    }
}

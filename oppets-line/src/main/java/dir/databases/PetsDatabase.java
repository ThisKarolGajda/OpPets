package dir.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.misc.PetDatabaseObject;
import dir.pets.OpPetsEntityTypes;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class PetsDatabase {
    private final HashMap<OpPetsEntityTypes.TypeOfEntity, PetDatabaseObject> map = new HashMap<>();

    public PetsDatabase() {
        String iPath;
        FileConfiguration config = Database.getInstance().getConfig();
        for (OpPetsEntityTypes.TypeOfEntity type : OpPetsEntityTypes.TypeOfEntity.values()) {
            iPath = "Pets." + type.toString() + ".";
            boolean abilityEnabled = config.getBoolean(iPath + "special_ability");
            float entitySpeed = (float) config.get(iPath + "speed");
            float entityDistance = (float) config.get(iPath + "distance");
            map.put(type, new PetDatabaseObject(abilityEnabled, entitySpeed, entityDistance));
        }
    }

    public PetDatabaseObject getObjectFromDatabase(OpPetsEntityTypes.TypeOfEntity entityType) {
        return map.get(entityType);
    }
}

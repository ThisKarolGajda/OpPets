package dir.interfaces;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.Pet;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface IDatabase {
    HashMap<UUID, List<Pet>> getPetsMap();

    void setPetsMap(HashMap<UUID, List<Pet>> loadObject);

    List<Pet> getPetList(UUID uuid);

    void setCurrentPet(UUID uniqueId, Pet pet);

    HashMap<UUID, Pet> getActivePetMap();

    void setActivePetMap(HashMap<UUID, Pet> loadObject);

    void startLogic();

    Pet getCurrentPet(UUID uuid);

    void addIdPet(UUID ownUUID, int id);

    int getIdPet(UUID ownUUID);

    default void removePet(UUID uniqueId, Pet pet) {}

    void removeCurrentPet(UUID uuid);

    void removeCurrentPet(@NotNull Pet pet, UUID uuid);

    void setPets(UUID uuid, List<Pet> objects);

    boolean addPetToPetsList(UUID playerUUID, Pet pet);

    default void databaseUUIDSaver(UUID playerUUID) {}
}

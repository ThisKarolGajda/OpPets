package me.opkarol.oppets.interfaces;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.pets.Pet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDatabase {
    OpMap<UUID, List<Pet>> getPetsMap();

    void setPetsMap(OpMap<UUID, List<Pet>> map);

    List<Pet> getPetList(UUID uuid);

    void setCurrentPet(UUID uuid, Pet pet);

    OpMap<UUID, Pet> getActivePetMap();

    void setActivePetMap(OpMap<UUID, Pet> map);

    void startLogic();

    Pet getCurrentPet(UUID uuid);

    void addIdPet(UUID uuid, int id);

    int getIdPet(UUID uuid);

    List<Integer> getPetIDs();

    void removePet(UUID uuid, Pet pet);

    Optional<Pet> removePet(UUID uuid, String name);

    boolean hasPet(UUID uuid, String name);

    Optional<Pet> getPet(UUID uuid, String name);

    void updatePet(UUID uuid, Pet pet);

    void removeCurrentPet(UUID uuid);

    void setPets(UUID uuid, List<Pet> objects);

    boolean addPetToPetsList(UUID uuid, Pet pet);

    default void databaseUUIDSaver(UUID uuid, boolean async) {
        setPets(uuid, getPetList(uuid));
    }
}

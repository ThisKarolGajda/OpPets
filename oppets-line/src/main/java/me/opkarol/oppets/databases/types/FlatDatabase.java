package me.opkarol.oppets.databases.types;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.files.manager.FileManager;
import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.database.IPetsDatabase;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FlatDatabase implements IPetsDatabase {
    private OpMap<UUID, Pet> activePetMap = new OpMap<>();
    private OpMap<UUID, List<Pet>> petsMap = new OpMap<>();
    private OpMap<UUID, Integer> idPetsMap;
    private final Database database = Database.getInstance();

    @Override
    public void startLogic() {
        database.getDatabase().setPetsMap(new FileManager<OpMap<UUID, List<Pet>>>("database/Pets.txt").loadObject());
        database.getDatabase().setActivePetMap(new FileManager<OpMap<UUID, Pet>>("database/ActivePets.txt").loadObject());
        idPetsMap = new OpMap<>();
    }

    @Override
    public void addIdPet(UUID petUUID, int petId) {
        idPetsMap.replace(petUUID, petId);
    }

    @Override
    public int getIdPet(UUID petUUID) {
        return idPetsMap.getOrDefault(petUUID, 0);
    }

    @Override
    public List<Integer> getPetIDs() {
        return new ArrayList<>(idPetsMap.getValues());
    }

    @Override
    public void removePet(UUID uuid, @NotNull Pet pet) {
        List<Pet> list = getPetList(uuid);
        int petID = pet.petUUID.getDatabaseId();
        if (list.stream().anyMatch(pet1 -> pet1.petUUID.getDatabaseId() == petID)) {
            removeCurrentPet(uuid);
        }
        list.removeIf(pet1 -> pet1.petUUID.getDatabaseId() == petID);
        setPets(uuid, list);
    }

    @Override
    public Optional<Pet> removePet(UUID uuid, String name) {
        if (hasPet(uuid, name)) {
            Optional<Pet> pet = getPet(uuid, name);
            pet.ifPresent(pet1 -> removePet(uuid, pet1));
            return pet;
        }
        return Optional.empty();
    }

    @Override
    public boolean hasPet(UUID uuid, String name) {
        if (uuid != null && name != null) {
            return getPetList(uuid).stream()
                    .anyMatch(pet -> Utils.equalsNames(pet, name));
        }
        return false;
    }

    @Override
    public Optional<Pet> getPet(UUID uuid, String name) {
        if (hasPet(uuid, name)) {
            return getPetList(uuid).stream()
                    .filter(pet -> Utils.equalsNames(pet, name)).findAny();
        }
        return Optional.empty();
    }

    @Override
    public OpMap<UUID, Pet> getActivePetMap() {
        return activePetMap;
    }

    @Override
    public void setActivePetMap(OpMap<UUID, Pet> activePetMap) {
        if (activePetMap == null) {
            activePetMap = new OpMap<>();
        }
        this.activePetMap = activePetMap;
    }

    @Override
    public OpMap<UUID, List<Pet>> getPetsMap() {
        return petsMap;
    }

    @Override
    public void setPetsMap(OpMap<UUID, List<Pet>> petsMap) {
        if (petsMap == null) {
            petsMap = new OpMap<>();
        }
        this.petsMap = petsMap;
    }

    @Override
    public void setCurrentPet(UUID uuid, Pet pet) {
        if (activePetMap.containsKey(uuid)) {
            activePetMap.replace(uuid, pet);
        } else {
            activePetMap.put(uuid, pet);
        }
    }

    @Override
    public Pet getCurrentPet(UUID uuid) {
        return activePetMap.getOrDefault(uuid, null);
    }

    @Override
    public void removeCurrentPet(UUID uuid) {
        Utils.killPetFromPlayerUUID(uuid);
        activePetMap.remove(uuid);
    }

    @Override
    public void setPets(UUID uuid, List<Pet> list) {
        petsMap.set(uuid, list);
    }

    @Override
    public List<Pet> getPetList(UUID uuid) {
        return petsMap.getOrDefault(uuid, new ArrayList<>());
    }

    @Override
    public boolean addPetToPetsList(UUID uuid, Pet pet) {
        if (pet == null) {
            return false;
        }
        List<Pet> petList = getPetList(uuid);
        petList.add(pet);
        setPets(uuid, petList);
        return true;
    }

}

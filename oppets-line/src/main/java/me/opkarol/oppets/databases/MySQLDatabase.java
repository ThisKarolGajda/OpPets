package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.interfaces.IDatabase;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.OpUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MySQLDatabase implements IDatabase {
    private OpMap<UUID, Pet> activePetMap = new OpMap<>();
    private OpMap<UUID, List<Pet>> petsMap = new OpMap<>();
    private OpMap<UUID, Integer> idPetsMap;
    private Logger logger;
    private final Database database = Database.getInstance();

    @Override
    public OpMap<UUID, List<Pet>> getPetsMap() {
        return petsMap;
    }

    @Override
    public void setPetsMap(OpMap<UUID, List<Pet>> loadObject) {
        petsMap = loadObject;
    }

    @Override
    public List<Pet> getPetList(UUID uuid) {
        return petsMap.getOrDefault(uuid, new ArrayList<>());
    }

    @Override
    public void setCurrentPet(UUID uuid, Pet pet) {
        List<Pet> pets = getPetList(uuid);
        for (Pet pet1 : pets) {
            pet1.setActive(false);
        }
        pet.setActive(true);
        activePetMap.set(uuid, pet);
        setPets(uuid, pets);
    }

    @Override
    public OpMap<UUID, Pet> getActivePetMap() {
        return activePetMap;
    }

    @Override
    public void setActivePetMap(OpMap<UUID, Pet> loadObject) {
        activePetMap = loadObject;
    }

    @Override
    public void startLogic() {
        idPetsMap = new OpMap<>();
        setupPetsMap();
        setupActiveMap();
        logger = database.getPlugin().getLogger();
    }

    private void setupPetsMap() {
        List<Pet> petList = database.getMySQL().getPets();
        OpMap<UUID, List<Pet>> map = new OpMap<>();
        for (Pet pet : petList) {
            UUID uuid = pet.getOwnerUUID();
            List<Pet> list = map.getOrDefault(uuid, new ArrayList<>());
            list.add(pet);
            map.set(uuid, list);
        }
        setPetsMap(map);
    }

    private void setupActiveMap() {
        OpMap<UUID, Pet> OpMap = new OpMap<>();
        for (UUID uuid : getPetsMap().keySet()) {
            List<Pet> activePets = getPetsMap().getOrDefault(uuid, new ArrayList<>()).stream().filter(Pet::isActive).collect(Collectors.toList());
            if (activePets.size() > 1) {
                for (Pet pet : activePets) {
                    pet.setActive(false);
                }
                activePets.get(0).setActive(true);
            }
            if (activePets.size() == 1) {
                OpMap.put(uuid, activePets.get(0));
            }
        }
        setActivePetMap(OpMap);
    }

    @Override
    public Pet getCurrentPet(UUID uuid) {
        return activePetMap.getOrDefault(uuid, null);
    }

    @Override
    public void addIdPet(UUID ownUUID, int id) {
        idPetsMap.replace(ownUUID, id);
    }

    @Override
    public int getIdPet(UUID ownUUID) {
        return idPetsMap.getOrDefault(ownUUID, 0);
    }

    @Override
    public List<Integer> getPetIDs() {
        return new ArrayList<>(idPetsMap.getValues());
    }

    @Override
    public void removePet(UUID uuid, @NotNull Pet pet) {
        List<Pet> list = getPetList(uuid);
        int petID = pet.getPetUUID().getID();
        if (list.stream().anyMatch(pet1 -> pet1.getPetUUID().getID() == petID)) {
            removeCurrentPet(uuid);
        }
        database.getMySQL().deletePet(pet);
        list.removeIf(pet1 -> pet1.getPetUUID().getID() == petID);
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
                    .anyMatch(pet -> PetsUtils.equalsNames(pet, name));
        }
        return false;
    }

    @Override
    public Optional<Pet> getPet(UUID uuid, String name) {
        if (hasPet(uuid, name)) {
            return getPetList(uuid).stream()
                    .filter(pet -> PetsUtils.equalsNames(pet, name)).findAny();
        }
        return Optional.empty();
    }

    @Override
    public void updatePet(UUID uuid, @NotNull Pet pet) {
        List<Pet> list = getPetList(uuid);
        int petID = pet.getPetUUID().getID();
        database.getMySQL().insertPet(pet);
        list.removeIf(pet1 -> pet1.getPetUUID().getID() == petID);
        list.add(pet);
        setPets(uuid, list);
    }

    @Override
    public void removeCurrentPet(UUID uuid) {
        OpUtils.killPetFromPlayerUUID(uuid);
        activePetMap.remove(uuid);
    }

    @Override
    public void setPets(UUID uuid, List<Pet> objects) {
        petsMap.set(uuid, objects);
        for (Pet pet : objects) {
            database.getMySQL().asyncInsertPet(pet);
        }
    }

    @Override
    public boolean addPetToPetsList(UUID uuid, Pet pet) {
        if (pet == null) {
            return false;
        }
        List<Pet> petList = getPetList(uuid);
        petList.add(pet);
        database.getMySQL().asyncInsertPet(pet);
        setPets(uuid, petList);
        return true;
    }

    @Override
    public void databaseUUIDSaver(UUID uuid, boolean async) {
        if (uuid == null) {
            logger.warning("Error while trying to get UUID (databaseUUIDSaver). Provided UUID seems to be invalid.");
        }
        List<Pet> list = petsMap.getOrDefault(uuid, new ArrayList<>());
        for (Pet pet : list) {
            if (async) {
                database.getMySQL().asyncInsertPet(pet);
            } else {
                database.getMySQL().insertPet(pet);
            }
        }
        petsMap.replace(uuid, list);
    }
}

package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.interfaces.IDatabase;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The type My sql database.
 */
public class MySQLDatabase implements IDatabase {
    /**
     * The Active pet map.
     */
    private HashMap<UUID, Pet>  activePetMap;
    /**
     * The Pets map.
     */
    private HashMap<UUID, List<Pet>> petsMap;
    /**
     * The Id pets map.
     */
    private HashMap<UUID, Integer> idPetsMap;
    /**
     * The Logger.
     */
    private Logger logger;

    /**
     * Gets pets map.
     *
     * @return the pets map
     */
    @Override
    public HashMap<UUID, List<Pet>> getPetsMap() {
        return petsMap;
    }

    /**
     * Sets pets map.
     *
     * @param loadObject the load object
     */
    @Override
    public void setPetsMap(HashMap<UUID, List<Pet>> loadObject) {
        petsMap = loadObject;
    }

    /**
     * Gets pet list.
     *
     * @param uuid the uuid
     * @return the pet list
     */
    @Override
    public List<Pet> getPetList(UUID uuid) {
        return petsMap.get(uuid);
    }

    /**
     * Sets current pet.
     *
     * @param uniqueId the unique id
     * @param pet      the pet
     */
    @Override
    public void setCurrentPet(UUID uniqueId, Pet pet) {
        List<Pet> pets = getPetsMap().get(uniqueId);
        for (Pet pet1 : pets) {
            pet1.setActive(false);
        }
        pet.setActive(true);
        if (activePetMap.containsKey(uniqueId)) {
            activePetMap.replace(uniqueId, pet);
        } else {
            activePetMap.put(uniqueId, pet);
        }
        setPets(uniqueId, pets);
    }

    /**
     * Gets active pet map.
     *
     * @return the active pet map
     */
    @Override
    public HashMap<UUID, Pet> getActivePetMap() {
        return activePetMap;
    }

    /**
     * Sets active pet map.
     *
     * @param loadObject the load object
     */
    @Override
    public void setActivePetMap(HashMap<UUID, Pet> loadObject) {
        activePetMap = loadObject;
    }

    /**
     * Start logic.
     */
    @Override
    public void startLogic() {
        idPetsMap = new HashMap<>();
        setupPetsMap();
        setupActiveMap();
        logger = Database.getInstance().getLogger();
    }

    /**
     * Sets pets map.
     */
    private void setupPetsMap() {
        List<Pet> petList = MySQL.getPets();
        HashMap<UUID, List<Pet>> hashMap = new HashMap<>();
        for (Pet pet : petList) {
            UUID uuid = pet.getOwnerUUID();
            if (hashMap.containsKey(uuid)) {
                List<Pet> list = new ArrayList<>(hashMap.get(uuid));
                list.add(pet);
                hashMap.replace(uuid, list);
            } else {
                hashMap.put(uuid, Collections.singletonList(pet));
            }
        }
        setPetsMap(hashMap);
    }

    /**
     * Sets active map.
     */
    private void setupActiveMap() {
        HashMap<UUID, Pet> hashMap = new HashMap<>();
        for (UUID uuid : getPetsMap().keySet()) {
            List<Pet> activePets = getPetsMap().get(uuid).stream().filter(Pet::isActive).collect(Collectors.toList());
            if (activePets.size() > 1) {
                for (Pet pet : activePets) {
                    pet.setActive(false);
                }
                activePets.get(0).setActive(true);
            }
            if (activePets.size() == 1) {
                hashMap.put(uuid, activePets.get(0));
            }
        }
        setActivePetMap(hashMap);
    }

    /**
     * Gets current pet.
     *
     * @param uuid the uuid
     * @return the current pet
     */
    @Override
    public Pet getCurrentPet(UUID uuid) {
        return activePetMap.get(uuid);
    }

    /**
     * Add id pet.
     *
     * @param ownUUID the own uuid
     * @param id      the id
     */
    @Override
    public void addIdPet(UUID ownUUID, int id) {
        idPetsMap.replace(ownUUID, id);
    }

    /**
     * Gets id pet.
     *
     * @param ownUUID the own uuid
     * @return the id pet
     */
    @Override
    public int getIdPet(UUID ownUUID) {
        return idPetsMap.get(ownUUID);
    }

    /**
     * Remove pet.
     *
     * @param uuid the uuid
     * @param pet  the pet
     */
    @Override
    public void removePet(UUID uuid, @NotNull Pet pet) {
        if (activePetMap.get(uuid).getPetUUID().getStringID().equals(pet.getPetUUID().getStringID())) {
            removeCurrentPet(uuid);
        }
        try {
            MySQL.deletePet(pet);
        } catch (SQLException e) {
            logger.warning("Error while trying to delete Pet from UUID: " + uuid.toString());
            e.printStackTrace();
        }
        List<Pet> list = petsMap.get(uuid);
        list.removeIf(petI -> Objects.equals(FormatUtils.getNameString(petI.getPetName()), FormatUtils.getNameString(pet.getPetName())));
        setPets(uuid, list);
    }

    /**
     * Remove current pet.
     *
     * @param uuid the uuid
     */
    @Override
    public void removeCurrentPet(UUID uuid) {
        Database.getUtils().killPetFromPlayerUUID(uuid);
        activePetMap.remove(uuid);
    }

    /**
     * Sets pets.
     *
     * @param uuid    the uuid
     * @param objects the objects
     */
    @Override
    public void setPets(UUID uuid, List<Pet> objects) {
        petsMap.replace(uuid, objects);
        for (Pet pet : objects) {
            MySQL.asyncInsertPet(pet);
        }
    }

    /**
     * Add pet to pets list boolean.
     *
     * @param playerUUID the player uuid
     * @param pet        the pet
     * @return the boolean
     */
    @Override
    public boolean addPetToPetsList(UUID playerUUID, Pet pet) {
        if (pet == null) {
            return false;
        }
        List<Pet> petList = new ArrayList<>(petsMap.get(playerUUID));
        petList.add(pet);
        MySQL.asyncInsertPet(pet);
        setPets(playerUUID, petList);
        return true;
    }

    /**
     * Database uuid saver.
     *
     * @param playerUUID the player uuid
     * @param async      the async
     */
    @Override
    public void databaseUUIDSaver(UUID playerUUID, boolean async) {
        if (playerUUID == null) {
            logger.warning("Error while trying to get UUID (databaseUUIDSaver). Provided UUID seems to be invalid.");
        }
        List<Pet> list = petsMap.get(playerUUID);
        for (Pet pet : (list != null ? list : new ArrayList<Pet>())) {
            if (async) {
                MySQL.asyncInsertPet(pet);
            } else {
                try {
                    MySQL.insertPet(pet);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        petsMap.replace(playerUUID, list);
    }


}

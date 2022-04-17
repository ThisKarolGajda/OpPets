package me.opkarol.oppets.interfaces;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.Pet;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The interface Database.
 */
public interface IDatabase {
    /**
     * Gets pets map.
     *
     * @return the pets map
     */
    HashMap<UUID, List<Pet>> getPetsMap();

    /**
     * Sets pets map.
     *
     * @param loadObject the load object
     */
    void setPetsMap(HashMap<UUID, List<Pet>> loadObject);

    /**
     * Gets pet list.
     *
     * @param uuid the uuid
     * @return the pet list
     */
    List<Pet> getPetList(UUID uuid);

    /**
     * Sets current pet.
     *
     * @param uniqueId the unique id
     * @param pet      the pet
     */
    void setCurrentPet(UUID uniqueId, Pet pet);

    /**
     * Gets active pet map.
     *
     * @return the active pet map
     */
    HashMap<UUID, Pet> getActivePetMap();

    /**
     * Sets active pet map.
     *
     * @param loadObject the load object
     */
    void setActivePetMap(HashMap<UUID, Pet> loadObject);

    /**
     * Start logic.
     */
    void startLogic();

    /**
     * Gets current pet.
     *
     * @param uuid the uuid
     * @return the current pet
     */
    Pet getCurrentPet(UUID uuid);

    /**
     * Add id pet.
     *
     * @param ownUUID the own uuid
     * @param id      the id
     */
    void addIdPet(UUID ownUUID, int id);

    /**
     * Gets id pet.
     *
     * @param ownUUID the own uuid
     * @return the id pet
     */
    int getIdPet(UUID ownUUID);

    /**
     * Remove pet.
     *
     * @param uniqueId the unique id
     * @param pet      the pet
     */
    void removePet(UUID uniqueId, Pet pet);

    /**
     * Remove current pet.
     *
     * @param uuid the uuid
     */
    void removeCurrentPet(UUID uuid);

    /**
     * Sets pets.
     *
     * @param uuid    the uuid
     * @param objects the objects
     */
    void setPets(UUID uuid, List<Pet> objects);

    /**
     * Add pet to pets list boolean.
     *
     * @param playerUUID the player uuid
     * @param pet        the pet
     * @return the boolean
     */
    boolean addPetToPetsList(UUID playerUUID, Pet pet);

    /**
     * Database uuid saver.
     *
     * @param playerUUID the player uuid
     * @param async      the async
     */
    default void databaseUUIDSaver(UUID playerUUID, boolean async) {
        setPets(playerUUID, getPetList(playerUUID));
    }
}

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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * The type Mini pets database.
 */
public class MiniPetsDatabase implements IDatabase {
    /**
     * The Active pet map.
     */
    private HashMap<UUID, Pet> activePetMap;
    /**
     * The Pets map.
     */
    private HashMap<UUID, List<Pet>> petsMap;
    /**
     * The Id pets map.
     */
    private HashMap<UUID, Integer> idPetsMap;

    /**
     * Start logic.
     */
    @Override
    public void startLogic() {
        if (activePetMap == null) {
            activePetMap = new HashMap<>();
        }
        if (petsMap == null) {
            petsMap = new HashMap<>();
        }
        if (idPetsMap == null) {
            idPetsMap = new HashMap<>();
        }
    }

    /**
     * Add id pet.
     *
     * @param petUUID the pet uuid
     * @param petId   the pet id
     */
    @Override
    public void addIdPet(UUID petUUID, int petId) {
        idPetsMap.put(petUUID, petId);
    }

    /**
     * Gets id pet.
     *
     * @param petUUID the pet uuid
     * @return the id pet
     */
    @Override
    public int getIdPet(UUID petUUID) {
        return idPetsMap.getOrDefault(petUUID, 0);
    }

    /**
     * Remove pet.
     *
     * @param uuid the uuid
     * @param pet  the pet
     */
    @Override
    public void removePet(UUID uuid, @NotNull Pet pet) {
        ArrayList<Pet> list = (ArrayList<Pet>) petsMap.get(uuid);
        if (pet.getPetName() == null) {
            return;
        }
        if (getCurrentPet(uuid) != null) {
            if (Objects.equals(getName(Objects.requireNonNull(getCurrentPet(uuid).getPetName())), getName(pet.getPetName()))) {
                removeCurrentPet(uuid);
            }
        }
        list.removeIf(pet1 -> Objects.equals(getName(pet1.getPetName()), getName(pet.getPetName())));
        setPets(uuid, list);
    }

    /**
     * Gets name.
     *
     * @param s the s
     * @return the name
     */
    private String getName(String s) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', s));
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
     * @param activePetMap the active pet map
     */
    @Override
    public void setActivePetMap(HashMap<UUID, Pet> activePetMap) {
        this.activePetMap = activePetMap;
    }

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
     * @param petsMap the pets map
     */
    @Override
    public void setPetsMap(HashMap<UUID, List<Pet>> petsMap) {
        this.petsMap = petsMap;
    }

    /**
     * Sets current pet.
     *
     * @param uuid the uuid
     * @param pet  the pet
     */
    @Override
    public void setCurrentPet(UUID uuid, Pet pet) {
        if (activePetMap.containsKey(uuid)) {
            activePetMap.replace(uuid, pet);
        } else {
            activePetMap.put(uuid, pet);
        }
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
     * Remove current pet.
     *
     * @param uuid the uuid
     */
    @Override
    public void removeCurrentPet(UUID uuid) {
        Pet pet = getCurrentPet(uuid);
        activePetMap.values().stream().filter(pet1 -> Objects.equals(pet1.getPetName(), pet.getPetName())).forEach(pet1 -> {
            Entity entity = Bukkit.getEntity(pet.getOwnUUID());
            if (entity != null) {
                entity.remove();
            }
            Database.getDatabase().removePet(uuid, pet);
        });
        activePetMap.remove(uuid);
    }

    /**
     * Sets pets.
     *
     * @param uuid the uuid
     * @param list the list
     */
    @Override
    public void setPets(UUID uuid, List<Pet> list) {
        petsMap.remove(uuid);
        if (petsMap.containsKey(uuid)) {
            petsMap.remove(uuid, list);
        }
        petsMap.put(uuid, list);

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
     * Add pet to pets list boolean.
     *
     * @param uuid the uuid
     * @param pet  the pet
     * @return the boolean
     */
    @Override
    public boolean addPetToPetsList(UUID uuid, Pet pet) {
        List<Pet> petList;
        if (getPetList(uuid) == null) {
            setPets(uuid, new ArrayList<>(Collections.singletonList(pet)));
            return true;
        } else {
            petList = new ArrayList<>(getPetList(uuid));
        }
        petList.add(pet);
        setPets(uuid, petList);
        return true;
    }

}

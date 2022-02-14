package dir.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.interfaces.IDatabase;
import dir.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MiniPetsDatabase implements IDatabase {

    private HashMap<UUID, Pet> activePetMap;
    private HashMap<UUID, List<Pet>> petsMap;
    private HashMap<UUID, Integer> idPetsMap;

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

    @Override
    public void addIdPet(UUID petUUID, int petId) {
        idPetsMap.put(petUUID, petId);
    }

    @Override
    public int getIdPet(UUID petUUID) {
        return idPetsMap.getOrDefault(petUUID, 0);
    }

    @Override
    public void removePet(UUID uuid, @NotNull Pet pet) {
        ArrayList<Pet> list = (ArrayList<Pet>) petsMap.get(uuid);

        assert pet.getPetName() != null;
        if (getCurrentPet(uuid) != null) {
            if (Objects.equals(getName(Objects.requireNonNull(getCurrentPet(uuid).getPetName())), getName(pet.getPetName()))) {
                removeCurrentPet(uuid);
            }
        }

        list.removeIf(pet1 -> Objects.equals(getName(pet1.getPetName()), getName(pet.getPetName())));

        setPets(uuid, list);

    }

    private String getName(String s) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', s));
    }

    @Override
    public HashMap<UUID, Pet> getActivePetMap() {
        return activePetMap;
    }

    @Override
    public void setActivePetMap(HashMap<UUID, Pet> activePetMap) {
        this.activePetMap = activePetMap;
    }

    @Override
    public HashMap<UUID, List<Pet>> getPetsMap() {
        return petsMap;
    }

    @Override
    public void setPetsMap(HashMap<UUID, List<Pet>> petsMap) {
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
        return activePetMap.get(uuid);
    }

    @Override
    public void removeCurrentPet(UUID uuid) {
        Objects.requireNonNull(Bukkit.getEntity(activePetMap.get(uuid).getOwnUUID())).remove();
        activePetMap.remove(uuid);
    }

    @Override
    public void removeCurrentPet(@NotNull Pet pet, UUID uuid) {
        Objects.requireNonNull(Bukkit.getEntity(pet.getOwnUUID())).remove();
        activePetMap.remove(uuid);
    }

    @Override
    public void setPets(UUID uuid, List<Pet> list) {
        petsMap.remove(uuid);
        if (petsMap.containsKey(uuid)) {
            petsMap.remove(uuid, list);
        }
        petsMap.put(uuid, list);

    }

    @Override
    public List<Pet> getPetList(UUID uuid) {
        return petsMap.get(uuid);
    }

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

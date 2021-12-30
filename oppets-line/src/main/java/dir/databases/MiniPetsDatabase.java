package dir.databases;

import dir.interfaces.DatabaseInterface;
import dir.pets.Pet;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MiniPetsDatabase implements DatabaseInterface {

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
        LinkedList<Pet> list = (LinkedList<Pet>) petsMap.get(uuid);
        if (Objects.equals(getCurrentPet(uuid).getPetName(), pet.getPetName())){
            removeCurrentPet(uuid);
        }
        list.remove(pet);
        setPets(uuid, list);

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
        activePetMap.remove(uuid);
    }

    @Override
    public void setPets(UUID uuid, List<Pet> list) {
        if (petsMap.containsKey(uuid)) {
            petsMap.replace(uuid, list);
        } else {
            petsMap.put(uuid, list);
        }
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

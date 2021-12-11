package dir.pets;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MiniPetsDatabase {

    private HashMap<UUID, Pet> activePetMap;
    private HashMap<UUID, List<Pet>> petsMap;
    private HashMap<UUID, Integer> idPetsMap;

    public void startLogic(){
        if (activePetMap == null){
            activePetMap = new HashMap<>();
        }
        if (petsMap == null){
            petsMap = new HashMap<>();
        }
        if (idPetsMap == null){
            idPetsMap = new HashMap<>();
        }
    }

    public void addIdPet(UUID petUUID, int petId){
        idPetsMap.put(petUUID, petId);
    }

    public int getIdPet(UUID petUUID){
        return idPetsMap.getOrDefault(petUUID, 0);
    }

    public void removeIdPet(UUID petUUID, int petId){
        idPetsMap.remove(petUUID, petId);
    }

    public void removeIdPet(UUID petUUID){
        idPetsMap.remove(petUUID);
    }

    public void removePet(UUID uuid, @NotNull Pet pet){
        List<Pet> list = petsMap.get(uuid);
        try {
            list.remove(pet);
        } catch (Exception ignore){}
        setPets(uuid, list);
    }

    public HashMap<UUID, Pet> getActivePetMap() {
        return activePetMap;
    }

    public void setActivePetMap(HashMap<UUID, Pet> activePetMap) {
        this.activePetMap = activePetMap;
    }

    public HashMap<UUID, List<Pet>> getPetsMap() {
        return petsMap;
    }

    public void setPetsMap(HashMap<UUID, List<Pet>> petsMap) {
        this.petsMap = petsMap;
    }

    public void setCurrentPet(UUID uuid, Pet pet){
        if (activePetMap.containsKey(uuid)){
            activePetMap.replace(uuid, pet);
        } else {
            activePetMap.put(uuid, pet);
        }
    }

    public Pet getCurrentPet(UUID uuid){
        return activePetMap.get(uuid);
    }

    public void removeCurrentPet(UUID uuid){
        activePetMap.remove(uuid);
    }

    public void setPets(UUID uuid, List<Pet> list){
        if (petsMap.containsKey(uuid)){
            petsMap.replace(uuid, list);
        } else {
            petsMap.put(uuid, list);
        }
    }

    public List<Pet> getPetList(UUID uuid){
        return petsMap.get(uuid);
    }

    public void addPetToPetsList(UUID uuid, Pet pet){
        List<Pet> petList;
        if (getPetList(uuid) == null) {
            setPets(uuid, new ArrayList<>(Collections.singletonList(pet)));
            return;
        } else {
            petList = new ArrayList<>(getPetList(uuid));
        }
        petList.add(pet);
        setPets(uuid, petList);
    }
}

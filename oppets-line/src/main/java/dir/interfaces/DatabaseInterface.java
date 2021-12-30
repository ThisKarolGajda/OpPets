package dir.interfaces;

import dir.pets.Pet;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface DatabaseInterface {
    HashMap<UUID, List<Pet>> getPetsMap();

    List<Pet> getPetList(UUID uuid);

    void setCurrentPet(UUID uniqueId, Pet pet);

    void setPetsMap(HashMap<UUID, List<Pet>> loadObject);

    void setActivePetMap(HashMap<UUID, Pet> loadObject);

    HashMap<UUID, Pet> getActivePetMap();

    void startLogic();

    Pet getCurrentPet(UUID uuid);

    void addIdPet(UUID ownUUID, int id);

    int getIdPet(UUID ownUUID);

    void removePet(UUID uniqueId, Pet pet);

    void removeCurrentPet(UUID uuid);

    void setPets(UUID uuid, List<Pet> objects);

    boolean addPetToPetsList(UUID playerUUID, Pet pet);

    default void databaseUUIDSaver(UUID playerUUID) {

    }
}

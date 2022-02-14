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
import dir.pets.PetsConverter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static dir.databases.MySQL.*;


public class MySQLMiniPetsDatabase implements IDatabase {
    /*
    TODO
    -
    NOT-WORKING:
    -   [-x] Deleting not current pet = kills current pet = not removing it from database  -> Should work
    -   Deleting pet doesn't delete pet from map and adds values to database               -> deletes from local map but not mysql
    -   [x] Names with colour doesn't save                                                 -> It has been fixed
    -   SettingsInventory doesn't works                                                    -> Change saving current pet, spawns entities without killing previous <= Should work - works too well (spawns new entities / access mysql connection every time which cause lag)
    -   [x] Renamed pet doesn't save after restart                                         -> Works <= Need to change - update command doesn't work
    -   [x] Renamed pet name isn't stripped in auto-complete                               -> Works even with database save - FormatUtils.getNameString()
    -   [] Creating methods looks like they are called twice (sometime)                    -> Recognizing error
     */
    private HashMap<UUID, Integer> idPetsMap;
    private HashMap<UUID, List<Pet>> petsMap;
    private HashMap<UUID, Pet> activePets;

    public void databaseUUIDSaver(UUID id) {
        if (!isConnected()) connect();

        try {
            PreparedStatement statement = getConnection().prepareStatement("UPDATE " + MySQL.table + " SET object=? WHERE id=?;");
            List<Pet> list = getPetList(id);
            if (list == null) {
                return;
            }
            for (Pet pet : list) {
                if (pet != null) {
                    statement.setString(1, new PetsConverter().convertPetToJSON(pet).toString());
                    statement.setString(2, id.toString());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        disconnect();

    }

    @Override
    public List<Pet> getPetList(@NotNull UUID uuid) {
        List<Pet> i;

        if (petsMap == null) {
            return null;
        }

        if (petsMap.get(uuid) == null) {
            i = new ArrayList<>();

        } else {
            i = petsMap.get(uuid);
        }

        return i;
    }

    private @NotNull List<Pet> getPetListFromMySQL(UUID uuid) {
        List<String> sI = new ArrayList<>();
        if (!isConnected()) {
            connect();
        }

        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + MySQL.table + " WHERE `id`=?;");
            statement.setString(1, uuid.toString());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String o = rs.getString("object");
                sI.add(o);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        disconnect();

        List<Pet> i = new ArrayList<>();
        for (String s : sI) {
            try {
                i.add(new PetsConverter().convertStringToPet(s));
            } catch (Exception ignore) {
                i.addAll(new PetsConverter().convertJSONArrayToPetList(sI.toArray()));
            }
        }
        return i;
    }

    @Override
    public void setCurrentPet(UUID uniqueId, Pet pet) {
        List<Pet> pets = getPetList(uniqueId);

        if (pets == null) {
            return;
        }

        for (Pet petI : pets) {
            if (petI.isActive()) {
                petI.setActive(false);
            }
        }

        pet.setActive(true);
        activePets.put(uniqueId, pet);
        setPets(uniqueId, pets);
    }

    @Override
    public HashMap<UUID, List<Pet>> getPetsMap() {
        return petsMap;
    }

    @Override
    public void setPetsMap(HashMap<UUID, List<Pet>> loadObject) {
        petsMap = loadObject;
    }

    private @NotNull HashMap<UUID, List<Pet>> getPetsMapFromMySQL() {
        HashMap<UUID, List<Pet>> i = new HashMap<>();
        connect();

        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + MySQL.table);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String o = rs.getString("object");
                if (!o.startsWith("{")) o = o.substring(1, o.length() - 1);

                if (i.get(id) == null) {
                    List<Pet> list = new ArrayList<>();
                    list.add(new PetsConverter().convertStringToPet(o));
                    i.put(id, list);
                } else {
                    Pet pet = new PetsConverter().convertStringToPet(o);
                    if (!i.get(id).contains(pet)) {
                        List<Pet> list = i.get(id);
                        list.add(pet);
                        i.replace(id, list);
                    }
                }
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        disconnect();

        return i;
    }

    private @NotNull HashMap<UUID, Pet> getActivePetsFromMap() {
        HashMap<UUID, Pet> i = new HashMap<>();
        new BukkitRunnable() {
            @Override
            public void run() {
                getPetsMap().keySet().forEach(uuid -> i.put(uuid, getCurrentPet(uuid)));

            }
        }.runTaskAsynchronously(Database.getInstance());
        return i;
    }

    @Override
    public HashMap<UUID, Pet> getActivePetMap() {
        return activePets;
    }

    @Override
    public void setActivePetMap(HashMap<UUID, Pet> loadObject) {
        activePets = loadObject;
    }

    @Override
    public void startLogic() {
        idPetsMap = new HashMap<>();
        setPetsMap(getPetsMapFromMySQL());
        setActivePetMap(getActivePetsFromMap());

        if (petsMap == null) {
            petsMap = new HashMap<>();
        }

    }

    @Override
    public Pet getCurrentPet(UUID uuid) {
        List<Pet> pet = getPetList(uuid);
        List<Pet> actives = new ArrayList<>();

        if (pet == null || pet.size() == 0) {
            return null;
        }

        for (Pet petI : pet) {
            if (petI.isActive()) {
                actives.add(petI);
            }
        }

        if (activePets.containsKey(uuid)) {
            return activePets.get(uuid);
        }

        if (actives.size() == 1) {
            final Pet[] pet1 = new Pet[1];
            petsMap.get(uuid).forEach(petI -> {
                if (petI.isActive()) {
                    pet1[0] = petI;
                }
            });
            return pet1[0];
        } else {
            petsMap.get(uuid).replaceAll(pet1 -> {
                pet1.setActive(false);
                return pet1;
            });
            Pet pet1 = petsMap.get(uuid).get(0);
            pet1.setActive(true);
            petsMap.get(uuid).set(0, pet1);
            return pet1;
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
        List<Pet> list = petsMap.get(uuid);
        if (Objects.equals(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getCurrentPet(uuid).getPetName()))), ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', pet.getPetName())))) {
            removeCurrentPet(pet, uuid);
        }

        List<Pet> listI = new ArrayList<>();
        for (Pet petI : list) {
            if (!Objects.equals(petI.getPetName(), pet.getPetName())) {
                listI.add(petI);
            }
        }

        setPets(uuid, listI);

        for (Pet pet1 : getPetListFromMySQL(uuid)) {
            if (Objects.equals(pet1.getPetName(), pet.getPetName())) {
                connect();

                try {
                    PreparedStatement statement = getConnection().prepareStatement("DELETE FROM " + table + " WHERE id=? AND object=?");
                    statement.setString(1, uuid.toString());
                    statement.setString(2, new PetsConverter().convertPetToJSON(pet1).toString());

                    statement.executeUpdate();

                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                disconnect();
            }
        }

    }

    @Override
    public void removeCurrentPet(UUID uuid) {

    }

    @Override
    public void removeCurrentPet(@NotNull Pet pet, UUID uuid) {
        Objects.requireNonNull(Bukkit.getEntity(pet.getOwnUUID())).remove();
        List<Pet> pets = getPetList(uuid);
        Database.getUtils().removeEntity(Database.getUtils().getEntityByUniqueId(pet.getOwnUUID()));
        pets.removeIf(pet1 -> Objects.equals(pet1.getPetName(), pet.getPetName()));
        pet.setActive(false);
        pets.add(pet);
        setPets(uuid, pets);
    }


    @Override
    public void setPets(UUID uuid, List<Pet> objects) {
        petsMap.replace(uuid, objects);

        if (!isConnected()) connect();
        try {
            if (getConnection() == null) {
                return;
            }
            PreparedStatement statement = getConnection().prepareStatement("UPDATE " + MySQL.table + " SET object=? WHERE id=?");
            statement.setString(1, uuid.toString());
            for (Pet pet1 : objects) {
                statement.setString(2, new PetsConverter().convertPetToJSON(pet1).toString());
                statement.executeUpdate();
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    @Override
    public boolean addPetToPetsList(UUID playerUUID, Pet pet) {
        addPetToPetsFromMySQL(playerUUID, pet);
        if (petsMap.containsKey(playerUUID)) {
            if (petsMap.get(playerUUID).contains(pet)) {
                petsMap.get(playerUUID).removeIf(pet1 -> pet1.equals(pet));
            }
            petsMap.get(playerUUID).add(pet);
        } else {
            List<Pet> list = new ArrayList<>();
            list.add(pet);
            petsMap.put(playerUUID, list);
        }
        return true;
    }

    private void addPetToPetsFromMySQL(UUID playerUUID, Pet pet) {
        List<Pet> list = getPetList(playerUUID);

        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
        }

        list.add(pet);
        if (!isConnected()) connect();
        try {
            if (getConnection() == null) {
                return;
            }
            PreparedStatement statement = getConnection().prepareStatement("INSERT INTO " + MySQL.table + " (id, object) VALUES (?, ?)");
            statement.setString(1, playerUUID.toString());
            for (Pet pet1 : list) {
                statement.setString(2, new PetsConverter().convertPetToJSON(pet1).toString());
                statement.executeUpdate();
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

}

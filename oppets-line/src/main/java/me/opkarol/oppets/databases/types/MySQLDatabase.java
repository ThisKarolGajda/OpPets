package me.opkarol.oppets.databases.types;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import com.zaxxer.hikari.HikariDataSource;
import me.opkarol.oppets.collections.map.OpMap;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.exceptions.Exception;
import me.opkarol.oppets.exceptions.ExceptionLogger;
import me.opkarol.oppets.exceptions.types.InvalidDatabaseException;
import me.opkarol.oppets.interfaces.database.IDatabase;
import me.opkarol.oppets.interfaces.database.IPetsDatabase;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.pets.id.UniquePet;
import me.opkarol.oppets.storage.OpObjects;
import me.opkarol.oppets.utils.OpUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MySQLDatabase implements IPetsDatabase {
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
        List<Pet> petList = database.getIDatabase().getPetsFromDatabase();
        OpMap<UUID, List<Pet>> map = new OpMap<>();
        for (Pet pet : petList) {
            UUID uuid = pet.petUUID.getOwnerUUID();
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
        int petID = pet.petUUID.getDatabaseId();
        if (list.stream().anyMatch(pet1 -> pet1.petUUID.getDatabaseId() == petID)) {
            removeCurrentPet(uuid);
        }
        database.getIDatabase().deletePet(pet);
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
        int petID = pet.petUUID.getDatabaseId();
        database.getIDatabase().insertPet(pet);
        list.removeIf(pet1 -> pet1.petUUID.getDatabaseId() == petID);
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
            database.getIDatabase().asyncInsertPet(pet);
        }
    }

    @Override
    public boolean addPetToPetsList(UUID uuid, Pet pet) {
        if (pet == null) {
            return false;
        }
        List<Pet> petList = getPetList(uuid);
        petList.add(pet);
        database.getIDatabase().asyncInsertPet(pet);
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
                database.getIDatabase().asyncInsertPet(pet);
            } else {
                database.getIDatabase().insertPet(pet);
            }
        }
        petsMap.replace(uuid, list);
    }

    public static class MySQL extends IDatabase {
        private DataSource source;
        private HikariDataSource hikariDataSource;

        @Override
        public IDatabase setupDatabase() {
            FileConfiguration configuration = getPlugin().getConfig();
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(configuration.getString("sql.url"));
            dataSource.setUsername(configuration.getString("sql.user"));
            dataSource.setPassword(configuration.getString("sql.password"));
            dataSource.setMaximumPoolSize(10);
            source = dataSource;
            hikariDataSource = dataSource;
            initDb();
            return this;
        }

        public void closeConnection() {
            if (hikariDataSource != null && !hikariDataSource.isClosed()) {
                hikariDataSource.close();
            }
        }

        private void initDb() {
            try (Connection conn = source.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "CREATE TABLE IF NOT EXISTS oppets (" +
                                 "id INT NOT NULL" +
                                 ", name TEXT NOT NULL" +
                                 ", settings TEXT NOT NULL" +
                                 ", preferences TEXT NOT NULL" +
                                 ", skill TEXT NOT NULL" +
                                 ", experience DOUBLE NOT NULL" +
                                 ", level INT NOT NULL" +
                                 ", type TEXT NOT NULL" +
                                 ", active BOOLEAN NOT NULL" +
                                 ", ownerUUID VARCHAR(36) NOT NULL" +
                                 ", prestige TEXT NOT NULL" +
                                 ", PRIMARY KEY (id));")) {
                stmt.execute();
            } catch (SQLException e) {
                ExceptionLogger.getInstance().addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(getConnectionError())));
            }
        }

        public void insertPet(@NotNull Pet pet) {
            try (Connection conn = source.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO oppets(id, name, settings, preferences, skill, experience, level, type, active, ownerUUID, prestige)" +
                                 " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                                 "ON DUPLICATE KEY UPDATE " +
                                 "name = ?, settings = ?, preferences = ?, skill = ?, experience = ?, level = ?, type = ?, active = ?, ownerUUID = ?, prestige = ?;")) {
                String petName = pet.getPetName();
                String settings = pet.settings.toString();
                String preferences = pet.preferences.toString();
                String skillName = pet.getSkillName();
                double experience = pet.getPetExperience();
                int level = pet.getLevel();
                String type = pet.getPetType().toString();
                boolean active = pet.isActive();
                String ownerUUID = pet.petUUID.getOwnerUUID().toString();
                int petID = pet.petUUID.getDatabaseId();
                String prestige = pet.getPrestige();
                stmt.setInt(1, petID);
                stmt.setString(2, petName);
                stmt.setString(3, settings);
                stmt.setString(4, preferences);
                stmt.setString(5, skillName);
                stmt.setDouble(6, experience);
                stmt.setInt(7, level);
                stmt.setString(8, type);
                stmt.setBoolean(9, active);
                stmt.setString(10, ownerUUID);
                stmt.setString(11, prestige);
                stmt.setString(12, petName);
                stmt.setString(13, settings);
                stmt.setString(14, preferences);
                stmt.setString(15, skillName);
                stmt.setDouble(16, experience);
                stmt.setInt(17, level);
                stmt.setString(18, type);
                stmt.setBoolean(19, active);
                stmt.setString(20, ownerUUID);
                stmt.setString(21, prestige);
                stmt.executeUpdate();
            } catch (SQLException e) {
                ExceptionLogger.getInstance().addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(getConnectionError())));
            }
        }

        public void deletePet(@NotNull Pet pet) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try (Connection conn = source.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(
                                 "DELETE FROM oppets WHERE id = ?;"
                         )) {
                        stmt.setString(1, pet.petUUID.getStringDatabaseId());
                        int updated = stmt.executeUpdate();
                        if (updated != 1) {
                            try {
                                throw new InvalidDatabaseException("Could not find a pet to delete for specific id: " + pet.petUUID.getStringDatabaseId());
                            } catch (InvalidDatabaseException exception) {
                                ExceptionLogger.getInstance().addException(new Exception(exception.getCause().toString(), this.getClass().toString(), exception.fillInStackTrace()));
                            }
                        }
                    } catch (SQLException e) {
                        ExceptionLogger.getInstance().addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(getConnectionError())));
                    }
                }
            }.runTaskAsynchronously(getPlugin());
        }

        @Override
        public List<Pet> getPetsFromDatabase() {
            final List<Pet> pets = new ArrayList<>();
            try (Connection conn = source.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT * FROM oppets;"
                 )) {
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    UUID ownUUID = null;
                    UUID ownerUUID = UUID.fromString(resultSet.getString("ownerUUID"));
                    Pet pet = new Pet(resultSet.getString("name"), resultSet.getDouble("experience"), resultSet.getInt("level"), TypeOfEntity.valueOf(resultSet.getString("type")), resultSet.getBoolean("active"), ownUUID, ownerUUID, resultSet.getString("skill"), resultSet.getString("prestige"), new UniquePet(resultSet.getInt("id"), null, ownerUUID), OpObjects.get(resultSet.getString("preferences")), OpObjects.get(resultSet.getString("settings")));
                    pets.add(pet);
                }
            } catch (SQLException e) {
                ExceptionLogger.getInstance().addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(getConnectionError())));
            }
            return pets;
        }
    }
}

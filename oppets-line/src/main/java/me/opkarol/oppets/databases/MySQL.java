package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import com.zaxxer.hikari.HikariDataSource;
import me.opkarol.oppets.exceptions.Exception;
import me.opkarol.oppets.exceptions.ExceptionLogger;
import me.opkarol.oppets.exceptions.InvalidDatabaseException;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.storage.OpObjects;
import me.opkarol.oppets.uuid.PetUUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySQL {
    private DataSource source;
    private HikariDataSource hikariDataSource;
    private Plugin plugin;
    private final Database database = Database.getInstance();
    private final String NOT_CONNECTED_TO_MYSQL = "Database connection isn't stable, make sure you are connected to a working SQL provider.";

    public MySQL setupMySQL() {
        plugin = database.getPlugin();
        FileConfiguration configuration = plugin.getConfig();
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(configuration.getString("mysql.url"));
        dataSource.setUsername(configuration.getString("mysql.user"));
        dataSource.setPassword(configuration.getString("mysql.password"));
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
            ExceptionLogger.getInstance().addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(NOT_CONNECTED_TO_MYSQL)));
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
            String settings = pet.getSettings().toString();
            String preferences = pet.getPreferences().toString();
            String skillName = pet.getSkillName();
            double experience = pet.getPetExperience();
            int level = pet.getLevel();
            String type = pet.getPetType().toString();
            boolean active = pet.isActive();
            String ownerUUID = pet.getOwnerUUID().toString();
            int petID = pet.getPetUUID().getID();
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
            ExceptionLogger.getInstance().addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(NOT_CONNECTED_TO_MYSQL)));
        }
    }

    public void asyncInsertPet(Pet pet) {
        new BukkitRunnable() {
            boolean b = false;
            @Override
            public void run() {
                if (!b) {
                    insertPet(pet);
                    b = true;
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void deletePet(@NotNull Pet pet) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection conn = source.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                             "DELETE FROM oppets WHERE id = ?;"
                     )) {
                    stmt.setString(1, pet.getPetUUID().getStringID());
                    int updated = stmt.executeUpdate();
                    if (updated != 1) {
                        try {
                            throw new InvalidDatabaseException("Could not find a pet to delete for specific id: " + pet.getPetUUID().getStringID());
                        } catch (InvalidDatabaseException exception) {
                            ExceptionLogger.getInstance().addException(new Exception(exception.getCause().toString(), this.getClass().toString(), exception.fillInStackTrace()));
                        }
                    }
                } catch (SQLException e) {
                    ExceptionLogger.getInstance().addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(NOT_CONNECTED_TO_MYSQL)));
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public @NotNull List<Pet> getPets() {
        final List<Pet> pets = new ArrayList<>();
        try (Connection conn = source.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM oppets;"
             )) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Pet pet = new Pet(resultSet.getString("name"), resultSet.getDouble("experience"), resultSet.getInt("level"), TypeOfEntity.valueOf(resultSet.getString("type")), resultSet.getBoolean("active"), null, UUID.fromString(resultSet.getString("ownerUUID")), resultSet.getString("skill"), resultSet.getString("prestige"), new PetUUID(resultSet.getInt("id")), OpObjects.get(resultSet.getString("preferences")), OpObjects.get(resultSet.getString("settings")));
                pets.add(pet);
            }
        } catch (SQLException e) {
            ExceptionLogger.getInstance().addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(NOT_CONNECTED_TO_MYSQL)));
        }
        return pets;
    }

    @SuppressWarnings("unused")
    public synchronized boolean containsID(int id) {
        final boolean[] supplier = new boolean[1];
        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection conn = source.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                             "SELECT * FROM `oppets` WHERE id=?;"
                     )) {
                    stmt.setString(1, String.valueOf(id));
                    ResultSet resultSet = stmt.executeQuery();
                    supplier[0] = resultSet.next();
                } catch (SQLException e) {
                    ExceptionLogger.getInstance().addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(NOT_CONNECTED_TO_MYSQL)));
                }
            }
        }.runTaskAsynchronously(plugin);
        return supplier[0];
    }
}

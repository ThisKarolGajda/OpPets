package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import com.zaxxer.hikari.HikariDataSource;
import me.opkarol.oppets.pets.OpPetsEntityTypes;
import me.opkarol.oppets.pets.Pet;
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
import java.util.stream.Stream;

/**
 * The type My sql.
 */
public class MySQL {
    /**
     * The constant source.
     */
    private static DataSource source;
    /**
     * The constant hikariDataSource.
     */
    private static HikariDataSource hikariDataSource;
    /**
     * The constant plugin.
     */
    private static Plugin plugin;

    /**
     * Sets my sql.
     */
    public static void setupMySQL() {
        plugin = Database.getInstance();
        FileConfiguration configuration = plugin.getConfig();
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(configuration.getString("mysql.url"));
        dataSource.setUsername(configuration.getString("mysql.user"));
        dataSource.setPassword(configuration.getString("mysql.password"));
        dataSource.setMaximumPoolSize(10);
        source = dataSource;
        hikariDataSource = dataSource;
        try {
            initDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection.
     */
    public static void closeConnection() {
        if (hikariDataSource != null && !hikariDataSource.isClosed()) {
            hikariDataSource.close();
        }
    }

    /**
     * Log sql error.
     *
     * @param error the error
     */
    private static void logSQLError(String error) {
        plugin.getLogger().warning(error);
    }

    /**
     * Init db.
     *
     * @throws SQLException the sql exception
     */
    private static void initDb() throws SQLException {
        try (Connection conn = source.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS oppets (" +
                             "id INT NOT NULL" +
                             ", name TINYTEXT NOT NULL" +
                             ", settings TINYTEXT NOT NULL" +
                             ", skill TINYTEXT NOT NULL" +
                             ", experience DOUBLE NOT NULL" +
                             ", level SMALLINT NOT NULL" +
                             ", type TINYTEXT NOT NULL" +
                             ", active BOOLEAN NOT NULL" +
                             ", ownerUUID VARCHAR(36) NOT NULL" +
                             ", prestige TINYTEXT NOT NULL" +
                             ", PRIMARY KEY (id));")) {
            stmt.execute();
        }
    }

    /**
     * Insert pet.
     *
     * @param pet the pet
     * @throws SQLException the sql exception
     */
    public static void insertPet(@NotNull Pet pet) throws SQLException {
        try (Connection conn = source.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO oppets(id, name, settings, skill, experience, level, type, active, ownerUUID, prestige) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, settings = ?, skill = ?, experience = ?, level = ?, type = ?, active = ?, ownerUUID = ?, prestige = ?;")) {
            String petName = pet.getPetName();
            String settings = pet.getSettingsSerialized();
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
            stmt.setString(4, skillName);
            stmt.setDouble(5, experience);
            stmt.setInt(6, level);
            stmt.setString(7, type);
            stmt.setBoolean(8, active);
            stmt.setString(9, ownerUUID);
            stmt.setString(10, prestige);
            stmt.setString(11, petName);
            stmt.setString(12, settings);
            stmt.setString(13, skillName);
            stmt.setDouble(14, experience);
            stmt.setInt(15, level);
            stmt.setString(16, type);
            stmt.setBoolean(17, active);
            stmt.setString(18, ownerUUID);
            stmt.setString(19, prestige);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logSQLError("Could not insert pet " + e.getLocalizedMessage());
        }
    }

    /**
     * Async insert pet.
     *
     * @param pet the pet
     */
    public static void asyncInsertPet(Pet pet) {
        new BukkitRunnable() {
            boolean b = false;
            @Override
            public void run() {
                try {
                    if (!b) {
                        insertPet(pet);
                        b = true;
                    }
                } catch (SQLException e) {
                    logSQLError("Error while trying to save pet from UUID (asyncInsertPet)");
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    /**
     * Delete pet.
     *
     * @param pet the pet
     * @throws SQLException the sql exception
     */
    public static void deletePet(@NotNull Pet pet) throws SQLException {
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
                        throw new SQLException("Could not find a pet to delete for specific id: " + pet.getPetUUID().getStringID());
                    }
                } catch (SQLException e) {
                    logSQLError("Could not delete pet of player. " + e.getLocalizedMessage());
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    /**
     * Gets pets.
     *
     * @return the pets
     */
    public static @NotNull List<Pet> getPets() {
        final List<Pet> pets = new ArrayList<>();
        try (Connection conn = source.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM oppets;"
             )) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Pet pet = new Pet(resultSet.getString("name"), resultSet.getDouble("experience"), resultSet.getInt("level"), OpPetsEntityTypes.TypeOfEntity.valueOf(resultSet.getString("type")), resultSet.getBoolean("active"), null, UUID.fromString(resultSet.getString("ownerUUID")), resultSet.getString("settings"), resultSet.getString("skill"), resultSet.getString("prestige"), new PetUUID(resultSet.getInt("id")));
                Stream.of(pet).forEachOrdered(pets::add);
            }
        } catch (SQLException e) {
            logSQLError("Could not fetch all player pets " + e.getLocalizedMessage());
        }
        return pets;
    }

    /**
     * Contains id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @SuppressWarnings("unused")
    @Deprecated
    public static boolean containsID(int id) {
        try (Connection conn = source.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM `oppets` WHERE id=?;"
             )) {
            stmt.setString(1, String.valueOf(id));
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logSQLError("Could not check player pet " + e.getLocalizedMessage());
        }
        return false;
    }
}

package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.interfaces.IDatabase;
import me.opkarol.oppets.interfaces.IOpPets;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * The type Database.
 */
public class Database {
    /**
     * The constant mySQLAccess.
     */
    public static boolean mySQLAccess = false;
    /**
     * The constant instance.
     */
    private static Plugin instance;
    /**
     * The constant database.
     */
    private static IDatabase database;
    /**
     * The constant utils.
     */
    private static IUtils utils;
    /**
     * The constant petsDatabase.
     */
    private static PetsDatabase petsDatabase;
    /**
     * The constant opPets.
     */
    private static IOpPets opPets;
    /**
     * The constant lastID.
     */
    private static int lastID;

    /**
     * Sets database.
     */
    public static void setupDatabase() {
        mySQLAccess = instance.getConfig().getBoolean("mysql.enabled");
        if (mySQLAccess) {
            MySQL.setupMySQL();
            setDatabase(new MySQLDatabase());
            setupLastID();
        } else {
            setDatabase(new MiniPetsDatabase());
        }
        database.startLogic();
        petsDatabase = new PetsDatabase();
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public static IDatabase getDatabase() {
        return database;
    }

    /**
     * Sets database.
     *
     * @param database the database
     */
    public static void setDatabase(IDatabase database) {
        Database.database = database;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Plugin getInstance() {
        return instance;
    }

    /**
     * Sets instance.
     *
     * @param instance the instance
     */
    public static void setInstance(Plugin instance) {
        Database.instance = instance;
        setupDatabase();
    }

    /**
     * Gets utils.
     *
     * @return the utils
     */
    public static IUtils getUtils() {
        return utils;
    }

    /**
     * Sets utils.
     *
     * @param utils the utils
     */
    public static void setUtils(IUtils utils) {
        Database.utils = utils;
    }

    /**
     * Gets pets database.
     *
     * @return the pets database
     */
    public static PetsDatabase getPetsDatabase() {
        return petsDatabase;
    }

    /**
     * Gets op pets.
     *
     * @return the op pets
     */
    public static IOpPets getOpPets() {
        return opPets;
    }

    /**
     * Sets op pets.
     *
     * @param opPetsInterface the op pets interface
     */
    public static void setOpPets(IOpPets opPetsInterface) {
        opPets = opPetsInterface;
    }

    /**
     * Sets last id.
     */
    private static void setupLastID() {
        new BukkitRunnable() {
            int largestID = 0;
            @Override
            public void run() {
                for (List<Pet> list : getDatabase().getPetsMap().values()) {
                    for (Pet pet : list) {
                        int id = pet.getPetUUID().getID();
                        if (id > largestID) {
                            largestID = id;
                        }
                    }
                }
                lastID = largestID;
            }
        }.runTaskAsynchronously(getInstance());

    }

    /**
     * Gets last id.
     *
     * @return the last id
     */
    public static int getLastID() {
        return lastID;
    }

    /**
     * Sets last id.
     *
     * @param lastID the last id
     */
    public static void setLastID(int lastID) {
        Database.lastID = lastID;
    }
}

package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.LastIDCache;
import me.opkarol.oppets.interfaces.IDatabase;
import me.opkarol.oppets.interfaces.IOpPets;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.misc.SessionIdentifier;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The type Database.
 */
public class Database {
    /**
     * The constant database.
     */
    protected static Database database;

    /**
     * The My sql access.
     */
    public boolean mySQLAccess = false;
    /**
     * The Instance.
     */
    private final Plugin instance;
    /**
     * The Op pets.
     */
    private IOpPets opPets;
    /**
     * The Utils.
     */
    private IUtils utils;
    /**
     * The Database.
     */
    private IDatabase iDatabase;
    /**
     * The Pets database.
     */
    private PetsDatabase petsDatabase;
    /**
     * The My sql.
     */
    private MySQL mySQL;
    /**
     * The Cache.
     */
    private LastIDCache cache;
    /**
     * The Api database.
     */
    private APIDatabase apiDatabase;
    /**
     * The Session identifier.
     */
    private final SessionIdentifier sessionIdentifier;

    /**
     * Instantiates a new Database.
     *
     * @param plugin            the plugin
     * @param sessionIdentifier the session identifier
     */
    public Database(Plugin plugin, SessionIdentifier sessionIdentifier) {
        database = this;
        this.sessionIdentifier = sessionIdentifier;
        this.instance = plugin;
        setupDatabase();
    }

    /**
     * Sets database.
     */
    public void setupDatabase() {
        mySQLAccess = instance.getConfig().getBoolean("mysql.enabled");
        apiDatabase = new APIDatabase(this);
        if (mySQLAccess) {
            cache = new LastIDCache(this);
            mySQL = new MySQL().setupMySQL();
            setDatabase(new MySQLDatabase());
        } else {
            setDatabase(new MiniPetsDatabase());
        }
        iDatabase.startLogic();
        petsDatabase = new PetsDatabase();
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public IDatabase getDatabase() {
        return iDatabase;
    }

    /**
     * Sets database.
     *
     * @param database the database
     */
    public void setDatabase(IDatabase database) {
        this.iDatabase = database;
    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public Plugin getPlugin() {
        return instance;
    }

    /**
     * Gets utils.
     *
     * @return the utils
     */
    public IUtils getUtils() {
        return utils;
    }

    /**
     * Gets pets database.
     *
     * @return the pets database
     */
    public PetsDatabase getPetsDatabase() {
        return petsDatabase;
    }

    /**
     * Gets op pets.
     *
     * @return the op pets
     */
    public IOpPets getOpPets() {
        return opPets;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    protected static Database getInstance() {
        return database;
    }

    /**
     * Gets cache.
     *
     * @return the cache
     */
    public LastIDCache getCache() {
        return cache;
    }

    /**
     * Gets instance.
     *
     * @param session the session
     * @return the instance
     */
    public static Database getInstance(String session) {
        if (!database.sessionIdentifier.getSession().equals(session)) {
            return new Database(database.getPlugin(), null);
        }
        return database;
    }

    /**
     * Gets my sql.
     *
     * @return the my sql
     */
    public MySQL getMySQL() {
        return mySQL;
    }

    /**
     * Sets op pets.
     *
     * @param opPets the op pets
     */
    public void setOpPets(@NotNull IOpPets opPets) {
        this.opPets = opPets;
        this.utils = opPets.getUtils();
    }

    /**
     * Gets api database.
     *
     * @return the api database
     */
    public APIDatabase getAPIDatabase() {
        return apiDatabase;
    }
}

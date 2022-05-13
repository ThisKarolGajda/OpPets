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
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class Database {
    protected static Database database;

    public boolean mySQLAccess = false;
    private final Plugin instance;
    private IOpPets opPets;
    private IUtils utils;
    private IDatabase iDatabase;
    private PetsDatabase petsDatabase;
    private MySQL mySQL;
    private LastIDCache cache;
    private APIDatabase apiDatabase;

    public Database(Plugin plugin) {
        database = this;
        this.instance = plugin;
        setupDatabase();
    }

    public void setupDatabase() {
        mySQLAccess = instance.getConfig().getBoolean("mysql.enabled");
        cache = new LastIDCache(this);
        if (mySQLAccess) {
            mySQL = new MySQL().setupMySQL();
            setDatabase(new MySQLDatabase());
        } else {
            setDatabase(new MiniPetsDatabase());
            sendMultiMessageWarning(Arrays.asList("SQL database isn't enabled.", "Please enable it to get the best experience from OpPets plugin.", "Current database could have a problems with performance.", "For more information visit this page: https://github.com/ThisKarolGajda/OpPets/wiki/"));
        }
        apiDatabase = new APIDatabase(this);
        iDatabase.startLogic();
        petsDatabase = new PetsDatabase();
    }

    public IDatabase getDatabase() {
        return iDatabase;
    }

    public void setDatabase(IDatabase database) {
        this.iDatabase = database;
    }

    public Plugin getPlugin() {
        return instance;
    }

    public IUtils getUtils() {
        return utils;
    }

    public PetsDatabase getPetsDatabase() {
        return petsDatabase;
    }

    public IOpPets getOpPets() {
        return opPets;
    }

    public LastIDCache getCache() {
        return cache;
    }

    public static Database getInstance() {
        return database;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public void setOpPets(@NotNull IOpPets opPets) {
        this.opPets = opPets;
        this.utils = opPets.getUtils();
    }

    public APIDatabase getAPIDatabase() {
        return apiDatabase;
    }

    public void sendMultiMessageWarning(List<String> messages) {
        Logger logger = getPlugin().getLogger();
        if (messages == null) {
            return;
        }
        messages.forEach(logger::warning);
    }

    public void hideEntity(@NotNull Player playerFrom) {
        UUID uuid = playerFrom.getUniqueId();
        int id = database.getDatabase().getIdPet(uuid);
        if (id == 0) {
            return;
        }
        for (int entityID : database.getDatabase().getPetIDs()) {
            if (id != entityID) {
                database.getUtils().hideEntityFromPlayer(playerFrom, entityID);
            }
        }
    }
}

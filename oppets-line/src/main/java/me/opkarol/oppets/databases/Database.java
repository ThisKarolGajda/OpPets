package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.LastIDCache;
import me.opkarol.oppets.databases.external.APIDatabase;
import me.opkarol.oppets.databases.external.PetsDatabase;
import me.opkarol.oppets.databases.types.FlatDatabase;
import me.opkarol.oppets.databases.types.MySQLDatabase;
import me.opkarol.oppets.interfaces.IOpPets;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.interfaces.database.IDatabase;
import me.opkarol.oppets.interfaces.database.IPetsDatabase;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

public class Database {
    protected static Database database;
    public boolean mySQLAccess = false;
    private String sqlFormat = "";
    private final Plugin instance;
    private IOpPets opPets;
    private IPetsDatabase iPetsDatabase;
    private IDatabase iDatabase;
    private LastIDCache cache;
    private APIDatabase apiDatabase;

    public Database(Plugin plugin) {
        database = this;
        this.instance = plugin;
        setupDatabase();
    }

    public void setupDatabase() {
        mySQLAccess = instance.getConfig().getBoolean("sql.enabled");
        cache = new LastIDCache(this);
        switch (sqlFormat) {
            case "mysql": {
                iDatabase = new MySQLDatabase.MySQL().setupDatabase();
                setDatabase(new MySQLDatabase());
                break;
            }
            case "flat": {
                setDatabase(new FlatDatabase());
                sendMultiMessageWarning("SQL database isn't enabled.", "Please enable it to get the best experience from OpPets plugin.", "Current database could have a problems with performance.", "For more information visit this page: https://github.com/ThisKarolGajda/OpPets/wiki/");
                break;
            }
        }
        apiDatabase = new APIDatabase(this);
        iPetsDatabase.startLogic();
    }

    public IPetsDatabase getDatabase() {
        return iPetsDatabase;
    }

    public void setDatabase(IPetsDatabase database) {
        this.iPetsDatabase = database;
    }

    public Plugin getPlugin() {
        return instance;
    }

    public IUtils getUtils() {
        return opPets.getUtils();
    }

    public PetsDatabase getPetsDatabase() {
        return opPets.getPetsDatabase();
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

    public IDatabase getIDatabase() {
        return iDatabase;
    }

    public void setOpPets(@NotNull IOpPets opPets) {
        this.opPets = opPets;
    }

    public APIDatabase getAPIDatabase() {
        return apiDatabase;
    }

    public void sendMultiMessageWarning(String... messages) {
        Logger logger = getPlugin().getLogger();
        if (messages == null) {
            return;
        }
        Arrays.stream(messages).forEach(logger::warning);
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

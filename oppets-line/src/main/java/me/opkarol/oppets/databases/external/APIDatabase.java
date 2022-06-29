package me.opkarol.oppets.databases.external;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.entities.manager.IEntityManager;
import me.opkarol.oppets.boosters.BoosterProvider;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.eggs.EggManager;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import org.bukkit.plugin.Plugin;

public class APIDatabase {
    protected Database database;
    private static APIDatabase apiDatabase;

    public APIDatabase(Database database) {
        apiDatabase = this;
        this.database = database;
    }

    public PetsDatabase getPetsDatabase() {
        return database.getPetsDatabase();
    }

    public int getLastID() {
        return database.getCache().getLastID();
    }

    public void setLastID(int lastID) {
        database.getCache().setLastID(lastID);
    }

    public BoosterProvider getBoosterProvider() {
        return database.getOpPets().getBoosterProvider();
    }

    public LeaderboardCounter getLeaderboard() {
        return database.getOpPets().getLeaderboard();
    }

    public Plugin getPlugin() {
        return database.getPlugin();
    }

    public IUtils getUtils() {
        return database.getUtils();
    }

    public IEntityManager getEntityManager() {
        return database.getOpPets().getEntityManager();
    }

    public EggManager getEggManager() {
        return database.getOpPets().getEggManager();
    }

    public static APIDatabase getInstance() {
        return apiDatabase;
    }
}
package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.addons.AddonManager;
import me.opkarol.oppets.boosters.BoosterProvider;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import me.opkarol.oppets.skills.SkillDatabase;
import me.opkarol.oppets.utils.OpUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * The type Api database.
 */
public class APIDatabase {
    /**
     * The Database.
     */
    protected Database database;
    /**
     * The constant apiDatabase.
     */
    private static APIDatabase apiDatabase;

    /**
     * Instantiates a new Api database.
     *
     * @param database the database
     */
    public APIDatabase(Database database) {
        apiDatabase = this;
        this.database = database;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        AddonManager.setDatabase(database);
        PetsUtils.setDatabase(database);
        OpUtils.setDatabase(database);
    }

    /**
     * Gets pets database.
     *
     * @return the pets database
     */
    public PetsDatabase getPetsDatabase() {
        return database.getPetsDatabase();
    }

    /**
     * Gets last id.
     *
     * @return the last id
     */
    public int getLastID() {
        return database.getCache().getLastID();
    }

    /**
     * Sets last id.
     *
     * @param lastID the last id
     */
    public void setLastID(int lastID) {
        database.getCache().setLastID(lastID);
    }

    /**
     * Gets skill database.
     *
     * @return the skill database
     */
    public SkillDatabase getSkillDatabase() {
        return database.getOpPets().getSkillDatabase();
    }

    /**
     * Gets booster provider.
     *
     * @return the booster provider
     */
    public BoosterProvider getBoosterProvider() {
        return database.getOpPets().getBoosterProvider();
    }

    /**
     * Gets leaderboard.
     *
     * @return the leaderboard
     */
    public LeaderboardCounter getLeaderboard() {
        return database.getOpPets().getLeaderboard();
    }

    /**
     * Gets data folder.
     *
     * @return the data folder
     */
    public File getDataFolder() {
        return database.getPlugin().getDataFolder();
    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public Plugin getPlugin() {
        return database.getPlugin();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static APIDatabase getInstance() {
        return apiDatabase;
    }
}
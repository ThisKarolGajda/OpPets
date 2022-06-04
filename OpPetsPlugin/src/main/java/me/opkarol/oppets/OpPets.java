package me.opkarol.oppets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.abilities.AbilitiesDatabase;
import me.opkarol.oppets.boosters.BoosterProvider;
import me.opkarol.oppets.broadcasts.BroadcastManager;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.databases.external.PetsDatabase;
import me.opkarol.oppets.eggs.EggManager;
import me.opkarol.oppets.entities.manager.IEntityManager;
import me.opkarol.oppets.interfaces.IOpPets;
import me.opkarol.oppets.interfaces.database.IPetsDatabase;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.databases.external.SkillDatabase;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class OpPets extends JavaPlugin implements IOpPets {
    private static OpPets opPets;
    private Database database;
    private PetPluginController controller;
    private IEntityManager entityManager;
    private IUtils utils;
    private SkillDatabase skillDatabase;
    private PrestigeManager prestigeManager;
    private PetsDatabase petsDatabase;
    private AbilitiesDatabase abilitiesDatabase;
    private BoosterProvider boosterProvider;
    private LeaderboardCounter leaderboardCounter;
    private BroadcastManager broadcastManager;
    private EggManager eggManager;

    @Override
    public void onEnable() {
        opPets = this;
        database = new Database(opPets);
        database.setOpPets(opPets);
        petsDatabase = new PetsDatabase();
        abilitiesDatabase = new AbilitiesDatabase();
        eggManager = new EggManager();
        controller = new PetPluginController(opPets);
        skillDatabase = new SkillDatabase();
        this.setEnabled(controller.setupVersion());
        boosterProvider = new BoosterProvider(database);
        prestigeManager = new PrestigeManager();
        leaderboardCounter = new LeaderboardCounter(database);
        broadcastManager = new BroadcastManager();
        database.setOpPets(this);
        controller.registerEvents();
    }

    @Override
    public void onDisable() {
        controller.saveFiles();
        if (database != null && database.getIDatabase() != null) {
            database.getIDatabase().closeConnection();
        }
        database = null;
        opPets = null;
        controller = null;
        skillDatabase = null;
        entityManager = null;
        utils = null;
        prestigeManager = null;
        boosterProvider = null;
    }

    public void disablePlugin(String reason) {
        this.getLogger().log(Level.SEVERE, reason);
        this.setEnabled(false);
    }

    public void setEntityManager(IEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setUtils(IUtils utils) {
        this.utils = utils;
    }

    public static OpPets getInstance() {
        return opPets;
    }

    public IPetsDatabase getDatabase() {
        return database.getDatabase();
    }

    @Override
    public IEntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public IUtils getUtils() {
        return utils;
    }

    @Override
    public SkillDatabase getSkillDatabase() {
        return skillDatabase;
    }

    @Override
    public PrestigeManager getPrestigeManager() {
        return prestigeManager;
    }

    @Override
    public PetsDatabase getPetsDatabase() {
        return petsDatabase;
    }

    @Override
    public AbilitiesDatabase getAbilitiesDatabase() {
        return abilitiesDatabase;
    }

    @Override
    public BoosterProvider getBoosterProvider() {
        return boosterProvider;
    }

    @Override
    public LeaderboardCounter getLeaderboard() {
        return leaderboardCounter;
    }

    @Override
    public BroadcastManager getBroadcastManager() {
        return broadcastManager;
    }

    @Override
    public Object getEconomy() {
        return controller.setupEconomy();
    }

    @Override
    public EggManager getEggManager() {
        return eggManager;
    }
}
//TODO: panel for oppets

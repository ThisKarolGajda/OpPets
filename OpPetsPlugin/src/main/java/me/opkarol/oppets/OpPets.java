package me.opkarol.oppets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.databases.PetsDatabase;
import dir.interfaces.*;
import dir.prestiges.PrestigeManager;
import dir.abilities.AbilitiesDatabase;
import dir.boosters.BoosterProvider;
import dir.broadcasts.BroadcastManager;
import dir.files.Messages;
import dir.leaderboards.LeaderboardCounter;
import dir.skills.SkillDatabase;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * OpPets main Class which extends JavaPlugin.
 * Used as a getter and setter for all classes objects.
 *
 * Overrides main methods of JavaPlugin - onEnable and onDisable
 * creating custom methods which sets private static objects.
 *
 * This class also contains disablePlugin method
 * which can be used to disable OpPets functionality.
 */

public final class OpPets extends JavaPlugin implements IOpPets {
    private static OpPets opPets;
    private static PetPluginController controller;
    private static IBabyEntityCreator creator;
    private static IEntityManager entityManager;
    private static IUtils utils;
    private static SkillDatabase skillDatabase;
    private static PrestigeManager prestigeManager;
    private static Messages messages;
    private static PetsDatabase petsDatabase;
    private static AbilitiesDatabase abilitiesDatabase;
    //private static Economy economy;
    private static BoosterProvider boosterProvider;
    private static LeaderboardCounter leaderboard;
    private static BroadcastManager broadcastManager;

    @Override
    public IEntityManager getEntityManager() {
        return entityManager;
    }
    public static void setEntityManager(IEntityManager entityManager) {
        OpPets.entityManager = entityManager;
    }
    @Override
    public IUtils getUtils() {
        return utils;
    }
    public static void setUtils(IUtils utils) {
        OpPets.utils = utils;
    }
    public PetPluginController getController() {
        return controller;
    }
    public OpPets getInstance() {
        return opPets;
    }
    @Override
    public IBabyEntityCreator getCreator() {
        return creator;
    }
    public static void setCreator(IBabyEntityCreator creator2) {
        OpPets.creator = creator2;
    }
    public IDatabase getDatabase() {
        return Database.getDatabase();
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
        return leaderboard;
    }
    @Override
    public BroadcastManager getBroadcastManager() {
        return broadcastManager;
    }

    @Override
    public void onEnable() {
        opPets = this;
        messages = new Messages().onEnable();
        Database.setInstance(this, opPets);
        petsDatabase = new PetsDatabase();
        abilitiesDatabase = new AbilitiesDatabase();
        controller = new PetPluginController(opPets);
        skillDatabase = new SkillDatabase();
        if (!skillDatabase.isCanRun()) {
            disablePlugin("Config file is invalid!");
        }
        this.setEnabled(controller.setupVersion());
        prestigeManager = new PrestigeManager();
        //economy = controller.setupEconomy();
        boosterProvider = new BoosterProvider();
        leaderboard = new LeaderboardCounter();
        broadcastManager = new BroadcastManager();
    }

    @Override
    public void onDisable() {
        controller.saveFiles();
        opPets = null;
        creator = null;
        controller = null;
        skillDatabase = null;
        entityManager = null;
        utils = null;
        messages = null;
        prestigeManager = null;
        //economy = null;
        boosterProvider = null;
    }

    //public static Economy getEconomy() {
    //return economy;
    //}

    /**
     * Method that informs a server with provided reason about
     * OpPets`s shutdown and logs it as a class method.
     *
     * @param reason string value is a message shown in the server's console
     */
    public void disablePlugin(String reason) {
        this.getLogger().info(reason);
        this.setEnabled(false);
    }

}
//TODO: panel for oppets, addons (Discord integration panel), mysql working

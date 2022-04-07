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
import me.opkarol.oppets.databases.MySQL;
import me.opkarol.oppets.databases.PetsDatabase;
import me.opkarol.oppets.files.MessagesFile;
import me.opkarol.oppets.interfaces.*;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.skills.SkillDatabase;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * OpPets main Class which extends JavaPlugin.
 * Used as a getter and setter for all classes objects.
 * <p>
 * Overrides main methods of JavaPlugin - onEnable and onDisable
 * creating custom methods which sets private static objects.
 * <p>
 * This class also contains disablePlugin method
 * which can be used to disable OpPets functionality.
 */
public final class OpPets extends JavaPlugin implements IOpPets {
    /**
     * The constant opPets.
     */
    private static OpPets opPets;
    /**
     * The constant controller.
     */
    private static PetPluginController controller;
    /**
     * The constant creator.
     */
    private static IBabyEntityCreator creator;
    /**
     * The constant entityManager.
     */
    private static IEntityManager entityManager;
    /**
     * The constant utils.
     */
    private static IUtils utils;
    /**
     * The constant skillDatabase.
     */
    private static SkillDatabase skillDatabase;
    /**
     * The constant prestigeManager.
     */
    private static PrestigeManager prestigeManager;
    /**
     * The constant messages.
     */
    private static MessagesFile messages;
    /**
     * The constant petsDatabase.
     */
    private static PetsDatabase petsDatabase;
    /**
     * The constant abilitiesDatabase.
     */
    private static AbilitiesDatabase abilitiesDatabase;
    /**
     * The constant boosterProvider.
     */
    private static Economy economy;
    /**
     * The constant boosterProvider.
     */
    private static BoosterProvider boosterProvider;
    /**
     * The constant leaderboard.
     */
    private static LeaderboardCounter leaderboard;
    /**
     * The constant broadcastManager.
     */
    private static BroadcastManager broadcastManager;

    /**
     * Gets messages.
     *
     * @return the messages
     */
    @Override
    public MessagesFile getMessages() {
        return messages;
    }

    /**
     * Gets entity manager.
     *
     * @return the entity manager
     */
    @Override
    public IEntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Sets entity manager.
     *
     * @param entityManager the entity manager
     */
    public static void setEntityManager(IEntityManager entityManager) {
        OpPets.entityManager = entityManager;
    }

    /**
     * Gets utils.
     *
     * @return the utils
     */
    @Override
    public IUtils getUtils() {
        return utils;
    }

    /**
     * Sets utils.
     *
     * @param utils the utils
     */
    public static void setUtils(IUtils utils) {
        OpPets.utils = utils;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public OpPets getInstance() {
        return opPets;
    }

    /**
     * Gets creator.
     *
     * @return the creator
     */
    @Override
    public IBabyEntityCreator getCreator() {
        return creator;
    }

    /**
     * Sets creator.
     *
     * @param creator2 the creator 2
     */
    public static void setCreator(IBabyEntityCreator creator2) {
        OpPets.creator = creator2;
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public IDatabase getDatabase() {
        return Database.getDatabase();
    }

    /**
     * Gets skill database.
     *
     * @return the skill database
     */
    @Override
    public SkillDatabase getSkillDatabase() {
        return skillDatabase;
    }

    /**
     * Gets prestige manager.
     *
     * @return the prestige manager
     */
    @Override
    public PrestigeManager getPrestigeManager() {
        return prestigeManager;
    }

    /**
     * Gets pets database.
     *
     * @return the pets database
     */
    @Override
    public PetsDatabase getPetsDatabase() {
        return petsDatabase;
    }

    /**
     * Gets abilities database.
     *
     * @return the abilities database
     */
    @Override
    public AbilitiesDatabase getAbilitiesDatabase() {
        return abilitiesDatabase;
    }

    /**
     * Gets booster provider.
     *
     * @return the booster provider
     */
    @Override
    public BoosterProvider getBoosterProvider() {
        return boosterProvider;
    }

    /**
     * Gets leaderboard.
     *
     * @return the leaderboard
     */
    @Override
    public LeaderboardCounter getLeaderboard() {
        return leaderboard;
    }

    /**
     * Gets broadcast manager.
     *
     * @return the broadcast manager
     */
    @Override
    public BroadcastManager getBroadcastManager() {
        return broadcastManager;
    }

    /**
     * On enable.
     */
    @Override
    public void onEnable() {
        opPets = this;
        Database.setInstance(opPets);
        Database.setOpPets(opPets);
        messages = new MessagesFile();
        petsDatabase = new PetsDatabase();
        abilitiesDatabase = new AbilitiesDatabase();
        controller = new PetPluginController(opPets);
        skillDatabase = new SkillDatabase();
        if (!skillDatabase.isCanRun()) {
            disablePlugin("Config file is invalid!");
        }
        this.setEnabled(controller.setupVersion());
        prestigeManager = new PrestigeManager();
        economy = controller.setupEconomy();
        boosterProvider = new BoosterProvider();
        leaderboard = new LeaderboardCounter();
        broadcastManager = new BroadcastManager();
        Database.setOpPets(opPets);
    }

    /**
     * On disable.
     */
    @Override
    public void onDisable() {
        controller.saveFiles();
        MySQL.closeConnection();
        opPets = null;
        creator = null;
        controller = null;
        skillDatabase = null;
        entityManager = null;
        utils = null;
        messages = null;
        prestigeManager = null;
        economy = null;
        boosterProvider = null;
    }

    /**
     * Gets economy.
     *
     * @return the economy
     */
    @Override
    public Economy getEconomy() {
        return economy;
    }

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
//TODO: panel for oppets

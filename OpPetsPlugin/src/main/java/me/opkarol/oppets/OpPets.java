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
import me.opkarol.oppets.databases.PetsDatabase;
import me.opkarol.oppets.files.MessagesFile;
import me.opkarol.oppets.interfaces.*;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import me.opkarol.oppets.misc.SessionIdentifier;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.skills.SkillDatabase;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The type Op pets.
 */
public final class OpPets extends JavaPlugin implements IOpPets {
    /**
     * The constant opPets.
     */
    private static OpPets opPets;
    /**
     * The Database.
     */
    private Database database;
    /**
     * The Controller.
     */
    private PetPluginController controller;
    /**
     * The Creator.
     */
    private IBabyEntityCreator creator;
    /**
     * The Entity manager.
     */
    private IEntityManager entityManager;
    /**
     * The Utils.
     */
    private IUtils utils;
    /**
     * The Messages.
     */
    private MessagesFile messages;
    /**
     * The Skill database.
     */
    private SkillDatabase skillDatabase;
    /**
     * The Prestige manager.
     */
    private PrestigeManager prestigeManager;
    /**
     * The Pets database.
     */
    private PetsDatabase petsDatabase;
    /**
     * The Abilities database.
     */
    private AbilitiesDatabase abilitiesDatabase;
    /**
     * The Booster provider.
     */
    private BoosterProvider boosterProvider;
    /**
     * The Leaderboard counter.
     */
    private LeaderboardCounter leaderboardCounter;
    /**
     * The Broadcast manager.
     */
    private BroadcastManager broadcastManager;
    /**
     * The Session identifier.
     */
    private SessionIdentifier sessionIdentifier;

    /**
     * On enable.
     */
    @Override
    public void onEnable() {
        opPets = this;
        sessionIdentifier = new SessionIdentifier();
        database = new Database(opPets, sessionIdentifier);
        database.setOpPets(opPets);
        messages = new MessagesFile();
        petsDatabase = new PetsDatabase();
        abilitiesDatabase = new AbilitiesDatabase();
        controller = new PetPluginController(opPets);
        skillDatabase = new SkillDatabase();
        this.setEnabled(controller.setupVersion());
        boosterProvider = new BoosterProvider(database);
        prestigeManager = new PrestigeManager();
        leaderboardCounter = new LeaderboardCounter(database);
        broadcastManager = new BroadcastManager();
        controller.registerEvents();
    }

    /**
     * On disable.
     */
    @Override
    public void onDisable() {
        controller.saveFiles();
        database.getMySQL().closeConnection();
        opPets = null;
        creator = null;
        controller = null;
        skillDatabase = null;
        entityManager = null;
        utils = null;
        messages = null;
        prestigeManager = null;
        boosterProvider = null;
    }

    /**
     * Disable plugin.
     *
     * @param reason the reason
     */
    public void disablePlugin(String reason) {
        this.getLogger().info(reason);
        this.setEnabled(false);
    }

    /**
     * Sets entity manager.
     *
     * @param entityManager the entity manager
     */
    public void setEntityManager(IEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Sets utils.
     *
     * @param utils the utils
     */
    public void setUtils(IUtils utils) {
        this.utils = utils;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static OpPets getInstance() {
        return opPets;
    }

    /**
     * Sets creator.
     *
     * @param creator2 the creator 2
     */
    public void setCreator(IBabyEntityCreator creator2) {
        this.creator = creator2;
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public IDatabase getDatabase() {
        return database.getDatabase();
    }

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
     * Gets utils.
     *
     * @return the utils
     */
    @Override
    public IUtils getUtils() {
        return utils;
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
        return leaderboardCounter;
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
     * Gets economy.
     *
     * @return the economy
     */
    @Override
    public Object getEconomy() {
        return controller.setupEconomy();
    }

    /**
     * Gets session identifier.
     *
     * @return the session identifier
     */
    public SessionIdentifier getSessionIdentifier() {
        return sessionIdentifier;
    }
}
//TODO: panel for oppets

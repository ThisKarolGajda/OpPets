package me.opkarol.oppets.interfaces;

import me.opkarol.oppets.abilities.AbilitiesDatabase;
import me.opkarol.oppets.boosters.BoosterProvider;
import me.opkarol.oppets.broadcasts.BroadcastManager;
import me.opkarol.oppets.databases.PetsDatabase;
import me.opkarol.oppets.files.MessagesFile;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.skills.SkillDatabase;

/**
 * The interface Op pets.
 */
public interface IOpPets {
    /**
     * Gets messages.
     *
     * @return the messages
     */
    MessagesFile getMessages();

    /**
     * Gets entity manager.
     *
     * @return the entity manager
     */
    IEntityManager getEntityManager();

    /**
     * Gets utils.
     *
     * @return the utils
     */
    IUtils getUtils();

    /**
     * Gets creator.
     *
     * @return the creator
     */
    IBabyEntityCreator getCreator();

    /**
     * Gets skill database.
     *
     * @return the skill database
     */
    SkillDatabase getSkillDatabase();

    /**
     * Gets prestige manager.
     *
     * @return the prestige manager
     */
    PrestigeManager getPrestigeManager();

    /**
     * Gets pets database.
     *
     * @return the pets database
     */
    PetsDatabase getPetsDatabase();

    /**
     * Gets abilities database.
     *
     * @return the abilities database
     */
    AbilitiesDatabase getAbilitiesDatabase();

    /**
     * Gets booster provider.
     *
     * @return the booster provider
     */
    BoosterProvider getBoosterProvider();

    /**
     * Gets leaderboard.
     *
     * @return the leaderboard
     */
    LeaderboardCounter getLeaderboard();

    /**
     * Gets broadcast manager.
     *
     * @return the broadcast manager
     */
    BroadcastManager getBroadcastManager();

    /**
     * Gets economy.
     *
     * @return the economy
     */
    Object getEconomy();
}

package me.opkarol.oppets.interfaces;

import me.opkarol.oppets.abilities.AbilitiesDatabase;
import me.opkarol.oppets.boosters.BoosterProvider;
import me.opkarol.oppets.broadcasts.BroadcastManager;
import me.opkarol.oppets.databases.PetsDatabase;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.skills.SkillDatabase;

public interface IOpPets {
    IEntityManager getEntityManager();

    IUtils getUtils();

    IBabyEntityCreator getCreator();

    IDatabase getDatabase();

    SkillDatabase getSkillDatabase();

    PrestigeManager getPrestigeManager();

    PetsDatabase getPetsDatabase();

    AbilitiesDatabase getAbilitiesDatabase();

    BoosterProvider getBoosterProvider();

    LeaderboardCounter getLeaderboard();

    BroadcastManager getBroadcastManager();
}

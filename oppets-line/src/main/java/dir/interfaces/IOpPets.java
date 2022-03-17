package dir.interfaces;

import dir.abilities.AbilitiesDatabase;
import dir.boosters.BoosterProvider;
import dir.broadcasts.BroadcastManager;
import dir.databases.PetsDatabase;
import dir.leaderboards.LeaderboardCounter;
import dir.prestiges.PrestigeManager;
import dir.skills.SkillDatabase;

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

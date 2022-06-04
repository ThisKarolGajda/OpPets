package me.opkarol.oppets.interfaces;

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
import me.opkarol.oppets.databases.external.PetsDatabase;
import me.opkarol.oppets.eggs.EggManager;
import me.opkarol.oppets.entities.manager.IEntityManager;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.databases.external.SkillDatabase;

public interface IOpPets {
    IEntityManager getEntityManager();

    IUtils getUtils();

    SkillDatabase getSkillDatabase();

    PrestigeManager getPrestigeManager();

    PetsDatabase getPetsDatabase();

    AbilitiesDatabase getAbilitiesDatabase();

    BoosterProvider getBoosterProvider();

    LeaderboardCounter getLeaderboard();

    BroadcastManager getBroadcastManager();

    Object getEconomy();

    EggManager getEggManager();
}

package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/**
 * The type Skill enums.
 */
public class SkillEnums {

    /**
     * The enum Skills abilities.
     */
    public enum SkillsAbilities {
        /**
         * The Custom command.
         */
// CURRENT PLUGIN CONNECTIONS: Vault
        CUSTOM_COMMAND,
        /**
         * Plugin connection skills abilities.
         */
        PLUGIN_CONNECTION,
        /**
         * Vanilla effect skills abilities.
         */
        VANILLA_EFFECT,
        /**
         * Treasure skills abilities.
         */
        TREASURE,
        /**
         * Custom message skills abilities.
         */
        CUSTOM_MESSAGE
    }

    /**
     * The enum Skills requirements.
     */
    public enum SkillsRequirements {
        /**
         * Pet level skills requirements.
         */
        PET_LEVEL,
    }

    /**
     * The enum Skills boosters.
     */
    public enum SkillsBoosters {
        /**
         * Custom booster skills boosters.
         */
        CUSTOM_BOOSTER,
        /**
         * Server boost skills boosters.
         */
        SERVER_BOOST,
        /**
         * Player boost skills boosters.
         */
        PLAYER_BOOST,
        /**
         * World boost skills boosters.
         */
        WORLD_BOOST,
        /**
         * Pet boost skills boosters.
         */
        PET_BOOST
    }

    /**
     * The enum Skills adders.
     */
    public enum SkillsAdders {
        /**
         * Mining skills adders.
         */
        MINING,
        /**
         * Harvesting skills adders.
         */
        HARVESTING,
        /**
         * Crafting skills adders.
         */
        CRAFTING,
        /**
         * Smelting skills adders.
         */
        SMELTING,
        /**
         * Fishing skills adders.
         */
        FISHING
    }
}

package me.opkarol.oppets.misc;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/**
 * The type Abilities enums.
 */
public class AbilitiesEnums {
    /**
     * The enum Ability category.
     */
    public enum AbilityCategory {
        /**
         * Passive ability category.
         */
        PASSIVE,
        /**
         * Aggressive ability category.
         */
        AGGRESSIVE,
        /**
         * Hostile ability category.
         */
        HOSTILE,
        /**
         * Neutral ability category.
         */
        NEUTRAL
    }

    /**
     * The enum Ability type.
     */
    public enum AbilityType {
        /**
         * Actionbar ability type.
         */
        ACTIONBAR,
        /**
         * Actionbar after ability type.
         */
        ACTIONBAR_AFTER,
        /**
         * Add food ability type.
         */
        ADD_FOOD,
        /**
         * Add health ability type.
         */
        ADD_HEALTH,
        /**
         * Console command ability type.
         */
        CONSOLE_COMMAND,
        /**
         * Cure ability type.
         */
        CURE,
        /**
         * Lighting ability type.
         */
        LIGHTING,
        /**
         * Potion ability type.
         */
        POTION,
        /**
         * Message ability type.
         */
        MESSAGE,
        /**
         * Message after ability type.
         */
        MESSAGE_AFTER,
        /**
         * Revive ability type.
         */
        REVIVE,
        /**
         * Stop attack ability type.
         */
        STOP_ATTACK,
        /**
         * Stop knockback ability type.
         */
        STOP_KNOCKBACK,
        /**
         * Exp ability type.
         */
        EXP,
        /**
         * Steal health ability type.
         */
        STEAL_HEALTH,
        /**
         * Particle ability type.
         */
        PARTICLE,
        /**
         * Invincible ability type.
         */
        INVINCIBLE,
        /**
         * Player command ability type.
         */
        PLAYER_COMMAND,
        /**
         * Flame ability type.
         */
        FLAME,
        /**
         * Fireball ability type.
         */
        FIREBALL,
        /**
         * Stop fall damage ability type.
         */
        STOP_FALL_DAMAGE
    }
}




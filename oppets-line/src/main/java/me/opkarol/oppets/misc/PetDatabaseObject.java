package me.opkarol.oppets.misc;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.abilities.AbilitiesEnums;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The type Pet database object.
 */
public class PetDatabaseObject {
    /**
     * The Ability.
     */
    private final List<String> ability;
    /**
     * The Entity speed.
     */
    private final float entitySpeed;
    /**
     * The Entity distance.
     */
    private final float entityDistance;

    /**
     * Instantiates a new Pet database object.
     *
     * @param a the a
     * @param b the b
     * @param c the c
     */
    public PetDatabaseObject(List<String> a, float b, float c) {
        this.ability = a;
        this.entitySpeed = b;
        this.entityDistance = c;
    }

    /**
     * Gets entity speed.
     *
     * @return the entity speed
     */
    public float getEntitySpeed() {
        return entitySpeed;
    }

    /**
     * Is ability enabled boolean.
     *
     * @return the boolean
     */
    public boolean isAbilityEnabled() {
        if (ability == null) return false;
        return ability.size() != 0;
    }

    /**
     * Gets ability.
     *
     * @param s the s
     * @return the ability
     */
    public AbilitiesEnums.AbilityType getAbility(String s) {
        return AbilitiesEnums.AbilityType.valueOf(getAbilityArgs(s)[0]);
    }

    /**
     * Get ability args string [ ].
     *
     * @param s the s
     * @return the string [ ]
     */
    public String[] getAbilityArgs(@NotNull String s) {
        return s.split(":");
    }

    /**
     * Gets entity distance.
     *
     * @return the entity distance
     */
    public float getEntityDistance() {
        return entityDistance;
    }

    /**
     * Gets abilities.
     *
     * @return the abilities
     */
    public List<String> getAbilities() {
        return ability;
    }
}

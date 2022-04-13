package me.opkarol.oppets.abilities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.OpPetsEntityTypes;

/**
 * The type Pet ability.
 */
public class PetAbility {
    /**
     * The Type.
     */
    private final AbilitiesEnums.AbilityCategory type;
    /**
     * The Entity type.
     */
    private final OpPetsEntityTypes.TypeOfEntity entityType;
    /**
     * The Description.
     */
    private final String description;

    /**
     * Instantiates a new Pet ability.
     *
     * @param type        the type
     * @param entityType  the entity type
     * @param description the description
     */
    public PetAbility(AbilitiesEnums.AbilityCategory type, OpPetsEntityTypes.TypeOfEntity entityType, String description) {
        this.type = type;
        this.entityType = entityType;
        this.description = description;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public AbilitiesEnums.AbilityCategory getType() {
        return this.type;
    }

    /**
     * Gets entity type.
     *
     * @return the entity type
     */
    public OpPetsEntityTypes.TypeOfEntity getEntityType() {
        return this.entityType;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }
}


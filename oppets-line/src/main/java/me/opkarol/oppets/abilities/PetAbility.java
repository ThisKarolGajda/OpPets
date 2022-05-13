package me.opkarol.oppets.abilities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.TypeOfEntity;

public class PetAbility {
    private final AbilitiesEnums.AbilityCategory type;
    private final TypeOfEntity entityType;
    private final String description;

    public PetAbility(AbilitiesEnums.AbilityCategory type, TypeOfEntity entityType, String description) {
        this.type = type;
        this.entityType = entityType;
        this.description = description;
    }

    public AbilitiesEnums.AbilityCategory getType() {
        return this.type;
    }

    public TypeOfEntity getEntityType() {
        return this.entityType;
    }

    public String getDescription() {
        return this.description;
    }
}


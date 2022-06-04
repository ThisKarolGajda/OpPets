package me.opkarol.oppets.abilities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.external.APIDatabase;
import me.opkarol.oppets.databases.external.PetsDatabase;
import me.opkarol.oppets.exceptions.ExceptionLogger;
import me.opkarol.oppets.misc.external.api.CooldownModule;
import me.opkarol.oppets.misc.external.api.PetDatabaseObject;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AbilitiesDatabase {
    public final CooldownModule<UUID> cooldownMap = new CooldownModule<>();
    private final Set<PetAbility> set = new HashSet<>();

    public AbilitiesDatabase() {
        APIDatabase database = APIDatabase.getInstance();
        PetsDatabase petsDatabase = database.getPetsDatabase();
        for (TypeOfEntity type : petsDatabase.getMap().keySet()) {
            PetDatabaseObject object = petsDatabase.getObjectFromDatabase(type);
            if (object.isAbilityEnabled()) {
                set.add(new PetAbility(getAbilityTypeFromEntity(type), type, getDescriptionFromEntity(type)));
            }
        }
    }

    public PetAbility getPetAbility(TypeOfEntity type) {
        return set.stream()
                .filter(ability -> ability.getEntityType().equals(type)).collect(Collectors.toList())
                .get(0);
    }

    @Contract(pure = true)
    private AbilitiesEnums.@Nullable AbilityCategory getAbilityTypeFromEntity(@NotNull TypeOfEntity type) {
        switch (type) {
            case TURTLE:
            case SHEEP:
            case PIG:
            case PARROT:
            case DONKEY:
            case OCELOT:
            case MULE:
            case HORSE:
            case CHICKEN:
            case AXOLOTL:
            case COW:
                return AbilitiesEnums.AbilityCategory.PASSIVE;
            case LLAMA:
            case GOAT:
            case FOX:
            case CAT:
                return AbilitiesEnums.AbilityCategory.NEUTRAL;
            case RABBIT:
            case PANDA:
            case POLAR_BEAR:
                return AbilitiesEnums.AbilityCategory.AGGRESSIVE;
            case MUSHROOM_COW:
            case WOLF:
                return AbilitiesEnums.AbilityCategory.HOSTILE;
            default:
                ExceptionLogger.getInstance().throwException("Unexpected value: " + type);

        }
        return null;
    }

    @Contract(pure = true)
    private @Nullable String getDescriptionFromEntity(@NotNull TypeOfEntity type) {
        switch (type) {
            case AXOLOTL:
            case WOLF:
            case TURTLE:
            case SHEEP:
            case RABBIT:
            case POLAR_BEAR:
            case PIG:
            case PARROT:
            case PANDA:
            case OCELOT:
            case MUSHROOM_COW:
            case MULE:
            case LLAMA:
            case HORSE:
            case GOAT:
            case FOX:
            case DONKEY:
            case COW:
            case CHICKEN:
            case CAT:
                break;
            default:
ExceptionLogger.getInstance().throwException("Unexpected value: " + type);
        }
        return null;
    }

}

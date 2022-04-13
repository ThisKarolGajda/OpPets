package me.opkarol.oppets.abilities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.APIDatabase;
import me.opkarol.oppets.databases.PetsDatabase;
import me.opkarol.oppets.misc.CooldownModule;
import me.opkarol.oppets.misc.PetDatabaseObject;
import me.opkarol.oppets.pets.OpPetsEntityTypes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Abilities database.
 */
public class AbilitiesDatabase {
    /**
     * The Cooldown map.
     */
    public final CooldownModule<UUID> cooldownMap = new CooldownModule<>();
    /**
     * The Set.
     */
    private final Set<PetAbility> set = new HashSet<>();

    /**
     * Instantiates a new Abilities database.
     */
    public AbilitiesDatabase() {
        APIDatabase database = APIDatabase.getInstance();
        PetsDatabase petsDatabase = database.getPetsDatabase();
        for (OpPetsEntityTypes.TypeOfEntity type : petsDatabase.getMap().keySet()) {
            PetDatabaseObject object = petsDatabase.getObjectFromDatabase(type);
            if (object.isAbilityEnabled()) {
                set.add(new PetAbility(getAbilityTypeFromEntity(type), type, getDescriptionFromEntity(type)));
            }
        }
    }

    /**
     * Gets pet ability.
     *
     * @param type the type
     * @return the pet ability
     */
    public PetAbility getPetAbility(OpPetsEntityTypes.TypeOfEntity type) {
        return set.stream()
                .filter(ability -> ability.getEntityType().equals(type)).collect(Collectors.toList())
                .get(0);
    }

    /**
     * Gets ability type from entity.
     *
     * @param type the type
     * @return the ability type from entity
     */
    @Contract(pure = true)
    private AbilitiesEnums.AbilityCategory getAbilityTypeFromEntity(OpPetsEntityTypes.@NotNull TypeOfEntity type) {
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
                throw new IllegalStateException("Unexpected value: " + type);
        }

    }

    /**
     * Gets description from entity.
     *
     * @param type the type
     * @return the description from entity
     */
    @Contract(pure = true)
    private @Nullable String getDescriptionFromEntity(OpPetsEntityTypes.@NotNull TypeOfEntity type) {
        switch (type) {
            case AXOLOTL:
                break;
            case CAT:
                break;
            case CHICKEN:
                break;
            case COW:
                break;
            case DONKEY:
                break;
            case FOX:
                break;
            case GOAT:
                break;
            case HORSE:
                break;
            case LLAMA:
                break;
            case MULE:
                break;
            case MUSHROOM_COW:
                break;
            case OCELOT:
                break;
            case PANDA:
                break;
            case PARROT:
                break;
            case PIG:
                break;
            case POLAR_BEAR:
                break;
            case RABBIT:
                break;
            case SHEEP:
                break;
            case TURTLE:
                break;
            case WOLF:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return null;
    }

}

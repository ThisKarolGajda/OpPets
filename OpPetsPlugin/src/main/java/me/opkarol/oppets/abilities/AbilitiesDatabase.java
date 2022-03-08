package me.opkarol.oppets.abilities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.databases.PetsDatabase;
import dir.databases.misc.AbilitiesEnums;
import dir.databases.misc.PetDatabaseObject;
import dir.pets.OpPetsEntityTypes;
import me.opkarol.oppets.misc.CooldownModule;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AbilitiesDatabase {
    private final Set<PetAbility> set = new HashSet<>();
    public final CooldownModule<UUID> cooldownMap = new CooldownModule<>();

    public AbilitiesDatabase() {
        PetsDatabase database = Database.getPetsDatabase();
        for (OpPetsEntityTypes.TypeOfEntity type : database.getMap().keySet()) {
            PetDatabaseObject object = database.getObjectFromDatabase(type);
            if (object.isAbilityEnabled()) {
                set.add(new PetAbility(getAbilityTypeFromEntity(type), type, getDescriptionFromEntity(type)));
            }
        }
    }

    public PetAbility getPetAbility(OpPetsEntityTypes.TypeOfEntity type) {
        final PetAbility[] abilityType = new PetAbility[1];
        set.forEach(ability -> {
            if (ability.getEntityType().equals(type)) {
                abilityType[0] = ability;
            }
        });
        return abilityType[0];
    }

    @Contract(pure = true)
    private AbilitiesEnums.AbilityCategory getAbilityTypeFromEntity(OpPetsEntityTypes.@NotNull TypeOfEntity type) {
        switch (type) {
            case AXOLOTL, COW, CHICKEN, HORSE, MULE, OCELOT, DONKEY, PARROT, PIG, SHEEP, TURTLE -> {
                return AbilitiesEnums.AbilityCategory.PASSIVE;
            }
            case CAT, FOX, GOAT, LLAMA -> {
                return AbilitiesEnums.AbilityCategory.NEUTRAL;
            }
            case POLAR_BEAR, RABBIT, PANDA -> {
                return AbilitiesEnums.AbilityCategory.AGGRESSIVE;
            }
            case WOLF, MUSHROOM_COW -> {
                return AbilitiesEnums.AbilityCategory.HOSTILE;
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }

    }

    @Contract(pure = true)
    private @Nullable String getDescriptionFromEntity(OpPetsEntityTypes.@NotNull TypeOfEntity type) {
        switch (type) {
            case AXOLOTL -> {
            }
            case CAT -> {
            }
            case CHICKEN -> {
            }
            case COW -> {
            }
            case DONKEY -> {
            }
            case FOX -> {
            }
            case GOAT -> {
            }
            case HORSE -> {
            }
            case LLAMA -> {
            }
            case MULE -> {
            }
            case MUSHROOM_COW -> {
            }
            case OCELOT -> {
            }
            case PANDA -> {
            }
            case PARROT -> {
            }
            case PIG -> {
            }
            case POLAR_BEAR -> {
            }
            case RABBIT -> {
            }
            case SHEEP -> {
            }
            case TURTLE -> {
            }
            case WOLF -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        return null;
    }

}

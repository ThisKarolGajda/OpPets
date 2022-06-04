package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.entities.IEntityPet;
import me.opkarol.oppets.files.MessagesHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.utils.external.FormatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

import static me.opkarol.oppets.utils.external.FormatUtils.getNameString;
import static me.opkarol.oppets.utils.external.FormatUtils.returnMessage;

public final class PetsUtils {
    private static Database database;
    private static final MessagesHolder messages = MessagesHolder.getInstance();
    private static final PrestigeManager prestigeManager = new PrestigeManager();

    public static void savePetProgress(Pet pet, UUID playerUUID) {
        database.getDatabase().updatePet(playerUUID, pet);
    }

    public static @NotNull String getPetFormattedName(@NotNull Pet pet) {
        return FormatUtils.formatMessage(getPetPrestigeName(pet));
    }

    public static @NotNull String getPetPrestigeName(@NotNull Pet pet) {
        return pet.getPetName().replace("%p%", getPrestige(pet));
    }

    public static String getPrestige(Pet pet) {
        return prestigeManager.getFilledPrestige(pet.getPrestige());
    }

    // USE ONLY WHEN COMPARING STRING AS A NAME FROM LIVING ENTITY - PET
    public static boolean equalsFormatNames(Pet pet, String formatted) {
        return getNameString(getPetFormattedName(pet)).equals(getNameString(formatted));
    }

    public static String getEmptyPetName(@NotNull Pet pet) {
        return getNameString(pet.getPetName());
    }

    public static boolean equalsNames(Pet pet, String name) {
        return getEmptyPetName(pet).equals(getNameString(name));
    }

    public static Material getMaterialByPetType(@NotNull TypeOfEntity type) {
        if (type.equals(TypeOfEntity.MUSHROOM_COW)) {
            return Material.MOOSHROOM_SPAWN_EGG;
        }
        return Material.valueOf(type.name() + "_SPAWN_EGG");
    }

    public static boolean summonPet(String name, @NotNull Player sender) {
        Optional<Pet> optional = database.getDatabase().getPetList(sender.getUniqueId()).stream()
                .filter(pet -> equalsNames(pet, name))
                .findAny();
        return optional.filter(pet -> summonPet(pet, sender, false)).isPresent();
    }

    public static boolean summonPet(Pet pet, Player player, boolean bypassPetLimit) {
        if (pet == null) {
            return false;
        }
        UUID uuid = player.getUniqueId();
        Pet activePet = database.getDatabase().getCurrentPet(uuid);
        if (activePet == pet && !bypassPetLimit) {
            return returnMessage(player, messages.getString("Commands.samePet"));
        }
        OpUtils.killPetFromPlayerUUID(uuid);
        Optional<IEntityPet> optional = database.getOpPets().getEntityManager().spawnEntity(player, pet);
        if (optional.isPresent()) {
            return returnMessage(player, messages.getString("Commands.summonedPet").replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName())));
        }
        return false;
    }

    public static void setDatabase(Database database) {
        PetsUtils.database = database;
    }
}

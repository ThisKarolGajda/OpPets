package me.opkarol.oppets.utils;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.entities.IEntityPet;
import me.opkarol.oppets.api.files.MessagesHolder;
import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.cache.NamespacedKeysCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.eggs.types.EggItem;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.pets.id.UniquePet;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.utils.external.FormatUtils;
import me.opkarol.oppets.utils.external.PDCUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static me.opkarol.oppets.cache.NamespacedKeysCache.priceKey;
import static me.opkarol.oppets.cache.NamespacedKeysCache.typeKey;
import static me.opkarol.oppets.utils.external.FormatUtils.getNameString;
import static me.opkarol.oppets.utils.external.FormatUtils.returnMessage;
import static me.opkarol.oppets.utils.external.MathUtils.getPrestigeLevel;

public class Utils {

    private static final Database database = Database.getInstance();
    private static final MessagesHolder messages = MessagesHolder.getInstance();
    private static final PrestigeManager prestigeManager = new PrestigeManager();

    public static void savePetProgress(Pet pet, UUID playerUUID) {
        Database.getInstance().getDatabase().updatePet(playerUUID, pet);
    }

    public static @NotNull String getFilledPetName(@NotNull Pet pet) {
        return FormatUtils.formatMessage(pet.getPetName().replace("%p%", getPrestige(pet)));
    }

    public static String getPrestige(Pet pet) {
        return prestigeManager.getFilledPrestige(pet.getPrestige());
    }

    /**
     * This method should be used only when comparing component name to pet name
     */
    public static boolean equalsComponentAndPetName(Pet pet, String component) {
        return Objects.equals(getNameString(getFilledPetName(pet)), getNameString(component));
    }

    public static String getEmptyPetName(@NotNull Pet pet) {
        return getNameString(pet.getPetName());
    }

    public static boolean equalsNames(Pet pet, String name) {
        return Objects.equals(getEmptyPetName(pet), getNameString(name));
    }

    public static boolean isStringFormatted(String str) {
        return FormatUtils.formatMessage(str).length() != str.length();
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
        Utils.killPetFromPlayerUUID(uuid);
        Optional<IEntityPet> optional = database.getOpPets().getEntityManager().spawnEntity(player, pet);
        if (optional.isPresent()) {
            return returnMessage(player, messages.getString("Commands.summonedPet").replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName())));
        }
        return false;
    }

    public static UUID getUUIDFromName(String name) {
        final Player online = Bukkit.getPlayerExact(name);
        if (online != null) {
            return online.getUniqueId();
        }
        final Optional<OfflinePlayer> optional = Arrays.stream(Bukkit.getOfflinePlayers())
                .filter(player -> name.equals(player.getName()))
                .findAny();
        return optional.map(OfflinePlayer::getUniqueId).orElse(UUID.fromString(name));
    }

    public static String getNameFromUUID(UUID uuid) {
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

    //TODO
    public static @NotNull String getRewardMessage(@NotNull Pet pet) {
        return "";
    }

    public static void tryToBuyItemFromInventory(@NotNull Player player, ItemStack item) {
        UUID uuid = player.getUniqueId();
        if (item == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        int price = StringTransformer.getIntFromString(Objects.requireNonNull(PDCUtils.getNBT(item, priceKey)));
        String type = PDCUtils.getNBT(item, typeKey);
        Object economy = database.getOpPets().getEconomy();
        if (economy != null && price != -1) {
            Economy economy1 = (Economy) economy;
            if (!economy1.has(player, price)) {
                player.sendMessage(messages.getString("Messages.notEnoughMoney"));
                player.closeInventory();
                return;
            }
            economy1.withdrawPlayer(player, price);
        }
        final String[] name = {type};
        int i = 0;
        while (database.getDatabase().getPetList(uuid).stream().anyMatch(pet1 -> {
            if (name[0] != null) {
                return FormatUtils.getNameString(name[0]).equals(FormatUtils.getNameString(pet1.getPetName()));
            }
            return false;
        })) {
            name[0] = type + "-" + i;
            i++;
        }
        TypeOfEntity entity = TypeOfEntity.valueOf(type);
        Pet pet1 = new Pet(name[0], entity, null, uuid, true);
        database.getDatabase().addPetToPetsList(uuid, pet1);
        player.sendMessage(messages.getString("Messages.successfullyBought"));
        player.closeInventory();
    }

    public static boolean canPrestige(@NotNull Pet pet) {
        return pet.getLevel() >= getPrestigeLevel(pet);
    }

    public static Pet getPetByName(UUID playerUUID, String petName) {
        Optional<Pet> optional = database.getDatabase().getPetList(playerUUID).stream()
                .filter(pet -> Utils.equalsNames(pet, petName))
                .collect(Collectors.toList())
                .stream()
                .findFirst();
        return optional.orElse(null);
    }

    public static boolean hasPetByName(UUID playerUUID, String petName) {
        return getPetByName(playerUUID, petName) != null;
    }

    public static void killPetFromPlayerUUID(UUID uuid) {
        if (uuid == null) {
            return;
        }
        Pet pet = database.getDatabase().getCurrentPet(uuid);
        if (pet == null) {
            return;
        }
        database.getDatabase().getActivePetMap()
                .getValuesStream()
                .filter(pet1 -> pet1.petUUID.getDatabaseId() == pet.petUUID.getDatabaseId())
                .map(Pet::getPetUUID)
                .map(UniquePet::getOwnUUID)
                .filter(Objects::nonNull)
                .forEach(uuid1 -> {
                    LivingEntity entity = (LivingEntity) Bukkit.getEntity(uuid1);
                    if (entity != null && PDCUtils.hasNBT(entity, NamespacedKeysCache.petKey)) {
                        entity.remove();
                    }
                });
    }

    public static Optional<EggItem> getEggItemFromString(@NotNull String args) {
        Optional<TypeOfEntity> optional = StringTransformer.getEnumValue(args.toUpperCase(), TypeOfEntity.class);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        return database.getOpPets().getEggManager().getEggFromType(optional.get());
    }

    public static @NotNull String replaceAndGet(@NotNull String s, String replaceFrom, String replaceTo) {
        return s.replace(replaceFrom, replaceTo);
    }
}

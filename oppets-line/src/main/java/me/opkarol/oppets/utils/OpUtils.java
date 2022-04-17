package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoryCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.pets.OpPetsEntityTypes;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.skills.Adder;
import me.opkarol.oppets.skills.SkillUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.opkarol.oppets.cache.NamespacedKeysCache.priceKey;
import static me.opkarol.oppets.cache.NamespacedKeysCache.typeKey;

/**
 * The type Op utils.
 */
public class OpUtils {
    /**
     * The constant cache.
     */
    private static InventoryCache cache;
    /**
     * The constant database.
     */
    private static Database database;

    /**
     * Gets level.
     *
     * @param pet the pet
     * @return the level
     */
    public static int getLevel(@NotNull Pet pet) {
        return pet.getLevel();
    }

    /**
     * Gets max level.
     *
     * @param pet the pet
     * @return the max level
     */
    public static int getMaxLevel(@NotNull Pet pet) {
        return database.getOpPets().getSkillDatabase().getSkillFromMap(pet.getSkillName()).getMaxLevel();
    }

    /**
     * Gets pet level experience.
     *
     * @param pet the pet
     * @return the pet level experience
     */
    public static int getPetLevelExperience(@NotNull Pet pet) {
        return Math.toIntExact(Math.round(getAdderPoints(pet.getSkillName(), true, pet) - pet.getPetExperience()));
    }

    /**
     * Gets percentage of next level.
     *
     * @param pet the pet
     * @return the percentage of next level
     */
    public static @NotNull String getPercentageOfNextLevel(@NotNull Pet pet) {
        String skillName = pet.getSkillName();
        String s = String.valueOf(((pet.getPetExperience() * 100) / getAdderPoints(skillName, false, pet)));
        if (s.length() > 4) s = s.substring(0, 4);
        return s + "%";
    }

    /**
     * Gets adder points.
     *
     * @param skillName    the skill name
     * @param lowestOutput the lowest output
     * @param pet          the pet
     * @return the adder points
     */
    public static double getAdderPoints(String skillName, boolean lowestOutput, Pet pet) {
        double number = 0D;
        List<Adder> list = database.getOpPets().getSkillDatabase().getSkillFromMap(skillName).getAdderList();
        for (Adder adder : list) {
            double calculated = adder.calculateMaxCurrent(pet.getLevel());
            if (number == 0) {
                number = calculated;
                continue;
            }
            if (lowestOutput) {
                if (calculated < number) {
                    number = calculated;
                }
            } else {
                if (calculated > number) {
                    number = calculated;
                }
            }
        }
        return number;
    }

    /**
     * Gets uuid from name.
     *
     * @param name the name
     * @return the uuid from name
     */
    public static UUID getUUIDFromName(String name) {
        final Player online = Bukkit.getPlayerExact(name);
        if (online != null) {
            return online.getUniqueId();
        }
        final Optional<OfflinePlayer> optional = Arrays.stream(Bukkit.getOfflinePlayers())
                .filter(player -> name.equals(player.getName()))
                .findAny();
        return optional.map(OfflinePlayer::getUniqueId).orElse(null);
    }

    /**
     * Gets name from uuid.
     *
     * @param uuid the uuid
     * @return the name from uuid
     */
    public static String getNameFromUUID(UUID uuid) {
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

    /**
     * Can prestige boolean.
     *
     * @param pet the pet
     * @return the boolean
     */
    public static boolean canPrestige(@NotNull Pet pet) {
        return pet.getLevel() >= getMaxLevel(pet);
    }

    /**
     * Gets reward message.
     *
     * @param pet the pet
     * @return the reward message
     */
    @Contract(pure = true)
    //TODO
    public static @NotNull String getRewardMessage(@NotNull Pet pet) {
        return "";
    }

    /**
     * Gets levels for prestige.
     *
     * @param pet the pet
     * @return the levels for prestige
     */
    public static int getLevelsForPrestige(Pet pet) {
        if (canPrestige(pet)) {
            return 0;
        }
        return getMaxLevel(pet) - pet.getLevel();
    }

    public static void tryToBuyItemFromInventory(@NotNull Player player, Inventory inventory, int slot) {
        UUID uuid = player.getUniqueId();
        if (inventory == null) {
            return;
        }
        ItemStack item = inventory.getItem(slot);
        if (item == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        int price = new StringTransformer().getIntFromString(Objects.requireNonNull(PDCUtils.getNBT(item, priceKey)));
        String type = PDCUtils.getNBT(item, typeKey);
        Object economy = database.getOpPets().getEconomy();
        if (economy != null && price != -1) {
            Economy economy1 = (Economy) economy;
            if (!economy1.has(player, price)) {
                player.sendMessage(database.getOpPets().getMessages().getMessagesAccess().stringMessage("notEnoughMoney"));
                player.closeInventory();
                return;
            }
            economy1.withdrawPlayer(player, price);
        }
        final String[] name = {type};
        int i = 0;
        while (database.getDatabase().getPetList(uuid).stream().anyMatch(pet1 -> FormatUtils.getNameString(name[0]).equals(FormatUtils.getNameString(pet1.getPetName())))) {
            name[0] = type + "-" + i;
            i++;
        }
        OpPetsEntityTypes.TypeOfEntity entity = OpPetsEntityTypes.TypeOfEntity.valueOf(type);
        Pet pet1 = new Pet(name[0], entity, null, uuid, new SkillUtils().getRandomSkillName(entity), true);
        database.getDatabase().addPetToPetsList(uuid, pet1);
        player.sendMessage(database.getOpPets().getMessages().getMessagesAccess().stringMessage("successfullyBought"));
        player.closeInventory();
    }

    /**
     * Sets database.
     *
     * @param database the database
     */
    public static void setDatabase(Database database) {
        OpUtils.database = database;
    }
}

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
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.PetMainInventory;
import me.opkarol.oppets.skills.Adder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * OpUtils class is a main utils class, that can be also accessed as under-api
 * to in-source files.
 * It contains a lot of useful methods which are based around Pet and Player.
 */
public class OpUtils {
    /**
     * The constant cache.
     */
    private static InventoryCache cache;

    /**
     * Returns a level Integer value which is taken
     * from Pet object that was previously provided
     * in the method.
     * <p>
     * <p>
     * This method won't return an object if a
     * provided is null.
     *
     * @param pet class object from which level will be read
     * @return level integer level localized in object class
     */
    public static int getLevel(@NotNull Pet pet) {
        return pet.getLevel();
    }

    /**
     * Returns a max level Integer value which is taken
     * from Pet object that was previously provided
     * in this method.
     * <p>
     * This method will not return any object if a
     * pet provided is null.
     *
     * @param pet class object from which level will be read
     * @return level max level from skill which name was read from pet class
     * @see me.opkarol.oppets.skills.SkillDatabase
     */
    public static int getMaxLevel(@NotNull Pet pet) {
        return Database.getOpPets().getSkillDatabase().getSkillFromMap(pet.getSkillName()).getMaxLevel();
    }

    /**
     * Returns a pet level experience Integer value that
     * is transformed from divided from adderPoints of
     * provided pet`s experience, which next is rounded
     * using Math.round method and selected using
     * Math.toIntExact method.
     * <p>
     * This method will throw an error if pet is invalid.
     *
     * @param pet class object from which level will be read
     * @return experience is a divided operation from pet`s experience and adderPoints
     * @see me.opkarol.oppets.utils.OpUtils#getAdderPoints me.opkarol.oppets.utils.OpUtils#getAdderPointsme.opkarol.oppets.utils.OpUtils#getAdderPoints
     */
    public static int getPetLevelExperience(@NotNull Pet pet) {
        return Math.toIntExact(Math.round(getAdderPoints(pet.getSkillName(), true, pet) - pet.getPetExperience()));
    }

    /**
     * Returns a String formatted object that has been
     * transformed from a skill name of pet.
     * Returned string contains "%" character at the end,
     * and has 4 character capacity. [x.xx, xx.x]
     * <p>
     * Returned object is a percentage calculation, using method:
     * a * 100 / b,
     * which provides real-time calculation of current level progress.
     *
     * @param pet class object from which object will be read
     * @return percentage special mathematics operation that transforms pet and skill information into readable string
     */
    public static @NotNull String getPercentageOfNextLevel(@NotNull Pet pet) {
        String skillName = pet.getSkillName();
        String s = String.valueOf(((pet.getPetExperience() * 100) / getAdderPoints(skillName, false, pet)));
        if (s.length() > 4) s = s.substring(0, 4);
        return s + "%";
    }

    /**
     * Returns a double object that is being specified by
     * using the lowest and highest number, which is looped
     * through every Adder from the skill.
     *
     * @param skillName    string object that is equals to a skill name
     * @param lowestOutput boolean value which represents if method has to do lowestOutput operation
     * @param pet          class object from which object will be read
     * @return adderPoints double object that is a upper value
     */
    public static double getAdderPoints(String skillName, boolean lowestOutput, Pet pet) {
        double number = 0D;
        List<Adder> list = Database.getOpPets().getSkillDatabase().getSkillFromMap(skillName).getAdderList();
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
     * Returns a UUID object that is being created
     * from Online of Offline player object.
     *
     * @param name string object which is equal to a player name
     * @return uuid not null value of resulted method
     */
    public static @NotNull UUID getUUIDFromName(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }

    /**
     * Returns a name object that is being created
     * from Online of Offline player object, using
     * player uuid.
     * <p>
     * Will return null if the player wasn't before
     * on the server / hasn't been registered.
     *
     * @param uuid value of resulted method
     * @return name string object which is equal to a player name
     */
    public static String getNameFromUUID(UUID uuid) {
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

    /**
     * Returns a boolean object that is being
     * a representation of equals operation
     * between pet level and pet max level.
     * <p>
     * If the result is bigger or equal to
     * than itself, it returns true.
     *
     * @param pet class object from which object will be read
     * @return prestige boolean value from specified operation
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
    public static @NotNull String getRewardMessage(@NotNull Pet pet) {
        return "";
    }

    /**
     * Returns a integer value that has been calculated
     * from operation: a - b, where a stands for
     * maximum pet level, and b for pet level.
     *
     * @param pet class object from which object will be read
     * @return level needed for next prestige
     */
    public static int getLevelsForPrestige(Pet pet) {
        if (canPrestige(pet)) {
            return 0;
        }
        return getMaxLevel(pet) - pet.getLevel();
    }

    /**
     * This method opens a saved in a cache, pet main inventory
     * and if it doesn't exist, it will generate it own.
     * It will repeat itself if an inventory is invalid.
     *
     * @param player used as an object whose inventory will be open
     */
    public static void openMainInventory(Player player) {
        if (cache == null) {
            cache = new InventoryCache();
        }
        Inventory inventory = cache.getInventory();
        if (inventory == null) {
            cache.setInventory(new PetMainInventory().getInventory());
            openMainInventory(player);
        } else {
            player.openInventory(inventory);
        }
    }

}

package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.prestiges.PrestigeManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Pets utils.
 */
public class PetsUtils {

    /**
     * Gets binary from string and char.
     *
     * @param text   the text
     * @param number the number
     * @return the binary from string and char
     */
    @Contract(pure = true)
    public static boolean getBinaryFromStringAndChar(@NotNull String text, int number) {
        return String.valueOf(text.charAt(number)).equals(String.valueOf(1));
    }

    /**
     * Gets binary from string to char.
     *
     * @param text   the text
     * @param number the number
     * @param value  the value
     * @return the binary from string to char
     */
    @Contract(pure = true)
    public static @NotNull String getBinaryFromStringToChar(@NotNull String text, int number, boolean value) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (char c : text.toCharArray()) {
            if (i == number) {
                builder.append(value ? 1 : 0);
            } else {
                builder.append(c);
            }
            i++;
        }
        return builder.toString();
    }

    /**
     * Save pet progress.
     *
     * @param pet        the pet
     * @param playerUUID the player uuid
     */
    public static void savePetProgress(Pet pet, UUID playerUUID) {
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Pet> list = Database.getDatabase().getPetList(playerUUID);
                list.removeIf(pet1 -> Objects.equals(pet1.getPetName(), pet.getPetName()));
                list.add(pet);
                Database.getDatabase().setPets(playerUUID, list);
            }
        }.runTaskAsynchronously(Database.getInstance());
    }

    /**
     * Gets pet formatted name.
     *
     * @param pet the pet
     * @return the pet formatted name
     */
    public static @Nullable String getPetFormattedName(@NotNull Pet pet) {
        if (pet.getPetName() != null) {
            return ChatColor.translateAlternateColorCodes('&', pet.getPetName().replace("%p%", new PrestigeManager().getFilledPrestige(pet.getPrestige())));
        }
        return null;
    }
}

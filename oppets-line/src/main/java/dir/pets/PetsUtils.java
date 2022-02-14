package dir.pets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.prestiges.PrestigeManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class PetsUtils {

    @Contract(pure = true)
    public static boolean getBinaryFromStringAndChar(@NotNull String text, int number) {
        return String.valueOf(text.charAt(number)).equals(String.valueOf(1));
    }

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

    public static void savePetProgress(Pet pet, UUID playerUUID) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Pet> list = (ArrayList<Pet>) Database.getDatabase().getPetList(playerUUID);
                list.removeIf(pet1 -> Objects.equals(pet1.getPetName(), pet.getPetName()));
                list.add(pet);
                Database.getDatabase().setPets(playerUUID, list);
            }
        }.runTaskAsynchronously(Database.getInstance());
    }

    public static @Nullable String getPetFormattedName(@NotNull Pet pet) {
        if (pet.getPetName() != null) {
            return ChatColor.translateAlternateColorCodes('&', pet.getPetName().replace("%p%", new PrestigeManager().getFilledPrestige(pet.getPrestige())));
        }
        return null;
    }
}

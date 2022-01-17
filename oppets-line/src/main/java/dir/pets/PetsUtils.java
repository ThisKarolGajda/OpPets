package dir.pets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.interfaces.UtilsInterface;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PetsUtils {

    @TestOnly
    public static @NotNull String serializeSettingsOfPet(boolean @NotNull ... settings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (boolean b : settings) {
            if (b) {
                stringBuilder.append("1");
            } else stringBuilder.append("0");
        }
        return stringBuilder.toString();
    }

    @Contract(pure = true)
    public static boolean @NotNull [] deserializeSettingsOfPet(@NotNull String serializedSettings) {
        boolean[] booleans = new boolean[serializedSettings.length()];
        int i = 0;
        for (char c : serializedSettings.toCharArray()) {
            booleans[i] = String.valueOf(c).equals("1");
            i++;
        }
        return booleans;
    }

    @Contract(pure = true)
    public static boolean getDeserializedValueOfSettingsPetFromIndex(@NotNull String serializedSettings, int i) {
        if (i > serializedSettings.length()) return false;
        char c = serializedSettings.charAt(i);
        return String.valueOf(c).equals("1");
    }

    public static @NotNull String setDeserializedSettings(@NotNull String serializedSettings, int i, boolean b) {
        if (i > serializedSettings.length()) return serializedSettings;
        StringBuilder stringBuilder = new StringBuilder();
        int iI = 0;
        for (char c : serializedSettings.toCharArray()) {
            if (iI == i) {
                if (b) {
                    stringBuilder.append("1");
                } else stringBuilder.append("0");
            } else {
                stringBuilder.append(c);
            }
            iI++;
        }
        return stringBuilder.toString();
    }

    public static void removePet(@NotNull UtilsInterface utils, @NotNull Pet pet) {
        utils.removeEntity(utils.getEntityByUniqueId(pet.getOwnUUID()));
    }

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
}

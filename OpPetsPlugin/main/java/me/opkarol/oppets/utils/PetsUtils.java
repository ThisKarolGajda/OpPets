package me.opkarol.oppets.utils;

import me.opkarol.oppets.interfaces.UtilsInterface;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.craftbukkit.libs.jline.internal.TestAccessible;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

public class PetsUtils {

    @TestOnly
    @TestAccessible
    public static @NotNull String serializeSettingsOfPet(boolean @NotNull... settings){
        StringBuilder stringBuilder = new StringBuilder();
        for (boolean b : settings){
            if (b) {
                stringBuilder.append("1");
            } else stringBuilder.append("0");
        }
        return stringBuilder.toString();
    }

    @Contract(pure = true)
    public static boolean @NotNull[] deserializeSettingsOfPet(@NotNull String serializedSettings){
        boolean[] booleans = new boolean[serializedSettings.length()];
        int i = 0;
        for (char c : serializedSettings.toCharArray()){
            booleans[i] = String.valueOf(c).equals("1");
            i++;
        }
        return booleans;
    }

    @Contract(pure = true)
    public static boolean getDeserializedValueOfSettingsPetFromIndex(@NotNull String serializedSettings, int i){
        if (i > serializedSettings.length()) return false;
        char c = serializedSettings.charAt(i);
        return String.valueOf(c).equals("1");
    }

    public static @NotNull String setDeserializedSettings(@NotNull String serializedSettings, int i, boolean b){
        if (i > serializedSettings.length()) return serializedSettings;
        StringBuilder stringBuilder = new StringBuilder();
        int iI = 0;
        for (char c : serializedSettings.toCharArray()){
            if (iI == i){
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

    public static void removePet(@NotNull UtilsInterface utils, @NotNull Pet pet){
        utils.removeEntity(utils.getEntityByUniqueId(pet.getOwnUUID()));
    }


}

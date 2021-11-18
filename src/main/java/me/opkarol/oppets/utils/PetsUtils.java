package me.opkarol.oppets.utils;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class PetsUtils {

    public static void killPetFromPlayerUUID(UUID playerUUID) {
        if (OpPets.getDatabase().getCurrentPet(playerUUID) != null) {
            if (EntityUtils.getEntityByUniqueId(OpPets.getDatabase().getCurrentPet(playerUUID).getOwnUUID()) != null)
                ((LivingEntity) Objects.requireNonNull(EntityUtils.getEntityByUniqueId(OpPets.getDatabase().getCurrentPet(playerUUID).getOwnUUID()))).setHealth(0);

        }
    }

    public static void respawnPet(Pet pet, @NotNull Player player){
        killPetFromPlayerUUID(player.getUniqueId());
        OpPets.getCreator().spawnMiniPet(pet, player);
    }

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


}

package me.opkarol.oppets.utils.versionUtils;

import me.opkarol.oppets.pets.Pet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface UtilsInterface {

    @Nullable Entity getEntityByUniqueId(UUID uniqueId);

    void hideEntityFromServer(Player owner, int entityId);

    void hideEntityFromPlayer(Player player, int entityId);

    String getVanillaPetName(@NotNull EntityType entityType);

    void killPetFromPlayerUUID(UUID playerUUID);

    void respawnPet(Pet pet, @NotNull Player player);

    Object getVersion();
}

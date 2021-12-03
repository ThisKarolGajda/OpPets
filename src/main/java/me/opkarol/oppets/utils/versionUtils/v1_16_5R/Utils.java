package me.opkarol.oppets.utils.versionUtils.v1_16_5R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.versionUtils.UtilsInterface;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class Utils implements UtilsInterface {
    @Override
    public org.bukkit.entity.@Nullable Entity getEntityByUniqueId(UUID uniqueId) {
        return Bukkit.getEntity(uniqueId);
    }

    @Override
    public void hideEntityFromServer(Player owner, int entityId) {
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            if (player1 != owner) {
                ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entityId));
            }
        });
    }

    @Override
    public void hideEntityFromPlayer(Player player, int entityId) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entityId));
            }
        }.runTask(OpPets.getInstance());
    }

    @Override
    public String getVanillaPetName(@NotNull EntityType entityType) {
        if (entityType.getEntityClass() != null) {
            return entityType.getEntityClass().getClassLoader().getName();
        }
        return "";
    }

    @Override
    public void killPetFromPlayerUUID(UUID playerUUID) {
        if (OpPets.getDatabase().getCurrentPet(playerUUID) == null) {
            return;
        }
        if (getEntityByUniqueId(OpPets.getDatabase().getCurrentPet(playerUUID).getOwnUUID()) == null) {
            return;
        }
        CraftEntity entity = (CraftEntity) Objects.requireNonNull(getEntityByUniqueId(OpPets.getDatabase().getCurrentPet(playerUUID).getOwnUUID()));
        entity.remove();

    }

    @Override
    public void respawnPet(Pet pet, @NotNull Player player) {
        killPetFromPlayerUUID(player.getUniqueId());
        OpPets.getCreator().spawnMiniPet(pet, player);
    }

    @Override
    public Object getVersion() {
        return "v1_16_3R";
    }
}

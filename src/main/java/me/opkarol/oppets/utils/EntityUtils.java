package me.opkarol.oppets.utils;

import me.opkarol.oppets.OpPets;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.world.entity.EntityAgeable;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EntityUtils {

    public static @Nullable Entity getEntityByUniqueId(UUID uniqueId){
        return Bukkit.getEntity(uniqueId);
    }


    public static void hideEntityFromServer(Player owner, int entityId) {
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            if (player1 != owner) {
                ((CraftPlayer) player1).getHandle().b.sendPacket(new PacketPlayOutEntityDestroy(entityId));
            }
        });
    }

    public static void hideEntityFromPlayer(Player player, int entityId){
        new BukkitRunnable() {
            @Override
            public void run() {
                ((CraftPlayer) player).getHandle().b.sendPacket(new PacketPlayOutEntityDestroy(entityId));
            }
        }.runTask(OpPets.getInstance());
    }

    public static String getVanillaPetName(@NotNull EntityType entityType){
        if (entityType.getEntityClass() != null) {
            return entityType.getEntityClass().getClassLoader().getName();
        }
        return "";
    }
}

package v1_16_1R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IPacketPlayInSteerVehicleEvent;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.pets.Pet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_16_R1.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R1.PathfinderGoal;
import net.minecraft.server.v1_16_R1.PathfinderGoalSelector;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;

/**
 * The type Utils.
 */
public class Utils implements IUtils {
    private final Database database = Database.getInstance(SessionHolder.getInstance().getSession());

    /**
     * Gets entity by unique id.
     *
     * @param uniqueId the unique id
     * @return the entity by unique id
     */
    @Override
    public org.bukkit.entity.@Nullable Entity getEntityByUniqueId(UUID uniqueId) {
        return Bukkit.getEntity(uniqueId);
    }

    /**
     * Hide entity from server.
     *
     * @param owner    the owner
     * @param entityId the entity id
     */
    @Override
    public void hideEntityFromServer(Player owner, int entityId) {
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            if (player1 != owner) {
                ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entityId));
            }
        });
    }

    /**
     * Hide entity from player.
     *
     * @param player   the player
     * @param entityId the entity id
     */
    @Override
    public void hideEntityFromPlayer(Player player, int entityId) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entityId));
            }
        }.runTask(database.getPlugin());
    }

    /**
     * Gets vanilla pet name.
     *
     * @param entityType the entity type
     * @return the vanilla pet name
     */
    @Override
    public String getVanillaPetName(@NotNull EntityType entityType) {
        if (entityType.getEntityClass() != null) {
            return entityType.getEntityClass().getClassLoader().toString();
        }
        return "";
    }

    /**
     * Kill pet from player uuid.
     *
     * @param playerUUID the player uuid
     */
    @Override
    public void killPetFromPlayerUUID(UUID playerUUID) {
        if (playerUUID == null) {
            return;
        }
        Pet pet = database.getDatabase().getCurrentPet(playerUUID);
        if (pet == null) {
            return;
        }
        database.getDatabase().getActivePetMap().values().stream().filter(pet1 -> Objects.equals(pet1.getPetName(), pet.getPetName())).forEach(pet1 -> {
            UUID uuid = pet.getOwnUUID();
            if (uuid == null) {
                return;
            }
            Entity entity = Bukkit.getEntity(uuid);
            if (entity != null) {
                entity.remove();
            }
        });
    }

    /**
     * Respawn pet.
     *
     * @param pet    the pet
     * @param player the player
     */
    @Override
    public void respawnPet(Object pet, @NotNull Player player) {
        killPetFromPlayerUUID(player.getUniqueId());
        new BabyEntityCreator().spawnMiniPet((Pet) pet, player);
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    @Override
    public Object getVersion() {
        return "v1_16_2R";
    }

    /**
     * Remove pathfinders.
     *
     * @param bP the b p
     * @param bQ the b q
     */
    public void removePathfinders(Object bP, Object bQ) {
        PathfinderGoalSelector goalSelector = (PathfinderGoalSelector) bP;
        PathfinderGoalSelector targetSelector = (PathfinderGoalSelector) bQ;

        Field dField;
        Field cField;
        Field fField;
        try {
            dField = PathfinderGoalSelector.class.getDeclaredField("d");
            dField.setAccessible(true);
            dField.set(goalSelector, new LinkedHashSet<>());
            cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            cField.set(goalSelector, new EnumMap<>(PathfinderGoal.Type.class));
            fField = PathfinderGoalSelector.class.getDeclaredField("f");
            fField.setAccessible(true);
            fField.set(goalSelector, EnumSet.noneOf(PathfinderGoal.Type.class));
            dField = PathfinderGoalSelector.class.getDeclaredField("d");
            dField.setAccessible(true);
            dField.set(targetSelector, new LinkedHashSet<>());
            cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            cField.set(targetSelector, new EnumMap<>(PathfinderGoal.Type.class));
            fField = PathfinderGoalSelector.class.getDeclaredField("f");
            fField.setAccessible(true);
            fField.set(targetSelector, EnumSet.noneOf(PathfinderGoal.Type.class));
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var8) {
            var8.printStackTrace();
        }

    }

    /**
     * Gets player channel.
     *
     * @param player the player
     * @return the player channel
     */
    @Override
    public Channel getPlayerChannel(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
    }

    /**
     * Gets player pipeline.
     *
     * @param player the player
     * @return the player pipeline
     */
    @Override
    public ChannelPipeline getPlayerPipeline(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
    }

    /**
     * Ride event register.
     *
     * @param event  the event
     * @param packet the packet
     * @param player the player
     */
    @Override
    public void rideEventRegister(Object event, Object packet, Player player) {
        if (packet instanceof PacketPlayInSteerVehicle) {
            Bukkit.getPluginManager().callEvent((Event) ((IPacketPlayInSteerVehicleEvent) event).initialize(packet, player));
        }
    }
}

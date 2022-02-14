package v1_16_5R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.interfaces.IPacketPlayInSteerVehicleEvent;
import dir.interfaces.IUtils;
import dir.pets.Pet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import net.minecraft.server.v1_16_R3.PathfinderGoalSelector;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;

public class Utils implements IUtils {
    @Override
    public org.bukkit.entity.@Nullable Entity getEntityByUniqueId(UUID uniqueId) {
        if (uniqueId == null) {
            return null;
        }
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
        }.runTask(Database.getInstance());
    }

    @Override
    public String getVanillaPetName(@NotNull EntityType entityType) {
        if (entityType.getEntityClass() != null) {
            return entityType.getEntityClass().getClassLoader().toString();
        }
        return "";
    }

    @Override
    public void killPetFromPlayerUUID(UUID playerUUID) {
        if (Database.getDatabase().getCurrentPet(playerUUID) == null) {
            return;
        }
        Pet pet = Database.getDatabase().getCurrentPet(playerUUID);
        if (pet.getOwnerUUID() == null) {
            pet.setOwnerUUID(playerUUID);
        }
        if (getEntityByUniqueId(pet.getOwnUUID()) == null) {
            return;
        }
        CraftEntity entity = (CraftEntity) Objects.requireNonNull(getEntityByUniqueId(pet.getOwnUUID()));
        entity.remove();

    }

    @Override
    public void respawnPet(Object pet, @NotNull Player player) {
        killPetFromPlayerUUID(player.getUniqueId());
        new BabyEntityCreator().spawnMiniPet((Pet) pet, player);
    }

    @Override
    public Object getVersion() {
        return "v1_16_3R";
    }

    public void removePathfinders(Object bP, Object bQ) {
        PathfinderGoalSelector goalSelector = (PathfinderGoalSelector) bP;
        PathfinderGoalSelector targetSelector = (PathfinderGoalSelector) bQ;

        Field dField;
        Field cField;
        Field fField;
        try {
            dField = PathfinderGoalSelector.class.getDeclaredField("d");
            dField.setAccessible(true);
            dField.set(goalSelector, new LinkedHashSet());
            cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            cField.set(goalSelector, new EnumMap(PathfinderGoal.Type.class));
            fField = PathfinderGoalSelector.class.getDeclaredField("f");
            fField.setAccessible(true);
            fField.set(goalSelector, EnumSet.noneOf(PathfinderGoal.Type.class));
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var9) {
            var9.printStackTrace();
        }

        try {
            dField = PathfinderGoalSelector.class.getDeclaredField("d");
            dField.setAccessible(true);
            dField.set(targetSelector, new LinkedHashSet());
            cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            cField.set(targetSelector, new EnumMap(PathfinderGoal.Type.class));
            fField = PathfinderGoalSelector.class.getDeclaredField("f");
            fField.setAccessible(true);
            fField.set(targetSelector, EnumSet.noneOf(PathfinderGoal.Type.class));
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var8) {
            var8.printStackTrace();
        }

    }

    @Override
    public void removeEntity(Object obj1) {
        org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity entity = (org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity) obj1;
        if (entity != null) entity.remove();
    }

    @Override
    public Channel getPlayerChannel(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
    }

    @Override
    public ChannelPipeline getPlayerPipeline(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
    }

    @Override
    public boolean rideEventRegister(Object event, Object packet, Player player) {
        if (packet instanceof PacketPlayInSteerVehicle) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getPluginManager().callEvent((Event) ((IPacketPlayInSteerVehicleEvent) event).initialize(packet, player));
                }
            }.runTask(Database.getInstance());
            return true;
        }
        return false;
    }
}

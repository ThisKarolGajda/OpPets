package me.opkarol.oppets.v1_18_1R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.packets.IPacketPlayInSteerVehicleEvent;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.OpUtils;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashSet;

public class Utils implements IUtils {
    private final Database database = Database.getInstance();

    @Override
    public void hideEntityFromServer(Player owner, int entityId) {
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            if (player1 != owner) {
                ((CraftPlayer) player1).getHandle().connection.send(new PacketPlayOutEntityDestroy(entityId));
            }
        });
    }

    @Override
    public void hideEntityFromPlayer(Player player, int entityId) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ((CraftPlayer) player).getHandle().connection.send(new PacketPlayOutEntityDestroy(entityId));
            }
        }.runTask(database.getPlugin());
    }

    @Override
    public void respawnPet(Pet pet, @NotNull Player player) {
        OpUtils.killPetFromPlayerUUID(player.getUniqueId());
        database.getOpPets().getEntityManager().spawnEntity(player, pet);
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

    @Override
    public Channel getPlayerChannel(Player player) {
        return ((CraftPlayer) player).getHandle().connection.connection.channel;
    }

    @Override
    public ChannelPipeline getPlayerPipeline(Player player) {
        return ((CraftPlayer) player).getHandle().connection.connection.channel.pipeline();
    }

    @Override
    public void rideEventRegister(Object event, Object packet, Player player) {
        if (packet instanceof PacketPlayInSteerVehicle) {
            Bukkit.getPluginManager().callEvent((Event) ((IPacketPlayInSteerVehicleEvent) event).initialize(packet, player));
        }
    }
}

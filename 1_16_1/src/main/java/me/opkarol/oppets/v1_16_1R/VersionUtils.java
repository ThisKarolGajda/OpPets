package me.opkarol.oppets.v1_16_1R;

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
import me.opkarol.oppets.utils.Utils;
import net.minecraft.server.v1_16_R1.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class VersionUtils implements IUtils {
    private final Database database = Database.getInstance();

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
        }.runTask(database.getPlugin());
    }

    @Override
    public void respawnPet(Pet pet, @NotNull Player player) {
        Utils.killPetFromPlayerUUID(player.getUniqueId());
        database.getOpPets().getEntityManager().spawnEntity(player, pet);
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
    public void rideEventRegister(Object event, Object packet, Player player) {
        if (packet instanceof PacketPlayInSteerVehicle) {
            Bukkit.getPluginManager().callEvent((Event) ((IPacketPlayInSteerVehicleEvent) event).initialize(packet, player));
        }
    }
}

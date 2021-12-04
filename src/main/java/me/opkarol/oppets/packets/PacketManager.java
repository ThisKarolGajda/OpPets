package me.opkarol.oppets.packets;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.events.versions.PacketPlayInSteerVehicleEvent_v1_16_3;
import me.opkarol.oppets.events.versions.PacketPlayInSteerVehicleEvent_v1_16_5;
import me.opkarol.oppets.events.versions.PacketPlayInSteerVehicleEvent_v1_17_1;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PacketManager implements Listener {
    private static String version;

    public PacketManager() {
        version = OpPets.getController().getVersion();
    }

    public static void removePlayer(Player player) {
        Channel channel;
        switch (version) {
            case "v1_17_R1" -> {
                channel = ((CraftPlayer) player).getHandle().b.a.k;
                channel.eventLoop().submit(() -> {
                    channel.pipeline().remove(player.getName());
                });
            }
            case "v1_16_R1" -> {
                channel = ((org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer) player).getHandle().playerConnection.a().channel;
                channel.eventLoop().submit(() -> {
                    channel.pipeline().remove(player.getName());
                });
            }
            case "v1_16_R2" -> {
                channel = ((org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer) player).getHandle().playerConnection.a().channel;
                channel.eventLoop().submit(() -> {
                    channel.pipeline().remove(player.getName());
                });
            }
            case "v1_16_R3" -> {
                channel = ((org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer) player).getHandle().playerConnection.a().channel;
                channel.eventLoop().submit(() -> {
                    channel.pipeline().remove(player.getName());
                });
            }
        }
    }

    public static void injectPlayer(@NotNull Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) {
                switch (version) {
                    case "v1_17_R1" -> {
                        PacketPlayInSteerVehicleEvent_v1_17_1 event;
                        event = (PacketPlayInSteerVehicleEvent_v1_17_1) OpPets.getController().getPacketEvent().initialize(packet, player);
                        Bukkit.getPluginManager().callEvent(event);
                    }
                    case "v1_16_R1" -> {
                        me.opkarol.oppets.events.packets.versions.PacketPlayInSteerVehicleEvent_v1_16_1 event;
                        event = (me.opkarol.oppets.events.packets.versions.PacketPlayInSteerVehicleEvent_v1_16_1) OpPets.getController().getPacketEvent().initialize(packet, player);
                        Bukkit.getPluginManager().callEvent(event);
                    }
                    case "v1_16_R2" -> {
                        PacketPlayInSteerVehicleEvent_v1_16_3 event;
                        event = (PacketPlayInSteerVehicleEvent_v1_16_3) OpPets.getController().getPacketEvent().initialize(packet, player);
                        Bukkit.getPluginManager().callEvent(event);
                    }
                    case "v1_16_R3" -> {
                        PacketPlayInSteerVehicleEvent_v1_16_5 event;
                        event = (PacketPlayInSteerVehicleEvent_v1_16_5) OpPets.getController().getPacketEvent().initialize(packet, player);
                        Bukkit.getPluginManager().callEvent(event);
                    }
                }

            }


        };
        ChannelPipeline pipeline;

        switch (version) {
            case "v1_17_R1" -> {
                pipeline = ((CraftPlayer) player).getHandle().b.a.k.pipeline();
                pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
            }
            case "v1_16_R1" -> {
                pipeline = ((org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer) player).getHandle().playerConnection.a().channel.pipeline();
                pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
            }
            case "v1_16_R2" -> {
                pipeline = ((org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer) player).getHandle().playerConnection.a().channel.pipeline();
                pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
            }
            case "v1_16_R3" -> {
                pipeline = ((org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer) player).getHandle().playerConnection.a().channel.pipeline();
                pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
            }
        }

    }


}

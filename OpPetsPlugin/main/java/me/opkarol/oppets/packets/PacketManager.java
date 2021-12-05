package me.opkarol.oppets.packets;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.interfaces.UtilsInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import v1_16_1R.PacketPlayInSteerVehicleEvent_v1_16_1;
import v1_16_3R.PacketPlayInSteerVehicleEvent_v1_16_3;
import v1_16_5R.PacketPlayInSteerVehicleEvent_v1_16_5;
import v1_17_1R.PacketPlayInSteerVehicleEvent_v1_17_1;

public class PacketManager implements Listener {
    private static String version;
    private static UtilsInterface utils;

    public PacketManager() {
        version = OpPets.getController().getVersion();
        utils = OpPets.getUtils();
    }

    public static void removePlayer(Player player) {
        Channel channel = utils.getPlayerChannel(player);
        channel.pipeline().remove(player.getName());
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
                        PacketPlayInSteerVehicleEvent_v1_16_1 event;
                        event = (PacketPlayInSteerVehicleEvent_v1_16_1) OpPets.getController().getPacketEvent().initialize(packet, player);
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
        ChannelPipeline pipeline = utils.getPlayerPipeline(player);
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

    }


}

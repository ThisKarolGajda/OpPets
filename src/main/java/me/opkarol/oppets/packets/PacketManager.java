package me.opkarol.oppets.packets;

import io.netty.channel.*;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.events.PacketPlayInSteerVehicleEvent;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class PacketManager implements Listener {

    public static void removePlayer(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().b.a.k;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
            return null;
        });
    }

    public static void injectPlayer(@NotNull Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) {
                try {
                    super.channelRead(channelHandlerContext, packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (packet instanceof PacketPlayInSteerVehicle){
                            PacketPlayInSteerVehicleEvent event = new PacketPlayInSteerVehicleEvent((PacketPlayInSteerVehicle) packet, player);
                            Bukkit.getPluginManager().callEvent(event);
                        }
                    }
                }.runTask(OpPets.getInstance());

            }

        };

        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().b.a.k.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

    }

}

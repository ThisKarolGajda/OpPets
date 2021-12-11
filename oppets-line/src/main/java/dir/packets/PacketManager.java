package dir.packets;

import dir.interfaces.PacketPlayInSteerVehicleEvent;
import dir.interfaces.UtilsInterface;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PacketManager implements Listener {
    private static UtilsInterface utils;

    public static void removePlayer(Player player) {
        Channel channel = (Channel) utils.getPlayerChannel(player);
        channel.pipeline().remove(player.getName());
    }

    public static void injectPlayer(@NotNull Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) {
                Bukkit.getPluginManager().callEvent((Event) ((PacketPlayInSteerVehicleEvent) getEvent()).initialize(packet, player));
            }
        };
        ChannelPipeline pipeline = (ChannelPipeline) utils.getPlayerPipeline(player);
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

    }

    private static Object event;


    public static Object getEvent() {
        return event;
    }

    public static void setEvent(Object event) {
        PacketManager.event = event;
    }

    public static void setUtils(UtilsInterface utils) {
        PacketManager.utils = utils;
    }
}

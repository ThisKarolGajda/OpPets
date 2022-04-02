package me.opkarol.oppets.packets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import me.opkarol.oppets.interfaces.IUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * The type Packet manager.
 */
public class PacketManager implements Listener {
    /**
     * The constant utils.
     */
    private static IUtils utils;
    /**
     * The constant event.
     */
    private static Object event;

    /**
     * Remove player.
     *
     * @param player the player
     */
    public static void removePlayer(Player player) {
        Channel channel = (Channel) utils.getPlayerChannel(player);
        channel.pipeline().remove(player.getName());
    }

    /**
     * Inject player.
     *
     * @param player the player
     */
    public static void injectPlayer(@NotNull Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) {
                utils.rideEventRegister(getEvent(), packet, player);
            }
        };
        ChannelPipeline pipeline = (ChannelPipeline) utils.getPlayerPipeline(player);
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

    }

    /**
     * Gets event.
     *
     * @return the event
     */
    public static Object getEvent() {
        return event;
    }

    /**
     * Sets event.
     *
     * @param event the event
     */
    public static void setEvent(Object event) {
        PacketManager.event = event;
    }

    /**
     * Sets utils.
     *
     * @param utils the utils
     */
    public static void setUtils(IUtils utils) {
        PacketManager.utils = utils;
    }
}

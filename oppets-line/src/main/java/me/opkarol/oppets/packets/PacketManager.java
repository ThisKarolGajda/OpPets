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
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PacketManager implements Listener {
    private static IUtils utils = Database.getInstance().getUtils();
    private static Object event;

    public static void removeRider(Player player) {
        Channel channel = (Channel) utils.getPlayerChannel(player);
        channel.pipeline().remove(player.getName());
    }

    public static void injectRider(@NotNull Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) {
                utils.rideEventRegister(getEvent(), packet, player);
            }
        };

        ChannelPipeline pipeline = (ChannelPipeline) utils.getPlayerPipeline(player);
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
    }

    public static Object getEvent() {
        return event;
    }

    public static void setEvent(Object event) {
        PacketManager.event = event;
    }
}

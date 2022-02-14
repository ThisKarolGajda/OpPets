package me.opkarol.oppets.listeners;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ChatReceiver implements Listener {
    private static final HashMap<UUID, BiConsumer<UUID, String>> handlers = new HashMap<>();

    public static void chatRegister(@NotNull Player player, String message, BiConsumer<UUID, String> consumer) {
        player.closeInventory();
        player.sendMessage(FormatUtils.formatMessage(message));
        handlers.put(player.getUniqueId(), consumer);
    }

    @EventHandler
    public void playerChat(@NotNull AsyncPlayerChatEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        if (handlers.containsKey(player)) {
            handlers.get(player).accept(player, event.getMessage());
            handlers.remove(player);
            event.setCancelled(true);
        }
    }
}

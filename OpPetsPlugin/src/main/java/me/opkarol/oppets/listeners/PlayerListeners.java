package me.opkarol.oppets.listeners;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.Utils;
import me.opkarol.oppets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerListeners implements Listener {
    private final Database database = Database.getInstance();

    @EventHandler
    public void playerConsume(PlayerItemConsumeEvent event) {
        InventoryHolder holder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        event.setCancelled(holder instanceof IHolder);
    }

    @EventHandler
    public void playerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        database.getDatabase().databaseUUIDSaver(playerUUID, true);
        Utils.killPetFromPlayerUUID(playerUUID);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Pet currentPet = database.getDatabase().getCurrentPet(uuid);
        Utils.summonPet(currentPet, player, true);
        database.hideEntity(player);
    }

    @EventHandler
    public void serverLoad(@NotNull ServerLoadEvent event) {
        if (event.getType().equals(ServerLoadEvent.LoadType.RELOAD)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Utils.summonPet(database.getDatabase().getCurrentPet(player.getUniqueId()), player, true);
            }
        }
    }
}

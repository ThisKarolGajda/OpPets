package me.opkarol.oppets.listeners;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The type Player join.
 */
public class PlayerJoin implements Listener {
    /**
     * The Database.
     */
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());

    /**
     * Player join.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (database.getDatabase().getPetList(uuid) == null) {
            database.getDatabase().setPets(uuid, new ArrayList<>());
            return;
        }
        Pet currentPet = database.getDatabase().getCurrentPet(uuid);
        if (currentPet != null) {
            database.getOpPets().getCreator().spawnMiniPet(currentPet, player);
        }
    }

    /**
     * Player join 2.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin2(@NotNull PlayerJoinEvent event) {
        database.getDatabase().getActivePetMap().keySet().forEach(uuid -> {
            if (Bukkit.getPlayer(uuid) == null) {
                return;
            }
            if (database.getDatabase().getCurrentPet(uuid) == null) {
                return;
            }
            if (!database.getDatabase().getCurrentPet(uuid).isVisibleToOthers()) {
                if (database.getDatabase().getCurrentPet(uuid).getOwnUUID() == null) {
                    return;
                }
                database.getOpPets().getUtils().hideEntityFromPlayer(event.getPlayer(), database.getDatabase().getIdPet(database.getDatabase().getCurrentPet(uuid).getOwnUUID()));
            }
        });
    }
}

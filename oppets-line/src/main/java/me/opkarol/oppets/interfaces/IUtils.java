package me.opkarol.oppets.interfaces;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface IUtils {

    @Nullable Entity getEntityByUniqueId(UUID uniqueId);

    void hideEntityFromServer(Player owner, int entityId);

    void hideEntityFromPlayer(Player player, int entityId);

    String getVanillaPetName(@NotNull EntityType entityType);

    void killPetFromPlayerUUID(UUID playerUUID);

    void respawnPet(Object pet, @NotNull Player player);

    Object getVersion();

    void removePathfinders(Object var1, Object var2);

    Object getPlayerChannel(Player player);

    Object getPlayerPipeline(Player player);

    /**
     * Used to register a player ride event.
     * Since it's version specific, all parameters are objects which are later on assigned.
     *
     * @param event  specific version event object
     * @param packet received packet
     * @param player owner
     */
    void rideEventRegister(Object event, Object packet, Player player);
}

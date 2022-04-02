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

/**
 * The interface Utils.
 */
public interface IUtils {

    /**
     * Gets entity by unique id.
     *
     * @param uniqueId the unique id
     * @return the entity by unique id
     */
    @Nullable Entity getEntityByUniqueId(UUID uniqueId);

    /**
     * Hide entity from server.
     *
     * @param owner    the owner
     * @param entityId the entity id
     */
    void hideEntityFromServer(Player owner, int entityId);

    /**
     * Hide entity from player.
     *
     * @param player   the player
     * @param entityId the entity id
     */
    void hideEntityFromPlayer(Player player, int entityId);

    /**
     * Gets vanilla pet name.
     *
     * @param entityType the entity type
     * @return the vanilla pet name
     */
    String getVanillaPetName(@NotNull EntityType entityType);

    /**
     * Kill pet from player uuid.
     *
     * @param playerUUID the player uuid
     */
    void killPetFromPlayerUUID(UUID playerUUID);

    /**
     * Respawn pet.
     *
     * @param pet    the pet
     * @param player the player
     */
    void respawnPet(Object pet, @NotNull Player player);

    /**
     * Gets version.
     *
     * @return the version
     */
    Object getVersion();

    /**
     * Remove pathfinders.
     *
     * @param var1 the var 1
     * @param var2 the var 2
     */
    void removePathfinders(Object var1, Object var2);

    /**
     * Gets player channel.
     *
     * @param player the player
     * @return the player channel
     */
    Object getPlayerChannel(Player player);

    /**
     * Gets player pipeline.
     *
     * @param player the player
     * @return the player pipeline
     */
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

package me.opkarol.oppets.events;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Thrown when a prestige of a pet is changed.
 * Can be cancelled.
 */
public class PrestigeChangeEvent extends Event implements Cancellable {
    /**
     * The constant HANDLERS_LIST.
     */
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    /**
     * The Pet.
     */
    private final Pet pet;
    /**
     * The Player.
     */
    private final Player player;
    /**
     * The Is cancelled.
     */
    private boolean isCancelled;

    /**
     * Instantiates a new Prestige change event.
     *
     * @param player the player
     * @param pet    the pet
     */
    public PrestigeChangeEvent(Player player, Pet pet) {
        this.isCancelled = false;
        this.player = player;
        this.pet = pet;
    }

    /**
     * Gets handler list.
     *
     * @return the handler list
     */
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    /**
     * Is cancelled boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Sets cancelled.
     *
     * @param cancelled the cancelled
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    /**
     * Gets handlers.
     *
     * @return the handlers
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    /**
     * Gets pet.
     *
     * @return the pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

}

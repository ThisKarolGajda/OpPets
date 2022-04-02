package me.opkarol.oppets.interfaces;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The interface Packet play in steer vehicle event.
 */
public interface IPacketPlayInSteerVehicleEvent {

    /**
     * Gets handler list.
     *
     * @return the handler list
     */
    @Contract(pure = true)
    static @Nullable HandlerList getHandlerList() {
        return null;
    }

    /**
     * Gets handlers.
     *
     * @return the handlers
     */
    @NotNull
    HandlerList getHandlers();

    /**
     * Initialize packet play in steer vehicle event.
     *
     * @param var1 the var 1
     * @param var2 the var 2
     * @return the packet play in steer vehicle event
     */
    IPacketPlayInSteerVehicleEvent initialize(Object var1, Object var2);

    /**
     * Gets packet.
     *
     * @return the packet
     */
    Object getPacket();

    /**
     * Gets player.
     *
     * @return the player
     */
    Object getPlayer();

    /**
     * Gets event.
     *
     * @return the event
     */
    Object getEvent();

    /**
     * Gets vehicle keys.
     *
     * @return the vehicle keys
     */
    Object getVehicleKeys();

    /**
     * Gets vehicle.
     *
     * @return the vehicle
     */
    Object getVehicle();
}

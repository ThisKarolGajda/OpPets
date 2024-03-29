package me.opkarol.oppets.packets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IPacketPlayInSteerVehicleEvent {
    @Nullable
    static HandlerList getHandlerList() {
        return null;
    }

    @NotNull
    HandlerList getHandlers();

    IPacketPlayInSteerVehicleEvent initialize(Object var1, Object var2);

    Object getPacket();

    Object getPlayer();

    Object getEvent();

    Object getVehicleKeys();

    Object getVehicle();
}

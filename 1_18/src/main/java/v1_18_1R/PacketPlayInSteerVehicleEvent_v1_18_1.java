package v1_18_1R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.interfaces.IPacketPlayInSteerVehicleEvent;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PacketPlayInSteerVehicleEvent_v1_18_1 extends Event implements IPacketPlayInSteerVehicleEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private PacketPlayInSteerVehicle packet;
    private Player player;

    public PacketPlayInSteerVehicleEvent_v1_18_1() {
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public IPacketPlayInSteerVehicleEvent initialize(Object obj1, Object obj2) {
        this.packet = (PacketPlayInSteerVehicle) obj1;
        this.player = (Player) obj2;
        return this;
    }

    public PacketPlayInSteerVehicle getPacket() {
        return this.packet;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Event getEvent() {
        return this;
    }

    public List<VehicleKey> getVehicleKeys() {
        List<VehicleKey> keys = new ArrayList<>();
        PacketPlayInSteerVehicle p = this.packet;
        if (p.c() > 0) {
            keys.add(VehicleKey.FORWARDS);
        }
        if (p.c() < 0) {
            keys.add(VehicleKey.BACKWARDS);
        }
        if (p.b() > 0) {
            keys.add(VehicleKey.LEFT);
        }
        if (p.b() < 0) {
            keys.add(VehicleKey.RIGHT);
        }
        if (p.d()) {
            keys.add(VehicleKey.JUMPING);
        }
        if (p.e()) {
            keys.add(VehicleKey.SHIFTING);
        }
        return keys;
    }

    public boolean isJumping() {
        return this.packet.d();
    }

    public boolean isSneaking() {
        return this.packet.e();
    }

    public Entity getVehicle() {
        return this.player.getVehicle();
    }

    public enum VehicleKey {
        FORWARDS,
        BACKWARDS,
        RIGHT,
        LEFT,
        JUMPING,
        SHIFTING
    }
}
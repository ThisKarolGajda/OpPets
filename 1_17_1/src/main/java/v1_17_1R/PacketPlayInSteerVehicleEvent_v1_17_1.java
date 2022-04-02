package v1_17_1R;

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

/**
 * The type Packet play in steer vehicle event v 1 17 1.
 */
public class PacketPlayInSteerVehicleEvent_v1_17_1 extends Event implements IPacketPlayInSteerVehicleEvent {
    /**
     * The constant HANDLERS.
     */
    private static final HandlerList HANDLERS = new HandlerList();
    /**
     * The Packet.
     */
    private PacketPlayInSteerVehicle packet;
    /**
     * The Player.
     */
    private Player player;

    /**
     * Gets handler list.
     *
     * @return the handler list
     */
    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Gets handlers.
     *
     * @return the handlers
     */
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Initialize packet play in steer vehicle event.
     *
     * @param obj1 the obj 1
     * @param obj2 the obj 2
     * @return the packet play in steer vehicle event
     */
    public IPacketPlayInSteerVehicleEvent initialize(Object obj1, Object obj2) {
        this.packet = (PacketPlayInSteerVehicle) obj1;
        this.player = (Player) obj2;
        return this;
    }

    /**
     * Gets packet.
     *
     * @return the packet
     */
    public PacketPlayInSteerVehicle getPacket() {
        return this.packet;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets event.
     *
     * @return the event
     */
    public Event getEvent() {
        return this;
    }

    /**
     * Gets vehicle keys.
     *
     * @return the vehicle keys
     */
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

    /**
     * Is jumping boolean.
     *
     * @return the boolean
     */
    public boolean isJumping() {
        return this.packet.d();
    }

    /**
     * Is sneaking boolean.
     *
     * @return the boolean
     */
    public boolean isSneaking() {
        return this.packet.e();
    }

    /**
     * Gets vehicle.
     *
     * @return the vehicle
     */
    public Entity getVehicle() {
        return this.player.getVehicle();
    }

    /**
     * The enum Vehicle key.
     */
    public enum VehicleKey {
        /**
         * Forwards vehicle key.
         */
        FORWARDS,
        /**
         * Backwards vehicle key.
         */
        BACKWARDS,
        /**
         * Right vehicle key.
         */
        RIGHT,
        /**
         * Left vehicle key.
         */
        LEFT,
        /**
         * Jumping vehicle key.
         */
        JUMPING,
        /**
         * Shifting vehicle key.
         */
        SHIFTING
    }
}
package v1_17_1R;

import dir.interfaces.PacketPlayInSteerVehicleEvent;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PacketPlayInSteerVehicleEvent_v1_17_1 extends Event implements PacketPlayInSteerVehicleEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private PacketPlayInSteerVehicle packet;
    private Player player;

    public PacketPlayInSteerVehicleEvent_v1_17_1() {
    }

    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public PacketPlayInSteerVehicleEvent initialize(Object obj1, Object obj2) {
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
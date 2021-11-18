package me.opkarol.oppets.events;

import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PacketPlayInSteerVehicleEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final PacketPlayInSteerVehicle packet;
    private final Player player;

    public PacketPlayInSteerVehicleEvent(PacketPlayInSteerVehicle packet, Player player) {
        this.packet = packet;
        this.player = player;
    }

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public PacketPlayInSteerVehicle getPacket() {
        return packet;
    }

    public Player getPlayer() {
        return player;
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

    public boolean isKeyPressed(VehicleKey key) {
        boolean b;
        PacketPlayInSteerVehicle p = this.packet;
        b = switch (key) {
            case FORWARDS -> p.c() > 0;
            case BACKWARDS -> p.c() < 0;
            case LEFT -> p.b() > 0;
            case RIGHT -> p.b() < 0;
            case JUMPING -> p.d();
            case SHIFTING -> p.e();
        };
        return b;
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

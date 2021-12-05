package me.opkarol.oppets.listeners.packets;

import me.opkarol.oppets.events.packets.versions.PacketPlayInSteerVehicleEvent_v1_18_1;
import me.opkarol.oppets.packets.PacketManager;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class PlayerSteerVehicleEvent_v1_18_1 implements Listener {
    public PlayerSteerVehicleEvent_v1_18_1() {
    }

    @EventHandler
    public void playerSteerVehicle(PacketPlayInSteerVehicleEvent_v1_18_1 event) {
        Player player = event.getPlayer();
        PacketPlayInSteerVehicle packet = event.getPacket();
        Entity vehicle = player.getVehicle();
        float side = packet.b();
        float forw = packet.c();
        boolean jump = event.isJumping();
        boolean sneaking = event.isSneaking();

        assert vehicle != null;

        if (sneaking) {
            vehicle.removePassenger(player);
            PacketManager.removePlayer(player);
        }

        if (jump && vehicle.isOnGround()) {
            vehicle.setVelocity(vehicle.getVelocity().add(new Vector(0.0D, 0.5D, 0.0D)));
        }

        Vector vel = getVelocityVector(vehicle.getVelocity(), player, side, forw);
        vehicle.setVelocity(vel);
    }

    @NotNull
    private static Vector getVelocityVector(@NotNull Vector vector, Player player, float side, float forw) {
        vector.setX(0.0D);
        vector.setZ(0.0D);
        Vector mot = new Vector((double)forw * -1.0D, 0.0D, (double)side);
        if (mot.length() > 0.0D) {
            mot.rotateAroundY(Math.toRadians((double)(player.getLocation().getYaw() * -1.0F + 90.0F)));
            mot.normalize().multiply(0.25F);
        }

        return mot.add(vector);
    }
}

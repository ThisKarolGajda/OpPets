package me.opkarol.oppets.listeners;

import me.opkarol.oppets.events.PacketPlayInSteerVehicleEvent;
import me.opkarol.oppets.packets.PacketManager;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PlayerSteerVehicle implements Listener {

    @EventHandler
    public void playerSteerVehicle(@NotNull PacketPlayInSteerVehicleEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getVehicle();
        event.getVehicleKeys().forEach(vehicleKey -> {
            if (vehicleKey.equals(PacketPlayInSteerVehicleEvent.VehicleKey.SHIFTING)){
                PacketManager.removePlayer(player);
                player.sendMessage("ai turned on");
                //setting ai on
            }
            Location location = entity.getLocation();
            location.setYaw(player.getLocation().getYaw());
            location.setPitch(player.getLocation().getPitch());
            entity.teleport(location);

            player.sendMessage(entity.getLocation().getYaw() + " yaw " + player.getLocation().getYaw());
            player.sendMessage(entity.getLocation().getPitch() + " pitch " + player.getLocation().getPitch());
            switch (vehicleKey){
                case BACKWARDS -> {
                    if (entity.isOnGround()) {
                        entity.setVelocity(player.getLocation().getDirection().multiply(-0.2));
                    }
                }
                case FORWARDS -> {
                    if (entity.isOnGround()) {
                        entity.setVelocity(player.getLocation().getDirection().multiply(0.3));
                    }
                }
                case JUMPING -> {
                    if (!entity.isOnGround()) {
                        EntityLiving cle = ((CraftLivingEntity) entity).getHandle();
                    }
                    player.sendMessage("jump");
                }

            }


        });


    }
}

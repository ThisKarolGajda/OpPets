package me.opkarol.oppets.particles;

import me.opkarol.oppets.OpPets;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticlesManager {

    public static void spawnLevelUpPetEffect(Player player, Entity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int degree = 0; degree < 360; degree++) {
                    Location location = entity.getLocation();
                    double radians = Math.toRadians(degree);
                    double x = Math.cos(radians);
                    double z = Math.sin(radians);
                    location.add(x, 0, z);
                    player.spawnParticle(Particle.SPELL, location, 3);
                    location.subtract(x, 0, z);
                }
            }
        }.runTaskAsynchronously(OpPets.getInstance());
    }
}

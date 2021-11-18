package me.opkarol.oppets.particles;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;
import xyz.xenondevs.particle.task.TaskManager;

import java.util.ArrayList;
import java.util.List;

public class ParticlesManager {

    public static void getParticleDisplayed(@NotNull Player player){
        new ParticleBuilder(ParticleEffect.REDSTONE, player.getLocation())
                .setParticleData(new RegularColor(255,255,0))
                .display(player);
    }

    public static int startCubeTask(World world, @NotNull Location start, @NotNull Location end) {
        List<Object> packets = new ArrayList<>();
        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.FLAME);
        for (int x = start.getBlockX(); x <= end.getBlockX(); ++x) {
            for (int y = start.getBlockY(); y <= end.getBlockY(); ++y) {
                for (int z = start.getBlockZ(); z <= end.getBlockZ(); ++z) {
                    packets.add(particle.setLocation(new Location(world, x, y, z)).toPacket());
                }
            }
        }
        return TaskManager.startWorldTask(packets, 5, world); // Start a new task and return the id
    }
}

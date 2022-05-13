package me.opkarol.oppets.particles;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.OpPets;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticlesManager {
    private final Database database = Database.getInstance();

    public void spawnLevelUpPetEffect(Player player, Entity entity) {
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
        }.runTaskAsynchronously(database.getPlugin());
    }

    public void prestigeChangeEffect(Player player, Entity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int degree = 0; degree < 360; degree++) {
                    Location location = entity.getLocation();
                    double radians = Math.toRadians(degree);
                    double x = Math.cos(radians);
                    double z = Math.sin(radians);
                    location.add(x, 0, z);
                    player.spawnParticle(Particle.CLOUD, location, 3);
                    location.subtract(x, 0, z);
                }
            }
        }.runTaskAsynchronously(database.getPlugin());
    }
}

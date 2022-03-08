package me.opkarol.oppets.boosters;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.OpPets;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Booster {
    private final String name;
    private final double multiplier;
    private final long timeToEnd;
    private boolean running;
    private BOOSTER_TYPE type;
    private final String owner;

    public Booster(String name, double multiplier, long timeToEnd, boolean running, BOOSTER_TYPE type) {
        this.name = name;
        this.multiplier = multiplier;
        this.timeToEnd = timeToEnd;
        this.running = running;
        this.type = type;
        this.owner = "SERVER";
    }

    @Contract(pure = true)
    public Booster(String name, double multiplier, long timeToEnd, boolean running, BOOSTER_TYPE type, @NotNull UUID owner) {
        this.name = name;
        this.multiplier = multiplier;
        this.timeToEnd = timeToEnd;
        this.running = running;
        this.type = type;
        this.owner = owner.toString();
    }

    public void run() {
        running = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                running = false;
            }
        }.runTaskLaterAsynchronously(OpPets.getInstance(), 20 * timeToEnd);
    }

    public String getName() {
        return name;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public long getTimeToEnd() {
        return timeToEnd;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public BOOSTER_TYPE getType() {
        return type;
    }

    public void setType(BOOSTER_TYPE type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public enum BOOSTER_TYPE {
        SERVER, PLAYER, PET
    }
    // SERVER: SERVER
    // PLAYER: PLAYER_UUID
    // PET: //TODO
}

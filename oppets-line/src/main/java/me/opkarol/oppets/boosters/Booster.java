package me.opkarol.oppets.boosters;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.broadcasts.Broadcast;
import me.opkarol.oppets.broadcasts.BroadcastManager;
import me.opkarol.oppets.cache.StringsCache;
import me.opkarol.oppets.databases.Database;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * The type Booster.
 */
public class Booster {
    /**
     * The Name.
     */
    private final String name;
    /**
     * The Multiplier.
     */
    private final double multiplier;
    /**
     * The Time to end.
     */
    private final long timeToEnd;
    /**
     * The Owner.
     */
    private final String owner;
    /**
     * The Message.
     */
    private final String message;
    /**
     * The Running.
     */
    private boolean running;
    /**
     * The Type.
     */
    private BOOSTER_TYPE type;
    /**
     * The Database.
     */
    private final Database database;

    /**
     * Instantiates a new Booster.
     *
     * @param name       the name
     * @param multiplier the multiplier
     * @param timeToEnd  the time to end
     * @param running    the running
     * @param type       the type
     * @param database   the database
     */
    public Booster(String name, double multiplier, long timeToEnd, boolean running, BOOSTER_TYPE type, Database database) {
        this.name = name;
        this.multiplier = multiplier;
        this.timeToEnd = timeToEnd * 20;
        this.running = running;
        this.type = type;
        this.database = database;
        this.message = StringsCache.boosterEnabledMessage;
        this.owner = StringsCache.defaultServerValue;
    }

    /**
     * Instantiates a new Booster.
     *
     * @param name       the name
     * @param multiplier the multiplier
     * @param timeToEnd  the time to end
     * @param running    the running
     * @param type       the type
     * @param owner      the owner
     * @param database   the database
     */
    @Contract(pure = true)
    public Booster(String name, double multiplier, long timeToEnd, boolean running, BOOSTER_TYPE type, @NotNull UUID owner, Database database) {
        this.name = name;
        this.multiplier = multiplier;
        this.timeToEnd = timeToEnd * 20;
        this.running = running;
        this.type = type;
        this.owner = owner.toString();
        this.database = database;
        this.message = StringsCache.boosterEnabledMessage;
    }

    /**
     * Run.
     */
    public void run() {
        running = true;
        BroadcastManager manager = database.getOpPets().getBroadcastManager();
        List<Broadcast> broadcast = manager.getBroadcast(Broadcast.BROADCAST_TYPE.BOOSTER);
        if (owner.equalsIgnoreCase(StringsCache.defaultServerValue) && broadcast.get(0) != null) {
            broadcast.get(0).broadcastMessage(message.replace("%time%", String.valueOf(timeToEnd / 20)).replace("%name%", name));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                running = false;
            }
        }.runTaskLaterAsynchronously(database.getPlugin(), timeToEnd);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets multiplier.
     *
     * @return the multiplier
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * Is running boolean.
     *
     * @return the boolean
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public BOOSTER_TYPE getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(BOOSTER_TYPE type) {
        this.type = type;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * The enum Booster type.
     */
    public enum BOOSTER_TYPE {
        /**
         * Server booster type.
         */
        SERVER,
        /**
         * Player booster type.
         */
        PLAYER
    }
}

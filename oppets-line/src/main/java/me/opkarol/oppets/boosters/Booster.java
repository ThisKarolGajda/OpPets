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
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.api.files.MessagesHolder;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class Booster {
    private final MessagesHolder.Messages messages = MessagesHolder.getInstance().getMessages();
    private final String name,
    owner,
    message = messages.getString("Formats.boosterEnabledMessage"),
    defaultOwner = messages.getString("Formats.defaultServerValue");
    private final double multiplier;
    private final long timeToEnd;
    private boolean running;
    private BOOSTER_TYPE type;
    private final Database database;

    public Booster(String name, double multiplier, long timeToEnd, boolean running, BOOSTER_TYPE type, Database database) {
        this.name = name;
        this.multiplier = multiplier;
        this.timeToEnd = timeToEnd * 20;
        this.running = running;
        this.type = type;
        this.database = database;
        this.owner = defaultOwner;
    }

    public Booster(String name, double multiplier, long timeToEnd, boolean running, BOOSTER_TYPE type, @NotNull UUID owner, Database database) {
        this.name = name;
        this.multiplier = multiplier;
        this.timeToEnd = timeToEnd * 20;
        this.running = running;
        this.type = type;
        this.owner = owner.toString();
        this.database = database;
    }

    public void run() {
        running = true;
        BroadcastManager manager = database.getOpPets().getBroadcastManager();
        List<Broadcast> broadcast = manager.getBroadcast(Broadcast.BROADCAST_TYPE.BOOSTER);
        if (owner.equalsIgnoreCase(defaultOwner) && broadcast.get(0) != null) {
            broadcast.get(0).broadcastMessage(message.replace("%time%", String.valueOf(timeToEnd / 20)).replace("%name%", name));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                running = false;
            }
        }.runTaskLaterAsynchronously(database.getPlugin(), timeToEnd);
    }

    public String getName() {
        return name;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public boolean isRunning() {
        return running;
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
        SERVER,
        PLAYER
    }
}

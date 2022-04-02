package me.opkarol.oppets.broadcasts;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.Bukkit;

/**
 * The type Broadcast.
 */
public class Broadcast {
    /**
     * The Prefix.
     */
    private final String prefix;
    /**
     * The Format.
     */
    private final String format;
    /**
     * The Type.
     */
    private final BROADCAST_TYPE type;

    /**
     * Instantiates a new Broadcast.
     *
     * @param prefix the prefix
     * @param format the format
     * @param type   the type
     */
    public Broadcast(String prefix, String format, BROADCAST_TYPE type) {
        this.prefix = prefix;
        this.format = format;
        this.type = type;
    }

    /**
     * Broadcast message.
     *
     * @param message the message
     */
    public void broadcastMessage(String message) {
        Bukkit.broadcastMessage(FormatUtils.formatMessage(format.replace("%prefix%", prefix).replace("%message%", message).replace("%type%", type.name())));
    }

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Gets format.
     *
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public BROADCAST_TYPE getType() {
        return type;
    }

    /**
     * The enum Broadcast type.
     */
    public enum BROADCAST_TYPE {
        /**
         * Information broadcast type.
         */
        INFORMATION,
        /**
         * Warning broadcast type.
         */
        WARNING,
        /**
         * Announcement broadcast type.
         */
        ANNOUNCEMENT,
        /**
         * Booster broadcast type.
         */
        BOOSTER
    }
}

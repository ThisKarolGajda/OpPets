package me.opkarol.oppets.broadcasts;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.utils.external.FormatUtils;
import org.bukkit.Bukkit;

public class Broadcast {
    private final String prefix;
    private final String format;
    private final BROADCAST_TYPE type;

    public Broadcast(String prefix, String format, BROADCAST_TYPE type) {
        this.prefix = prefix;
        this.format = format;
        this.type = type;
    }

    public void broadcastMessage(String message) {
        Bukkit.broadcastMessage(FormatUtils.formatMessage(format.replace("%prefix%", prefix).replace("%message%", message).replace("%type%", type.name())));
    }

    public String getPrefix() {
        return prefix;
    }

    public String getFormat() {
        return format;
    }

    public BROADCAST_TYPE getType() {
        return type;
    }

    public enum BROADCAST_TYPE {
        INFORMATION,
        WARNING,
        ANNOUNCEMENT,
        BOOSTER
    }
}

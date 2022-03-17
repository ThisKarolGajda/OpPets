package dir.broadcasts;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.utils.ConfigUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BroadcastManager {
    private final List<Broadcast> broadcastList;
    private final String format;

    public BroadcastManager() {
        this.format = ConfigUtils.getString("Formats.broadcastFormat");
        broadcastList = new ArrayList<>();
        addDefaultBroadcasts();
    }

    public void addBroadcast(String prefix, String format, Broadcast.BROADCAST_TYPE type) {
        broadcastList.add(new Broadcast(prefix, format, type));
    }

    public void broadcastTemp(String prefix, String format, Broadcast.BROADCAST_TYPE type, String message) {
        new Broadcast(prefix, format, type).broadcastMessage(message);
    }

    public void removeBroadcast(Broadcast.@NotNull BROADCAST_TYPE type) {
        broadcastList.removeIf(broadcast -> type.equals(broadcast.type));
    }

    public List<Broadcast> getBroadcast(Broadcast.@NotNull BROADCAST_TYPE type) {
        return broadcastList.stream().filter(broadcast -> broadcast.type.equals(type)).collect(Collectors.toList());
    }

    public List<Broadcast> getBroadcastList() {
        return broadcastList;
    }

    public String getFormat() {
        return format;
    }

    public void addDefaultBroadcasts() {
        addBroadcast("*", format, Broadcast.BROADCAST_TYPE.BOOSTER);
    }
}

package me.opkarol.oppets.files;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.utils.ConfigUtils;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Messages extends ConfigUtils {
    private static HashMap<String, String> map;

    /**
     * Returns a formatted message which is taken from hash map, which values were saved at the onEnable method.
     *
     * @param name string object from which map will try to get a result
     * @return formatted string message / basic message
     */
    public static @NotNull String stringMessage(String name) {
        if (map == null) return name;
        if (map.containsKey(name)) return FormatUtils.formatMessage(map.get(name));
        try {
            throw new Exception("Database error - cannot find type: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public Messages onEnable() {
        map = new HashMap<>();
        setSection("Commands.");
        setSection("AnvilInventories.RenameInventory.");
        setSection("AnvilInventories.DeleteInventory.");
        setSection("AnvilInventories.PrestigeInventory.");
        setSection("Messages.");
        return this;
    }

    private void setSection(@NotNull String value) {
        ConfigurationSection section = Database.getInstance().getConfig().getConfigurationSection(value.substring(0, value.length() - 1));
        if (section == null) {
            return;
        }
        for (String key : section.getKeys(false)) {
            setPath(value, key);
        }

    }

    private void setPath(String path, String value) {
        map.put(value, getString(path + value));
    }

}

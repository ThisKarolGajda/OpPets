package me.opkarol.oppets.files;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * The type Messages.
 */
public class Messages {
    /**
     * The Map.
     */
    private HashMap<String, String> map;
    /**
     * The Configuration.
     */
    private final FileConfiguration configuration;

    /**
     * Instantiates a new Messages.
     *
     * @param configuration the configuration
     */
    public Messages(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Returns a formatted message which is taken from hash map, which values were saved at the onEnable method.
     *
     * @param name string object from which map will try to get a result
     * @return formatted string message / basic message
     */
    public @NotNull String stringMessage(String name) {
        if (map == null) {
            return name;
        }
        if (map.containsKey(name)) {
            return FormatUtils.formatMessage(map.get(name));
        }
        String configValue = getConfiguration().getString(name);
        if (configValue != null) {
            return FormatUtils.formatMessage(configValue);
        }
        try {
            throw new Exception("Database error - cannot find type: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * On enable messages.
     *
     * @return the messages
     */
    public Messages onEnable() {
        map = new HashMap<>();
        setSection("Commands.");
        setSection("AnvilInventories.RenameInventory.");
        setSection("AnvilInventories.DeleteInventory.");
        setSection("AnvilInventories.PrestigeInventory.");
        setSection("Messages.");
        return this;
    }

    /**
     * Sets section.
     *
     * @param value the value
     */
    private void setSection(@NotNull String value) {
        ConfigurationSection section = getConfiguration().getConfigurationSection(value.substring(0, value.length() - 1));
        if (section == null) {
            return;
        }
        for (String key : section.getKeys(false)) {
            setPath(value, key);
        }
    }

    /**
     * Sets path.
     *
     * @param path  the path
     * @param value the value
     */
    private void setPath(String path, String value) {
        map.put(value, configuration.getString(path + value));
    }

    /**
     * Gets configuration.
     *
     * @return the configuration
     */
    public FileConfiguration getConfiguration() {
        return configuration;
    }
}

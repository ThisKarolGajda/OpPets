package me.opkarol.oppets.files.manager;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.map.OpMap;
import me.opkarol.oppets.databases.external.APIDatabase;
import me.opkarol.oppets.exceptions.Exception;
import me.opkarol.oppets.exceptions.ExceptionLogger;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.utils.external.MathUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IConfigFile<K> {
    private final YmlConfiguration configuration;
    private final OpMap<String, K> map;

    public IConfigFile(String name) {
        map = new OpMap<>();
        configuration = new YmlConfiguration(name);
    }

    public IConfigFile(String name, boolean readOnly) {
        map = new OpMap<>();
        configuration = new YmlConfiguration(name, readOnly);
    }

    public void createConfig() {
        configuration.createConfig();
    }

    public void addValue(String path, K object) {
        map.set(path, object);
    }

    public void reloadData() {
        configuration.reload();
    }

    public YmlConfiguration getYmlConfiguration() {
        return configuration;
    }

    public OpMap<String, K> getMap() {
        return map;
    }

    public FileConfiguration getConfiguration() {
        return configuration.getConfig();
    }

    public Optional<ConfigurationSection> getConfigurationSection(String path) {
        return Optional.ofNullable(getConfiguration().getConfigurationSection(path));
    }

    public boolean containsObject(String path) {
        return getConfiguration().get(path) != null;
    }

    public boolean containsObjects(String prefix, String... values) {
        return Arrays.stream(values).allMatch(s -> containsObject(prefix + s));
    }

    public void addSectionToMap(boolean includePaths, @NotNull String... values) {
        if (values == null || values.length == 0) {
            return;
        }
        for (String s : values) {
            Optional<ConfigurationSection> section = getConfigurationSection(s.endsWith(".") ? MathUtils.substringFromEnd(s, 1) : s);
            section.ifPresent(section1 -> section1.getKeys(includePaths).forEach(key -> {
                K obj = (K) getConfiguration().get(s + "." + key);
                if (obj instanceof List) {
                    addValue(includePaths, s + "." + key, (K) StringTransformer.getListStringsFromObject(obj));
                } else {
                    addValue(includePaths, s + "." + key, obj);
                }
            }));
        }
    }

    private void addValue(boolean b, String path, K obj) {
        if (b) {
            addValue(path, obj);
        }
        addValue(path, obj);
    }


    public String getPath() {
        return getYmlConfiguration().getYamlConfig().getPath();
    }
}

class YmlConfiguration {
    private File ymlConfiguration;
    private File pluginDataFolder;
    private String name;
    private final Plugin plugin;
    private FileConfiguration config;

    public YmlConfiguration(String name) {
        this.plugin = APIDatabase.getInstance().getPlugin();
        if (name == null) {
            ExceptionLogger.getInstance().throwException("Provided name is invalid: null");
            return;
        }
        if (name.contains(".")) {
            this.name = name;
        } else {
            this.name = name + ".yml";
        }
        this.pluginDataFolder = plugin.getDataFolder();
        ymlConfiguration = new File(pluginDataFolder, this.name);
        config = YamlConfiguration.loadConfiguration(ymlConfiguration);
    }

    public YmlConfiguration(String name, boolean readOnly) {
        this.plugin = APIDatabase.getInstance().getPlugin();
        if (name == null) {
            ExceptionLogger.getInstance().throwException("Provided name is invalid: null");
            return;
        }
        if (name.contains(".")) {
            this.name = name;
        } else {
            this.name = name + ".yml";
        }
        this.pluginDataFolder = plugin.getDataFolder();
        ymlConfiguration = new File(pluginDataFolder, this.name);
        if (!readOnly) {
            config = YamlConfiguration.loadConfiguration(ymlConfiguration);
        }

    }

    public void createConfig() {
        if (!ymlConfiguration.exists()) {
            if (!getPluginDataFolder().exists()) {
                this.pluginDataFolder.mkdir();
            }
            plugin.saveResource(name, false);
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getPluginDataFolder() {
        return pluginDataFolder;
    }

    public String getName() {
        return name;
    }

    public File getYamlConfig() {
        return ymlConfiguration;
    }

    public void addDefault(String key, String value) {
        if (config.getString(key) == null) {
            config.set(key, value);
            try {
                config.save(getYamlConfig());
            } catch (IOException e) {
                ExceptionLogger.getInstance().addException(new Exception(String.format("Couldn't add value for %s in %s", value, key), this.getClass().toString(), e.getCause()));
                e.printStackTrace();
            }
        }
    }

    public void addDefaults(@NotNull OpMap<String,Object> defaults) {
        for (String s : defaults.keySet()) {
            this.config.set(s, defaults.getOrDefault(s, null));
            try {
                config.save(getYamlConfig());
            } catch (IOException e) {
                ExceptionLogger.getInstance().addException(new Exception(String.format("Couldn't add values in %s", s), this.getClass().toString(), e.getCause()));
            }
        }
    }

    public void save() {
        try {
            config.save(getYamlConfig());
        } catch(IOException e) {
            ExceptionLogger.getInstance().addException(new Exception("Couldn't save configuration file", this.getClass().toString(), e.getCause()));
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(getYamlConfig());
    }
}

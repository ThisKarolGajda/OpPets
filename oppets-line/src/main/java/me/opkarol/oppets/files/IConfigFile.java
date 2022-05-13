package me.opkarol.oppets.files;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.utils.MathUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

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

package me.opkarol.oppets.addons.types;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.addons.AddonManager;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;

public class AddonConfig implements IAddon {
    private String name;
    private String version;
    private List<String> description;
    private Plugin plugin;

    public AddonConfig(String name, String version, List<String> description, Plugin plugin) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.plugin = plugin;
    }

    public AddonConfig() {}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public List<String> getDescription() {
        return description;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean canBeLaunched() {
        return getName() != null && getDescription() != null && getVersion() != null && (getPlugin() != null || isVerified());
    }

    @Override
    public boolean isVerified() {
        return AddonManager.verifiedAddons.contains(getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = Collections.singletonList(description);
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public GETTER_TYPE getGetterType() {
        return GETTER_TYPE.ADDON;
    }

    @Override
    public Object getObject() {
        return this;
    }

}

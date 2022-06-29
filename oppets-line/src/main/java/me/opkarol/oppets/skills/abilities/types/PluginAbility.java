package me.opkarol.oppets.skills.abilities.types;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.skills.abilities.IAbility;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PluginAbility extends IAbility {
    protected String pluginName;
    protected Object pluginValue;
    private final String abilityName;
    private final String skillName;

    public PluginAbility(String abilityName, String skillName) {
        this.abilityName = abilityName;
        this.skillName = skillName;
    }

    public ABILITY_TYPE getType() {
        return ABILITY_TYPE.PLUGIN;
    }

    @Override
    public String getAbilityName() {
        return abilityName;
    }

    @Override
    public String getSkillName() {
        return skillName;
    }

    public void run(Object @NotNull ... args) {
        if (args.length != 2 || !(args[0] instanceof String)) {
            return;
        }
        pluginName = (String) args[0];
        pluginValue = args[1];
    }

    public boolean use(Player player) {
        if (pluginName == null || pluginValue == null) {
            return false;
        }
        if (pluginName.equalsIgnoreCase("VAULT")) {
            Object economy = Database.getInstance().getOpPets().getEconomy();
            if (economy != null) {
                Economy economy1 = (Economy) economy;
                economy1.depositPlayer(player, StringTransformer.getDoubleFromString(pluginValue.toString()));
            }
        }
        return true;
    }
}

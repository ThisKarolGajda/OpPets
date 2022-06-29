package me.opkarol.oppets.skills.abilities.types;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.skills.abilities.IAbility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandAbility extends IAbility {
    private String command;
    private final String abilityName;
    private final String skillName;

    public CommandAbility(String abilityName, String skillName) {
        this.abilityName = abilityName;
        this.skillName = skillName;
    }

    public ABILITY_TYPE getType() {
        return ABILITY_TYPE.COMMAND;
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
        if (args.length != 1 || !(args[0] instanceof String)) {
            return;
        }
        command = (String) args[0];
    }

    public boolean use(Player player) {
        if (command == null || command.length() == 0) {
            return false;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
        return true;
    }
}

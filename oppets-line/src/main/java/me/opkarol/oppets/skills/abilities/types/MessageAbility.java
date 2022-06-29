package me.opkarol.oppets.skills.abilities.types;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.skills.abilities.IAbility;
import me.opkarol.oppets.utils.external.FormatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageAbility extends IAbility {
    private String message;
    private final String abilityName;
    private final String skillName;

    public MessageAbility(String abilityName, String skillName) {
        this.abilityName = abilityName;
        this.skillName = skillName;
    }

    public ABILITY_TYPE getType() {
        return ABILITY_TYPE.MESSAGE;
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
        message = FormatUtils.formatMessage((String) args[0]);
    }

    public boolean use(Player player) {
        if (message == null || message.length() == 0) {
            return false;
        }
        player.sendMessage(message);
        return true;
    }
}

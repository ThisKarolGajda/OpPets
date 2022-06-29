package me.opkarol.oppets.skills.abilities;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class IAbility {
    public abstract ABILITY_TYPE getType();

    public abstract String getAbilityName();

    public abstract String getSkillName();

    public abstract void run(Object @NotNull ... args);

    public abstract boolean use(Player player);

    public enum ABILITY_TYPE {
        PLUGIN,
        COMMAND,
        EFFECT,
        MESSAGE
    }

    public String getAbilityPath() {
        return "Skills." + getSkillName() + ".abilities." + getAbilityName() + ".";
    }
}

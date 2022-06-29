package me.opkarol.oppets.skills.manager;

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

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.skills.abilities.IAbility;
import me.opkarol.oppets.skills.abilities.types.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbilityCreator {

    public static @NotNull IAbility getAbility(String type, String args, String abilityName, String skillName) {
        IAbility ability;
        switch (type == null ? "" : type.toUpperCase()) {
            case "PLUGIN": {
                ability = new PluginAbility(abilityName, skillName);
                break;
            }
            case "COMMAND": {
                ability = new CommandAbility(abilityName, skillName);
                break;
            }
            case "EFFECT": {
                ability = new EffectAbility(abilityName, skillName);
                break;
            }
            case "MESSAGE": {
                ability = new MessageAbility(abilityName, skillName);
                break;
            }
            default: throw new IllegalStateException("Unexpected value: " + (type == null ? "" : type.toUpperCase()));
        }
        Object[] objects = args.split(";");
        ability.run(objects);
        return ability;
    }

    public static @NotNull OpMap<IAbility.ABILITY_TYPE, List<IAbility>> getMap(@NotNull List<IAbility> list) {
        OpMap<IAbility.ABILITY_TYPE, List<IAbility>> temp = new OpMap<>();
        list.forEach(iAbility -> {
            IAbility.ABILITY_TYPE type = iAbility.getType();
            if (temp.containsKey(type)) {
                List<IAbility> list1 = temp.getMap().get(type);
                list1.add(iAbility);
                temp.set(type, list1);
            } else {
                temp.set(iAbility.getType(), new ArrayList<>(Collections.singletonList(iAbility)));
            }
        });
        return temp;
    }
}

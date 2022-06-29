package me.opkarol.oppets.skills.abilities.types;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.skills.abilities.IAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class EffectAbility extends IAbility {
    private PotionEffectType effectType;
    private int effectDuration;
    private int effectAmplifier;
    private final String abilityName;
    private final String skillName;

    public EffectAbility(String abilityName, String skillName) {
        this.abilityName = abilityName;
        this.skillName = skillName;
    }

    public ABILITY_TYPE getType() {
        return ABILITY_TYPE.EFFECT;
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
        if (args.length != 3 || !(args[0] instanceof String)) {
            return;
        }
        effectType = PotionEffectType.getByName((String) args[0]);
        effectDuration = StringTransformer.getIntFromString(args[1].toString());
        effectAmplifier = StringTransformer.getIntFromString(args[2].toString());
    }

    public boolean use(Player player) {
        if (effectType == null || effectAmplifier == 0 || effectDuration == 0) {
            return false;
        }
        player.addPotionEffect(effectType.createEffect(effectDuration, effectAmplifier));
        return true;
    }
}

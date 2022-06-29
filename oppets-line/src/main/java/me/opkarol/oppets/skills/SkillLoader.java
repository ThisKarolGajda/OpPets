package me.opkarol.oppets.skills;

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

import me.opkarol.oppets.api.files.manager.IConfigFile;
import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.skills.manager.AbilityCreator;
import me.opkarol.oppets.skills.abilities.IAbility;
import me.opkarol.oppets.skills.pointers.IPointer;
import me.opkarol.oppets.skills.manager.PointerCreator;
import me.opkarol.oppets.skills.manager.ProgressingLevel;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillLoader {
    private final IConfigFile<Object> fileManager = new IConfigFile<>("skills");
    private final FileConfiguration config = fileManager.getConfiguration();

    public List<Skill> startup() {
        fileManager.createConfig();
        List<Skill> skills = new ArrayList<>();
        Optional<ConfigurationSection> sectionOptional = fileManager.getConfigurationSection("Skills");
        if (!sectionOptional.isPresent()) {
            return skills;
        }

        for (String skill : sectionOptional.get().getKeys(false)) {
            String path = "Skills." + skill + ".";
            List<IAbility> abilities = new ArrayList<>();
            List<IPointer> pointers = new ArrayList<>();

            String customName = config.getString(path + "custom_skill_name");
            String availablePets = config.getString(path + "available_pets");
            int minPrestigeLevel = StringTransformer.getIntFromString(config.getString(path + "min_prestige_level"));
            ProgressingLevel progressingLevel = new ProgressingLevel(config.getString(path + "progressingLevel"));

            fileManager.getConfigurationSection(path + "abilities").ifPresent(section -> {
                for (String ability : section.getKeys(false)) {
                    String aPath = path + "abilities." + ability + ".";

                    String type = config.getString(aPath + "type");
                    String objects = config.getString(aPath + "objects");
                    abilities.add(AbilityCreator.getAbility(type, objects, ability, skill));
                }
            });

            fileManager.getConfigurationSection(path + "pointers").ifPresent(section -> {
                for (String pointer : section.getKeys(false)) {
                    String aPath = path + "pointers." + pointer + ".";

                    String type = config.getString(aPath + "type");
                    String materials = config.getString(aPath + "materials");
                    String pointsAwarded = config.getString(aPath + "points_awarded");
                    pointers.add(PointerCreator.getPointer(type, materials, pointsAwarded, pointer, skill));
                }
            });

            Skill newSkill = new Skill(path, skill, customName, availablePets, minPrestigeLevel, PointerCreator.getMap(pointers), AbilityCreator.getMap(abilities), progressingLevel);
            skills.add(newSkill);
        }
        return skills;
    }

}

package me.opkarol.oppets.utils;

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

import me.opkarol.oppets.databases.external.APIDatabase;
import me.opkarol.oppets.databases.external.SkillDatabase;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.skills.Skill;
import me.opkarol.oppets.skills.SkillEnums;
import me.opkarol.oppets.skills.types.Ability;
import me.opkarol.oppets.skills.types.Adder;
import me.opkarol.oppets.skills.types.Requirement;
import me.opkarol.oppets.utils.external.ConfigUtils;
import me.opkarol.oppets.utils.external.MathUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SkillUtils {
    private final FileConfiguration config = ConfigUtils.getConfig();
    private final SkillDatabase skillDatabase;

    public SkillUtils() {
        skillDatabase = APIDatabase.getInstance().getSkillDatabase();
    }

    public SkillUtils(SkillDatabase database) {
        this.skillDatabase = database;
    }

    public List<TypeOfEntity> getAllowedEntities(@NotNull String string) {
        String substring = string.substring(1, string.length() - 1);
        String[] strings = substring.split(",");
        List<TypeOfEntity> list = new ArrayList<>();
        try {
            for (String pseudoType : strings) {
                if (pseudoType.equals("[]")) {
                    break;
                }
                if (pseudoType.equals("ALL")) {
                    list.addAll(Arrays.stream(TypeOfEntity.values()).collect(Collectors.toList()));
                    break;
                }
                list.add(TypeOfEntity.valueOf(pseudoType));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Ability> getSkillAbilitiesFromSection(String pathI) {
        if (pathI == null) {
            return null;
        }
        pathI = MathUtils.substringFromEnd(pathI, 1);
        List<Ability> list = new ArrayList<>();
        ConfigurationSection sec = config.getConfigurationSection(pathI + ".abilities");
        if (sec == null) {
            return list;
        }
        for (String key : sec.getKeys(false)) {
            String iPath = pathI + ".abilities." + key + ".";
            list.add(new Ability(SkillEnums.SkillsAbilities.valueOf(config.getString(iPath + "type")), iPath));
        }
        return list;
    }

    public List<Requirement> getSkillRequirementFromSection(String path) {
        if (path == null) {
            return null;
        }
        path = MathUtils.substringFromEnd(path, 1);
        List<Requirement> list = new ArrayList<>();
        ConfigurationSection sec = config.getConfigurationSection(path + ".requirements");
        if (sec == null) {
            return list;
        }
        for (String key : sec.getKeys(false)) {
            String iPath = path + ".requirements." + key + ".";
            list.add(new Requirement(SkillEnums.SkillsRequirements.valueOf(config.getString(iPath + "type"))));
        }
        return list;
    }

    public List<Adder> getSkillAdderFromSection(String path) {
        if (path == null) {
            return null;
        }
        path = MathUtils.substringFromEnd(path, 1);
        List<Adder> list = new ArrayList<>();
        ConfigurationSection sec = config.getConfigurationSection(path + ".adders");
        if (sec == null) {
            return list;
        }
        for (String key : sec.getKeys(false)) {
            String iPath = path + ".adders." + key + ".";
            int everyAction = config.getInt(iPath + "levelup.everyAction");
            if (everyAction == 0) {
                list.add(new Adder(SkillEnums.SkillsAdders.valueOf(config.getString(iPath + "type")), config.getString(iPath + "levelup.progressiveLevel"), Objects.requireNonNull(config.getString(iPath + "materials"))));
            } else {
                list.add(new Adder(SkillEnums.SkillsAdders.valueOf(config.getString(iPath + "type")), config.getDouble(iPath + "levelup.grantedPoints"), everyAction, Objects.requireNonNull(config.getString(iPath + "materials"))));
            }
        }
        return list;
    }

    public double getGrantedPointsFromEnum(@NotNull Pet pet, SkillEnums.SkillsAdders skillsAdder) {
        List<Double> values = skillDatabase.getSkillFromMap(pet.getSkillName()).getAdderList().stream()
                .filter(adder -> adder.getAdder() == skillsAdder)
                .filter(adder -> !adder.progressiveAdderEnabled())
                .map(Adder::getGrantedPoints).collect(Collectors.toList());
        if (values.size() != 1) {
            return 1.0D;
        }
        return values.get(0);
    }

    public double getMaxPointsFromEnum(@NotNull Pet pet, SkillEnums.SkillsAdders skillsAdder) {
        List<Object> values = skillDatabase.getSkillFromMap(pet.getSkillName()).getAdderList()
                .stream().map(adder -> {
                    if (adder.progressiveAdderEnabled()) {
                        return adder.calculateMaxCurrent(pet.getLevel());
                    } else {
                        if (adder.getAdder() == skillsAdder) {
                            return adder.getEveryAction();
                        }
                    }
                    return 1.0D;
                }).collect(Collectors.toList());
        if (values.size() == 0) {
            return 1.0D;
        }
        return (double) values.get(0);
    }

    public String getRandomSkillName(TypeOfEntity type) {
        List<Skill> list = skillDatabase.getAccessibleSkillsToPetType(type);
        if (list == null || list.size() == 0) {
            return "";
        }
        if (list.size() == 1) {
            return list.get(0).getName();
        } else {
            return list.get(getRandomNumber(0, list.size() - 1)).getName();
        }
    }

    public int getRandomNumber(int min, int max) {
        if (min == max) {
            return min;
        }
        return (int) ((Math.random() * (max - min)) + min);
    }
}

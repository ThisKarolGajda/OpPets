package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.OpPetsEntityTypes;
import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.utils.ConfigUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillUtils {

    public List<OpPetsEntityTypes.TypeOfEntity> getAllowedEntities(@NotNull String string){
        String substring = string.substring(1, string.length() - 1);
        String[] strings = substring.split(",");


        List<OpPetsEntityTypes.TypeOfEntity> list = new ArrayList<>();
        try {
            for (String pseudoType : strings){
                if (pseudoType.equals("ALL")){
                    list.addAll(Arrays.stream(OpPetsEntityTypes.TypeOfEntity.values()).toList());
                    break;
                }
                list.add(OpPetsEntityTypes.TypeOfEntity.valueOf(pseudoType));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }


        return list;
    }

    public List<Ability> getSkillAbilitiesFromSection(String path){
        if (path == null){
            return null;
        }

        path = path.substring(0, path.length() - 1);

        List<Ability> list = new ArrayList<>();

        ConfigurationSection sec = OpPets.getInstance().getConfig().getConfigurationSection(path + ".abilities");

        if (sec != null) {
            for (String key : sec.getKeys(false)){
                String iPath = path + ".abilities." + key + ".";
                list.add(new Ability(SkillEnums.SkillsAbilities.valueOf(ConfigUtils.getString(iPath + "type")), ConfigUtils.getString(iPath + "command")));
            }
        }

        return list;
    }

    public List<Requirement> getSkillRequirementFromSection(String path){
        if (path == null){
            return null;
        }

        path = path.substring(0, path.length() - 1);

        List<Requirement> list = new ArrayList<>();

        ConfigurationSection sec = OpPets.getInstance().getConfig().getConfigurationSection(path + ".requirements");

        if (sec != null) {
            for (String key : sec.getKeys(false)){
                String iPath = path + ".requirements." + key + ".";
                list.add(new Requirement(SkillEnums.SkillsRequirements.valueOf(ConfigUtils.getString(iPath + "type"))));
            }
        }

        return list;
    }

    public List<Adder> getSkillAdderFromSection(String path){
        if (path == null){
            return null;
        }

        path = path.substring(0, path.length() - 1);

        List<Adder> list = new ArrayList<>();

        ConfigurationSection sec = OpPets.getInstance().getConfig().getConfigurationSection(path + ".adders");

        if (sec != null) {
            for (String key : sec.getKeys(false)) {
                String iPath = path + ".adders." + key + ".";
                int everyAction = ConfigUtils.getInt(iPath + "levelup.everyAction");
                if (everyAction == 0) {
                    list.add(new Adder(SkillEnums.SkillsAdders.valueOf(ConfigUtils.getString(iPath + "type")), ConfigUtils.getString(iPath + "levelup.progressiveLevel")));
                } else {
                    list.add(new Adder(SkillEnums.SkillsAdders.valueOf(ConfigUtils.getString(iPath + "type")), ConfigUtils.getDouble(iPath + "levelup.grantedPoints"), everyAction));
                }
            }
        }

        return list;
    }

    public double getGrantedPointsFromEnum(@NotNull Pet pet, SkillEnums.SkillsAdders skillsAdder) {
        final double[] i = {0};
        OpPets.getSkillDatabase().getSkillFromMap(pet.getSkillName()).getE().forEach(adder -> {
            if (adder.getAdder() == skillsAdder) {
                if (!adder.progressiveAdderEnabled()) {
                    i[0] = adder.getGrantedPoints();
                } else i[0] = 1.0D;
            }
        });
        return i[0];
    }

    public double getMaxPointsFromEnum(@NotNull Pet pet, SkillEnums.SkillsAdders skillsAdder) {
        final double[] i = {0};
        OpPets.getSkillDatabase().getSkillFromMap(pet.getSkillName()).getE().forEach(adder -> {
            if (adder.progressiveAdderEnabled()) {
                i[0] = adder.calculateMaxCurrent(pet.getLevel());
            } else {
                if (adder.getAdder() == skillsAdder) {
                    i[0] = adder.getEveryAction();
                }
            }
        });
        return i[0];
    }

    public String getRandomSkillName(OpPetsEntityTypes.TypeOfEntity type) {
        List<Skill> list = OpPets.getSkillDatabase().getAccessibleSkillsToPetType(type);

        if (list == null) return null;

        if (list.size() == 1) {
            return list.get(0).getA();
        } else {
            return list.get(getRandomNumber(0, list.size() - 1)).getA();
        }

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}

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
import dir.pets.PetsUtils;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.events.PetLevelupEvent;
import me.opkarol.oppets.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SkillDatabase {
    private String basicPath = "Skills";
    private final ConfigurationSection sec;
    private final HashMap<String, Skill> map = new HashMap<>();
    private final SkillUtils utils = new SkillUtils();

    public SkillDatabase() {
        sec = OpPets.getInstance().getConfig().getConfigurationSection(basicPath);
        basicPath = "Skills.";

        if (sec == null) {
            new OpPets().disablePlugin("Config file isn't valid");
            return;
        }

        for (String key : sec.getKeys(false)) {
            Skill skill = getSkillFromPath(key);
            map.put(key, skill);
        }

    }

    public Skill getSkillFromPath(@NotNull String skillName) {
        String iPath;
        for (String key : sec.getKeys(false)){
            if (skillName.equals(key)) {
                iPath = basicPath + key + ".";
                List<OpPetsEntityTypes.TypeOfEntity> entities = utils.getAllowedEntities(ConfigUtils.getString(iPath + "available_pets"));
                List<Ability> abilities = utils.getSkillAbilitiesFromSection(iPath);
                List<Requirement> requirements = utils.getSkillRequirementFromSection(iPath);
                List<Booster> boosters = null;
                List<Adder> adders = utils.getSkillAdderFromSection(iPath);
                return new Skill(skillName, abilities, requirements, boosters, adders, entities, UUID.randomUUID());
            }
        }
        return null;
    }

    public Skill getSkillFromMap(String name) {
        return map.get(name);
    }

    public List<Skill> getAccessibleSkillsToPetType(OpPetsEntityTypes.TypeOfEntity type) {
        if (map.size() == 0) return null;

        List<Skill> list = new ArrayList<>();

        for (Skill skill : map.values()) {
            if (skill != null) {
                if (skill.getF() != null) {
                    if (skill.getF().contains(type)) {
                        list.add(skill);
                    }
                }
            }
        }

        return list;
    }

    public void addPoint(SkillEnums.SkillsAdders skillEnums, @NotNull Pet pet, Player player) {
        SkillUtils utils = new SkillUtils();
        double experience = pet.getPetExperience();
        double grantedPoints = utils.getGrantedPointsFromEnum(pet, skillEnums);
        pet.setPetExperience(experience + grantedPoints);

        double maxPoints = utils.getMaxPointsFromEnum(pet, skillEnums);

        if (maxPoints <= pet.getPetExperience()) {
            Bukkit.getPluginManager().callEvent(new PetLevelupEvent(player, pet));
        }

        PetsUtils.savePetProgress(pet, pet.getOwnerUUID());
    }

}

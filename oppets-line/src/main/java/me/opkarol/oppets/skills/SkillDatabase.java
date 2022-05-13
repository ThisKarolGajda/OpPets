package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.databases.APIDatabase;
import me.opkarol.oppets.events.PetLevelupEvent;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.utils.ConfigUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkillDatabase {
    private final APIDatabase database = APIDatabase.getInstance();
    private final OpMap<String, Skill> skillMap = new OpMap<>();
    private final SkillUtils utils;
    private final FileConfiguration config = ConfigUtils.getConfig();
    private String basicPath = "Skills";

    public SkillDatabase() {
        utils = new SkillUtils(this);
        ConfigurationSection sec = config.getConfigurationSection(basicPath);
        basicPath = "Skills.";
        if (sec == null) {
            return;
        }
        for (String key : sec.getKeys(false)) {
            Skill skill = getSkillFromPath(key);
            skillMap.put(key, skill);
        }
    }

    public List<Skill> getSkills() {
        return new ArrayList<>(skillMap.getValues());
    }

    public Skill getSkillFromPath(@NotNull String skillName) {
        String iPath = basicPath + skillName + ".";
        if (config.getString(iPath + "available_pets") == null) {
            return null;
        }
        List<TypeOfEntity> entities = utils.getAllowedEntities(Objects.requireNonNull(config.getString(iPath + "available_pets")));
        List<Ability> abilities = utils.getSkillAbilitiesFromSection(iPath);
        List<Requirement> requirements = utils.getSkillRequirementFromSection(iPath);
        List<Adder> adders = utils.getSkillAdderFromSection(iPath);
        int maxLevel = config.getInt(iPath + "max_level");
        return new Skill(skillName, abilities, requirements, adders, entities, maxLevel);

    }

    public Skill getSkillFromMap(String name) {
        return skillMap.getOrDefault(name, null);
    }

    public List<Skill> getAccessibleSkillsToPetType(TypeOfEntity type) {
        if (skillMap.isEmpty()) {
            return new ArrayList<>();
        }
        return skillMap.getValues().stream().filter(skill -> skill.getTypeOfEntityList().contains(type)).collect(Collectors.toList());
    }

    public void addPoint(SkillEnums.SkillsAdders skillEnums, @NotNull Pet pet, @NotNull Player player) {
        double experience = pet.getPetExperience();
        double grantedPoints = utils.getGrantedPointsFromEnum(pet, skillEnums);
        double multiplier = database.getBoosterProvider().getMultiplier(player.getUniqueId().toString());
        pet.setPetExperience(experience + grantedPoints * multiplier);
        double maxPoints = utils.getMaxPointsFromEnum(pet, skillEnums);
        Stream<Requirement> stream = database.getSkillDatabase().getSkillFromMap(pet.getSkillName()).getRequirementList().stream().filter(requirement -> requirement.getRequirement().equals(SkillEnums.SkillsRequirements.PET_LEVEL));
        if (stream.findAny().isPresent()) {
            if (maxPoints <= pet.getPetExperience()) {
                Bukkit.getPluginManager().callEvent(new PetLevelupEvent(player, pet));
            }
        }
        PetsUtils.savePetProgress(pet, pet.getOwnerUUID());
    }
}

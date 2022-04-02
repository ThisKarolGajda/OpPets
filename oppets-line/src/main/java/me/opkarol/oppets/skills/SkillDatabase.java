package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.events.PetLevelupEvent;
import me.opkarol.oppets.pets.OpPetsEntityTypes;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * The type Skill database.
 */
public class SkillDatabase {
    /**
     * The Skill map.
     */
    private final HashMap<String, Skill> skillMap = new HashMap<>();
    /**
     * The Utils.
     */
    private final SkillUtils utils;
    /**
     * The Config.
     */
    private final FileConfiguration config;
    /**
     * The Basic path.
     */
    private String basicPath = "Skills";
    /**
     * The Can run.
     */
    private boolean canRun = true;

    /**
     * Instantiates a new Skill database.
     */
    public SkillDatabase() {
        utils = new SkillUtils(this);
        config = Database.getInstance().getConfig();
        ConfigurationSection sec = config.getConfigurationSection(basicPath);
        basicPath = "Skills.";
        if (sec == null) {
            canRun = false;
            return;
        }
        for (String key : sec.getKeys(false)) {
            Skill skill = getSkillFromPath(key);
            skillMap.put(key, skill);
        }
    }

    /**
     * Gets skills.
     *
     * @return the skills
     */
    public List<Skill> getSkills() {
        return new ArrayList<>(skillMap.values());
    }

    /**
     * Gets skill from path.
     *
     * @param skillName the skill name
     * @return the skill from path
     */
    public Skill getSkillFromPath(@NotNull String skillName) {
        String iPath = basicPath + skillName + ".";
        if (config.getString(iPath + "available_pets") == null) {
            return null;
        }
        List<OpPetsEntityTypes.TypeOfEntity> entities = utils.getAllowedEntities(Objects.requireNonNull(config.getString(iPath + "available_pets")));
        List<Ability> abilities = utils.getSkillAbilitiesFromSection(iPath);
        List<Requirement> requirements = utils.getSkillRequirementFromSection(iPath);
        List<Adder> adders = utils.getSkillAdderFromSection(iPath);
        int maxLevel = config.getInt(iPath + "max_level");
        return new Skill(skillName, abilities, requirements, adders, entities, maxLevel);

    }

    /**
     * Gets skill from map.
     *
     * @param name the name
     * @return the skill from map
     */
    public Skill getSkillFromMap(String name) {
        return skillMap.get(name);
    }

    /**
     * Gets accessible skills to pet type.
     *
     * @param type the type
     * @return the accessible skills to pet type
     */
    public List<Skill> getAccessibleSkillsToPetType(OpPetsEntityTypes.TypeOfEntity type) {
        if (skillMap.size() == 0) return null;
        List<Skill> list = new ArrayList<>();
        skillMap.values().forEach(skill -> {
            if (skill.getF() != null) {
                if (skill.getF().contains(type)) {
                    list.add(skill);
                }
            }
        });
        return list;
    }

    /**
     * Add point.
     *
     * @param skillEnums the skill enums
     * @param pet        the pet
     * @param player     the player
     */
    public void addPoint(SkillEnums.SkillsAdders skillEnums, @NotNull Pet pet, @NotNull Player player) {
        double experience = pet.getPetExperience();
        double grantedPoints = utils.getGrantedPointsFromEnum(pet, skillEnums);
        double multiplier = Database.getOpPets().getBoosterProvider().getMultiplier(player.getUniqueId().toString());
        pet.setPetExperience(experience + grantedPoints * multiplier);
        double maxPoints = utils.getMaxPointsFromEnum(pet, skillEnums);
        final boolean[] requirementPetLevel = {false};
        Database.getOpPets().getSkillDatabase().getSkillFromMap(pet.getSkillName()).getC().forEach(requirement -> {
            if (requirement.getRequirement().equals(SkillEnums.SkillsRequirements.PET_LEVEL)) {
                requirementPetLevel[0] = true;
            }
        });
        if (requirementPetLevel[0]) {
            if (maxPoints <= pet.getPetExperience()) {
                Bukkit.getPluginManager().callEvent(new PetLevelupEvent(player, pet));
            }
        }
        PetsUtils.savePetProgress(pet, pet.getOwnerUUID());
    }

    /**
     * Returns string that has been connected from table of strings,
     * which can later be used to transform it.
     *
     * @param strings table of strings
     * @return string result from connecting strings
     */
    @Contract(pure = true)
    private @NotNull String getStringFromStrings(String[] strings) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        if (strings != null) {
            for (String s : strings) {
                builder.append(s);
                i++;
                if (i != strings.length) builder.append(", ");
            }
        }
        return builder.toString();
    }

    /**
     * This method is based as on types taken.
     * It generates all possible materials that can be used in order to get through validation process.
     *
     * @param types table of Materials
     * @return materials string result
     * @see org.bukkit.Material
     */
    @Contract(pure = true)
    private @NotNull String getAvailableMaterials(Material[] types) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        if (types != null) {
            for (Material material : types) {
                builder.append(material.data.getName());
                i++;
                if (i != Arrays.stream(types).count()) builder.append(", ");
            }
        }
        return builder.toString();
    }

    /**
     * This method can generate all available pets to string, using provided types.
     *
     * @param types list of type of entity objects
     * @return pets string object containing all pet types
     * @see me.opkarol.oppets.pets.OpPetsEntityTypes
     */
    private @NotNull String getAvailablePets(List<OpPetsEntityTypes.TypeOfEntity> types) {
        StringBuilder builder = new StringBuilder().append("[");
        int i = 0;
        if (types != null) {
            for (OpPetsEntityTypes.TypeOfEntity type : types) {
                builder.append(type.name());
                i++;
                if (i != types.size()) builder.append(", ");
            }
        }
        return builder.append("]").toString();
    }

    /**
     * Is can run boolean.
     *
     * @return the boolean
     */
    public boolean isCanRun() {
        return canRun;
    }
}

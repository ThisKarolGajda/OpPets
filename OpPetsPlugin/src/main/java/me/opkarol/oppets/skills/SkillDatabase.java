package me.opkarol.oppets.skills;

import dir.pets.OpPetsEntityTypes;
import dir.pets.Pet;
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
    private final HashMap<UUID, Integer> actionMap = new HashMap<>();

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
        List<Skill> list = new ArrayList<>();

        if (map.size() == 0) {
            Bukkit.broadcastMessage("blank map");
            return null;
        }

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

    public int getCurrentActionNumberFromPet(UUID petUUID) {
        return actionMap.get(petUUID);
    }

    public void addActionToPet(UUID petUUID, int value) {
        actionMap.put(petUUID, value);
    }

    public void changeActionValueToPet(UUID petUUID, int newValue) {
        actionMap.replace(petUUID, newValue);
    }

    public boolean containsActionOfPet(UUID petUUID) {
        return actionMap.containsKey(petUUID);
    }

    public void addPoint(SkillEnums.SkillsAdders skillEnums, @NotNull Pet pet, Player player) {
        UUID uuid = pet.getOwnUUID();
        if (containsActionOfPet(uuid)) {
            changeActionValueToPet(uuid, getCurrentActionNumberFromPet(uuid) + 1);
        } else {
            addActionToPet(uuid, 1);
        }
        SkillUtils utils = new SkillUtils();
        pet.setPetExperience(pet.getPetExperience() + utils.getGrantedPointsFromEnum(pet, skillEnums));

        double maxPoints = utils.getMaxPointsFromEnum(pet, skillEnums);

        if (maxPoints <= getCurrentActionNumberFromPet(uuid)) {
            Bukkit.getPluginManager().callEvent(new PetLevelupEvent(player, pet));

        }
    }

}

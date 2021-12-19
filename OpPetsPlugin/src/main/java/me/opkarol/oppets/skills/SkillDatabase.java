package me.opkarol.oppets.skills;

import dir.pets.OpPetsEntityTypes;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SkillDatabase {
    private String basicPath = "Skills";
    private final ConfigurationSection sec;
    private final HashMap<String, Skill> map = new HashMap<>();
    private final SkillUtils utils = new SkillUtils();

    public SkillDatabase(){
        sec = OpPets.getInstance().getConfig().getConfigurationSection(basicPath);
        basicPath = "Skills.";

        if (sec == null){
            new OpPets().disablePlugin("Config file isn't valid");
            return;
        }

        for (String key : sec.getKeys(false)){
            Skill skill = getSkillFromPath(key);
            map.put(key, skill);
        }

    }

    public Skill getSkillFromPath(@NotNull String skillName){
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

    public Skill getSkillFromMap(String name){
        return map.get(name);
    }

    public List<Skill> getAccessibleSkillsToPetType(OpPetsEntityTypes.TypeOfEntity type){
        List<Skill> list = new ArrayList<>();

        if (map.size() == 0){
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

    public void skillsSize(){
        Bukkit.broadcastMessage(map.size() + " size");
    }

}

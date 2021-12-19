package me.opkarol.oppets.skills;

import dir.pets.OpPetsEntityTypes;
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
            for (String key : sec.getKeys(false)){
                String iPath = path + ".adders." + key + ".";
                list.add(new Adder(SkillEnums.SkillsAdders.valueOf(ConfigUtils.getString(iPath + "type"))));
            }
        }

        return list;
    }
}

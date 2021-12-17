package me.opkarol.oppets.skills;

public class SkillEnums extends SkillDatabase {

    /*
        SkillsAbilities are abilities that are given to a player         ->
        for every * action / for every level upgrade / every level * action
     */
    enum SkillsAbilities {
        CUSTOM_COMMAND, PLUGIN_CONNECTION, VANILLA_EFFECTS, VANILLA_ENCHANTS, TREASURE,
    }

    /*
        SkillsRequirements are requirements that are needed to level up such as:

     */
    enum SkillsRequirements {
        PET_LEVEL,
    }

    /*
        SkillBoosters are addons that can help to level up your pet
     */
    enum SkillsBoosters {
        CUSTOM_BOOSTER, SERVER_BOOST, PLAYER_BOOST, WORLD_BOOST, PET_BOOST
    }

    /*
        SkillAdders are selective group of actions that leads to leveling up your pet
     */
    enum SkillsAdders {
        MINING, HARVESTING, CRAFTING, SMELTING,
    }
}

package me.opkarol.oppets.skills;

public class Ability {
    private SkillEnums.SkillsAbilities ability;
    private String command;
    /*
    other actions like enchants
     */

    public Ability(SkillEnums.SkillsAbilities ability, String command){
        setAbility(ability);
        setCommand(command);
    }

    public SkillEnums.SkillsAbilities getAbility() {
        return ability;
    }

    public void setAbility(SkillEnums.SkillsAbilities ability) {
        this.ability = ability;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}

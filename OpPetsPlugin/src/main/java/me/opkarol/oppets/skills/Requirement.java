package me.opkarol.oppets.skills;

public class Requirement {
    private SkillEnums.SkillsRequirements requirement;

    public Requirement(SkillEnums.SkillsRequirements requirements){
        setRequirement(requirements);
    }

    public SkillEnums.SkillsRequirements getRequirement() {
        return requirement;
    }

    public void setRequirement(SkillEnums.SkillsRequirements requirement) {
        this.requirement = requirement;
    }
}

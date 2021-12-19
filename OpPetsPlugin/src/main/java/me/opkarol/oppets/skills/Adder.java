package me.opkarol.oppets.skills;

public class Adder {
    private SkillEnums.SkillsAdders adder;

    public Adder(SkillEnums.SkillsAdders adder){
        setAdder(adder);
    }

    public SkillEnums.SkillsAdders getAdder() {
        return adder;
    }

    public void setAdder(SkillEnums.SkillsAdders adder) {
        this.adder = adder;
    }
}

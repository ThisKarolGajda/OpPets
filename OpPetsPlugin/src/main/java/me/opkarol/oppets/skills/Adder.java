package me.opkarol.oppets.skills;

public class Adder {
    private SkillEnums.SkillsAdders adder;
    private double grantedPoints;
    private int everyAction;

    public Adder(SkillEnums.SkillsAdders adder, double grantedPoints, int everyAction){
        setGrantedPoints(grantedPoints);
        setEveryAction(everyAction);
        setAdder(adder);
    }

    public SkillEnums.SkillsAdders getAdder() {
        return adder;
    }

    public void setAdder(SkillEnums.SkillsAdders adder) {
        this.adder = adder;
    }

    public double getGrantedPoints() {
        return grantedPoints;
    }

    public void setGrantedPoints(double grantedPoints) {
        this.grantedPoints = grantedPoints;
    }

    public int getEveryAction() {
        return everyAction;
    }

    public void setEveryAction(int everyAction) {
        this.everyAction = everyAction;
    }
}

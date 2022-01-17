package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.jetbrains.annotations.NotNull;

public class Adder {
    private SkillEnums.SkillsAdders adder;
    private double grantedPoints;
    private int everyAction;
    private String[] formattedString;

    public Adder(SkillEnums.SkillsAdders adder, double grantedPoints, int everyAction){
        setGrantedPoints(grantedPoints);
        setEveryAction(everyAction);
        setAdder(adder);
    }

    public Adder(SkillEnums.SkillsAdders adder, String getConcludedString) {
        setAdder(adder);
        setFormattedString(getConcludedString);
    }

    public boolean progressiveAdderEnabled() {
        return formattedString != null;
    }

    public int getStartNumber() {
        return Integer.parseInt(this.formattedString[0]);
    }

    public boolean getChangingValue() {
        return Boolean.parseBoolean(this.formattedString[1]);
    }

    public double calculateMaxCurrent(int level) {
        double number = getStartNumber();
        for (int i = 0; i < level; i++) {
            if (!getChangingValue()) break;
            double toChange = number * getValue() / 100;

            switch (getIncrementChar()) {
                case 'u' -> number += toChange;
                case 'd' -> number -= toChange;
            }
        }
        if (getRounded()) return Math.round(number);
        return number;
    }

    public char getIncrementChar() {
        String s = this.formattedString[2];
        if (s.equals("up")) {
            return 'u';
        } else  {
            return 'd';
        }
    }

    public int getValue() {
        return Integer.parseInt(this.formattedString[3]);
    }

    public boolean getRounded() {
        return Boolean.parseBoolean(this.formattedString[4]);
    }

    public void setFormattedString(@NotNull String formattedString) {
        this.formattedString = formattedString.split(";");
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

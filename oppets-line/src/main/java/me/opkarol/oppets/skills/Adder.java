package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The type Adder.
 */
public class Adder {
    /**
     * The Adder.
     */
    private SkillEnums.SkillsAdders adder;
    /**
     * The Granted points.
     */
    private double grantedPoints;
    /**
     * The Every action.
     */
    private int everyAction;
    /**
     * The Formatted string.
     */
    private String[] formattedString;
    /**
     * The Types.
     */
    private Material[] types;
    /**
     * The All types.
     */
    private boolean allTypes;

    /**
     * Instantiates a new Adder.
     *
     * @param adder         the adder
     * @param grantedPoints the granted points
     * @param everyAction   the every action
     * @param s             the s
     */
    public Adder(SkillEnums.SkillsAdders adder, double grantedPoints, int everyAction, @NotNull String s) {
        setGrantedPoints(grantedPoints);
        setEveryAction(everyAction);
        setAdder(adder);
        setTypes(s.split(","));
    }

    /**
     * Instantiates a new Adder.
     *
     * @param adder              the adder
     * @param getConcludedString the get concluded string
     * @param s                  the s
     */
    public Adder(SkillEnums.SkillsAdders adder, String getConcludedString, @NotNull String s) {
        setAdder(adder);
        setFormattedString(getConcludedString);
        setTypes(s.split(","));
    }

    /**
     * Progressive adder enabled boolean.
     *
     * @return the boolean
     */
    public boolean progressiveAdderEnabled() {
        return formattedString != null;
    }

    /**
     * Gets start number.
     *
     * @return the start number
     */
    public int getStartNumber() {
        return Integer.parseInt(this.formattedString[0]);
    }

    /**
     * Gets changing value.
     *
     * @return the changing value
     */
    public boolean getChangingValue() {
        return Boolean.parseBoolean(this.formattedString[1]);
    }

    /**
     * Calculate max current double.
     *
     * @param level the level
     * @return the double
     */
    public double calculateMaxCurrent(int level) {
        double number = getStartNumber();
        for (int i = 0; i < level; i++) {
            if (!getChangingValue()) break;
            double toChange = number * getValue() / 100;

            switch (getIncrementChar()) {
                case 'u':
                    number += toChange;
                    break;
                case 'd':
                    number -= toChange;
                    break;
            }
        }
        if (getRounded()) return Math.round(number);
        return number;
    }

    /**
     * Gets increment char.
     *
     * @return the increment char
     */
    public char getIncrementChar() {
        String s = this.formattedString[2];
        if (s.equals("up")) {
            return 'u';
        } else {
            return 'd';
        }
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return Integer.parseInt(this.formattedString[3]);
    }

    /**
     * Gets rounded.
     *
     * @return the rounded
     */
    public boolean getRounded() {
        return Boolean.parseBoolean(this.formattedString[4]);
    }

    /**
     * Gets adder.
     *
     * @return the adder
     */
    public SkillEnums.SkillsAdders getAdder() {
        return adder;
    }

    /**
     * Sets adder.
     *
     * @param adder the adder
     */
    public void setAdder(SkillEnums.SkillsAdders adder) {
        this.adder = adder;
    }

    /**
     * Gets granted points.
     *
     * @return the granted points
     */
    public double getGrantedPoints() {
        return grantedPoints;
    }

    /**
     * Sets granted points.
     *
     * @param grantedPoints the granted points
     */
    public void setGrantedPoints(double grantedPoints) {
        this.grantedPoints = grantedPoints;
    }

    /**
     * Gets every action.
     *
     * @return the every action
     */
    public int getEveryAction() {
        return everyAction;
    }

    /**
     * Sets every action.
     *
     * @param everyAction the every action
     */
    public void setEveryAction(int everyAction) {
        this.everyAction = everyAction;
    }

    /**
     * Get types material [ ].
     *
     * @return the material [ ]
     */
    public Material[] getTypes() {
        return types;
    }

    /**
     * Sets types.
     *
     * @param s the s
     */
    @Contract(pure = true)
    private void setTypes(String @NotNull [] s) {
        if (!s[0].equalsIgnoreCase("ALL")) {
            types = new Material[s.length];
            for (int i = 0; i < s.length; i++) {
                types[i] = Material.valueOf(s[i].replace(" ", ""));
            }
            allTypes = false;
        } else {
            allTypes = true;
        }
    }

    /**
     * Is none types boolean.
     *
     * @return the boolean
     */
    public boolean isNoneTypes() {
        return !allTypes;
    }

    /**
     * Sets formatted string.
     *
     * @param formattedString the formatted string
     */
    public void setFormattedString(@NotNull String formattedString) {
        this.formattedString = formattedString.split(";");
    }
}

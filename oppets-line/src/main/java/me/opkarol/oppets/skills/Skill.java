package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.OpPetsEntityTypes;

import java.util.List;

/**
 * The type Skill.
 */
public class Skill extends SkillEnums {

    /**
     * The Name.
     */
    private String name;
    /**
     * The Ability list.
     */
    private List<Ability> abilityList;
    /**
     * The Requirement list.
     */
    private List<Requirement> requirementList;
    /**
     * The Adder list.
     */
    private List<Adder> adderList;
    /**
     * The Type of entity list.
     */
    private List<OpPetsEntityTypes.TypeOfEntity> typeOfEntityList;
    /**
     * The Max level.
     */
    private int maxLevel;

    /**
     * Instantiates a new Skill.
     *
     * @param name the name
     * @param b    the b
     * @param c    the c
     * @param e    the e
     * @param f    the f
     * @param h    the h
     */
    public Skill(String name, List<Ability> b, List<Requirement> c, List<Adder> e, List<OpPetsEntityTypes.TypeOfEntity> f, int h) {
        setA(name);
        setB(b);
        setC(c);
        setE(e);
        setF(f);
        setH(h);
    }

    /**
     * Gets a.
     *
     * @return the a
     */
    public String getA() {
        return name;
    }

    /**
     * Sets a.
     *
     * @param a the a
     */
    public void setA(String a) {
        this.name = a;
    }

    /**
     * Gets b.
     *
     * @return the b
     */
    public List<Ability> getB() {
        return abilityList;
    }

    /**
     * Sets b.
     *
     * @param b the b
     */
    public void setB(List<Ability> b) {
        this.abilityList = b;
    }

    /**
     * Gets c.
     *
     * @return the c
     */
    public List<Requirement> getC() {
        return requirementList;
    }

    /**
     * Sets c.
     *
     * @param c the c
     */
    public void setC(List<Requirement> c) {
        this.requirementList = c;
    }

    /**
     * Gets e.
     *
     * @return the e
     */
    public List<Adder> getE() {
        return adderList;
    }

    /**
     * Sets e.
     *
     * @param e the e
     */
    public void setE(List<Adder> e) {
        this.adderList = e;
    }

    /**
     * Gets f.
     *
     * @return the f
     */
    public List<OpPetsEntityTypes.TypeOfEntity> getF() {
        return typeOfEntityList;
    }

    /**
     * Sets f.
     *
     * @param f the f
     */
    public void setF(List<OpPetsEntityTypes.TypeOfEntity> f) {
        this.typeOfEntityList = f;
    }

    /**
     * Gets h.
     *
     * @return the h
     */
    public int getH() {
        return maxLevel;
    }

    /**
     * Sets h.
     *
     * @param h the h
     */
    public void setH(int h) {
        this.maxLevel = h;
    }
}

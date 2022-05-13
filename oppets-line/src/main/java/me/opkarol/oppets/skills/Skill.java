package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.TypeOfEntity;

import java.util.List;

public class Skill extends SkillEnums {
    private String name;
    private List<Ability> abilityList;
    private List<Requirement> requirementList;
    private List<Adder> adderList;
    private List<TypeOfEntity> typeOfEntityList;
    private int maxLevel;

    public Skill(String name, List<Ability> b, List<Requirement> c, List<Adder> e, List<TypeOfEntity> f, int h) {
        setA(name);
        setB(b);
        setC(c);
        setE(e);
        setF(f);
        setH(h);
    }

    public String getName() {
        return name;
    }

    public void setA(String a) {
        this.name = a;
    }

    public List<Ability> getAbilityList() {
        return abilityList;
    }

    public void setB(List<Ability> b) {
        this.abilityList = b;
    }

    public List<Requirement> getRequirementList() {
        return requirementList;
    }

    public void setC(List<Requirement> c) {
        this.requirementList = c;
    }

    public List<Adder> getAdderList() {
        return adderList;
    }

    public void setE(List<Adder> e) {
        this.adderList = e;
    }

    public List<TypeOfEntity> getTypeOfEntityList() {
        return typeOfEntityList;
    }

    public void setF(List<TypeOfEntity> f) {
        this.typeOfEntityList = f;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setH(int h) {
        this.maxLevel = h;
    }
}

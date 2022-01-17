package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.OpPetsEntityTypes;

import java.util.List;
import java.util.UUID;

public class Skill extends SkillEnums {

    /*
    String a = name
    SkillsAbilities[] b = list of skill abilities
    SkillsRequirements c = -"-
    SkillsBoosters d = -"-
    SkillsAdders e = -"-
    OpPetsEntityTypes.TypeOfEntity[] f = allowed entities to have selected skill
    UUID g = skill UUID;
     */

    private String a;
    private List<Ability> b;
    private List<Requirement> c;
    private List<Booster> d;
    private List<Adder> e;
    private List<OpPetsEntityTypes.TypeOfEntity> f;
    private UUID g;

    public Skill(String name, List<Ability> b, List<Requirement> c, List<Booster> d, List<Adder> e, List<OpPetsEntityTypes.TypeOfEntity> f, UUID g){
        setA(name);
        setB(b);
        setC(c);
        setD(d);
        setE(e);
        setF(f);
        setG(g);
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public List<Ability> getB() {
        return b;
    }

    public void setB(List<Ability> b) {
        this.b = b;
    }

    public List<Requirement> getC() {
        return c;
    }

    public void setC(List<Requirement> c) {
        this.c = c;
    }

    public List<Booster> getD() {
        return d;
    }

    public void setD(List<Booster> d) {
        this.d = d;
    }

    public List<Adder> getE() {
        return e;
    }

    public void setE(List<Adder> e) {
        this.e = e;
    }

    public List<OpPetsEntityTypes.TypeOfEntity> getF() {
        return f;
    }

    public void setF(List<OpPetsEntityTypes.TypeOfEntity> f) {
        this.f = f;
    }

    public UUID getG() {
        return g;
    }

    public void setG(UUID g) {
        this.g = g;
    }
}

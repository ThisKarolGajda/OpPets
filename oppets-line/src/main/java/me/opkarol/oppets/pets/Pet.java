package me.opkarol.oppets.pets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.graphic.IGetter;
import me.opkarol.oppets.pets.objects.PetPreferences;
import me.opkarol.oppets.pets.objects.PetSettings;
import me.opkarol.oppets.utils.SkillUtils;
import me.opkarol.oppets.storage.OpObjects;
import me.opkarol.oppets.pets.id.UniquePet;

import java.io.Serializable;
import java.util.UUID;

public class Pet implements Serializable, IGetter {
    private String name, skill, prestige;
    private TypeOfEntity type;
    private double experience;
    private int level;
    private boolean active;
    public UniquePet petUUID;
    public PetSettings settings;
    public PetPreferences preferences;

    public Pet(String petName, TypeOfEntity petType, UUID ownUUID, UUID ownerUUID, boolean active) {
        this();
        setPetName(petName);
        setPetExperience(0);
        setLevel(0);
        setPetUUID(new UniquePet().setOwnUUID(ownUUID).setOwnerUUID(ownerUUID));
        setPetType(petType);
        setSkillName(new SkillUtils().getRandomSkillName(petType));
        setActive(active);
        setPrestige("0;&a");
    }

    public Pet(String name, double experience, int level, TypeOfEntity type, boolean active, UUID ownUUID, UUID ownerUUID, String skill, String prestige, UniquePet petUUID, OpObjects preferences, OpObjects settings) {
        setPetName(name);
        setPetExperience(experience);
        setLevel(level);
        setPetType(type);
        setActive(active);
        setPetUUID(petUUID);
        this.petUUID.setOwnerUUID(ownerUUID).setOwnUUID(ownUUID);
        setSkillName(skill);
        setPrestige(prestige);
        setPreferences(new PetPreferences(this, preferences));
        setSettings(new PetSettings(this, settings));
    }

    public Pet() {
        setSettings(new PetSettings(this));
        setPreferences(new PetPreferences(this));
    }

    @Override
    public GETTER_TYPE getGetterType() {
        return GETTER_TYPE.PET;
    }

    @Override
    public Object getObject() {
        return this;
    }

    public String getPetName() {
        return name;
    }

    public void setPetName(String petName) {
        this.name = petName;
    }

    public double getPetExperience() {
        return experience;
    }

    public void setPetExperience(double petExperience) {
        this.experience = petExperience;
    }

    public void setPetType(TypeOfEntity petType) {
        this.type = petType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSkillName() {
        return skill;
    }

    public void setSkillName(String skill) {
        this.skill = skill;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPrestige() {
        return prestige;
    }

    public void setPrestige(String prestige) {
        this.prestige = prestige;
    }

    public void setPetUUID(UniquePet uuid) {
        this.petUUID = uuid;
    }

    public void setType(TypeOfEntity type) {
        this.type = type;
    }

    public TypeOfEntity getPetType() {
        return type;
    }

    public void setPreferences(PetPreferences object) {
        this.preferences = object;
    }

    public void setSettings(PetSettings settings) {
        this.settings = settings;
    }

    public UniquePet getPetUUID() {
        return petUUID;
    }
}

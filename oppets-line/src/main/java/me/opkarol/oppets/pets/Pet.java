package me.opkarol.oppets.pets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.graphic.IGetter;
import me.opkarol.oppets.skills.SkillUtils;
import me.opkarol.oppets.storage.OpObjects;
import me.opkarol.oppets.uuid.PetUUID;

import java.io.Serializable;
import java.util.UUID;

public class Pet implements Serializable, IGetter {
    private String name,
    skill,
    prestige;
    private TypeOfEntity type;
    private double experience;
    private int level;
    private boolean active;
    private UUID ownID,
    ownerID;
    private PetUUID petID;
    private OpObjects preferences = new OpObjects(),
    settings = new OpObjects();
    private final PetsConverter converter = new PetsConverter();
    // AGEABLE - bool = false -> ADULT, true -> BABY
    // PRIVATE - bool = false -> PUBLIC, true -> PRIVATE

    public Pet(String petName, TypeOfEntity petType, UUID ownUUID, UUID ownerUUID, boolean active) {
        this();
        setPetName(petName);
        setPetExperience(0);
        setLevel(0);
        setPetType(petType);
        setActive(false);
        setOwnUUID(ownUUID);
        setOwnerUUID(ownerUUID);
        setSkillName(new SkillUtils().getRandomSkillName(petType));
        resetSettings();
        setActive(active);
        setPrestige("0;&a");
        setPetUUID(new PetUUID());
        setPreferences(converter.createPetPreferences(this));
    }

    public Pet(String a, double b1, int b2, TypeOfEntity c, boolean d, UUID e1, UUID e2, String skill, String prestige, PetUUID petUUID, OpObjects preferences, OpObjects settings) {
        this();
        setPetName(a);
        setPetExperience(b1);
        setLevel(b2);
        setPetType(c);
        setActive(d);
        setOwnUUID(e1);
        setOwnerUUID(e2);
        setSettings(settings);
        setSkillName(skill);
        setPrestige(prestige);
        setPetUUID(petUUID);
        setPreferences(preferences);
    }

    public Pet() {
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

    @Override
    public GETTER_TYPE getGetterType() {
        return GETTER_TYPE.PET;
    }

    @Override
    public Object getObject() {
        return this;
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

    public UUID getOwnUUID() {
        return ownID;
    }

    public void setOwnUUID(UUID ownUUID) {
        this.ownID = ownUUID;
    }

    public UUID getOwnerUUID() {
        return ownerID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerID = ownerUUID;
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

    public boolean isVisibleToOthers() {
        return converter.readPetSetting(this, "visibleToOthers");
    }

    public void setVisibleToOthers(boolean b) {
        setSettings(converter.setPetSetting(this, "visibleToOthers", b));
    }

    public boolean isGiftable() {
        return converter.readPetSetting(this, "giftable");
    }

    public void setGiftable(boolean b) {
        setSettings(converter.setPetSetting(this, "giftable", b));
    }

    //TODO add glow colors
    public boolean isGlowing() {
        return converter.readPetSetting(this, "glows");
    }

    public void setGlow(boolean b) {
        setSettings(converter.setPetSetting(this, "glows", b));
    }

    public boolean isFollowingPlayer() {
        return converter.readPetSetting(this, "followPlayer");
    }

    public void setFollowPlayer(boolean b) {
        setSettings(converter.setPetSetting(this, "followPlayer", b));
    }

    public boolean isTeleportingToPlayer() {
        return converter.readPetSetting(this, "teleportToPlayer");
    }

    public void setTeleportingToPlayer(boolean b) {
        setSettings(converter.setPetSetting(this, "teleportToPlayer", b));
    }

    public boolean isRideable() {
        return converter.readPetSetting(this, "rideable");
    }

    public void setRideable(boolean b) {
        setSettings(converter.setPetSetting(this, "rideable", b));
    }

    public boolean isOtherRideable() {
        return converter.readPetSetting(this, "otherRideable");
    }

    public void setOtherRideable(boolean b) {
        setSettings(converter.setPetSetting(this, "otherRideable", b));
    }

    public boolean areParticlesEnabled() {
        return converter.readPetSetting(this, "particlesEnabled");
    }

    public void setParticlesEnabled(boolean b) {
        setSettings(converter.setPetSetting(this, "particlesEnabled", b));
    }

    public void resetSettings() {
        setSettings(converter.createPetSettings(this));
    }

    public PetUUID getPetUUID() {
        return petID;
    }

    public void setPetUUID(PetUUID uuid) {
        this.petID = uuid;
    }

    public void setType(TypeOfEntity type) {
        this.type = type;
    }

    public TypeOfEntity getPetType() {
        return type;
    }

    public void setPreferences(OpObjects object) {
        this.preferences = object;
    }

    public OpObjects getPreferences() {
        return this.preferences;
    }

    public boolean isBaby() {
        return converter.readPetPreference(this, "ageable");
    }

    public OpObjects getSettings() {
        return settings;
    }

    public void setSettings(OpObjects settings) {
        this.settings = settings;
    }
}

package dir.pets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.UUID;

import static dir.pets.PetsUtils.getBinaryFromStringAndChar;
import static dir.pets.PetsUtils.getBinaryFromStringToChar;

/**
 * Pet class is a main serializable public class that is used to create save-able pets objects.
 * It contains all necessary method to manage pet itself.
 * Every method from this class can work as an external API.
 */

public class Pet implements Serializable {
    private String                         name;
    private double                         experience;
    private int                            level;
    private OpPetsEntityTypes.TypeOfEntity type;
    private boolean                        active;
    private UUID                           own;
    private UUID                           owner;
    private String                         settings;
    private String                         skill;
    private String                         prestige;

    /**
     * This method is used to create new pet object.
     * Should be only used to generate not value-specific objects.
     *
     * Pet name could= be invalid which will return in a error.
     *
     * @param petName name of the pet
     * @param petType type of the pet
     * @param ownUUID own uuid of the pet
     * @param ownerUUID uuid of the owner's pet
     * @param skillName skill name that pet contains
     * @param active boolean value - is active
     */
    public Pet(String petName, OpPetsEntityTypes.TypeOfEntity petType, UUID ownUUID, UUID ownerUUID, String skillName, boolean active) {
        setPetName(petName);
        setPetExperience(0);
        setLevel(0);
        setPetType(petType);
        setActive(false);
        setOwnUUID(ownUUID);
        setOwnerUUID(ownerUUID);
        setSkillName(skillName);
        resetSettings();
        setActive(active);
        setPrestige("0;&a");
    }

    /**
     * This method creates pet entire from scratch.
     * It can be used as an external api connect to create and add own pet objects.
     * All params are focused on pet suitability.
     *
     * This can provide null if params are invalid.
     *
     * @param a string pet name
     * @param b1 double pet experience
     * @param b2 int level
     * @param c type
     * @param d boolean active
     * @param e1 own uuid
     * @param e2 owner uuid
     * @param sS string settings "Serialized as a binary"
     * @param skill string skill name
     * @param prestige string prestige combination "level;codes"
     */
    public Pet(String a, double b1, int b2, OpPetsEntityTypes.TypeOfEntity c, boolean d, UUID e1, UUID e2, String sS, String skill, String prestige) {
        setPetName(a);
        setPetExperience(b1);
        setLevel(b2);
        setPetType(c);
        setActive(d);
        setOwnUUID(e1);
        setOwnerUUID(e2);
        setSettingsSerialized(sS);
        setSkillName(skill);
        setPrestige(prestige);
    }

    public @Nullable String getPetName() {
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

    public OpPetsEntityTypes.TypeOfEntity getPetType() {
        return type;
    }

    public void setPetType(OpPetsEntityTypes.TypeOfEntity petType) {
        this.type = petType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UUID getOwnUUID() {
        return own;
    }

    public void setOwnUUID(UUID ownUUID) {
        this.own = ownUUID;
    }

    public UUID getOwnerUUID() {
        return owner;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.owner = ownerUUID;
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

    public String getSettingsSerialized() {
        return this.settings;
    }

    public void setSettingsSerialized(String settingsSerialized) {
        this.settings = settingsSerialized;
    }

    public boolean isVisibleToOthers() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 0);
    }

    public void setVisibleToOthers(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 0, b);
    }

    public boolean isGiftable() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 1);
    }

    public void setGiftable(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 1, b);
    }

    public boolean isGlowing() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 2);
    }

    public void setGlow(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 2, b);
    }

    public boolean isFollowingPlayer() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 3);
    }

    public void setFollowPlayer(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 3, b);
    }

    public boolean isTeleportingToPlayer() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 4);
    }

    public void setTeleportingToPlayer(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 4, b);
    }

    public boolean isRideable() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 5);
    }

    public void setRideable(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 5, b);
    }

    public boolean isOtherRideable() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 6);
    }

    public void setOtherRideable(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 6, b);
    }

    public boolean areParticlesEnabled() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 7);
    }

    public void setParticlesEnabled(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 7, b);
    }

    public void resetSettings() {
        this.settings = "10011101";
    }

}

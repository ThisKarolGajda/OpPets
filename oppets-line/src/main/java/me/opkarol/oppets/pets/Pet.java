package me.opkarol.oppets.pets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.uuid.PetUUID;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.UUID;

import static me.opkarol.oppets.utils.PetsUtils.getBinaryFromStringAndChar;
import static me.opkarol.oppets.utils.PetsUtils.getBinaryFromStringToChar;

/**
 * Pet class is a main serializable public class that is used to create save-able pets objects.
 * It contains all necessary method to manage pet itself.
 * Every method from this class can work as an external API.
 */
public class Pet implements Serializable {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Experience.
     */
    private double experience;
    /**
     * The Level.
     */
    private int level;
    /**
     * The Type.
     */
    private OpPetsEntityTypes.TypeOfEntity type;
    /**
     * The Active.
     */
    private boolean active;
    /**
     * The Own.
     */
    private UUID own;
    /**
     * The Owner.
     */
    private UUID owner;
    /**
     * The Settings.
     */
    private String settings;
    /**
     * The Skill.
     */
    private String skill;
    /**
     * The Prestige.
     */
    private String prestige;
    /**
     * The Uuid.
     */
    private PetUUID uuid;

    /**
     * This method is used to create new pet object.
     * Should be only used to generate not value-specific objects.
     * <p>
     * Pet name could= be invalid which will return in a error.
     *
     * @param petName   name of the pet
     * @param petType   type of the pet
     * @param ownUUID   own uuid of the pet
     * @param ownerUUID uuid of the owner's pet
     * @param skillName skill name that pet contains
     * @param active    boolean value - is active
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
        setPetUUID(new PetUUID());
    }

    /**
     * This method creates pet entire from scratch.
     * It can be used as an external api connect to create and add own pet objects.
     * All params are focused on pet suitability.
     * <p>
     * This can provide null if params are invalid.
     *
     * @param a        string pet name
     * @param b1       double pet experience
     * @param b2       int level
     * @param c        pet type
     * @param d        boolean active
     * @param e1       own uuid
     * @param e2       owner uuid
     * @param sS       string settings "Serialized as a binary"
     * @param skill    string skill name
     * @param prestige string prestige combination "level;codes"
     * @param petUUID  own pet uuid which contains unique value for pet
     */
    public Pet(String a, double b1, int b2, OpPetsEntityTypes.TypeOfEntity c, boolean d, UUID e1, UUID e2, String sS, String skill, String prestige, PetUUID petUUID) {
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
        setPetUUID(petUUID);
    }

    /**
     * Gets pet name.
     *
     * @return the pet name
     */
    public @Nullable String getPetName() {
        return name;
    }

    /**
     * Sets pet name.
     *
     * @param petName the pet name
     */
    public void setPetName(String petName) {
        this.name = petName;
    }

    /**
     * Gets pet experience.
     *
     * @return the pet experience
     */
    public double getPetExperience() {
        return experience;
    }

    /**
     * Sets pet experience.
     *
     * @param petExperience the pet experience
     */
    public void setPetExperience(double petExperience) {
        this.experience = petExperience;
    }

    /**
     * Gets pet type.
     *
     * @return the pet type
     */
    public OpPetsEntityTypes.TypeOfEntity getPetType() {
        return type;
    }

    /**
     * Sets pet type.
     *
     * @param petType the pet type
     */
    public void setPetType(OpPetsEntityTypes.TypeOfEntity petType) {
        this.type = petType;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets own uuid.
     *
     * @return the own uuid
     */
    public UUID getOwnUUID() {
        return own;
    }

    /**
     * Sets own uuid.
     *
     * @param ownUUID the own uuid
     */
    public void setOwnUUID(UUID ownUUID) {
        this.own = ownUUID;
    }

    /**
     * Gets owner uuid.
     *
     * @return the owner uuid
     */
    public UUID getOwnerUUID() {
        return owner;
    }

    /**
     * Sets owner uuid.
     *
     * @param ownerUUID the owner uuid
     */
    public void setOwnerUUID(UUID ownerUUID) {
        this.owner = ownerUUID;
    }

    /**
     * Gets skill name.
     *
     * @return the skill name
     */
    public String getSkillName() {
        return skill;
    }

    /**
     * Sets skill name.
     *
     * @param skill the skill
     */
    public void setSkillName(String skill) {
        this.skill = skill;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets prestige.
     *
     * @return the prestige
     */
    public String getPrestige() {
        return prestige;
    }

    /**
     * Sets prestige.
     *
     * @param prestige the prestige
     */
    public void setPrestige(String prestige) {
        this.prestige = prestige;
    }

    /**
     * Gets settings serialized.
     *
     * @return the settings serialized
     */
    public String getSettingsSerialized() {
        return this.settings;
    }

    /**
     * Sets settings serialized.
     *
     * @param settingsSerialized the settings serialized
     */
    public void setSettingsSerialized(String settingsSerialized) {
        this.settings = settingsSerialized;
    }

    /**
     * Is visible to others boolean.
     *
     * @return the boolean
     */
    public boolean isVisibleToOthers() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 0);
    }

    /**
     * Sets visible to others.
     *
     * @param b the b
     */
    public void setVisibleToOthers(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 0, b);
    }

    /**
     * Is giftable boolean.
     *
     * @return the boolean
     */
    public boolean isGiftable() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 1);
    }

    /**
     * Sets giftable.
     *
     * @param b the b
     */
    public void setGiftable(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 1, b);
    }

    /**
     * Is glowing boolean.
     *
     * @return the boolean
     */
    public boolean isGlowing() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 2);
    }

    /**
     * Sets glow.
     *
     * @param b the b
     */
    public void setGlow(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 2, b);
    }

    /**
     * Is following player boolean.
     *
     * @return the boolean
     */
    public boolean isFollowingPlayer() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 3);
    }

    /**
     * Sets follow player.
     *
     * @param b the b
     */
    public void setFollowPlayer(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 3, b);
    }

    /**
     * Is teleporting to player boolean.
     *
     * @return the boolean
     */
    public boolean isTeleportingToPlayer() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 4);
    }

    /**
     * Sets teleporting to player.
     *
     * @param b the b
     */
    public void setTeleportingToPlayer(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 4, b);
    }

    /**
     * Is rideable boolean.
     *
     * @return the boolean
     */
    public boolean isRideable() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 5);
    }

    /**
     * Sets rideable.
     *
     * @param b the b
     */
    public void setRideable(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 5, b);
    }

    /**
     * Is other rideable boolean.
     *
     * @return the boolean
     */
    public boolean isOtherRideable() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 6);
    }

    /**
     * Sets other rideable.
     *
     * @param b the b
     */
    public void setOtherRideable(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 6, b);
    }

    /**
     * Are particles enabled boolean.
     *
     * @return the boolean
     */
    public boolean areParticlesEnabled() {
        return getBinaryFromStringAndChar(getSettingsSerialized(), 7);
    }

    /**
     * Sets particles enabled.
     *
     * @param b the b
     */
    public void setParticlesEnabled(boolean b) {
        settings = getBinaryFromStringToChar(getSettingsSerialized(), 7, b);
    }

    /**
     * Reset settings.
     */
    public void resetSettings() {
        this.settings = "10011101";
    }

    /**
     * Gets pet uuid.
     *
     * @return the pet uuid
     */
    public PetUUID getPetUUID() {
        return uuid;
    }

    /**
     * Sets pet uuid.
     *
     * @param uuid the uuid
     */
    public void setPetUUID(PetUUID uuid) {
        this.uuid = uuid;
    }
}

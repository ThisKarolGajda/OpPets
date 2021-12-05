package me.opkarol.oppets.pets;

import org.bukkit.Warning;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.TestOnly;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

import static me.opkarol.oppets.utils.PetsUtils.*;

public class Pet implements Serializable {
    private String a;     //petName
    private float b;      //petExperience
    private EntityType c; //petType
    private boolean d;    //isActive
    private UUID e1;      //ownUUID
    private UUID e2;      //ownerUUID
    private String sS = "10011101";

    public Pet(String petName, EntityType petType, UUID ownUUID, UUID ownerUUID){
        setPetName(petName);
        setPetExperience(0);
        setPetType(petType);
        setActive(false);
        setOwnUUID(ownUUID);
        setOwnerUUID(ownerUUID);
    }

    public Pet(){}

    public @Nullable String getPetName() {
        return a;
    }

    public void setPetName(String petName) {
        this.a = petName;
    }

    public float getPetExperience() {
        return b;
    }

    public void setPetExperience(float petExperience) {
        this.b = petExperience;
    }

    public EntityType getPetType() {
        return c;
    }

    public void setPetType(EntityType petType) {
        this.c = petType;
    }

    public boolean isActive() {
        return d;
    }

    public void setActive(boolean active) {
        d = active;
    }

    public UUID getOwnUUID() {
        return e1;
    }

    public void setOwnUUID(UUID ownUUID) {
        this.e1 = ownUUID;
    }

    public UUID getOwnerUUID() {
        return e2;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.e2 = ownerUUID;
    }

    public boolean isVisibleToOthers() {
        return getDeserializedValueOfSettingsPetFromIndex(getSettingsSerialized(), 0);
    }

    public void setVisibleToOthers(boolean b) {
        setSettingsSerialized(setDeserializedSettings(getSettingsSerialized(), 0, b));
    }

    public boolean isGiftable() {
        return getDeserializedValueOfSettingsPetFromIndex(getSettingsSerialized(), 1);
    }

    public void setGiftable(boolean b) {
        setSettingsSerialized(setDeserializedSettings(getSettingsSerialized(), 1, b));
    }

    public boolean isFollowingPlayer() {
        return getDeserializedValueOfSettingsPetFromIndex(getSettingsSerialized(), 3);
    }

    public void setFollowPlayer(boolean b) {
        setSettingsSerialized(setDeserializedSettings(getSettingsSerialized(), 3, b));
    }

    public boolean isTeleportingToPlayer() {
        return getDeserializedValueOfSettingsPetFromIndex(getSettingsSerialized(), 4);
    }

    public void setTeleportingToPlayer(boolean b) {
        setSettingsSerialized(setDeserializedSettings(getSettingsSerialized(), 4, b));
    }

    public boolean isRideable() {
        return getDeserializedValueOfSettingsPetFromIndex(getSettingsSerialized(), 5);
    }

    public void setRideable(boolean b) {
        setSettingsSerialized(setDeserializedSettings(getSettingsSerialized(), 5, b));
    }

    public boolean isOtherRideable() {
        return getDeserializedValueOfSettingsPetFromIndex(getSettingsSerialized(), 6);
    }

    public void setOtherRideable(boolean b) {
        setSettingsSerialized(setDeserializedSettings(getSettingsSerialized(), 6, b));
    }

    public boolean areParticlesEnabled() {
        return getDeserializedValueOfSettingsPetFromIndex(getSettingsSerialized(), 7);
    }

    public void setParticlesEnabled(boolean b) {
        setSettingsSerialized(setDeserializedSettings(getSettingsSerialized(), 7, b));
    }

    public boolean isGlowing() {
        return getDeserializedValueOfSettingsPetFromIndex(getSettingsSerialized(), 2);
    }

    public void setGlow(boolean b) {
        setSettingsSerialized(setDeserializedSettings(getSettingsSerialized(), 2, b));
    }

    public String getSettingsSerialized() {
        return this.sS;
    }

    public void setSettingsSerialized(String settingsSerialized) {
        this.sS = settingsSerialized;
    }

    @Warning
    @TestOnly
    public boolean[] gDR(){
        return deserializeSettingsOfPet(getSettingsSerialized());
    }

    @Warning
    @TestOnly
    public void rS(){
        this.sS = "10011101";
    }
}

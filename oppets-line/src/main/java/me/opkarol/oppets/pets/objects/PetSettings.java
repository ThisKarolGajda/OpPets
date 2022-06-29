package me.opkarol.oppets.pets.objects;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.converter.PetsConverter;
import me.opkarol.oppets.api.storage.OpObjects;

import java.io.Serializable;

public class PetSettings implements Serializable {
    private OpObjects settings = new OpObjects();
    private final PetsConverter converter = new PetsConverter();
    private Pet pet;

    public PetSettings(Pet pet) {
        this.pet = pet;
        settings = converter.createPetSettings(pet);
    }

    public PetSettings(Pet pet, OpObjects objects) {
        this.pet = pet;
        setSettings(objects);
    }

    public PetSettings() {

    }

    public boolean isVisibleToOthers() {
        return converter.readPetSetting(pet, "visibleToOthers");
    }

    public void setVisibleToOthers(boolean b) {
        setSettings(converter.setPetSetting(pet, "visibleToOthers", b));
    }

    public boolean isGiftable() {
        return converter.readPetSetting(pet, "giftable");
    }

    public void setGiftable(boolean b) {
        setSettings(converter.setPetSetting(pet, "giftable", b));
    }

    //TODO add glow colors
    public boolean isGlowing() {
        return converter.readPetSetting(pet, "glows");
    }

    public void setGlow(boolean b) {
        setSettings(converter.setPetSetting(pet, "glows", b));
    }

    public boolean isFollowingPlayer() {
        return converter.readPetSetting(pet, "followPlayer");
    }

    public void setFollowPlayer(boolean b) {
        setSettings(converter.setPetSetting(pet, "followPlayer", b));
    }

    public boolean isTeleportingToPlayer() {
        return converter.readPetSetting(pet, "teleportToPlayer");
    }

    public void setTeleportingToPlayer(boolean b) {
        setSettings(converter.setPetSetting(pet, "teleportToPlayer", b));
    }

    public boolean isRideable() {
        return converter.readPetSetting(pet, "rideable");
    }

    public void setRideable(boolean b) {
        setSettings(converter.setPetSetting(pet, "rideable", b));
    }

    public boolean isOtherRideable() {
        return converter.readPetSetting(pet, "otherRideable");
    }

    public void setOtherRideable(boolean b) {
        setSettings(converter.setPetSetting(pet, "otherRideable", b));
    }

    public boolean areParticlesEnabled() {
        return converter.readPetSetting(pet, "particlesEnabled");
    }

    public void setParticlesEnabled(boolean b) {
        setSettings(converter.setPetSetting(pet, "particlesEnabled", b));
    }

    public void resetSettings() {
        setSettings(converter.createPetSettings(pet));
    }

    public void setSettings(OpObjects settings) {
        this.settings = settings;
    }

    public OpObjects getSettings() {
        return settings;
    }

    public PetSettings negate(String s) {
        settings.negate(s);
        return this;
    }

    @Override
    public String toString() {
        return settings.toString();
    }
}

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

public class PetPreferences implements Serializable {
    private OpObjects preferences = new OpObjects();
    private final PetsConverter converter = new PetsConverter();
    private Pet pet;

    public PetPreferences(Pet pet) {
        this.pet = pet;
        preferences = converter.createPetPreferences(pet);
    }

    public PetPreferences(Pet pet, OpObjects objects) {
        this.pet = pet;
        setPreferences(objects);
    }

    public PetPreferences() {

    }

    public void setPreferences(OpObjects object) {
        this.preferences = object;
    }

    public OpObjects getPreferences() {
        return this.preferences;
    }

    public boolean isBaby() {
        return converter.readPetPreference(pet, "ageable");
    }

    @Override
    public String toString() {
        return preferences.toString();
    }
}

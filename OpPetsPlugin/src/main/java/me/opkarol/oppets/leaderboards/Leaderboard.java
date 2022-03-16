package me.opkarol.oppets.leaderboards;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.Pet;

import java.util.List;

public class Leaderboard {
    private final String name;
    private List<Pet> places;
    private final LEADERBOARD_TYPE type;

    public Leaderboard(String name, List<Pet> places, LEADERBOARD_TYPE type) {
        this.name = name;
        this.places = places;
        this.type = type;
    }

    public List<Pet> getPlaces() {
        return places;
    }

    public void setPlaces(List<Pet> places) {
        this.places = places;
    }

    public String getName() {
        return name;
    }

    public LEADERBOARD_TYPE getType() {
        return type;
    }

    public enum LEADERBOARD_TYPE {
        TOP_LEVEL, TOP_PRESTIGE, TOP_EXPERIENCE
    }

    public int getPlaceFromPet(Pet pet) {
        int i = 1;
        for (Pet pet1 : places) {
            if (pet1.getPetName() != null) {
                if (pet1.getPetName().equals(pet.getPetName())) {
                    break;
                } else {
                    i++;
                }
            }
        }
        return i;
    }
}

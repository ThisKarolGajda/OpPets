package me.opkarol.oppets.leaderboards;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.Pet;

import java.util.List;

/**
 * The type Leaderboard.
 */
public class Leaderboard {
    /**
     * The Name.
     */
    private final String name;
    /**
     * The Type.
     */
    private final LEADERBOARD_TYPE type;
    /**
     * The Places.
     */
    private List<Pet> places;

    /**
     * Instantiates a new Leaderboard.
     *
     * @param name   the name
     * @param places the places
     * @param type   the type
     */
    public Leaderboard(String name, List<Pet> places, LEADERBOARD_TYPE type) {
        this.name = name;
        this.places = places;
        this.type = type;
    }

    /**
     * Gets places.
     *
     * @return the places
     */
    public List<Pet> getPlaces() {
        return places;
    }

    /**
     * Sets places.
     *
     * @param places the places
     */
    public void setPlaces(List<Pet> places) {
        this.places = places;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public LEADERBOARD_TYPE getType() {
        return type;
    }

    /**
     * Gets place from pet.
     *
     * @param pet the pet
     * @return the place from pet
     */
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

    /**
     * The enum Leaderboard type.
     */
    public enum LEADERBOARD_TYPE {
        /**
         * Top level leaderboard type.
         */
        TOP_LEVEL,
        /**
         * Top prestige leaderboard type.
         */
        TOP_PRESTIGE,
        /**
         * Top experience leaderboard type.
         */
        TOP_EXPERIENCE
    }
}

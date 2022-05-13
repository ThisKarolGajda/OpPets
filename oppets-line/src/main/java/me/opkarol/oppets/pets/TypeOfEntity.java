package me.opkarol.oppets.pets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.pets.TypeOfEntity.VARIANTS.*;

public enum TypeOfEntity {
    AXOLOTL("Axolotl", AGEABLE),
    CAT("Cat", AGEABLE),
    CHICKEN("Chicken", AGEABLE),
    COW("Cow", AGEABLE),
    DONKEY("Donkey", AGEABLE),
    FOX("Fox", AGEABLE),
    GOAT("Goat", AGEABLE),
    HORSE("Horse", AGEABLE),
    LLAMA("Llama", AGEABLE),
    MULE("Mule", AGEABLE),
    MUSHROOM_COW("MushroomCow", AGEABLE),
    OCELOT("Ocelot", AGEABLE),
    PANDA("Panda", AGEABLE),
    PARROT("Parrot", AGEABLE),
    PIG("Pig", AGEABLE),
    POLAR_BEAR("PolarBear", AGEABLE),
    RABBIT("Rabbit", AGEABLE),
    SHEEP("Sheep", AGEABLE),
    TURTLE("Turtle", AGEABLE),
    WOLF("Wolf", AGEABLE);

    private final String name;
    private final List<VARIANTS> variants;

    TypeOfEntity(String name, VARIANTS... variants) {
        this.name = name;
        this.variants = Arrays.stream(variants).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public List<VARIANTS> getVariants() {
        return variants;
    }

    public enum VARIANTS {
        AGEABLE,
    }
}


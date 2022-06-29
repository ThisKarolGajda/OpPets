package me.opkarol.oppets.pets.converter;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.storage.OpObjects;
import org.jetbrains.annotations.NotNull;

public interface IPetsConverter {

    default OpObjects negatePetObjects(OpObjects objects, String s) {
        if (hasPetObject(objects, s)) {
            objects.negate(s);
            return objects;
        }
        return objects;
    }

    default boolean hasPetObject(@NotNull OpObjects objects, String s) {
        return objects.hasObject(s);
    }

    default boolean readPetObject(OpObjects objects, String s) {
        if (hasPetObject(objects, s)) {
            return objects.getBoolean(s, false);
        }
        return false;
    }
}

package me.opkarol.oppets.interfaces;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.OpPetsEntityTypes;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public interface IEntityManager {

    /**
     * Used to initialize pathfinder values in a specific entity object.
     *
     * @param entity object that can be cast to a specific version value
     * @param type entity type that will be used to read float values
     */
    void initPathfinder(@NotNull Object entity, OpPetsEntityTypes.TypeOfEntity type);

    /**
     * Method that will spawn an entire entity from provided arguments.
     *
     * @param obj1 player object
     * @param obj2 specific version animal entity object
     * @param obj3 pet object
     */
    void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3);

    /**
     * Used to retrieve entities for version specific server.
     *
     * @return list of string containing - specific version - available pets
     */
    HashSet<String> getAllowedEntities();
}

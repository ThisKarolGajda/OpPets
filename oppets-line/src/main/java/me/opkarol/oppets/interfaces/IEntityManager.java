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

/**
 * The interface Entity manager.
 */
public interface IEntityManager {

    /**
     * Init pathfinder.
     *
     * @param entity the entity
     * @param type   the type
     */
    void initPathfinder(@NotNull Object entity, OpPetsEntityTypes.TypeOfEntity type);

    /**
     * Spawn entity.
     *
     * @param obj1 the obj 1
     * @param obj2 the obj 2
     * @param obj3 the obj 3
     */
    void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3);

    /**
     * Gets allowed entities.
     *
     * @return the allowed entities
     */
    HashSet<String> getAllowedEntities();
}

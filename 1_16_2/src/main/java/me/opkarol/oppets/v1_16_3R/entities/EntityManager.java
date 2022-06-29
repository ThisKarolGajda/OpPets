package me.opkarol.oppets.v1_16_3R.entities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.entities.IEntityPet;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EntityManager extends me.opkarol.oppets.api.entities.manager.EntityManager {
    @Override
    public void setAge(IEntityPet iPet, Pet pet) {

    }

    @Override
    public void initPathfinder(Object pathfinderMob, TypeOfEntity type) {

    }

    @Override
    public Optional<IEntityPet> spawnEntity(Player player, @NotNull Pet pet) {
        return Optional.empty();
    }
}



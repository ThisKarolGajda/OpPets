package me.opkarol.oppets.v1_18_1R.entities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.entities.IEntityPet;
import me.opkarol.oppets.api.entities.manager.IEntityManager;
import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.api.misc.PetDatabaseObject;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.v1_18_1R.PathfinderGoalPet_1_18_1;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class EntityManager implements IEntityManager {
    private final Database database = Database.getInstance();

    public void initPathfinder(@NotNull Object entity, TypeOfEntity type) {
        Animal e = (Animal) entity;
        PetDatabaseObject object = database.getPetsDatabase().getObjectFromDatabase(type);
        e.goalSelector.getAvailableGoals().clear();
        e.goalSelector.addGoal(1, new PathfinderGoalPet_1_18_1(e, object.getEntitySpeed(), object.getEntityDistance()));
        e.goalSelector.addGoal(0, new FloatGoal(e));
        e.goalSelector.addGoal(2, new LookAtPlayerGoal(e, net.minecraft.world.entity.player.Player.class, 4.0F));
    }

    @Override
    public Optional<IEntityPet> spawnEntity(Player player, @NotNull Pet pet) {
        return Optional.empty();
    }

    @Override
    public Stream<String> getAllowedEntities() {
        return Arrays.stream(TypeOfEntity.values()).map(Enum::name);
    }

    @Override
    public OpMap<TypeOfEntity, Constructor<?>> getMap() {
        return null;
    }

}



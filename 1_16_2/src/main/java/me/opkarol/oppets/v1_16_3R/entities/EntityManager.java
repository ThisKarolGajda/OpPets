package me.opkarol.oppets.v1_16_3R.entities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.entities.IEntityPet;
import me.opkarol.oppets.entities.manager.IEntityManager;
import me.opkarol.oppets.misc.PetDatabaseObject;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.v1_16_3R.PathfinderGoalPet_1_16_2;
import me.opkarol.oppets.v1_16_3R.Utils;
import net.minecraft.server.v1_16_R2.EntityAnimal;
import net.minecraft.server.v1_16_R2.EntityHuman;
import net.minecraft.server.v1_16_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R2.PathfinderGoalLookAtPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class EntityManager implements IEntityManager {
    private final Database database = Database.getInstance();

    public void initPathfinder(@NotNull Object entity, TypeOfEntity type) {
        EntityAnimal e = (EntityAnimal) entity;
        PetDatabaseObject object = database.getPetsDatabase().getObjectFromDatabase(type);
        e.goalSelector.d().close();
        e.goalSelector.a(1, new PathfinderGoalPet_1_16_2(e, object.getEntitySpeed(), object.getEntityDistance()));
        e.goalSelector.a(2, new PathfinderGoalLookAtPlayer(e, EntityHuman.class, 4.0F));
        e.goalSelector.a(0, new PathfinderGoalFloat(e));

    }

    @Override
    public void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3) {
        EntityAnimal entity = (EntityAnimal) obj1;
        Player player = (Player) obj2;
        Pet pet = (Pet) obj3;
        Location location = player.getLocation();
        entity.setPosition(location.getX(), location.getY(), location.getZ());
        entity.setHealth(20.0f);
        entity.ageLocked = true;
        entity.setBaby(true);
        entity.setCustomNameVisible(true);
        entity.setInvulnerable(true);
        entity.setGoalTarget(((CraftPlayer) player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
        pet.setOwnerUUID(player.getUniqueId());
        pet.setOwnUUID(entity.getUniqueID());
        new Utils().removePathfinders(entity.goalSelector, entity.targetSelector);
        initPathfinder(null, pet.getPetType());
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



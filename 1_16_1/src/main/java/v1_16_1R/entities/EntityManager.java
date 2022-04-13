package v1_16_1R.entities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.misc.PetDatabaseObject;
import me.opkarol.oppets.interfaces.IEntityManager;
import me.opkarol.oppets.pets.OpPetsEntityTypes;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.server.v1_16_R1.EntityAnimal;
import net.minecraft.server.v1_16_R1.EntityHuman;
import net.minecraft.server.v1_16_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R1.PathfinderGoalLookAtPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;
import v1_16_1R.PathfinderGoalPet_1_16_1;
import v1_16_1R.SessionHolder;
import v1_16_1R.Utils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * The type Entity manager.
 */
public class EntityManager implements IEntityManager {
    private final Database database = Database.getInstance(SessionHolder.getInstance().getSession());

    /**
     * Init pathfinder.
     *
     * @param entity the entity
     * @param type   the type
     */
    @Override
    public void initPathfinder(@NotNull Object entity, OpPetsEntityTypes.TypeOfEntity type) {
        EntityAnimal e = (EntityAnimal) entity;
        PetDatabaseObject object = database.getPetsDatabase().getObjectFromDatabase(type);
        e.goalSelector.d().close();
        e.goalSelector.a(1, new PathfinderGoalPet_1_16_1(e, object.getEntitySpeed(), object.getEntityDistance()));
        e.goalSelector.a(2, new PathfinderGoalLookAtPlayer(e, EntityHuman.class, 4.0F));
        e.goalSelector.a(0, new PathfinderGoalFloat(e));

    }

    /**
     * Spawn entity.
     *
     * @param obj1 the obj 1
     * @param obj2 the obj 2
     * @param obj3 the obj 3
     */
    @Override
    public void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3) {
        EntityAnimal entity = (EntityAnimal) obj1;
        Player player = (Player) obj2;
        Pet pet = (Pet) obj3;
        Location location = player.getLocation();
        entity.setPosition(location.getX(), location.getY(), location.getZ());
        entity.setHealth(20.0f);
        entity.setAge(1);
        entity.ageLocked = true;
        entity.setCustomNameVisible(true);
        entity.setInvulnerable(true);
        entity.setGoalTarget(((CraftPlayer) player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
        pet.setOwnerUUID(player.getUniqueId());
        pet.setOwnUUID(entity.getUniqueID());
        new Utils().removePathfinders(entity.goalSelector, entity.targetSelector);
        initPathfinder(entity, pet.getPetType());
    }

    /**
     * Gets allowed entities.
     *
     * @return the allowed entities
     */
    @Override
    public HashSet<String> getAllowedEntities() {
        return new HashSet<>(Arrays.asList("Cat", "Chicken", "Cow", "Donkey", "Fox", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf"));
    }
}



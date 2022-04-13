package v1_18_1R.entities;

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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;
import v1_18_1R.PathfinderGoalPet_1_18_1;
import v1_18_1R.SessionHolder;
import v1_18_1R.Utils;

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
    public void initPathfinder(@NotNull Object entity, OpPetsEntityTypes.TypeOfEntity type) {
        Animal e = (Animal) entity;
        PetDatabaseObject object = database.getPetsDatabase().getObjectFromDatabase(type);
        e.goalSelector.getAvailableGoals().clear();
        e.goalSelector.addGoal(1, new PathfinderGoalPet_1_18_1(e, object.getEntitySpeed(), object.getEntityDistance()));
        e.goalSelector.addGoal(0, new FloatGoal(e));
        e.goalSelector.addGoal(2, new LookAtPlayerGoal(e, net.minecraft.world.entity.player.Player.class, 4.0F));
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
        Animal entity = (Animal) obj1;
        Player player = (Player) obj2;
        Pet pet = (Pet) obj3;
        Location location = player.getLocation();
        entity.setPos(location.getX(), location.getY(), location.getZ());
        entity.setHealth(20.0f);
        entity.ageLocked = true;
        entity.setBaby(true);
        entity.setCustomNameVisible(true);
        entity.setTarget(((CraftPlayer) player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
        pet.setOwnerUUID(player.getUniqueId());
        pet.setOwnUUID(entity.getUUID());
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
        return new HashSet<>(Arrays.asList("Axolotl", "Cat", "Chicken", "Cow", "Donkey", "Fox", "Goat", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf"));
    }

}



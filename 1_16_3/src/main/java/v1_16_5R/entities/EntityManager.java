package v1_16_5R.entities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.interfaces.EntityManagerInterface;
import dir.pets.Pet;
import net.minecraft.server.v1_16_R3.EntityAnimal;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;
import v1_16_5R.PathfinderGoalPet_1_16_3;
import v1_16_5R.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityManager implements EntityManagerInterface {

    public void initPathfinder(@NotNull Object entity) {
        EntityAnimal e = (EntityAnimal) entity;
        e.goalSelector.d().close();
        e.goalSelector.a(1, new PathfinderGoalPet_1_16_3(e, 1.9, 15));
        e.goalSelector.a(2, new PathfinderGoalLookAtPlayer(e, EntityHuman.class, 4.0F));
        e.goalSelector.a(0, new PathfinderGoalFloat(e));
    }

    @Override
    public void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3) {
        Player player = (Player) obj2;
        net.minecraft.server.v1_16_R3.EntityAnimal entity = (net.minecraft.server.v1_16_R3.EntityAnimal) obj1;
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
        initPathfinder(entity);
    }

    @Override
    public List<String> getAllowedEntities() {
        return new ArrayList<>(Arrays.asList("Cat", "Chicken", "Cow", "Donkey", "Fox", "Zoglin", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf"));
    }
}



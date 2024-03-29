package me.opkarol.oppets.v1_18_2R.entities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.entities.IEntityPet;
import me.opkarol.oppets.api.misc.PetDatabaseObject;
import me.opkarol.oppets.cache.NamespacedKeysCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.utils.Utils;
import me.opkarol.oppets.utils.external.PDCUtils;
import me.opkarol.oppets.v1_18_2R.PathfinderGoalPet_1_18_2;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EntityManager extends me.opkarol.oppets.api.entities.manager.EntityManager {
    private final Database database = Database.getInstance();

    public EntityManager() {
        setMap(setupMap());
    }

    public void initPathfinder(@NotNull Object pathfinderMob, TypeOfEntity type) {
        PetDatabaseObject object = database.getPetsDatabase().getObjectFromDatabase(type);
        PathfinderMob mob = (PathfinderMob) pathfinderMob;
        mob.goalSelector.removeAllGoals();
        mob.goalSelector.addGoal(0, new FloatGoal(mob));
        mob.goalSelector.addGoal(1, new PathfinderGoalPet_1_18_2(mob, object.getEntitySpeed(), object.getEntityDistance()));
        mob.goalSelector.addGoal(2, new LookAtPlayerGoal(mob, net.minecraft.world.entity.player.Player.class, 4.0F));
    }

    @Override
    public Optional<IEntityPet> spawnEntity(Player player, @NotNull Pet pet) {
        // Retrieving optional IEntityPet from abstract EntityManager
        Optional<IEntityPet> optional = getPetFromType(player, pet);
        if (!optional.isPresent()) {
            return Optional.empty();
        }

        // Setting values
        IEntityPet iPet = optional.get();
        CraftEntity entity = (CraftEntity) iPet.getBukkitCraftEntity();
        PathfinderMob mob = (PathfinderMob) iPet.getEntity();
        Location location = player.getLocation();

        // Setting values of entity
        setAge(iPet, pet);
        mob.setHealth(20f);
        entity.setCustomNameVisible(true);
        entity.setInvulnerable(true);
        entity.setCustomName(Utils.getFilledPetName(pet));
        entity.setGlowing(pet.settings.isGlowing());
        PDCUtils.addNBT(entity, NamespacedKeysCache.petKey, iPet.getTypeOfEntity().getName());

        // Setting database values into pet
        setPetIDInDatabase(player, entity.getUniqueId(), pet, database);

        // Spawning pet
        CraftWorld world = (CraftWorld) player.getWorld();
        world.addEntity((Entity) iPet.getEntity(), CreatureSpawnEvent.SpawnReason.CUSTOM);
        mob.setTarget(((CraftPlayer) player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, false);
        entity.teleport(location);
        mob.setPos(location.getX(), location.getY(), location.getZ());

        // Overriding default pathfinders
        initPathfinder(iPet, pet.getPetType());

        // Setting visibility to players
        setPrivate(iPet, pet, player, database);
        return Optional.of(iPet);
    }

    @Override
    public void setAge(@NotNull IEntityPet iPet, Pet pet) {
        if (iPet.canBeAgeable()) {
            AgeableMob ageableMob = (AgeableMob) iPet.getEntity();
            ageableMob.ageLocked = true;
            ageableMob.setBaby(pet.preferences.isBaby());
        }
    }

}



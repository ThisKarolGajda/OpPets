package me.opkarol.oppets.v1_18_2R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Location;

import java.util.EnumSet;

import static me.opkarol.oppets.utils.Utils.equalsComponentAndPetName;

public class PathfinderGoalPet_1_18_2 extends Goal {
    private final Database database = Database.getInstance();
    private final PathfinderMob mob;
    private final double speed;
    private final double distance;
    private LivingEntity target;
    private Pet pet;
    private Location lastLocation;

    public PathfinderGoalPet_1_18_2(PathfinderMob a, double speed, float distance) {
        this.mob = a;
        this.speed = speed;
        this.distance = distance;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.mob != null;
    }

    @Override
    public void tick() {
        this.target = this.mob.getTarget();
        if (pet == null && target != null) {
            pet = database.getDatabase().getCurrentPet(target.getUUID());
        }
        if (target == null || this.mob.getDisplayName() == null) {
            return;
        }
        if (lastLocation != null && lastLocation == target.getBukkitEntity().getLocation()) {
            return;
        }
        if (!(equalsComponentAndPetName(pet, this.mob.getDisplayName().getString()))) {
            return;
        }
        if (target.distanceTo(this.mob) >= distance && pet.settings.isTeleportingToPlayer()) {
            this.mob.teleportTo(target.getX(), target.getY(), target.getZ());
            return;
        }
        if (pet.settings.isFollowingPlayer()) {
            this.mob.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), this.speed);
            lastLocation = target.getBukkitEntity().getLocation();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.target != null && this.mob.getDisplayName() != null;
    }
}

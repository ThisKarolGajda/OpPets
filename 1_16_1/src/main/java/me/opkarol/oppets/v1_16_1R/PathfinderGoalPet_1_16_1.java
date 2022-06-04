package me.opkarol.oppets.v1_16_1R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PetsUtils;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Location;

import java.util.EnumSet;

public class PathfinderGoalPet_1_16_1 extends PathfinderGoal {
    private final Database database = Database.getInstance();

    private final EntityAnimal a;
    private final double f;
    private final double g;
    private Pet pet;
    private EntityLiving b;
    private Location l;

    public PathfinderGoalPet_1_16_1(EntityAnimal a, double speed, float distance) {
        this.a = a;
        this.f = speed;
        this.g = distance;
        this.a(EnumSet.of(Type.MOVE));
        pet = null;
    }

    @Override
    public boolean a() {
        this.b = this.a.getGoalTarget();
        if (pet == null && this.b != null) pet = database.getDatabase().getCurrentPet(b.getUniqueID());
        if (this.b == null) {
            return false;
        } else if (this.a.getDisplayName() == null || pet.getPetName() == null) {
            return false;
        } else if (l != null && l == b.getBukkitEntity().getLocation()) {
            return false;
        } else if (!(this.a.getDisplayName().getString().equals(PetsUtils.getPetFormattedName(pet)))) {
            return false;
        } else if (this.b.h(this.a) > (this.g * this.g)) {
            if (pet.settings.isTeleportingToPlayer()) this.a.setPosition(this.b.locX(), b.locY(), b.locZ());
            return false;
        } else {
            if (!pet.settings.isFollowingPlayer()) return false;
            Vec3D vec = RandomPositionGenerator.a(this.a, 16, 7, this.b.getPositionVector());
            return vec != null;
        }
    }

    @Override
    public void c() {
        l = b.getBukkitEntity().getLocation();
        this.a.getNavigation().a(this.b.locX(), this.b.locY(), this.b.locZ(), this.f);
    }

    @Override
    public boolean b() {
        if (this.a.getNavigation().m()) return false;
        assert this.b != null;
        return this.b.h(this.a) > (this.g * this.g);
    }

    @Override
    public void d() {
        this.b = null;
    }

}

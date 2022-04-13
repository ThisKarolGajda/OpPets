package v1_17_1R;

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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.Location;

import java.util.EnumSet;

/**
 * The type Pathfinder goal pet 1 17 1.
 */
public class PathfinderGoalPet_1_17_1 extends Goal {
    private final Database database = Database.getInstance(SessionHolder.getInstance().getSession());
    /**
     * The A.
     */
    private final Animal a;
    /**
     * The F.
     */
    private final double f;
    /**
     * The G.
     */
    private final double g;
    /**
     * The B.
     */
    private LivingEntity b;
    /**
     * The P.
     */
    private Pet p;
    /**
     * The L.
     */
    private Location l;

    /**
     * Instantiates a new Pathfinder goal pet 1 17 1.
     *
     * @param a        the a
     * @param speed    the speed
     * @param distance the distance
     */
    public PathfinderGoalPet_1_17_1(Animal a, double speed, float distance) {
        this.a = a;
        this.f = speed;
        this.g = distance;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    /**
     * Can use boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean canUse() {
        return this.a != null;
    }

    /**
     * Tick.
     */
    @Override
    public void tick() {
        this.b = this.a.getTarget();
        if (p == null && b != null)
            p = database.getDatabase().getCurrentPet(b.getUUID());
        if (b == null || this.a.getDisplayName() == null) {
            return;
        }
        else if (l != null && l == b.getBukkitEntity().getLocation()) {
            return;
        } else if (!(this.a.getDisplayName().getString().equals(PetsUtils.getPetFormattedName(p)))) {
            return;
        } else if (b.distanceTo(this.a) >= (this.g * this.g)) {
            if (p.isTeleportingToPlayer()) this.a.teleportTo(b.getX(), b.getY(), b.getZ());
            return;
        }
        if (p.isFollowingPlayer()) {
            this.a.getNavigation().moveTo(b.getX(), b.getY(), b.getZ(), this.f);
            l = b.getBukkitEntity().getLocation();
        }
    }

    /**
     * Can continue to use boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean canContinueToUse() {
        return this.b != null && this.a.getDisplayName() != null;
    }
}

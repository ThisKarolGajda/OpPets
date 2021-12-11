package v1_17_1R;

import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import dir.pets.Database;
import dir.pets.Pet;

import java.util.EnumSet;

public class PathfinderGoalPet_1_17_1 extends PathfinderGoal{

    private final EntityInsentient a;
    private EntityLiving b;

    private final double f;
    private final double g;

    public PathfinderGoalPet_1_17_1(EntityInsentient a, double speed, float distance) {
        this.a = a;
        this.f = speed;
        this.g = distance;
        this.a(EnumSet.of(Type.c));
        pet = null;
    }

    Pet pet;

    @Override
    public boolean a() {
        this.b = this.a.getGoalTarget();
        if (pet == null && b != null) {
            pet = Database.getDatabase().getCurrentPet(b.getUUID());
        }

        if (this.b == null) {
            return false;
        } else if (this.a.getDisplayName() == null) {
            return false;
        } else if (!(this.a.getDisplayName().getString().equals(pet.getPetName()))){
            return false;
        } else if (this.b.distanceTo(this.a) > (this.g * this.g)){
            if (pet.isTeleportingToPlayer()) this.a.setPos(this.b.getX(), b.getY(), b.getZ());
            return false;
        } else {
            return pet.isFollowingPlayer();
        }
    }

    @Override
    public void c() {
        this.a.getNavigation().a(this.b.getX(), b.getY(), b.getZ(), this.f);
    }

    @Override
    public boolean b() {
        return !this.a.getNavigation().m() && this.b.distanceTo(this.a) < (this.g * this.g);
    }

    @Override
    public void d() {
        this.b = null;
    }
}

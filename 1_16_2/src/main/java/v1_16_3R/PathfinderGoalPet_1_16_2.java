package v1_16_3R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.server.v1_16_R2.*;

import java.util.EnumSet;

public class PathfinderGoalPet_1_16_2 extends PathfinderGoal {

    private final EntityAnimal a;
    private EntityLiving b;

    private final double f;
    private final double g;

    public PathfinderGoalPet_1_16_2(EntityAnimal a, double speed, float distance) {
        this.a = a;
        this.f = speed;
        this.g = distance;
        this.a(EnumSet.of(Type.MOVE));
        pet = null;
    }

    Pet pet;

    @Override
    public boolean a() {
        this.b = this.a.getGoalTarget();
        if (pet == null && this.b != null) pet = OpPets.getDatabase().getCurrentPet(b.getUniqueID());
        if (this.b == null) {
            return false;
        } else if (this.a.getDisplayName() == null || pet.getPetName() == null) {
            return false;
        } else if (!(this.a.getDisplayName().getString().equals(pet.getPetName()))){
            return false;
        } else if (this.b.h(this.a) > (this.g * this.g)){
            if (pet.isTeleportingToPlayer()) this.a.setPosition(this.b.locX(), b.locY(), b.locZ());
            return false;
        } else {
            if (!pet.isFollowingPlayer()) return false;
            Vec3D vec = RandomPositionGenerator.a(this.a, 16, 7, this.b.getPositionVector());
            return vec != null;
        }
    }

    @Override
    public void c() {
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

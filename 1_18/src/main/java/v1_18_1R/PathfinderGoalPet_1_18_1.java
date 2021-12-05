package v1_18_1R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;

import java.util.EnumSet;

public class PathfinderGoalPet_1_18_1 extends PathfinderGoal {

    private final EntityInsentient a;
    private EntityLiving b;

    private final double f;
    private final double g;

    public PathfinderGoalPet_1_18_1(EntityInsentient a, double speed, float distance) {
        this.a = a;
        this.f = speed;
        this.g = distance;
        this.a(EnumSet.of(Type.c));
        pet = null;
    }

    Pet pet;

    @Override
    public boolean a() {
        this.b = this.a.bd;
        if (pet == null && b != null) {
            pet = OpPets.getDatabase().getCurrentPet(b.cm());
        }

        if (this.b == null) {
            return false;
        } else if (this.a.C_() == null) {
            return false;
        } else if (!(this.a.C_().toString().equals(pet.getPetName()))){
            return false;
        } else if (this.b.f(this.a) > (this.g * this.g)){
            if (pet.isTeleportingToPlayer()) this.a.e(this.b.dc(), b.de(), b.di());
            return false;
        } else {
            return pet.isFollowingPlayer();
        }
    }

    @Override
    public void c() {
        this.a.D().a(this.b.dc(), b.de(), b.di(), this.f);
    }

    @Override
    public boolean b() {
        return !this.a.D().m() && this.b.f(this.a) < (this.g * this.g);
    }

    @Override
    public void d() {
        this.b = null;
    }
}

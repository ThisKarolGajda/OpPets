package me.opkarol.oppets.pathfinders;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;

import java.util.EnumSet;

public class PathfinderGoalPet extends PathfinderGoal {

    private final EntityInsentient a;
    private EntityLiving b;

    private final double f;
    private final double g;

    public PathfinderGoalPet(EntityInsentient a, double speed, float distance) {
        this.a = a;
        this.f = speed;
        this.g = distance;
        this.a(EnumSet.of(Type.c));
    }

    Pet pet;

    @Override
    public boolean a() {
        this.b = this.a.getGoalTarget();
        if (b != null) {
            pet = OpPets.getDatabase().getCurrentPet(b.getUniqueID());
        }

        if (this.b == null) {
            return false;
        } else if (this.a.getDisplayName() == null) {
            return false;
        } else if (!(this.a.getDisplayName().getText().equals(pet.getPetName()))){
            return false;
        } else if (this.b.f(this.a) > (this.g * this.g)){
            if (pet.isTeleportingToPlayer()) this.a.setPosition(this.b.locX(), b.locY(), b.locZ());
            return false;
        } else {
            return pet.isFollowingPlayer();
        }
    }

    @Override
    public void c() {
        this.a.getNavigation().a(this.b.locX(), this.b.locY(), this.b.locZ(), this.f);
    }

    @Override
    public boolean b() {
        return !this.a.getNavigation().m() && this.b.f(this.a) < (this.g * this.g);
    }

    @Override
    public void d() {
        this.b = null;
    }

}
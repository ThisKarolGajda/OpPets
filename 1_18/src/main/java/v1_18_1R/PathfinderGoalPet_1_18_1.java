package v1_18_1R;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import dir.pets.Database;
import dir.pets.Pet;

public class PathfinderGoalPet_1_18_1 extends Goal {

    private final Animal a;

    private final double f;
    private final double g;

    public PathfinderGoalPet_1_18_1(Animal a, double speed, float distance) {
        this.a = a;
        this.f = speed;
        this.g = distance;
        pet = null;
    }

    Pet pet;

    @Override
    public boolean canUse() {
        return false;
    }

    public void tick(){
        LivingEntity b = this.a.getTarget();
        if (pet == null && b != null) {
            pet = Database.getDatabase().getCurrentPet(b.getUUID());
        }

        if (b == null) {
            return;
        } else if (this.a.getDisplayName() == null) {
            return;
        } else if (!(this.a.getDisplayName().toString().equals(pet.getPetName()))){
            return;
        } else if (b.distanceTo(this.a) > (this.g * this.g)){
            if (pet.isTeleportingToPlayer()) this.a.teleportTo(b.getX(), b.getY(), b.getZ());
            return;
        }
        if (pet.isFollowingPlayer()){
            this.a.getNavigation().moveTo(b.getX(), b.getY(), b.getZ(), this.f);
        }
    }
}

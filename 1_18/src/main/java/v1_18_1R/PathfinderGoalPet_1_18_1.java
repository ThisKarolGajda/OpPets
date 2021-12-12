package v1_18_1R;

import dir.pets.Database;
import dir.pets.Pet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.Location;

import java.util.EnumSet;

public class PathfinderGoalPet_1_18_1 extends Goal {

    private final Animal a;
    private LivingEntity b;

    private final double f;
    private final double g;

    private Pet p = null;
    private Location l;


    public PathfinderGoalPet_1_18_1(Animal a, double speed, float distance) {
        this.a = a;
        this.f = speed;
        this.g = distance;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }


    @Override
    public boolean canUse() {
        return this.a != null;
    }

    @Override
    public void tick(){

        this.b = this.a.getTarget();
        if (p == null && b != null)
            p = Database.getDatabase().getCurrentPet(b.getUUID());

        if (b == null || this.a.getDisplayName() == null) {
            return;
        }
        // Last location (l) is a construction parameter which validates last owner's position with current
        // which prevents using navigation for the same location.
        else if (l != null && l == b.getBukkitEntity().getLocation()) {
            return;
        } else if (!(this.a.getName().getString().equals(p.getPetName()))){
            return;
        } else if (b.distanceTo(this.a) >= (this.g * this.g)){
            if (p.isTeleportingToPlayer()) this.a.teleportTo(b.getX(), b.getY(), b.getZ());
            return;
        }
        if (p.isFollowingPlayer()){
            this.a.getNavigation().moveTo(b.getX(), b.getY(), b.getZ(), this.f);
            l = b.getBukkitEntity().getLocation();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.b != null && this.a.getDisplayName() != null;
    }
}

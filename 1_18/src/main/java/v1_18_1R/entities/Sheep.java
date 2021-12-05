package v1_18_1R.entities;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntitySheep;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Sheep extends EntitySheep {

    public Sheep(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.ax, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        OpPets.getEntityManager().spawnEntity(this, player, pet);
    }

    @Override
    public boolean d_() {
        return super.d_();
    }

    @Override
    protected boolean x() {
        return false;
    }
}

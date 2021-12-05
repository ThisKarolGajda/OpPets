package v1_18_1R.entities;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityOcelot;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Ocelot extends EntityOcelot {

    public Ocelot(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.ai, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
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

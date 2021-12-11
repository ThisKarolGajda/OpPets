package v1_18_1R.entities;

import dir.pets.Pet;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;



public class Ocelot extends net.minecraft.world.entity.animal.Ocelot {

    public Ocelot(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityType.OCELOT, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }
}

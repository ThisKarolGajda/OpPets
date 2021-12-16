package v1_16_3R.entities;

import dir.pets.Pet;
import net.minecraft.server.v1_16_R2.EntityPolarBear;
import net.minecraft.server.v1_16_R2.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PolarBear extends EntityPolarBear {
    public PolarBear(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.POLAR_BEAR, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }
}

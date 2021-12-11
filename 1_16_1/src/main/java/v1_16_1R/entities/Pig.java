package v1_16_1R.entities;

import net.minecraft.server.v1_16_R1.EntityPig;
import net.minecraft.server.v1_16_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import dir.pets.Pet;

import java.util.Objects;

public class Pig extends EntityPig {

    public Pig(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.PIG, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }
}

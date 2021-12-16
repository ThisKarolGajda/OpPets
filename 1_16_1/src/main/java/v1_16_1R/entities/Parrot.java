package v1_16_1R.entities;

import dir.pets.Pet;
import net.minecraft.server.v1_16_R1.EntityParrot;
import net.minecraft.server.v1_16_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Parrot extends EntityParrot {
    public Parrot(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.PARROT, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }
}

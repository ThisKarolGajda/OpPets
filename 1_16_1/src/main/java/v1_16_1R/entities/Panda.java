package v1_16_1R.entities;

import net.minecraft.server.v1_16_R1.EntityPanda;
import net.minecraft.server.v1_16_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import dir.pets.Pet;

import java.util.Objects;

public class Panda extends EntityPanda {
    public Panda(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.PANDA, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }
}

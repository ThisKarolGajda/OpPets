package v1_16_3R.entities;

import net.minecraft.server.v1_16_R2.EntityFox;
import net.minecraft.server.v1_16_R2.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import dir.pets.Pet;

import java.util.Objects;

public class Fox extends EntityFox {

    public Fox(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.FOX, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }
}

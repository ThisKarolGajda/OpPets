package v1_16_1R.entities;

import dir.pets.Pet;
import net.minecraft.server.v1_16_R1.EntityLlama;
import net.minecraft.server.v1_16_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Llama extends EntityLlama {
    public Llama(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.LLAMA, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }
}

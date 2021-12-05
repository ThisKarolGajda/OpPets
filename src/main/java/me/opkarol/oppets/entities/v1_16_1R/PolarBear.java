package me.opkarol.oppets.entities.v1_16_1R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.server.v1_16_R1.EntityPolarBear;
import net.minecraft.server.v1_16_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PolarBear extends EntityPolarBear {
    public PolarBear(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.POLAR_BEAR, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        OpPets.getEntityManager().spawnEntity(this, player, pet);
    }
}
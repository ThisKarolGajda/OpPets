package me.opkarol.oppets.entities.v1_17_1R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityWolf;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Wolf extends EntityWolf {
    public Wolf(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.bc, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        OpPets.getEntityManager().spawnEntity(this, player, pet);
    }
}

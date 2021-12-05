package me.opkarol.oppets.entities.v1_18_1R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityPig;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Pig extends EntityPig {

    public Pig(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.an, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        OpPets.getEntityManager().spawnEntity(this, player, pet);
    }
}

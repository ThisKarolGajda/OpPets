package me.opkarol.oppets.entities.v1_16_3R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.server.v1_16_R2.EntityPig;
import net.minecraft.server.v1_16_R2.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Pig extends EntityPig {

    public Pig(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.PIG, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        OpPets.getEntityManager().spawnEntity(this, player, pet);
    }
}
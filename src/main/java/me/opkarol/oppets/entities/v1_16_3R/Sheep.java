package me.opkarol.oppets.entities.v1_16_3R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.server.v1_16_R2.EntitySheep;
import net.minecraft.server.v1_16_R2.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Sheep extends EntitySheep {

    public Sheep(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.SHEEP, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        OpPets.getEntityManager().spawnEntity(this, player, pet);

    }
}

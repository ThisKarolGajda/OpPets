package me.opkarol.oppets.entities.v1_16_5R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.server.v1_16_R3.EntityCow;
import net.minecraft.server.v1_16_R3.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Cow extends EntityCow {

    public Cow(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.COW, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        OpPets.getEntityManager().spawnEntity(this, player, pet);
    }
}

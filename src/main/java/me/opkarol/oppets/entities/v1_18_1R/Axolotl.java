package me.opkarol.oppets.entities.v1_18_1R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Axolotl extends net.minecraft.world.entity.animal.axolotl.Axolotl {

    public Axolotl(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.e, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        OpPets.getEntityManager().spawnEntity(this, player, pet);
    }

}

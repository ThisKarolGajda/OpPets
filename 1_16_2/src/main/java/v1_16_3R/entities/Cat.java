package v1_16_3R.entities;

import dir.pets.Pet;
import net.minecraft.server.v1_16_R2.EntityCat;
import net.minecraft.server.v1_16_R2.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Cat extends EntityCat {

    public Cat(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.CAT, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }
}

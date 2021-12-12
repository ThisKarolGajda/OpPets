package v1_17_1R.entities;

import dir.pets.Pet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Cat extends net.minecraft.world.entity.animal.Cat {

    public Cat(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityType.CAT, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }

    @Override
    public void registerGoals() {
    }

    @Override
    protected void dropExperience() {
    }

    @Override
    protected void dropFromLootTable(DamageSource damagesource, boolean flag) {
    }

    @Override
    public boolean hurt(DamageSource damagesource, float f) {
        return false;
    }

}

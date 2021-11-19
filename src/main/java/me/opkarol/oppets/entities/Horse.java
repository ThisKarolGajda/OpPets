package me.opkarol.oppets.entities;

import me.opkarol.oppets.pathfinders.PathfinderGoalPet;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.animal.horse.EntityHorse;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Horse extends EntityHorse {

    public Horse(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityTypes.M, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager(this, player, pet);
        initPathfinder();
    }

    public void initPathfinder() {
        this.bP.a(1, new PathfinderGoalPet(this, 1.9, 15));
        this.bP.a(0, new PathfinderGoalFloat(this));
        this.bP.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 4.0F));
    }
}

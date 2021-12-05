package me.opkarol.oppets.entities.v1_18_1R;


import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.entities.EntityManagerInterface;
import me.opkarol.oppets.pathfinders.versions.PathfinderGoalPet_1_18_1;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityManager implements EntityManagerInterface {

    @Override
    public void spawnEntity(@NotNull EntityAnimal entity, @NotNull Player player, @NotNull Pet pet) {
        Location location = player.getLocation();
        entity.setPosition(location.getX(), location.getY(), location.getZ());
        entity.setHealth(20.0f);
        entity.ageLocked = true;
        entity.setBaby(true);
        entity.setCustomNameVisible(true);
        entity.setInvulnerable(true);
        entity.setGoalTarget(((CraftPlayer) player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
        pet.setOwnerUUID(player.getUniqueId());
        pet.setOwnUUID(entity.getUniqueID());
        OpPets.getUtils().removePathfinders(entity., entity.bQ);
        initPathfinder(entity);
    }

    public void initPathfinder(@NotNull EntityAnimal e) {
        e.bP.c().clear();
        e.bP.a(1, new PathfinderGoalPet_1_18_1(e, 1.9, 15));
        e.bP.a(0, new PathfinderGoalFloat(e));
        e.bP.a(2, new PathfinderGoalLookAtPlayer(e, EntityHuman.class, 4.0F));

    }

    @Override
    public List<String> getAllowedEntities() {
        return new ArrayList<>(Arrays.asList("Axolotl", "Cat", "Chicken", "Cow", "Donkey", "Fox", "Goat", "Zoglin", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf"));
    }

}



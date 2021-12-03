package me.opkarol.oppets.entities.v1_16_3R;

import me.opkarol.oppets.entities.EntityManagerInterface;
import me.opkarol.oppets.pathfinders.PathfinderGoalPet_1_16_2;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.server.v1_16_R2.EntityHuman;
import net.minecraft.server.v1_16_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R2.PathfinderGoalLookAtPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class EntityManager implements EntityManagerInterface {

    @Override
    public void spawnEntity(@NotNull net.minecraft.server.v1_16_R2.EntityAnimal entity, @NotNull Player player, @NotNull Pet pet) {
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
        initPathfinder(entity);
    }

    public void initPathfinder(net.minecraft.server.v1_16_R2.@NotNull EntityAnimal e) {
        e.goalSelector.d().close();
        e.goalSelector.a(1, new PathfinderGoalPet_1_16_2(e, 1.9, 15));
        e.goalSelector.a(2, new PathfinderGoalLookAtPlayer(e, EntityHuman.class, 4.0F));
        e.goalSelector.a(0, new PathfinderGoalFloat(e));

    }

    @Override
    public List<String> getAllowedEntities() {
        return Arrays.asList("Cat", "Chicken", "Cow", "Donkey", "Fox", "Zoglin", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf");
    }
}



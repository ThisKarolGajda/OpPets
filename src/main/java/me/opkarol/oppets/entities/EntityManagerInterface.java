package me.opkarol.oppets.entities;

import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.animal.EntityAnimal;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;

public interface EntityManagerInterface {

    default void spawnEntity(){

    }

    default void initPathfinder(PathfinderGoalSelector bP, @NotNull EntityAnimal entity) {

    }

    default void spawnEntity(@NotNull EntityAnimal entity, @NotNull Player player, @NotNull Pet pet) {

    }

    default void spawnEntity(@NotNull net.minecraft.server.v1_16_R3.EntityAnimal entity, @NotNull Player player, @NotNull Pet pet) {

    }

    default void initPathfinder(@NotNull net.minecraft.server.v1_16_R3.PathfinderGoalSelector bP, net.minecraft.server.v1_16_R3.@NotNull EntityAnimal e) {

    }

    default Object getPrivateField(String fieldName, @NotNull Class clazz, Object object) {
        Field field;
        Object o = null;

        try
        {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return o;
    }


    default void spawnEntity(@NotNull net.minecraft.server.v1_16_R2.EntityAnimal entity, @NotNull Player player, @NotNull Pet pet) {

    }

    default void spawnEntity(@NotNull net.minecraft.server.v1_16_R1.EntityAnimal entity, @NotNull Player player, @NotNull Pet pet) {

    }

    List<String> getAllowedEntities();
}

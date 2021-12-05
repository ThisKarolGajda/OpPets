package me.opkarol.oppets.interfaces;

import me.opkarol.oppets.pets.Pet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;

public interface EntityManagerInterface {

    default void spawnEntity(){

    }

    default void initPathfinder(Object bP, @NotNull Object entity) {

    }

    default void spawnEntity(@NotNull Object entity, @NotNull Player player, @NotNull Pet pet) {

    }


    default Object getPrivateField(String fieldName, @NotNull Class clazz, Object object) {
        Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }

    void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3);

    List<String> getAllowedEntities();
}

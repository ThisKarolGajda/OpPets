package dir.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface EntityManagerInterface {

    void initPathfinder(@NotNull Object entity);

    void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3);

    List<String> getAllowedEntities();
}

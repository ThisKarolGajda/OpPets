package dir.interfaces;

import dir.pets.Pet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface BabyEntityCreatorInterface {
    void spawnMiniPet(@NotNull Pet pet, @NotNull Player player);

}

package me.opkarol.oppets.pets;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface BabyEntityCreatorInterface {
    void spawnMiniPet(@NotNull Pet pet, @NotNull Player player);

}

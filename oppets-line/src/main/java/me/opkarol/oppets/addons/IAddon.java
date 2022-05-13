package me.opkarol.oppets.addons;

import me.opkarol.oppets.graphic.IGetter;
import org.bukkit.plugin.Plugin;

import java.util.List;

public interface IAddon extends IGetter {
    String getName();

    String getVersion();

    List<String> getDescription();

    Plugin getPlugin();

    default boolean canBeLaunched() {
        return false;
    }

    default boolean isVerified() {
        return false;
    }
}

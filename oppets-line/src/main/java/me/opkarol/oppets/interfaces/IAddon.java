package me.opkarol.oppets.interfaces;

import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * The interface Addon.
 */
public interface IAddon extends IGetter {
    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets version.
     *
     * @return the version
     */
    String getVersion();

    /**
     * Gets description.
     *
     * @return the description
     */
    List<String> getDescription();

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    Plugin getPlugin();

    /**
     * Can be launched boolean.
     *
     * @return the boolean
     */
    boolean canBeLaunched();

    /**
     * Is verified boolean.
     *
     * @return the boolean
     */
    boolean isVerified();
}

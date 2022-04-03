package me.opkarol.oppets.addons;

import me.opkarol.oppets.interfaces.IAddon;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;

/**
 * The type Addon config.
 */
@SuppressWarnings("unused")
public class AddonConfig implements IAddon {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Version.
     */
    private String version;
    /**
     * The Description.
     */
    private List<String> description;
    /**
     * The Plugin.
     */
    private Plugin plugin;

    /**
     * Instantiates a new Addon config.
     *
     * @param name        the name
     * @param version     the version
     * @param description the description
     * @param plugin      the plugin
     */
    public AddonConfig(String name, String version, List<String> description, Plugin plugin) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.plugin = plugin;
    }

    /**
     * Instantiates a new Addon config.
     */
    public AddonConfig() {}

    /**
     * Gets name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    @Override
    public String getVersion() {
        return version;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    @Override
    public List<String> getDescription() {
        return description;
    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Can be launched boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean canBeLaunched() {
        return getName() != null && getDescription() != null && getVersion() != null && (getPlugin() != null || isVerified());
    }

    /**
     * Is verified boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isVerified() {
        return AddonManager.verifiedAddons.contains(getName());
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(List<String> description) {
        this.description = description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = Collections.singletonList(description);
    }

    /**
     * Sets plugin.
     *
     * @param plugin the plugin
     */
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        this.version = version;
    }
}

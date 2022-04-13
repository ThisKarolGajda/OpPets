package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.inventories.holders.SettingsInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreatorLamp;

/**
 * The type Settings inventory.
 */
public class SettingsInventory implements IInventory {
    /**
     * The Inventory.
     */
    private final Inventory inventory;
    /**
     * The Visible to others.
     */
    boolean visibleToOthers;
    /**
     * The Giftable.
     */
    boolean giftable;
    /**
     * The Glows.
     */
    boolean glows;
    /**
     * The Follow player.
     */
    boolean followPlayer;
    /**
     * The Teleport to player.
     */
    boolean teleportToPlayer;
    /**
     * The Rideable.
     */
    boolean rideable;
    /**
     * The Other rideable.
     */
    boolean otherRideable;
    /**
     * The Particles enabled.
     */
    boolean particlesEnabled;

    /**
     * Instantiates a new Settings inventory.
     *
     * @param pet the pet
     */
    public SettingsInventory(@NotNull Pet pet) {
        visibleToOthers = pet.isVisibleToOthers();
        giftable = pet.isGiftable();
        glows = pet.isGlowing();
        followPlayer = pet.isFollowingPlayer();
        teleportToPlayer = pet.isTeleportingToPlayer();
        rideable = pet.isRideable();
        otherRideable = pet.isOtherRideable();
        particlesEnabled = pet.areParticlesEnabled();
        inventory = Bukkit.createInventory(new SettingsInventoryHolder(), 27, InventoriesCache.settingsInventoryTitle.replace("%pet_name%", FormatUtils.formatMessage(Objects.requireNonNull(pet.getPetName()))));
        setupInventory();
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets inventory.
     */
    private void setupInventory() {
        String path = "SettingsInventory.items.";
        inventory.setItem(9, itemCreatorLamp(path + "visibleToOthers.", visibleToOthers, this));
        inventory.setItem(10, itemCreatorLamp(path + "giftable.", giftable, this));
        inventory.setItem(11, itemCreatorLamp(path + "glows.", glows, this));
        inventory.setItem(12, itemCreatorLamp(path + "followPlayer.", followPlayer, this));
        inventory.setItem(13, itemCreatorLamp(path + "teleportToPlayer.", teleportToPlayer, this));
        inventory.setItem(14, itemCreatorLamp(path + "rideable.", rideable, this));
        inventory.setItem(15, itemCreatorLamp(path + "otherRideable.", otherRideable, this));
        inventory.setItem(16, itemCreatorLamp(path + "particlesEnabled.", particlesEnabled, this));
        inventory.setItem(17, itemCreator(path + "resetSettings.", this));
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE);

    }

    /**
     * Sets place holders.
     *
     * @param lore the lore
     * @return the place holders
     */
    @Override
    @Contract(pure = true)
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore.stream().map(s -> FormatUtils.formatMessage(s
                .replace("%state_visibleToOthers%", String.valueOf(visibleToOthers))
                .replace("%state_giftable%", String.valueOf(giftable))
                .replace("%state_glows%", String.valueOf(glows))
                .replace("%state_followPlayer%", String.valueOf(followPlayer))
                .replace("%state_teleportToPlayer%", String.valueOf(teleportToPlayer))
                .replace("%state_rideable%", String.valueOf(rideable))
                .replace("%state_otherRideable%", String.valueOf(otherRideable))
                .replace("%state_particlesEnabled%", String.valueOf(particlesEnabled))))
                .collect(Collectors.toList());
    }
}

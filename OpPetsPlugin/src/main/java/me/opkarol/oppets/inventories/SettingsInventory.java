package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.interfaces.IInventory;
import dir.pets.Pet;
import me.opkarol.oppets.inventories.holders.SettingsInventoryHolder;
import dir.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static dir.utils.ConfigUtils.getMessage;
import static dir.utils.InventoryUtils.*;

public class SettingsInventory implements IInventory {
    private final Inventory inventory;
    Pet pet;

    boolean visibleToOthers;
    boolean giftable;
    boolean glows;
    boolean followPlayer;
    boolean teleportToPlayer;
    boolean rideable;
    boolean otherRideable;
    boolean particlesEnabled;

    public SettingsInventory(@NotNull Pet pet) {
        this.pet = pet;
        visibleToOthers = pet.isVisibleToOthers();
        giftable = pet.isGiftable();
        glows = pet.isGlowing();
        followPlayer = pet.isFollowingPlayer();
        teleportToPlayer = pet.isTeleportingToPlayer();
        rideable = pet.isRideable();
        otherRideable = pet.isOtherRideable();
        particlesEnabled = pet.areParticlesEnabled();
        inventory = Bukkit.createInventory(new SettingsInventoryHolder(), 27, getMessage("SettingsInventory.title").replace("%pet_name%", FormatUtils.formatMessage(Objects.requireNonNull(pet.getPetName()))));
        setupInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

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
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }

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

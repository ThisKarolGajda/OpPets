package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.Pet;
import me.opkarol.oppets.inventories.holders.SettingsInventoryHolder;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.opkarol.oppets.utils.ConfigUtils.*;
import static me.opkarol.oppets.utils.InventoryUtils.*;

public class SettingsInventory {
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

        inventory.setItem(9, itemCreatorLamp(getMessage(path + "visibleToOthers.name"), setPlaceHolders(getListString(path + "visibleToOthers.lore")), null, true, getBoolean(path + "visibleToOthers.glow"), null, visibleToOthers, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(10, itemCreatorLamp(getMessage(path + "giftable.name"), setPlaceHolders(getListString(path + "giftable.lore")), null, true, getBoolean(path + "giftable.glow"), null, giftable, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(11, itemCreatorLamp(getMessage(path + "glows.name"), setPlaceHolders(getListString(path + "glows.lore")), null, true, getBoolean(path + "glows.glow"), null, glows, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(12, itemCreatorLamp(getMessage(path + "followPlayer.name"), setPlaceHolders(getListString(path + "followPlayer.lore")), null, true, getBoolean(path + "followPlayer.glow"), null, followPlayer, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(13, itemCreatorLamp(getMessage(path + "teleportToPlayer.name"), setPlaceHolders(getListString(path + "teleportToPlayer.lore")), null, true, getBoolean(path + "teleportToPlayer.glow"), null, teleportToPlayer, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(14, itemCreatorLamp(getMessage(path + "rideable.name"), setPlaceHolders(getListString(path + "rideable.lore")), null, true, getBoolean(path + "rideable.glow"), null, rideable, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(15, itemCreatorLamp(getMessage(path + "otherRideable.name"), setPlaceHolders(getListString(path + "otherRideable.lore")), null, true, getBoolean(path + "otherRideable.glow"), null, otherRideable, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(16, itemCreatorLamp(getMessage(path + "particlesEnabled.name"), setPlaceHolders(getListString(path + "particlesEnabled.lore")), null, true, getBoolean(path + "particlesEnabled.glow"), null, particlesEnabled, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(17, itemCreator(Material.valueOf(getString(path + "resetSettings.material")), getMessage(path + "resetSettings.name"), setPlaceHolders(getListString(path + "resetSettings.lore")), null, true, getBoolean(path + "resetSettings.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }


    @Contract(pure = true)
    private @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        List<String> list = new ArrayList<>();
        for (String sI : lore) {
            list.add(FormatUtils.formatMessage(sI.replace("%state_visibleToOthers%", String.valueOf(visibleToOthers)).replace("%state_giftable%", String.valueOf(giftable)).replace("%state_glows%", String.valueOf(glows)).replace("%state_followPlayer%", String.valueOf(followPlayer)).replace("%state_teleportToPlayer%", String.valueOf(teleportToPlayer)).replace("%state_rideable%", String.valueOf(rideable)).replace("%state_otherRideable%", String.valueOf(otherRideable)).replace("%state_particlesEnabled%", String.valueOf(particlesEnabled))));
        }
        return list;
    }
}

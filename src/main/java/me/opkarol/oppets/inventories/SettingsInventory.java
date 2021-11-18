package me.opkarol.oppets.inventories;

import me.opkarol.oppets.inventories.holders.SettingsInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.opkarol.oppets.utils.ConfigUtils.*;
import static me.opkarol.oppets.utils.InventoryUtils.*;

public class SettingsInventory {
    Pet pet;

    private final Inventory inventory;

    public Inventory getInventory() {
        return inventory;
    }

    public SettingsInventory(@NotNull Pet pet) {
        this.pet = pet;
        inventory = Bukkit.createInventory(new SettingsInventoryHolder(), 27, getMessage("SettingsInventory.title").replace("%pet_name%", pet.getPetName()));
        setupInventory();
    }

    private void setupInventory() {
        String path = "SettingsInventory.items.";
        boolean enabled_visibleToOthers = pet.isVisibleToOthers();
        boolean enabled_giftable = pet.isGiftable();
        boolean enabled_glows = pet.isGlowing();
        boolean enabled_followPlayer = pet.isFollowingPlayer();
        boolean enabled_teleportToPlayer = pet.isTeleportingToPlayer();
        boolean rideable = pet.isRideable();
        boolean otherRideable = pet.isOtherRideable();
        boolean particlesEnabled = pet.areParticlesEnabled();

        inventory.setItem(9, itemCreatorLamp(getMessage(path + "visibleToOthers.name"), setPlaceHolders(getListString(path + "visibleToOthers.lore")), null, true, getBoolean(path + "visibleToOthers.glow"), null, enabled_visibleToOthers, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(10, itemCreatorLamp(getMessage(path + "giftable.name"), setPlaceHolders(getListString(path + "giftable.lore")), null, true, getBoolean(path + "giftable.glow"), null,enabled_giftable,  ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(11, itemCreatorLamp(getMessage(path + "glows.name"), setPlaceHolders(getListString(path + "glows.lore")), null, true, getBoolean(path + "glows.glow"), null,enabled_glows,  ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(12, itemCreatorLamp(getMessage(path + "followPlayer.name"), setPlaceHolders(getListString(path + "followPlayer.lore")), null, true, getBoolean(path + "followPlayer.glow"), null, enabled_followPlayer, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(13, itemCreatorLamp(getMessage(path + "teleportToPlayer.name"), setPlaceHolders(getListString(path + "teleportToPlayer.lore")), null, true, getBoolean(path + "teleportToPlayer.glow"), null,enabled_teleportToPlayer,  ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(14, itemCreatorLamp(getMessage(path + "rideable.name"), setPlaceHolders(getListString(path + "rideable.lore")), null, true, getBoolean(path + "rideable.glow"), null, rideable, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(15, itemCreatorLamp(getMessage(path + "otherRideable.name"), setPlaceHolders(getListString(path + "otherRideable.lore")), null, true, getBoolean(path + "otherRideable.glow"), null, otherRideable, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(16, itemCreatorLamp(getMessage(path + "particlesEnabled.name"), setPlaceHolders(getListString(path + "particlesEnabled.lore")), null, true, getBoolean(path + "particlesEnabled.glow"), null, particlesEnabled,  ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(17, itemCreator(Material.valueOf(getString(path + "resetSettings.material")), getMessage(path + "resetSettings.name"), setPlaceHolders(getListString(path + "resetSettings.lore")), null, true, getBoolean(path + "resetSettings.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }


    @Contract(pure = true)
    private @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        List<String> list = new ArrayList<>();
        for (String sI : lore) {
            list.add(sI.replace("%max_pet_level%", "TODO-2").replace("%percentage_of_next_experience%", "TODO-1").replace("%pet_experience_next%", "TODO-3"));
        }
        return list;
    }
}

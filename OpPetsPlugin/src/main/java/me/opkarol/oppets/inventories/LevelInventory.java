package me.opkarol.oppets.inventories;

import me.opkarol.oppets.inventories.holders.LevelInventoryHolder;
import dir.pets.Pet;
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
import static me.opkarol.oppets.utils.ConfigUtils.getBoolean;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.setupEmptyGlassPanes;

public class LevelInventory {
    Pet pet;

    private final Inventory inventory;

    public Inventory getInventory() {
        return inventory;
    }

    public LevelInventory(@NotNull Pet pet){
        this.pet = pet;
        inventory = Bukkit.createInventory(new LevelInventoryHolder(), 27, getMessage("LevelInventory.title").replace("%pet_name%", Objects.requireNonNull(pet.getPetName())));
        setupInventory();
    }

    private void setupInventory(){
        String path = "LevelInventory.items.";
        inventory.setItem(10, itemCreator(Material.valueOf(getString(path + "informationBook.material")), getMessage(path + "informationBook.name"), setPlaceHolders(getListString(path + "informationBook.lore")), null, true, getBoolean(path + "informationBook.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(13, itemCreator(Material.valueOf(getString(path + "level.material")), getMessage(path + "level.name"), setPlaceHolders(getListString(path + "level.lore")), null, true, getBoolean(path + "level.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        inventory.setItem(16, itemCreator(Material.valueOf(getString(path + "abilities.material")), getMessage(path + "abilities.name"), setPlaceHolders(getListString(path + "abilities.lore")), null, true, getBoolean(path + "abilities.glow"), null, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE));
        setupEmptyGlassPanes(Material.BLACK_STAINED_GLASS_PANE, inventory);

    }

    @Contract(pure = true)
    private @NotNull List<String> setPlaceHolders(@NotNull List<String> lore){
        List<String> list = new ArrayList<>();
        for (String sI : lore){
            list.add(sI.replace("%max_pet_level%", "TODO-2").replace("%percentage_of_next_experience%", "TODO-1").replace("%pet_experience_next%", "TODO-3"));
        }
        return list;
    }
}

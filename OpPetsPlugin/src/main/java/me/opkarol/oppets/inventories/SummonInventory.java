package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.cache.NamespacedKeysCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IHolder;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.inventories.holders.SummonInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PDCUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.utils.InventoryUtils.fillInventory;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

public class SummonInventory implements IInventory {
    /**
     * The Pet.
     */
    final List<Pet> pets;
    /**
     * The Inventory.
     */
    private final Inventory inventory;
    private final UUID uuid;

    /**
     * Instantiates a new Level inventory.
     *
     */
    public SummonInventory(UUID uuid) {
        this.pets = Database.getDatabase().getPetList(uuid);
        this.uuid = uuid;
        IHolder holder = new SummonInventoryHolder();
        String title = InventoriesCache.summonInventoryTitle;
        inventory = Bukkit.createInventory(holder, 54, title);
        setupInventory(0);
    }

    public SummonInventory(UUID uuid, int page) {
        this.pets = Database.getDatabase().getPetList(uuid);
        this.uuid = uuid;
        IHolder holder = new SummonInventoryHolder();
        String title = InventoriesCache.summonInventoryTitle;
        inventory = Bukkit.createInventory(holder, 54, title);
        setupInventory(page);
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
    private void setupInventory(int page) {
        setupPages();
        if (page >= pages.size()) {
            page = page - 1;
        } else if (page < 0) {
            page = 0;
        }
        createPageInInventory(page);
    }

    private final List<Pet[]> pages = new ArrayList<>();

    public Pet[] getPage(int i) {
        return pages.get(i);
    }

    public void addPage(int i, Pet[] board) {
        if (pages.contains(board)) {
            pages.set(i, board);
        } else {
            pages.add(i, board);
        }
    }

    public void createPageInInventory(int page) {
        Pet[] pets = getPage(page);
        if (pets == null) {
            pets = getPage(0);
        }
        List<ItemStack> items = getMovingPagesItemStacks(page);
        inventory.setItem(45, items.get(0));
        inventory.setItem(53, items.get(1));
        for (int i = 0; i < Arrays.stream(pets).count(); i++) {
            if (i > 53) {
                break;
            }
            Pet pet = pets[i];
            if (pet == null) {
                break;
            }
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType().equals(Material.BARRIER)) {
                i++;
            }
            inventory.setItem(i, itemCreator(PetsUtils.getMaterialByPetType(pet), pet.getPetName(), new ArrayList<>(), this));
        }
        fillInventory(inventory, Material.GREEN_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE);

    }

    public void setupPages() {
        int pageNumber = 0;
        int i = 0;
        Pet[] petsBoard = new Pet[53];
        for (Pet pet : pets) {
            if (i > 52) {
                i = 0;
                addPage(pageNumber, petsBoard);
                petsBoard = new Pet[53];
                pageNumber++;
            }
            petsBoard[i] = pet;
            i++;
        }
        addPage(pageNumber, petsBoard);
    }

    @Override
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore;
    }

    public List<ItemStack> getMovingPagesItemStacks(int page) {
        ItemStack itemStack1 = itemCreator(Material.BARRIER, "back", new ArrayList<>(), this);
        ItemStack itemStack2 = itemCreator(Material.BARRIER, "forward", new ArrayList<>(), this);
        PDCUtils.addNBT(itemStack1, NamespacedKeysCache.summonItemKey, String.valueOf(page - 1));
        PDCUtils.addNBT(itemStack2, NamespacedKeysCache.summonItemKey, String.valueOf(page + 1));
        return Arrays.asList(itemStack1, itemStack2);
    }
}

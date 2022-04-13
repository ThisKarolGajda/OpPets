package me.opkarol.oppets.cache;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.addons.AddonConfig;
import me.opkarol.oppets.interfaces.IGetter;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.shops.Shop;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.InventoryUtils;
import me.opkarol.oppets.utils.PDCUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.InventoryUtils.*;

/**
 * The type Page cache.
 *
 * @param <K> the type parameter
 */
public class PageCache<K extends IGetter> implements IInventory {
    /**
     * The List.
     */
    private final List<IGetter[]> list = new ArrayList<>();
    /**
     * The Values.
     */
    private final List<K> values;
    /**
     * The Inventory.
     */
    private final Inventory inventory;
    /**
     * The Max table size.
     */
    private final int maxTableSize;
    /**
     * The Current page.
     */
    private int currentPage;

    /**
     * Instantiates a new Page cache.
     *
     * @param inventory    the inventory
     * @param values       the values
     * @param maxTableSize the max table size
     */
    public PageCache(Inventory inventory, List<K> values, int maxTableSize) {
        this.inventory = inventory;
        this.values = values;
        this.maxTableSize = maxTableSize;
    }

    /**
     * Get page getter [ ].
     *
     * @param i the
     * @return the getter [ ]
     */
    public IGetter[] getPage(int i) {
        return list.get(i);
    }

    /**
     * Add page.
     *
     * @param i     the
     * @param board the board
     */
    public void addPage(int i, IGetter[] board) {
        if (list.contains(board)) {
            list.set(i, board);
        } else {
            list.add(i, board);
        }
    }

    /**
     * Sets inventory.
     *
     * @param page the page
     */
    public void setupInventory(int page) {
        setupPages();
        if (page >= list.size()) {
            page = page - 1;
        } else if (page < 0) {
            page = 0;
        }
        this.currentPage = page;
        createPageInInventory(page);
    }

    /**
     * Create page in inventory.
     *
     * @param page the page
     */
    public void createPageInInventory(int page) {
        IGetter[] objects = getPage(page);
        if (objects == null) {
            objects = getPage(0);
        }
        fillStyledInventory(inventory, InventoryUtils.FillStyles.INVENTORY_STYLE.SQUARE);
        List<ItemStack> items = getMovingPagesItemStacks();
        Collections.reverse(items);
        for (int i = 0; i < Arrays.stream(objects).count(); i++) {
            if (i > 27) {
                break;
            }
            IGetter object = objects[i];
            if (object == null) {
                break;
            }
            ItemStack item = inventory.getItem(i);
            int set = i;
            if (item != null) {
                while (item != null && (item.getType().equals(Material.BARRIER) || item.getType().name().contains("STAINED_GLASS_PANE") || !item.getType().equals(Material.AIR))) {
                    set++;
                    if (set > 53) {
                        break;
                    }
                    item = inventory.getItem(set);
                }
            }
            switch (object.getType()) {
                case ADDON:
                    AddonConfig config = (AddonConfig) object;
                    inventory.setItem(set, itemCreator(Material.BOOK, config.getName(), config.getDescription(), this));
                    break;
                case PET:
                    Pet pet = (Pet) object;
                    inventory.setItem(set, itemCreator(PetsUtils.getMaterialByPetType(pet.getPetType()), pet.getPetName(), new ArrayList<>(), this));
                    break;
                case SHOP:
                    Shop shop = (Shop) object;
                    inventory.setItem(set, shop.getItem());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + object.getType());
            }
        }
        inventory.setItem(45, items.get(0));
        inventory.setItem(53, items.get(1));
    }

    /**
     * Sets pages.
     */
    public void setupPages() {
        int pageNumber = 0;
        int i = 0;
        IGetter[] board = new IGetter[getMaxTableSize()];
        for (K object : values) {
            if (i > 27) {
                i = 0;
                addPage(pageNumber, board);
                board = new IGetter[getMaxTableSize()];
                pageNumber++;
            }
            board[i] = object;
            i++;
        }
        addPage(pageNumber, board);
    }

    /**
     * Gets moving pages item stacks.
     *
     * @return the moving pages item stacks
     */
    public List<ItemStack> getMovingPagesItemStacks() {
        String path = "PagesCache.items.";
        ItemStack itemStack1 = itemCreator(path + "previousPage.", this);
        ItemStack itemStack2 = itemCreator(path + "nextPage.", this);
        PDCUtils.addNBT(itemStack1, NamespacedKeysCache.summonItemKey, String.valueOf(currentPage - 1));
        PDCUtils.addNBT(itemStack2, NamespacedKeysCache.summonItemKey, String.valueOf(currentPage + 1));
        return Arrays.asList(itemStack1, itemStack2);
    }

    /**
     * Sets place holders.
     *
     * @param lore the lore
     * @return the place holders
     */
    @Override
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore.stream().map(s -> FormatUtils.formatMessage(s
                        .replace("%current_page%", String.valueOf(currentPage)))
                        .replace("%next_page%", String.valueOf(currentPage + 1))
                        .replace("%previous_page%", String.valueOf(Math.max(currentPage - 1, 0))))
                        .collect(Collectors.toList());
    }

    /**
     * Gets max table size.
     *
     * @return the max table size
     */
    public int getMaxTableSize() {
        return maxTableSize;
    }
}

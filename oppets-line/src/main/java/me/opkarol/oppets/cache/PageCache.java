package me.opkarol.oppets.cache;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.graphic.GraphicItemStack;
import me.opkarol.oppets.interfaces.IGetter;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.PDCUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.InventoryUtils.*;

/**
 * The type Page cache.
 *
 * @param <K> the type parameter
 */
public class PageCache<K extends IGetter> {
    /**
     * The List.
     */
    private final List<IGetter[]> list = new ArrayList<>();
    /**
     * The Values.
     */
    private final List<K> values;
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
     * @param values       the values
     * @param maxTableSize the max table size
     */
    public PageCache(List<K> values, int maxTableSize) {
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
        if (list.size() >= i) {
            return list.get(i);
        }
        return null;
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
    }


    public HashMap<Integer, IGetter> getPagedInventory(int page) {
        IGetter[] objects = getPage(page);
        HashMap<Integer, IGetter> map = new HashMap<>();
        GraphicItemStack default_item = new GraphicItemStack(getEmptyItemStack(Material.BLACK_STAINED_GLASS_PANE));
        GraphicItemStack corner_item = new GraphicItemStack(getEmptyItemStack(Material.YELLOW_STAINED_GLASS_PANE));
        FillStyles styles = new FillStyles();
        for (int i : styles.getMap().get(54)) {
            map.put(i, default_item);
        }
        for (int i : styles.getCorners().get(54)) {
            map.put(i, corner_item);
        }
        List<ItemStack> items = getMovingPagesItemStacks();
        map.replace(45, new GraphicItemStack(items.get(0)));
        map.replace(53, new GraphicItemStack(items.get(1)));
        for (int i = 0; i < Arrays.stream(objects).count(); i++) {
            if (i > getMaxTableSize() - 1) {
                break;
            }
            IGetter object = objects[i];
            if (object == null) {
                continue;
            }
            int set = i;
            while (map.get(set) != null) {
                set++;
                if (set > 53) {
                    break;
                }
            }
            map.put(set, object);
        }
        return map;
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
        Function<List<String>, List<String>> function = strings -> strings.stream().map(s -> FormatUtils.formatMessage(s
                                .replace("%current_page%", String.valueOf(currentPage)))
                                .replace("%next_page%", String.valueOf(currentPage + 1))
                                .replace("%previous_page%", String.valueOf(Math.max(currentPage - 1, 0))))
                                .collect(Collectors.toList());
        ItemStack itemStack1 = itemCreator(path + "previousPage.", function);
        ItemStack itemStack2 = itemCreator(path + "nextPage.", function);
        PDCUtils.addNBT(itemStack1, NamespacedKeysCache.summonItemKey, String.valueOf(currentPage - 1));
        PDCUtils.addNBT(itemStack2, NamespacedKeysCache.summonItemKey, String.valueOf(currentPage + 1));
        return Arrays.asList(itemStack1, itemStack2);
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

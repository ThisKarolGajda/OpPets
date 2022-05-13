package me.opkarol.oppets.cache;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.graphic.GraphicItemStack;
import me.opkarol.oppets.graphic.IGetter;
import me.opkarol.oppets.items.OpItemBuilder;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.PDCUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.InventoryUtils.*;

public class PageCache<K extends IGetter> {
    private final List<IGetter[]> list = new ArrayList<>();
    private final List<K> values;
    private final int maxTableSize;
    private int currentPage;
    private static final OpItemBuilder builder = OpItemBuilder.getBuilder();


    public PageCache(List<K> values, int maxTableSize) {
        this.maxTableSize = maxTableSize;
        if (values == null) {
            this.values = new ArrayList<>();
        } else {
            this.values = values;
        }
    }

    public IGetter[] getPage(int i) {
        if (list.size() >= i) {
            return list.get(i);
        }
        return null;
    }

    public void addPage(int i, IGetter[] board) {
        if (list.contains(board)) {
            list.set(i, board);
        } else {
            list.add(i, board);
        }
    }

    public void setupInventory(int page) {
        setupPages();
        if (page >= list.size()) {
            page = page - 1;
        } else if (page < 0) {
            page = 0;
        }
        this.currentPage = page;
    }


    public OpMap<Integer, IGetter> getPagedInventory(int page) {
        IGetter[] objects = getPage(page);
        OpMap<Integer, IGetter> map = new OpMap<>();
        GraphicItemStack default_item = new GraphicItemStack(builder.dump().setMaterial(Material.BLACK_STAINED_GLASS_PANE).getItem());
        GraphicItemStack corner_item = new GraphicItemStack(builder.dump().setMaterial(Material.GREEN_STAINED_GLASS_PANE).getItem());

        for (int i : FillSizes.R6.getInts()) {
            map.put(i, default_item);
        }
        for (int i : FillCorners.R6.getInts()) {
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
            while (map.getOrDefault(set, null) != null) {
                set++;
                if (set > 53) {
                    break;
                }
            }
            map.put(set, object);
        }
        return map;
    }


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

    public List<ItemStack> getMovingPagesItemStacks() {
        String path = "PagesCache.items.";
        Function<List<String>, List<String>> function = strings -> strings.stream().map(s -> FormatUtils.formatMessage(s
                                .replace("%current_page%", String.valueOf(currentPage)))
                                .replace("%next_page%", String.valueOf(currentPage + 1))
                                .replace("%previous_page%", String.valueOf(Math.max(currentPage - 1, 0))))
                                .collect(Collectors.toList());

        ItemStack itemStack1 = builder.dump().setPath(path + "previousPage").setLoreAction(function).getItem();
        ItemStack itemStack2 = builder.dump().setPath(path + "nextPage").setLoreAction(function).getItem();
        PDCUtils.addNBT(itemStack1, NamespacedKeysCache.summonItemKey, String.valueOf(currentPage - 1));
        PDCUtils.addNBT(itemStack2, NamespacedKeysCache.summonItemKey, String.valueOf(currentPage + 1));
        return Arrays.asList(itemStack1, itemStack2);
    }

    public int getMaxTableSize() {
        return maxTableSize;
    }
}

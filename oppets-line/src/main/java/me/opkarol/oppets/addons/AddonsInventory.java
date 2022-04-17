package me.opkarol.oppets.addons;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.GraphicCache;
import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.cache.PageCache;
import me.opkarol.oppets.graphic.GraphicInterface;
import me.opkarol.oppets.graphic.GraphicItem;
import me.opkarol.oppets.graphic.GraphicItemData;
import me.opkarol.oppets.interfaces.IAddon;
import me.opkarol.oppets.interfaces.IGetter;
import me.opkarol.oppets.interfaces.IGraphicInventoryData;
import me.opkarol.oppets.interfaces.IInventory;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

/**
 * The type Addons inventory.
 */
public class AddonsInventory implements IInventory {
    /**
     * The Cache.
     */
    public PageCache<IAddon> cache;
    private final int page;

    /**
     * Instantiates a new Addons inventory.
     *
     * @param page the page
     */
    public AddonsInventory(int page) {
        cache = new PageCache<>(AddonManager.getAddons().stream()
                .filter(IAddon::canBeLaunched).collect(Collectors.toList()), 28);
        cache.setupInventory(page);
        this.page = page;
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        GraphicCache cache = GraphicCache.getInstance();
        Inventory cached = cache.getInventory(this);
        if (cached == null) {
            loadButtons();
            cache.addInventory(this, GraphicInterface.getInventory(this, new IGraphicInventoryData() {
                @Override
                public InventoryHolder getHolder() {
                    return new AddonsInventoryHolder();
                }

                @Override
                public int getSize() {
                    return 54;
                }

                @Override
                public String getTitle() {
                    return InventoriesCache.addonsInventoryTitle;
                }
            }, null));
            return cache.getInventory(this);
        }
        return cached;
    }

    @Override
    public void loadButtons() {
        GraphicInterface graphicInterface = GraphicInterface.getInstance();
        HashMap<Integer, IGetter> map = cache.getPagedInventory(page);
        for (int i : map.keySet()) {
            IGetter object = map.get(i);
            switch (object.getGetterType()) {
                case ADDON:
                    AddonConfig config = (AddonConfig) object;
                    graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(Material.BOOK, config.getName(), config.getDescription(), false, this), i)));
                    break;
                case ITEM_STACK: {
                    graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(((ItemStack) object.getObject()), i)));
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + map.get(i).getGetterType());
                }
            }
        }
    }

    @Override
    public String getHolderName() {
        return "AddonsInventory";
    }

    /**
     * Sets place holders.
     *
     * @param lore the lore
     * @return the place holders
     */
    @Override
    @NotNull
    public List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore;
    }
}

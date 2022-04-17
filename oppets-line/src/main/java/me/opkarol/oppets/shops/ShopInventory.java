package me.opkarol.oppets.shops;

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
import me.opkarol.oppets.interfaces.IGetter;
import me.opkarol.oppets.interfaces.IGraphicInventoryData;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.utils.ConfigUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.opkarol.oppets.utils.ConfigUtils.getInt;
import static me.opkarol.oppets.utils.ConfigUtils.getString;

/**
 * The type Shop inventory.
 */
public class ShopInventory implements IInventory {

    /**
     * The Cache.
     */
    public PageCache<Shop> cache;
    private int page;

    /**
     * Instantiates a new Shop inventory.
     *
     * @param page the page
     */
    public ShopInventory(int page) {
        ConfigurationSection sec = ConfigUtils.getConfig().getConfigurationSection("ShopInventory.items");
        if (sec == null) {
            return;
        }
        String path = "ShopInventory.items.";
        List<Shop> shopList = new ArrayList<>();
        for (String key : sec.getKeys(false)) {
            key = key + ".";
            shopList.add(new Shop(getInt(path + key + "options.price"), getString(path + key + "options.type"), path + key));
        }
        cache = new PageCache<>(shopList, 28);
        cache.setupInventory(page);
        this.page = page;
    }

    @Override
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore;
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
                    return new ShopInventoryHolder();
                }

                @Override
                public int getSize() {
                    return 54;
                }

                @Override
                public String getTitle() {
                    return InventoriesCache.shopInventoryTitle;
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
                case SHOP:
                    Shop shop = (Shop) object;
                    graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(shop.getItem(), i), (player, item) -> {
                        ItemMeta meta = item.getItemMeta();
                        if (meta != null) {
                            Material type = item.getType();
                            if (!type.equals(Material.BARRIER) && !type.name().contains("STAINED_GLASS_PANE") && !item.getType().equals(Material.AIR)) {
                                player.openInventory(new BuyerAdmitInventory(item).getInventory());
                            }
                        }
                    }));
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
        return "ShopInventory";
    }
}

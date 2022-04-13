package me.opkarol.oppets.shops;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.interfaces.IGetter;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.InventoryUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Shop.
 */
public class Shop implements IGetter, IInventory {
    /**
     * The Price.
     */
    private final int price;
    /**
     * The Type.
     */
    private final String type;
    /**
     * The Path.
     */
    private final String path;
    /**
     * The Item.
     */
    private final ItemStack item;

    /**
     * Instantiates a new Shop.
     *
     * @param price the price
     * @param type  the type
     * @param path  the path
     */
    public Shop(int price, String type, String path) {
        this.price = price;
        this.type = type;
        this.path = path;
        item = InventoryUtils.itemCreatorShop(getShopType(), getPrice(), getPath(), this);
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets shop type.
     *
     * @return the shop type
     */
    public String getShopType() {
        return type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    @Override
    public GETTER_TYPE getType() {
        return GETTER_TYPE.SHOP;
    }

    /**
     * Gets object.
     *
     * @return the object
     */
    @Override
    public Object getObject() {
        return this;
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
                        .replace("%type%", type)
                        .replace("%price%", String.valueOf(price))))
                .collect(Collectors.toList());    }

    /**
     * Gets item.
     *
     * @return the item
     */
    public ItemStack getItem() {
        return item;
    }
}

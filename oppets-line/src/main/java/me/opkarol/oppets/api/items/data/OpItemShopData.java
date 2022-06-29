package me.opkarol.oppets.api.items.data;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.items.builder.OpItemBuilder;
import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.shops.Shop;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OpItemShopData {
    private final ItemStack item;
    private final int price;
    private final String type;

    public OpItemShopData(int price, String type, @NotNull OpItemBuilder builder) {
        this.item = builder.getItem();
        this.price = price;
        this.type = type;
    }

    public OpItemShopData(String price, String type) {
        this.item = null;
        this.price = StringTransformer.getIntFromString(price);
        this.type = type;
    }

    public OpItemShopData(@NotNull Shop shop) {
        this.item = shop.getItem().getItem();
        this.price = shop.getPrice();
        this.type = shop.getShopType();
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public ItemStack getItem() {
        return item;
    }
}

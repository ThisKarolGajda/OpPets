package me.opkarol.oppets.items;

import me.opkarol.oppets.misc.StringTransformer;
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

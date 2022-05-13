package me.opkarol.oppets.inventory;

import me.opkarol.oppets.shops.Shop;

public abstract class ShopInventoryAccessor extends InventoryAccessor {
    private Shop shop;

    public ShopInventoryAccessor(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}

package me.opkarol.oppets.inventory;

import me.opkarol.oppets.cache.PageCache;
import me.opkarol.oppets.graphic.IGetter;

public abstract class PagesInventoryAccessor<K extends IGetter> extends InventoryAccessor {
    private PageCache<K> cache;
    private int page;

    public PagesInventoryAccessor(PageCache<K> cache, int page) {
        this.cache = cache;
        this.page = page;
    }

    public PagesInventoryAccessor() {

    }

    public PagesInventoryAccessor(int page) {
        this.page = page;
    }

    public PageCache<K> getCache() {
        return cache;
    }

    public void setCache(PageCache<K> cache) {
        this.cache = cache;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

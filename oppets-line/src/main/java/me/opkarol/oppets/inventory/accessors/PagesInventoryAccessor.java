package me.opkarol.oppets.inventory.accessors;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.PageCache;
import me.opkarol.oppets.api.graphic.IGetter;

public abstract class PagesInventoryAccessor<K extends IGetter> extends InventoryAccessor {
    private PageCache<K> cache;
    private int page;

    public PagesInventoryAccessor(PageCache<K> cache, int page) {
        this.cache = cache;
        this.page = page;
    }

    public PagesInventoryAccessor() {}

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

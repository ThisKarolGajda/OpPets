package me.opkarol.oppets.shops.cache;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.inventory.cache.InventoryCache;
import me.opkarol.oppets.inventory.OpInventories;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShopCache {
    private static List<InventoryCache> cache;

    public static @NotNull InventoryCache getCache(int page) {
        if (cache == null) {
            cache = new ArrayList<>();
        }
        if (cache.size() <= page) {
            cache.add(page, new InventoryCache());
        }
        InventoryCache current = cache.get(page);
        if (current.getInventory() == null) {
            current.setInventory(new OpInventories.ShopInventory(page).buildInventory());
        }
        return current;
    }

}

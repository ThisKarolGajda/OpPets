package me.opkarol.oppets.cache;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.interfaces.IInventory;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GraphicCache {
    private static GraphicCache graphicCache;
    private final HashMap<String, Inventory> map = new HashMap<>();

    public GraphicCache() {
        graphicCache = this;
    }

    public void addInventory(@NotNull IInventory s, Inventory inventory) {
        String holderName = s.getHolderName();
        if (map.containsKey(holderName)) {
            map.replace(holderName, inventory);
        } else {
            map.put(holderName, inventory);
        }
    }

    public Inventory getInventory(@NotNull IInventory s) {
        return map.getOrDefault(s.getHolderName(), null);
    }

    public HashMap<String, Inventory> getMap() {
        return map;
    }

    public static GraphicCache getInstance() {
        return graphicCache;
    }
}

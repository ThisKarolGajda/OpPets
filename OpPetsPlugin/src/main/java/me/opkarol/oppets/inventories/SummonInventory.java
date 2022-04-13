package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.cache.PageCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IHolder;
import me.opkarol.oppets.inventories.holders.SummonInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type Summon inventory.
 */
public class SummonInventory {
    /**
     * The Database.
     */
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());
    /**
     * The Pets.
     */
    List<Pet> pets;
    /**
     * The Inventory.
     */
    private final Inventory inventory;
    /**
     * The Cache.
     */
    public PageCache<Pet> cache;

    /**
     * Instantiates a new Summon inventory.
     *
     * @param uuid the uuid
     */
    public SummonInventory(UUID uuid) {
        this.pets = database.getDatabase().getPetList(uuid);
        if (pets == null) {
            pets = new ArrayList<>();
        }
        IHolder holder = new SummonInventoryHolder();
        String title = InventoriesCache.summonInventoryTitle;
        inventory = Bukkit.createInventory(holder, 54, title);
        cache = new PageCache<>(inventory, pets, 28);
        cache.setupInventory(0);
    }

    /**
     * Instantiates a new Summon inventory.
     *
     * @param uuid the uuid
     * @param page the page
     */
    public SummonInventory(UUID uuid, int page) {
        this.pets = database.getDatabase().getPetList(uuid);
        if (pets == null) {
            pets = new ArrayList<>();
        }
        IHolder holder = new SummonInventoryHolder();
        String title = InventoriesCache.summonInventoryTitle;
        inventory = Bukkit.createInventory(holder, 54, title);
        cache = new PageCache<>(inventory, pets, 28);
        cache.setupInventory(page);
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
}

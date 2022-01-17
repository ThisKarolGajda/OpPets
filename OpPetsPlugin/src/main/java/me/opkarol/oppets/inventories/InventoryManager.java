package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    private final List<Inventory> list = new ArrayList<>();

    public Inventory getInventoryByIndex(int index) {
        return list.get(index);
    }

    public void setupList() {
        list.add(new PetMainInventory().getInventory());
        /*
        list.add(1, new LevelInventory().getInventory());
        -> Not used due to its own placeholders that need to be generated every time it is being open
        -> SettingsInventory same as LevelInventory
         */
    }
}

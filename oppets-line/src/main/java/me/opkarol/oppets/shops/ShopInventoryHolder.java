package me.opkarol.oppets.shops;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.interfaces.IHolder;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * The type Shop inventory holder.
 */
public class ShopInventoryHolder implements IHolder {
    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public String getName() {
        return "ShopInventory";
    }
}

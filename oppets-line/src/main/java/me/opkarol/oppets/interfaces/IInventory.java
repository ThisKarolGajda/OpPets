package me.opkarol.oppets.interfaces;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The interface Inventory.
 */
public interface IInventory {

    /**
     * Used to set class built placeholders which replace specific text values with text objects.
     *
     * @param lore list of string that will be replaced
     * @return replaced lore with placeholders
     */
    @NotNull
    default List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore;
    }
}

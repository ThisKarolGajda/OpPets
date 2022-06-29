package me.opkarol.oppets.eggs.types;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class EggItem {
    private final ItemStack item;
    private final TypeOfEntity type;
    private final List<EggCost> cost;
    private final List<EggRecipe> recipes;
    private final OpMap<Material, Integer> allCosts;

    public EggItem(ItemStack item, TypeOfEntity type, List<EggCost> cost, List<EggRecipe> recipes) {
        this.item = item;
        this.type = type;
        this.cost = cost;
        this.recipes = recipes;
        this.allCosts = getAllCosts();
    }

    public ItemStack getItem() {
        return item;
    }

    public TypeOfEntity getType() {
        return type;
    }

    public List<EggCost> getCost() {
        return cost;
    }

    private @NotNull OpMap<Material, Integer> getAllCosts() {
        OpMap<Material, Integer> map = new OpMap<>();
        for (EggCost cost : getCost()) {
            Material material = cost.getMaterial();
            if (map.containsKey(material)) {
                map.replace(material, map.getOrDefault(material, null) + cost.getCost());
            } else {
                map.put(material, cost.getCost());
            }
        }
        return map;
    }

    private OpMap<Material, Integer> getCosts() {
        return allCosts;
    }

    public List<EggRecipe> getRecipes() {
        return recipes;
    }

    public Optional<EggRecipe> getRecipe(String name) {
        return recipes.stream().filter(recipe -> recipe.getRecipeName().equals(name)).findAny();
    }
}

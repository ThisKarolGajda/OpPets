package me.opkarol.oppets.eggs;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.graphic.IGetter;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EggRecipe implements IGetter {
    private final OpMap<String, List<Material>> map = new OpMap<>();
    private final EggManager eggManager;
    private final String recipe;
    private final ItemStack result;
    private final TypeOfEntity type;

    public EggRecipe(@NotNull TypeOfEntity type, String recipe, EggManager manager, ItemStack result) {
        this.type = type;
        this.eggManager = manager;
        this.result = result;
        this.recipe = recipe;
        setupMapOfRecipe("Eggs.types." + type.name() + ".recipes." + this.recipe + ".");
    }

    private void setupMapOfRecipe(String path) {
        if (!eggManager.containsObjects(path, "TOP", "MIDDLE", "DOWN")) {
            return;
        }
        FileConfiguration config = eggManager.getConfiguration();
        map.put("TOP", getListOfMaterialFromString(config.getString(path + "TOP")));
        map.put("MIDDLE", getListOfMaterialFromString(config.getString(path + "MIDDLE")));
        map.put("DOWN", getListOfMaterialFromString(config.getString(path + "DOWN")));
    }

    private List<Material> getListOfMaterialFromString(String s) {
        return Arrays.stream((s == null ? "" : s).split(";")).map(StringTransformer::getMaterialFromString).collect(Collectors.toList());
    }

    public OpMap<String, List<Material>> getMap() {
        return map;
    }

    public String getRecipeName() {
        return recipe;
    }

    @Override
    public GETTER_TYPE getGetterType() {
        return GETTER_TYPE.EGG_RECIPE;
    }

    @Override
    public Object getObject() {
        return this;
    }

    public ItemStack getResult() {
        return result;
    }

    public TypeOfEntity getType() {
        return type;
    }
}

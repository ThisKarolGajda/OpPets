package me.opkarol.oppets.api.items.data;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.Material;

public class OpItemLampData {
    private final Material itemLighted;
    private final Material itemNotLighted;
    private final boolean light;

    public OpItemLampData(Material itemLighted, Material itemNotLighted, boolean light) {
        this.itemLighted = itemLighted;
        this.itemNotLighted = itemNotLighted;
        this.light = light;
    }

    public OpItemLampData(boolean light) {
        this.itemLighted = Material.GREEN_CONCRETE;
        this.itemNotLighted = Material.BLACK_CONCRETE;
        this.light = light;
    }

    public boolean isLighted() {
        return light;
    }

    public Material getItemLighted() {
        return itemLighted;
    }

    public Material getItemNotLighted() {
        return itemNotLighted;
    }

    public Material getSelectedType() {
        return this.isLighted() ? this.getItemLighted() : this.getItemNotLighted();
    }
}

package me.opkarol.oppets.graphic;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static me.opkarol.oppets.graphic.GraphicItem.TYPE.BICONSUMER;
import static me.opkarol.oppets.graphic.GraphicItem.TYPE.CONSUMER;

public class GraphicItem {
    private GraphicItemData buttonItem;
    private BiConsumer<Player, ItemStack> biConsumer;
    private Consumer<Player> consumer;

    public GraphicItem(@NotNull GraphicItemData buttonItem, @Nullable BiConsumer<Player, ItemStack> action) {
        this.buttonItem = buttonItem;
        this.biConsumer = action;
    }

    public GraphicItem(@NotNull GraphicItemData buttonItem, @Nullable Consumer<Player> action) {
        this.buttonItem = buttonItem;
        this.consumer = action;
    }

    public GraphicItem(@NotNull GraphicItemData buttonItem) {
        this.buttonItem = buttonItem;
    }

    public GraphicItemData getButtonItem() {
        return buttonItem;
    }

    public GraphicItem setButtonItem(GraphicItemData buttonItem) {
        this.buttonItem = buttonItem;
        return this;
    }

    public TYPE getAction() {
        return biConsumer == null ? CONSUMER : BICONSUMER;
    }

    enum TYPE {
        CONSUMER, BICONSUMER
    }

    public BiConsumer<Player, ItemStack> getBiConsumer() {
        return biConsumer;
    }

    public Consumer<Player> getConsumer() {
        return consumer;
    }
}

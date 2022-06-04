package me.opkarol.oppets.graphic.builder;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.items.builder.OpItemBuilder;
import me.opkarol.oppets.misc.external.api.BiOptional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static me.opkarol.oppets.graphic.builder.GraphicItem.TYPE.BICONSUMER;
import static me.opkarol.oppets.graphic.builder.GraphicItem.TYPE.CONSUMER;

public class GraphicItem {
    private BiConsumer<Player, ItemStack> biConsumer;
    private Consumer<Player> consumer;
    private final BiOptional<OpItemBuilder, Integer> biOptional;

    public GraphicItem(OpItemBuilder builder, int integer) {
        this.biOptional = BiOptional.of(builder, integer);
    }

    public GraphicItem(OpItemBuilder builder, int integer, @Nullable Consumer<Player> action) {
        this.biOptional = BiOptional.of(builder, integer);
        this.consumer = action;
    }

    public GraphicItem(OpItemBuilder builder, int integer, @Nullable BiConsumer<Player, ItemStack> action) {
        this.biOptional = BiOptional.of(builder, integer);
        this.biConsumer = action;
    }

    public ItemStack getItem() {
        Optional<OpItemBuilder> optional = biOptional.getFirst();
        if (optional.isPresent()) {
            return optional.get().getItem();
        }
        return OpItemBuilder.getBuilder().DEFAULT_ITEM;
    }

    public int getSlot() {
        return biOptional.getSecond().orElse(0);
    }

    public TYPE getAction() {
        return biConsumer == null ? CONSUMER : BICONSUMER;
    }

    public BiOptional<OpItemBuilder, Integer> getBiOptional() {
        return biOptional;
    }

    public enum TYPE {
        CONSUMER,
        BICONSUMER
    }

    public BiConsumer<Player, ItemStack> getBiConsumer() {
        return biConsumer;
    }

    public Consumer<Player> getConsumer() {
        return consumer;
    }
}

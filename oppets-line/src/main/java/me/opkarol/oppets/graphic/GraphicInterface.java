package me.opkarol.oppets.graphic;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.GraphicCache;
import me.opkarol.oppets.interfaces.IGraphicInventoryData;
import me.opkarol.oppets.interfaces.IHolder;
import me.opkarol.oppets.interfaces.IInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GraphicInterface implements Listener {
    private static GraphicInterface graphicInterface;
    protected final HashMap<String, GraphicItem[]> map = new HashMap<>();

    public GraphicInterface() {
        graphicInterface = this;
        new GraphicCache();
    }

    public static Inventory getInventory(@NotNull IInventory inventory, IGraphicInventoryData holder, Consumer<Inventory> consumer) {
        Optional<GraphicItem[]> list = Optional.ofNullable(getInstance().map.get(inventory.getHolderName()));
        if (!list.isPresent()) {
            return Bukkit.createInventory(holder.getHolder(), holder.getSize(), holder.getTitle());
        }
        GraphicItem[] itemList = list.get();
        return new GraphicInventoryCreator(itemList, holder, consumer).getInventory();
    }

    @EventHandler
    public void onPlayerClick(@NotNull InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (event.getClickedInventory() == null || holder == null || !isProvided(holder)) {
            return;
        }
        event.setCancelled(true);
        String name = ((IHolder) holder).getName();
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (slot == -999) {
            return;
        }
        GraphicItem[] list = getList(name);
        if (list == null || list.length < slot) {
            return;
        }
        Optional<GraphicItem> item = Optional.ofNullable(list[slot]);
        item.ifPresent(s -> {
            switch (s.getAction()) {
                case CONSUMER:
                    Consumer<Player> action =  s.getConsumer();
                    if (action != null) {
                        action.accept(player);
                    }
                    break;
                case BICONSUMER:
                    BiConsumer<Player, ItemStack> biAction = s.getBiConsumer();
                    if (biAction != null) {
                        biAction.accept(player, event.getCurrentItem());
                    }
                    break;
            }
        });
    }

    public void addGraphicItem(String string, GraphicItem ... graphicItem) {
        if (graphicItem == null || graphicItem.length == 0) {
            return;
        }
        if (map.containsKey(string)) {
            GraphicItem[] list = getList(string);
            for (GraphicItem item : graphicItem) {
                int slot = item.getButtonItem().getSlot();
                if (slot >= 54) {
                    continue;
                }
                list[slot] = item;
            }
            map.replace(string, list);
        } else {
            GraphicItem[] list = new GraphicItem[54];
            for (GraphicItem item : graphicItem) {
                list[item.getButtonItem().getSlot()] = item;
            }
            map.put(string, list);
        }
    }

    public GraphicItem[] getList(String string) {
        return map.getOrDefault(string, null);
    }

    public void setButton(@NotNull IInventory inventory, GraphicItem button) {
        addGraphicItem(inventory.getHolderName(), button);
    }

    protected boolean isProvided(InventoryHolder holder) {
        return holder instanceof IHolder;
    }

    public static GraphicInterface getInstance() {
        return graphicInterface;
    }
}

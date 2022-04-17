package me.opkarol.oppets.shops;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.graphic.GraphicInterface;
import me.opkarol.oppets.graphic.GraphicItem;
import me.opkarol.oppets.graphic.GraphicItemData;
import me.opkarol.oppets.interfaces.IGraphicInventoryData;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.InventoryUtils;
import me.opkarol.oppets.utils.OpUtils;
import me.opkarol.oppets.utils.PDCUtils;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.cache.NamespacedKeysCache.priceKey;
import static me.opkarol.oppets.cache.NamespacedKeysCache.typeKey;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreatorShop;

public class BuyerAdmitInventory implements IInventory {
    private final String price;
    private final String type;

    public BuyerAdmitInventory(@NotNull ItemStack item) {
        price = PDCUtils.getNBT(item, priceKey);
        type = PDCUtils.getNBT(item, typeKey);
    }

    @Override
    public Inventory getInventory() {
        loadButtons();
        return GraphicInterface.getInventory(this, new IGraphicInventoryData() {
            @Override
            public InventoryHolder getHolder() {
                return new BuyerAdmitInventoryHolder();
            }

            @Override
            public int getSize() {
                return 27;
            }

            @Override
            public String getTitle() {
                return InventoriesCache.buyerAdmitInventoryTitle;
            }
        }, inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE));
    }

    @Override
    public void loadButtons() {
        String path = "BuyerAdmitInventory.items.";
        GraphicInterface graphicInterface = GraphicInterface.getInstance();
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "decline.", this), 10), HumanEntity::closeInventory));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "informationBook.", this), 13)));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreatorShop(type, Integer.parseInt(price), path + "confirm.", this), 16), player -> {
            Inventory inventory = player.getOpenInventory().getInventory(16);
            OpUtils.tryToBuyItemFromInventory(player, inventory, 16);
        }));

    }

    @Override
    public String getHolderName() {
        return "BuyerAdmitInventory";
    }

    @Override
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore.stream().map(s -> FormatUtils.formatMessage(s
                .replace("%type%", type)
                .replace("%price%", price)))
                .collect(Collectors.toList());
    }
}

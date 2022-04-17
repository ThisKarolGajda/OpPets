package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.cache.PageCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.graphic.GraphicInterface;
import me.opkarol.oppets.graphic.GraphicItem;
import me.opkarol.oppets.graphic.GraphicItemData;
import me.opkarol.oppets.interfaces.IGetter;
import me.opkarol.oppets.interfaces.IGraphicInventoryData;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.inventories.holders.SummonInventoryHolder;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PDCUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.opkarol.oppets.cache.NamespacedKeysCache.summonItemKey;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;

public class SummonInventory implements IInventory {
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());
    private final List<Pet> pets;
    private final int page;
    private final PageCache<Pet> cache;

    public SummonInventory(UUID uuid) {
        this.pets = reverseList(database.getDatabase().getPetList(uuid));
        cache = new PageCache<>(pets, 28);
        cache.setupInventory(0);
        loadButtons();
        page = 0;
    }

    public SummonInventory(UUID uuid, int page) {
        this.pets = reverseList(database.getDatabase().getPetList(uuid));
        cache = new PageCache<>(pets, 28);
        cache.setupInventory(page);
        loadButtons();
        this.page = page;
    }

    private @NotNull List<Pet> reverseList(List<Pet> pets) {
        if (pets == null) {
            pets = new ArrayList<>();
        }
        Collections.reverse(pets);
        return pets;
    }

    @Override
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore;
    }

    @Override
    public Inventory getInventory() {
        return GraphicInterface.getInventory(this, new IGraphicInventoryData() {
            @Override
            public InventoryHolder getHolder() {
                return new SummonInventoryHolder();
            }

            @Override
            public int getSize() {
                return 54;
            }

            @Override
            public String getTitle() {
                return InventoriesCache.summonInventoryTitle;
            }
        }, null);
    }

    @Override
    public void loadButtons() {
        GraphicInterface graphicInterface = GraphicInterface.getInstance();
        HashMap<Integer, IGetter> map = cache.getPagedInventory(page);
        for (int i : map.keySet()) {
            IGetter object = map.get(i);
            switch (object.getGetterType()) {
                case PET -> {
                    Pet pet = (Pet) object.getObject();
                    graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(PetsUtils.getMaterialByPetType(pet.getPetType()), pet.getPetName(), new ArrayList<>(), false, this), i), (player, item) -> {
                        if (item != null) {
                            UUID uuid = player.getUniqueId();
                            if (PDCUtils.hasNBT(item, summonItemKey)) {
                                String nbtString = PDCUtils.getNBT(item, summonItemKey);
                                if (nbtString != null) {
                                    int page = new StringTransformer().getIntFromString(nbtString);
                                    player.openInventory(new SummonInventory(uuid, page).getInventory());
                                }
                            } else {
                                ItemMeta meta = item.getItemMeta();
                                if (meta != null) {
                                    String name = meta.getDisplayName();
                                    if (PetsUtils.summonPet(name, uuid, player)) {
                                        player.closeInventory();
                                    }
                                }
                            }
                        }
                    }));
                }
                case ITEM_STACK -> graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(((ItemStack) object.getObject()), i)));
                default -> throw new IllegalStateException("Unexpected value: " + map.get(i).getGetterType());
            }
        }
    }

    @Override
    public String getHolderName() {
        return "SummonInventory";
    }
}

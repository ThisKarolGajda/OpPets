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
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.graphic.GraphicInterface;
import me.opkarol.oppets.graphic.GraphicItem;
import me.opkarol.oppets.graphic.GraphicItemData;
import me.opkarol.oppets.interfaces.IGraphicInventoryData;
import me.opkarol.oppets.interfaces.IInventory;
import me.opkarol.oppets.inventories.holders.SettingsInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.InventoryUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.InventoryUtils.itemCreator;
import static me.opkarol.oppets.utils.InventoryUtils.itemCreatorLamp;

public class SettingsInventory implements IInventory {
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());
    private final Pet pet;
    boolean visibleToOthers;
    boolean giftable;
    boolean glows;
    boolean followPlayer;
    boolean teleportToPlayer;
    boolean rideable;
    boolean otherRideable;
    boolean particlesEnabled;

    public SettingsInventory(@NotNull Pet pet) {
        this.pet = pet;
        visibleToOthers = pet.isVisibleToOthers();
        giftable = pet.isGiftable();
        glows = pet.isGlowing();
        followPlayer = pet.isFollowingPlayer();
        teleportToPlayer = pet.isTeleportingToPlayer();
        rideable = pet.isRideable();
        otherRideable = pet.isOtherRideable();
        particlesEnabled = pet.areParticlesEnabled();
    }

    @Override
    public Inventory getInventory() {
        loadButtons();
        return GraphicInterface.getInventory(this, new IGraphicInventoryData() {
            @Override
            public InventoryHolder getHolder() {
                return new SettingsInventoryHolder();
            }

            @Override
            public int getSize() {
                return 27;
            }

            @Override
            public String getTitle() {
                return InventoriesCache.settingsInventoryTitle;
            }
        }, inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE));
    }

    @Override
    public void loadButtons() {
        String path = "SettingsInventory.items.";
        GraphicInterface graphicInterface = GraphicInterface.getInstance();
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreatorLamp(path + "visibleToOthers.", visibleToOthers, this), 9), player -> {
            pet.setVisibleToOthers(!visibleToOthers);
            savePetProgress(player);
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreatorLamp(path + "giftable.", giftable, this), 10), player -> {
            pet.setGiftable(!giftable);
            savePetProgress(player);
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreatorLamp(path + "glows.", glows, this), 11), player -> {
            pet.setGlow(!glows);
            savePetProgress(player);
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreatorLamp(path + "followPlayer.", followPlayer, this), 12), player -> {
            pet.setFollowPlayer(!followPlayer);
            savePetProgress(player);
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreatorLamp(path + "teleportToPlayer.", teleportToPlayer, this), 13), player -> {
            pet.setTeleportingToPlayer(!teleportToPlayer);
            savePetProgress(player);
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreatorLamp(path + "rideable.", rideable, this), 14), player -> {
            pet.setRideable(!rideable);
            savePetProgress(player);
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreatorLamp(path + "otherRideable.", otherRideable, this), 15), player -> {
            pet.setOtherRideable(!otherRideable);
            savePetProgress(player);
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreatorLamp(path + "particlesEnabled.", particlesEnabled, this), 16), player -> {
            pet.setParticlesEnabled(!particlesEnabled);
            savePetProgress(player);
        }));
        graphicInterface.setButton(this, new GraphicItem(new GraphicItemData(itemCreator(path + "resetSettings.", this), 17), player -> {
            pet.resetSettings();
            savePetProgress(player);
        }));
    }

    protected void savePetProgress(Player player) {
        database.getUtils().respawnPet(pet, player);
        PetsUtils.savePetProgress(pet, player.getUniqueId());
        player.openInventory(new SettingsInventory(pet).getInventory());
    }

    @Override
    public String getHolderName() {
        return "SettingsInventory";
    }

    @Override
    public @NotNull List<String> setPlaceHolders(@NotNull List<String> lore) {
        return lore.stream().map(s -> FormatUtils.formatMessage(s
                .replace("%state_visibleToOthers%", String.valueOf(visibleToOthers))
                .replace("%state_giftable%", String.valueOf(giftable))
                .replace("%state_glows%", String.valueOf(glows))
                .replace("%state_followPlayer%", String.valueOf(followPlayer))
                .replace("%state_teleportToPlayer%", String.valueOf(teleportToPlayer))
                .replace("%state_rideable%", String.valueOf(rideable))
                .replace("%state_otherRideable%", String.valueOf(otherRideable))
                .replace("%state_particlesEnabled%", String.valueOf(particlesEnabled))))
                .collect(Collectors.toList());
    }
}

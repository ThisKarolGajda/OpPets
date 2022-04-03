package me.opkarol.oppets.listeners;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IHolder;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.inventories.BuyerAdmitInventory;
import me.opkarol.oppets.inventories.GuestInventory;
import me.opkarol.oppets.inventories.LevelInventory;
import me.opkarol.oppets.inventories.SettingsInventory;
import me.opkarol.oppets.inventories.anvil.PrestigeConfirmAnvilInventory;
import me.opkarol.oppets.inventories.anvil.RenameAnvilInventory;
import me.opkarol.oppets.inventories.holders.BuyerAdmitInventoryHolder;
import me.opkarol.oppets.inventories.holders.PrestigeInventoryHolder;
import me.opkarol.oppets.inventories.holders.SettingsInventoryHolder;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.pets.OpPetsEntityTypes;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.PetMainInventoryHolder;
import me.opkarol.oppets.shops.ShopInventoryHolder;
import me.opkarol.oppets.skills.SkillUtils;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.OpUtils;
import me.opkarol.oppets.utils.PDCUtils;
import me.opkarol.oppets.utils.PetsUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

import static me.opkarol.oppets.cache.NamespacedKeysCache.priceKey;
import static me.opkarol.oppets.cache.NamespacedKeysCache.typeKey;
import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

/**
 * The type Player interact.
 */
public class PlayerInteract implements Listener {

    /**
     * Player interact.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteract(@NotNull PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            return;
        }
        if (Database.getOpPets().getDatabase().getCurrentPet(player.getUniqueId()) != null) {
            if (Database.getOpPets().getDatabase().getCurrentPet(player.getUniqueId()).getOwnUUID() == event.getRightClicked().getUniqueId()) {
                event.setCancelled(true);
                OpUtils.openMainInventory(player);
                event.getPlayer().updateInventory();
                return;
            }
        }
        for (UUID uuid : Database.getOpPets().getDatabase().getActivePetMap().keySet()) {
            Pet pet = Database.getOpPets().getDatabase().getCurrentPet(uuid);
            if (pet == null || pet.getOwnUUID() == null) {
                return;
            }
            if (!pet.getOwnUUID().equals(event.getRightClicked().getUniqueId())) {
                return;
            }
            event.setCancelled(true);
            Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).openInventory(new GuestInventory(pet).getInventory());
            event.getPlayer().updateInventory();
        }

    }

    /**
     * Player interact.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteract(@NotNull InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder == null || event.getSlot() == -999) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        Pet pet = Database.getDatabase().getCurrentPet(uuid);
        int slot = event.getSlot();
        IUtils utils = Database.getOpPets().getUtils();
        if (!(holder instanceof IHolder)) {
            return;
        }
        event.setCancelled(true);
        if (holder instanceof SettingsInventoryHolder) {
            switch (slot) {
                case 9 -> pet.setVisibleToOthers(!pet.isVisibleToOthers());
                case 10 -> pet.setGiftable(!pet.isGiftable());
                case 11 -> pet.setGlow(!pet.isGlowing());
                case 12 -> pet.setFollowPlayer(!pet.isFollowingPlayer());
                case 13 -> pet.setTeleportingToPlayer(!pet.isTeleportingToPlayer());
                case 14 -> pet.setRideable(!pet.isRideable());
                case 15 -> pet.setOtherRideable(!pet.isOtherRideable());
                case 16 -> pet.setParticlesEnabled(!pet.areParticlesEnabled());
                case 17 -> pet.resetSettings();
            }
            utils.respawnPet(pet, player);
            PetsUtils.savePetProgress(pet, uuid);
            player.openInventory(new SettingsInventory(pet).getInventory());
        } else if (holder instanceof PetMainInventoryHolder) {
            switch (slot) {
                case 10 -> player.openInventory(new LevelInventory(pet).getInventory());
                case 12 -> new RenameAnvilInventory(pet, player);
                case 14 -> player.openInventory(new SettingsInventory(pet).getInventory());
                case 16 -> utils.respawnPet(pet, player);
            }
        } else if (holder instanceof ShopInventoryHolder) {
            Inventory inventory = player.getOpenInventory().getInventory(slot);
            if (inventory == null) {
                return;
            }
            ItemStack item = inventory.getItem(slot);
            if (item == null) {
                return;
            }
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
            String displayName = meta.getDisplayName();
            if (FormatUtils.formatMessage(displayName).equals("") || displayName.contains("PANE")) {
                return;
            }
            player.openInventory(new BuyerAdmitInventory(item).getInventory());
        } else if (holder instanceof BuyerAdmitInventoryHolder) {
            switch (slot) {
                case 10 -> player.closeInventory();
                case 16 -> {
                    Inventory inventory = player.getOpenInventory().getInventory(slot);
                    if (inventory == null) {
                        return;
                    }
                    ItemStack item = inventory.getItem(slot);
                    if (item == null) {
                        return;
                    }
                    ItemMeta meta = item.getItemMeta();
                    if (meta == null) {
                        return;
                    }
                    int price = new StringTransformer().getIntFromString(Objects.requireNonNull(PDCUtils.getNBT(item, priceKey)));
                    String type = PDCUtils.getNBT(item, typeKey);
                    Economy economy = Database.getOpPets().getEconomy();
                    if (economy != null) {
                        if (!economy.has(player, price)) {
                            return;
                        }
                        economy.withdrawPlayer(player, price);
                    }
                    final String[] name = {type};
                    int i = 0;
                    while (Database.getOpPets().getDatabase().getPetList(uuid).stream().anyMatch(pet1 -> FormatUtils.getNameString(name[0]).equals(FormatUtils.getNameString(pet1.getPetName())))) {
                        name[0] = type + "-" + i;
                        i++;
                    }
                    OpPetsEntityTypes.TypeOfEntity entity = OpPetsEntityTypes.TypeOfEntity.valueOf(type);
                    Pet pet1 = new Pet(name[0], entity, null, uuid, new SkillUtils().getRandomSkillName(entity), true);
                    Database.getOpPets().getDatabase().addPetToPetsList(uuid, pet1);
                    player.closeInventory();
                }
            }
        } else if (holder instanceof PrestigeInventoryHolder) {
            if (slot == 15) {
                if (!OpUtils.canPrestige(pet)) {
                    returnMessage(player, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("cantPrestige").replace("%more_levels%", String.valueOf(OpUtils.getLevelsForPrestige(pet))));
                    return;
                }
                player.closeInventory();
                new PrestigeConfirmAnvilInventory(pet, player);
            }
        }
    }
}

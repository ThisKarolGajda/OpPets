package me.opkarol.oppets.listeners;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.inventories.LevelInventory;
import me.opkarol.oppets.inventories.RenameAnvilInventory;
import me.opkarol.oppets.inventories.SettingsInventory;
import me.opkarol.oppets.inventories.holders.LevelInventoryHolder;
import me.opkarol.oppets.inventories.holders.PetMainInventoryHolder;
import me.opkarol.oppets.inventories.holders.SettingsInventoryHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.EntityUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public class PlayerInteract implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteract(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        if (OpPets.getDatabase().getCurrentPet(player.getUniqueId()) != null){
            if (OpPets.getDatabase().getCurrentPet(player.getUniqueId()).getOwnUUID() == event.getRightClicked().getUniqueId()){
                event.setCancelled(true);
                player.openInventory(OpPets.getInventoryManager().getInventoryByIndex(0));
                return;
            }
        }
        for (UUID uuid : OpPets.getDatabase().getActivePetMap().keySet()) {
            if (OpPets.getDatabase().getCurrentPet(uuid).getOwnUUID().equals(event.getRightClicked().getUniqueId())){
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteract2(@NotNull InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (event.getSlot() == -999) return;

        if (holder instanceof SettingsInventoryHolder) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            Pet pet = OpPets.getDatabase().getCurrentPet(event.getWhoClicked().getUniqueId());

            switch (event.getSlot()) {
                case 9 -> {
                    pet.setVisibleToOthers(getOppositeBoolean(pet.isVisibleToOthers()));
                    PetsUtils.respawnPet(pet, player);
                }
                case 10 -> {
                    pet.setGiftable(getOppositeBoolean(pet.isGiftable()));
                }
                case 11 -> {
                    pet.setGlow(getOppositeBoolean(pet.isGlowing()));
                    PetsUtils.respawnPet(pet, player);
                }
                case 12 -> {
                    pet.setFollowPlayer(getOppositeBoolean(pet.isFollowingPlayer()));
                    PetsUtils.respawnPet(pet, player);
                }
                case 13 -> {
                    pet.setTeleportingToPlayer(getOppositeBoolean(pet.isTeleportingToPlayer()));
                    PetsUtils.respawnPet(pet, player);
                }
                case 14 -> {
                    pet.setRideable(getOppositeBoolean(pet.isRideable()));
                }
                case 15 -> {
                    pet.setOtherRideable(getOppositeBoolean(pet.isOtherRideable()));
                }
                case 16 -> {
                    pet.setParticlesEnabled(getOppositeBoolean(pet.areParticlesEnabled()));
                }
                case 17 -> {
                    pet.setVisibleToOthers(true);
                    pet.setGiftable(false);
                    pet.setGlow(false);
                    pet.setFollowPlayer(true);
                    pet.setTeleportingToPlayer(true);
                    pet.setRideable(true);
                    pet.setOtherRideable(false);
                    pet.setParticlesEnabled(true);
                    PetsUtils.respawnPet(pet, player);
                }

            }
            openSettingsInventory(player, pet);
            player.sendMessage("");
        } else if (holder instanceof LevelInventoryHolder) {
            event.setCancelled(true);
        } else if (holder instanceof  PetMainInventoryHolder){
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            Pet pet = OpPets.getDatabase().getCurrentPet(event.getWhoClicked().getUniqueId());
            LivingEntity entity = (LivingEntity) EntityUtils.getEntityByUniqueId(pet.getOwnUUID());
            assert entity != null;
            switch (event.getSlot()) {
                case 10 -> player.openInventory(new LevelInventory(pet).getInventory());
                case 12 -> new RenameAnvilInventory(pet, player);
                case 14 -> player.openInventory(new SettingsInventory(pet).getInventory());
                case 16 -> {
                    player.closeInventory();
                    entity.setHealth(0);
                    OpPets.getCreator().spawnMiniPet(pet, player);
                }
            }
        }
    }

    private boolean getOppositeBoolean(boolean b){
        return !b;
    }

    private void openSettingsInventory(@NotNull Player player, @NotNull Pet pet){
        player.openInventory(new SettingsInventory(pet).getInventory());
    }

}

package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.inventories.DeleteAnvilInventory;
import me.opkarol.oppets.packets.PacketManager;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.EntityUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.*;

public class MainCommand implements CommandExecutor, TabCompleter {
    public static String noPetsString = "<NO-PETS>";
    List<String> allowedEntities = Arrays.asList("Axolotl", "Cat", "Chicken", "Cow", "Donkey", "Fox", "Goat", "Zoglin", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf");

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        Player player = (Player) sender;
        List<String> result = new ArrayList<>();
        if (args.length == 1 || args[0].equals(" ")){
            return Arrays.asList("ride", "delete", "create", "summon");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("summon")){
            OpPets.getDatabase().getPetList(player.getUniqueId()).forEach(pet -> result.add(ChatColor.stripColor(pet.getPetName())));
            if (result.size() == 0) result.add(noPetsString);
            return result;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")){
            OpPets.getDatabase().getPetList(player.getUniqueId()).forEach(pet -> result.add(ChatColor.stripColor(pet.getPetName())));
            if (result.size() == 0) result.add(noPetsString);
            return result;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("create")){
            return allowedEntities;
        }

        return result;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, "");
        }
        UUID playerUUID = player.getUniqueId();
        if (args.length == 2) {
            String arg1 = args[1];
            switch (args[0]) {
                case "delete" -> {
                    Pet pet = getPetByName(playerUUID, arg1);
                    if (pet == null) {
                        return returnMessage(sender, "");
                    }
                    new DeleteAnvilInventory(pet, player);
                }
                case "summon" -> {
                    List<Pet> playerPets = OpPets.getDatabase().getPetList(playerUUID);
                    Pet activePet = OpPets.getDatabase().getCurrentPet(playerUUID);

                    if (arg1.equals(noPetsString)) {
                        returnMessage(sender, "blocked name");
                    }

                    if (playerPets == null) {
                        return returnMessage(sender, "null lista petów");
                    }

                    for (Pet pet : playerPets) {
                        if (ChatColor.stripColor(pet.getPetName()).equals(ChatColor.stripColor(arg1))) {
                            if (activePet == pet) {
                                return returnMessage(sender, "Ten sam pet");
                            } else {
                                PetsUtils.killPetFromPlayerUUID(playerUUID);
                                OpPets.getCreator().spawnMiniPet(pet, player);
                                return returnMessage(sender, "Zespawnowano peta");
                            }
                        }
                    }
                    return returnMessage(sender, "Pet nie istnieje");
                }
            }

        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("create")) {
                String petType = args[1];
                if (petType.equals("PolarBear")){
                    petType = "Polar_Bear";
                } else if (petType.equals("Mushroom")){
                    petType = "Mushroom_Cow";
                } else if (!(allowedEntities.contains(petType))){
                    return returnMessage(sender, "invalid");
                }

                String petName = args[2];
                if (OpPets.getDatabase().getPetList(playerUUID) != null){
                    for (Pet pet : OpPets.getDatabase().getPetList(playerUUID)){
                        if (pet.getPetName().equals(petName)){
                            return returnMessage(sender, "");
                        }
                    }
                }
                EntityType type;

                try {
                    type = EntityType.valueOf(petType.toUpperCase());
                } catch (IllegalArgumentException e) {
                    return returnMessage(sender, "zly typ");
                }

                Pet pet = new Pet(petName, type, null, ((Player) sender).getUniqueId());
                OpPets.getDatabase().addPetToPetsList(((Player) sender).getUniqueId(), pet);

                return returnMessage(sender, "stworzono: " + petType + ", " + petName);
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("ride")){
            Entity entity = EntityUtils.getEntityByUniqueId(OpPets.getDatabase().getCurrentPet(playerUUID).getOwnUUID());

            if (entity != null) {
                entity.addPassenger(player);
                PacketManager.injectPlayer(player);

            }
        }
        return false;
    }

    private Pet getPetByName(UUID playerUUID, String petName){
        final Pet[] petI = new Pet[1];
        OpPets.getDatabase().getPetList(playerUUID).forEach(pet -> {
            if (ChatColor.stripColor(pet.getPetName()).equals(ChatColor.stripColor(petName))){
                petI[0] = pet;
            }
        });
        return petI[0];
    }
}

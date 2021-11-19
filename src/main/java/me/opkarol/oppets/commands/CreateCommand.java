package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.commands.MainCommand.allowedEntities;
import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class CreateCommand implements SubCommandInterface{
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)){
            return returnMessage(sender, "");
        }

        if (args.length != 3){
            return returnMessage(sender, "");
        }

        UUID playerUUID = player.getUniqueId();
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

    @Override
    public String getPermission() {
        return "oppets.command.create";
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public String getDescriptionAsString() {
        return null;
    }

    @Override
    public String getSubCommand() {
        return "create";
    }
}

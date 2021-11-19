package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.commands.MainCommand.noPetsString;
import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class SummonCommand implements SubCommandInterface {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)){
            return returnMessage(sender, "");
        }

        if (args.length != 2){
            return returnMessage(sender, "");
        }

        UUID playerUUID = player.getUniqueId();
        List<Pet> playerPets = OpPets.getDatabase().getPetList(playerUUID);
        Pet activePet = OpPets.getDatabase().getCurrentPet(playerUUID);

        if (args[1].equals(noPetsString)) {
            returnMessage(sender, "blocked name");
        }

        if (playerPets == null) {
            return returnMessage(sender, "null lista petów");
        }

        for (Pet pet : playerPets) {
            if (ChatColor.stripColor(pet.getPetName()).equals(ChatColor.stripColor(args[1]))) {
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

    @Override
    public String getPermission() {
        return "oppets.command.summon";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("", "");
    }

    @Override
    public String getDescriptionAsString() {
        return null;
    }

    @Override
    public String getSubCommand() {
        return "summon";
    }

}

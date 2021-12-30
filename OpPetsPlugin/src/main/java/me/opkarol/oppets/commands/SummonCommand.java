package me.opkarol.oppets.commands;

import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static me.opkarol.oppets.commands.MainCommand.noPetsString;
import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class SummonCommand implements SubCommandInterface {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, "");
        }

        if (args.length != 2) {
            return returnMessage(sender, "");
        }

        UUID playerUUID = player.getUniqueId();
        List<Pet> playerPets = OpPets.getDatabase().getPetList(playerUUID);
        Pet activePet = OpPets.getDatabase().getCurrentPet(playerUUID);

        if (args[1].equals(noPetsString)) {
            return returnMessage(sender, "blocked name");
        }

        if (playerPets == null || playerPets.size() == 0) {
            return returnMessage(sender, "Your pets list is empty.");
        }

        for (Pet pet : playerPets) {
            if (FormatUtils.getNameString(Objects.requireNonNull(pet.getPetName())).equals(FormatUtils.getNameString(args[1]))) {
                if (activePet == pet) {
                    return returnMessage(sender, "Ten sam pet");
                } else {
                    OpPets.getUtils().killPetFromPlayerUUID(playerUUID);
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

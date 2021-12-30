package me.opkarol.oppets.commands;

import dir.pets.OpPetsEntityTypes;
import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class CreateCommand implements SubCommandInterface {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, "");
        }

        if (args.length != 3) {
            return returnMessage(sender, "");
        }

        UUID playerUUID = player.getUniqueId();
        String petType = args[1];
        if (petType.toLowerCase().equals("polarbear")) {
            petType = "Polar_Bear";
        } else if (petType.toLowerCase().equals("mushroom")) {
            petType = "Mushroom_Cow";
        } else if (!(OpPets.getEntityManager().getAllowedEntities()
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList()).contains(petType.toLowerCase()))) {
            return returnMessage(sender, "invalid");
        }

        String petName = args[2];
        Bukkit.broadcastMessage(petName);
        if (OpPets.getDatabase().getPetList(playerUUID) != null) {
            for (Pet pet : OpPets.getDatabase().getPetList(playerUUID)) {
                assert pet.getPetName() != null;
                if (pet.getPetName().equals(petName)) {
                    return returnMessage(sender, "");
                }
            }
        }
        OpPetsEntityTypes.TypeOfEntity type;

        try {
            type = OpPetsEntityTypes.TypeOfEntity.valueOf(petType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return returnMessage(sender, "zly typ");
        }

        Pet pet = new Pet(petName, type, null, playerUUID, "example_skill", true);
        OpPets.getDatabase().addPetToPetsList(playerUUID, pet);

        return returnMessage(sender, "stworzono: " + pet.getPetType() + ", " + petName);
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

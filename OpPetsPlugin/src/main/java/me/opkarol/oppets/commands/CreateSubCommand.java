package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class CreateSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();

    public CreateSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        if (args.length != 3) {
            return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets create <TYPE> <NAME>"));
        }
        UUID playerUUID = player.getUniqueId();
        String petType = args[1];
        if (petType.equalsIgnoreCase("polarbear")) {
            petType = "Polar_Bear";
        } else if (petType.equalsIgnoreCase("mushroom")) {
            petType = "Mushroom_Cow";
        } else if (!(database.getOpPets().getEntityManager().getAllowedEntities()
                .map(String::toLowerCase)
                .collect(Collectors.toList()).contains(petType.toLowerCase()))) {
            return returnMessage(sender, getMessages().getString("Commands.wrongType").replace("%pet_type%", petType));
        }
        String petName = args[2];
        if (database.getDatabase().getPetList(playerUUID) != null) {
            for (Pet pet : database.getDatabase().getPetList(playerUUID)) {
                assert pet.getPetName() != null;
                if (pet.getPetName().equals(petName)) {
                    return returnMessage(sender, getMessages().getString("Commands.petWithSameName").replace("%pet_name%", petName));
                }
            }
        }
        Optional<TypeOfEntity> type = StringTransformer.getEnumValue(petType.toUpperCase(), TypeOfEntity.class);
        String finalPetType = petType;
        type.ifPresentOrElse(typeOfEntity -> {
            Pet pet = new Pet(petName, typeOfEntity, null, playerUUID, true);
            if (database.getDatabase().addPetToPetsList(playerUUID, pet)) {
                returnMessage(sender, getMessages().getString("Commands.createdPet").replace("%pet_name%", petName).replace("%pet_type%", typeOfEntity.getName()));
            }}, () -> returnMessage(sender, getMessages().getString("Commands.wrongType").replace("%pet_type%", finalPetType)));
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        List<String> result = new ArrayList<>();
        switch (args.length) {
            case 2 -> {
                List<String> completions = database.getOpPets().getEntityManager().getAllowedEntities().collect(Collectors.toList());
                StringUtil.copyPartialMatches(args[1], completions, result);
            }
            case 3 -> {
                String name = args[1];
                result.add(name.toLowerCase());
            }
        }
        return result;
    }
}

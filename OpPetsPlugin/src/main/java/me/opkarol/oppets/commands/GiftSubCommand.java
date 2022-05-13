package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class GiftSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();

    public GiftSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        if (args.length != 3) {
            return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets gift <PET> <PLAYER>"));
        }
        UUID uuid = player.getUniqueId();
        String petName = args[1];
        Optional<Pet> optional = database.getDatabase().removePet(uuid, petName);
        if (optional.isEmpty()) {
            return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
        }
        Pet pet = optional.get();
        if (!pet.isGiftable()) {
            return returnMessage(sender, getMessages().getString("Commands.petIsntGiftable"));
        }
        String playerName = args[2];
        UUID uuid1 = OpUtils.getUUIDFromName(playerName);
        if (database.getDatabase().hasPet(uuid1, petName)) {
            return returnMessage(sender, getMessages().getString("Commands.receiverSameNamedPet").replace("%pet_name%", petName));
        }
        database.getDatabase().removePet(uuid, pet);
        database.getDatabase().addPetToPetsList(uuid1, pet);
        return returnMessage(sender, getMessages().getString("Commands.petGifted").replace("%pet_name%", petName).replace("%player_name%", playerName));
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            return getPetList(((Player) sender).getUniqueId(), database);
        }
        if (args.length == 3) {
            return getPlayerList();
        }
        return new ArrayList<>();
    }
}

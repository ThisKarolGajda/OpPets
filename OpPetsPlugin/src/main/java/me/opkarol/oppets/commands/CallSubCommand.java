package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class CallSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();

    public CallSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
        if (pet == null) {
            return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
        }
        Entity entity = database.getOpPets().getUtils().getEntityByUniqueId(pet.getOwnUUID());
        if (entity == null) {
            return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
        }
        entity.teleport(player.getLocation());
        return returnMessage(sender, getMessages().getString("Commands.calledSuccessfully"));
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return new ArrayList<>();
    }
}

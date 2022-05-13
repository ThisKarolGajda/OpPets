package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.storage.OpObjects;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class AdminSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();

    public AdminSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        if (args.length != 5) {
            return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets admin <PLAYER> <PET> <KEY> <VALUE>"));
        }
        String playerName = args[1];
        UUID uuid = OpUtils.getUUIDFromName(playerName);
        List<Pet> petList = database.getDatabase().getPetList(uuid);
        if (petList.size() == 0) {
            return returnMessage(sender, getMessages().getString("Commands.petListEmpty"));
        }
        Optional<Pet> optional = database.getDatabase().removePet(uuid, args[2]);
        if (optional.isEmpty()) {
            return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
        }
        Pet pet = optional.get();
        String value = args[4];
        switch (args[3]) {
            case "level" -> pet.setLevel(Integer.parseInt(value));
            case "xp" -> pet.setPetExperience(Double.parseDouble(value));
            case "settings" -> pet.setSettings(OpObjects.get(value));
            case "prestige" -> pet.setPrestige(value);
            case "name" -> pet.setPetName(value);
            case "type" -> pet.setPetType(TypeOfEntity.valueOf(value));
            case "skill" -> pet.setSkillName(value);
            case "preferences" -> pet.setPreferences((OpObjects.get(value)));
        }
        database.getDatabase().addPetToPetsList(uuid, pet);
        return returnMessage(sender, getMessages().getString("Commands.changedAdminValue").replace("%value%", value).replace("%key%", args[3]).replace("%player_name%", playerName).replace("%pet_name%", args[2]));
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        UUID uuid = ((Player) sender).getUniqueId();
        List<String> result = new ArrayList<>();
        switch (args.length) {
            case 2 -> {
                return getPlayerList();
            }
            case 3 -> {
                return getPetList(uuid, database);
            }
            case 4 -> {
                return Arrays.asList("level", "xp", "prestige", "name", "settings", "type", "skill", "preferences");
            }
            case 5 -> {
                return database.getDatabase().getPetList(uuid).stream().filter(pet -> pet.getPetName().equals(args[2])).map(pet -> {
                    switch (args[3]) {
                        case "level" -> {
                            return String.valueOf(pet.getLevel());
                        }
                        case "xp" -> {
                            return String.valueOf(pet.getPetExperience());
                        }
                        case "prestige" -> {
                            return pet.getPrestige();
                        }
                        case "name" -> {
                            return pet.getPetName();
                        }
                        case "settings" -> {
                            return pet.getSettings().toString();
                        }
                        case "type" -> {
                            return String.valueOf(pet.getPetType());
                        }
                        case "skill" -> {
                            return pet.getSkillName();
                        }
                        case "preferences" -> {
                            return pet.getPreferences().toString();
                        }
                        default -> {
                            return "<WRONG-TYPE>";
                        }
                    }
                }).collect(Collectors.toList());
            }
        }
        return result;
    }
}

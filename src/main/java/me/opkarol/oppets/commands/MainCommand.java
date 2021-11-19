package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.opkarol.oppets.utils.FormatUtils.*;

public class MainCommand implements CommandExecutor, TabCompleter {
    public static String noPetsString = "<NO-PETS>";
    public static List<String> allowedEntities = Arrays.asList("Axolotl", "Cat", "Chicken", "Cow", "Donkey", "Fox", "Goat", "Zoglin", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf");

    List<SubCommandInterface> commands;
    public MainCommand(){
        commands = new ArrayList<>();
        commands.add(new SummonCommand());
        commands.add(new CreateCommand());
        commands.add(new DeleteCommand());
        commands.add(new RideCommand());
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        Player player = (Player) sender;

        if (args.length == 1 || args[0].equalsIgnoreCase(" ")){
            return Arrays.asList("ride", "delete", "create", "summon");
        } else if (args.length == 2){
            switch (args[0]){
                case "summon", "delete" -> {
                    List<String> result = new ArrayList<>();
                    OpPets.getDatabase().getPetList(player.getUniqueId()).forEach(pet -> result.add(ChatColor.stripColor(pet.getPetName())));
                    if (result.size() == 0) result.add(noPetsString);
                    return result;
                }
                case "create" -> {
                    return allowedEntities;
                }
            }
        }

        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String cmd = (args.length == 0 || args[0].equalsIgnoreCase(" ")) ? "help" : args[0];

        for (SubCommandInterface subCommandInterface : commands) {
            if (subCommandInterface.getSubCommand().equals(cmd)) {
                if (sender.hasPermission(subCommandInterface.getPermission()) || sender.isOp() || subCommandInterface.getPermission() == null) {
                    subCommandInterface.execute(sender, args);
                }
                return true;
            }
        }

        return returnMessage(sender, "");
    }

}

package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class MainCommand implements CommandExecutor, TabCompleter {
    public static String noPetsString = "<NO-PETS>";

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
                    if (OpPets.getDatabase().getPetList(player.getUniqueId()) == null) return List.of(noPetsString);
                    OpPets.getDatabase().getPetList(player.getUniqueId()).forEach(pet -> result.add(ChatColor.stripColor(Objects.requireNonNull(pet.getPetName()))));
                    if (result.size() == 0) result.add(noPetsString);
                    return result;
                }
                case "create" -> {
                    List<String> completions = new ArrayList<>(OpPets.getEntityManager().getAllowedEntities());
                    List<String> result = new ArrayList<>();
                    StringUtil.copyPartialMatches(args[1], completions, result);
                    Collections.sort(result);
                    return result;
                    /*
                    String arg = args.length > 1 ? args[args.length -1] : "";
                    return OpPets.getEntityManager().getAllowedEntities().stream()
                            .filter(s -> (arg.isEmpty() || s.startsWith(arg.toLowerCase(Locale.ENGLISH))))
                            .collect(Collectors.toList());
                                                 */

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

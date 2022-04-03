package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.boosters.Booster;
import me.opkarol.oppets.broadcasts.Broadcast;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.ICommand;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

/**
 * The type Op pets command.
 */
public class OpPetsCommand implements CommandExecutor, TabCompleter {
    /**
     * The constant noPetsString.
     */
    public static String noPetsString = "<NO-PETS>";

    /**
     * The Commands.
     */
    private final List<ICommand> commands = new ArrayList<>();

    /**
     * Instantiates a new Op pets command.
     */
    public OpPetsCommand() {
        commands.add(new SummonCommand());
        commands.add(new CreateCommand());
        commands.add(new DeleteCommand());
        commands.add(new RideCommand());
        commands.add(new HelpCommand());
        commands.add(new RenameCommand());
        commands.add(new ShopCommand());
        commands.add(new GiftCommand());
        commands.add(new PrestigeCommand());
        commands.add(new AdminCommand());
        commands.add(new CallCommand());
        commands.add(new BoosterCommand());
        commands.add(new LeaderboardCommand());
        commands.add(new BroadcastCommand());
        commands.add(new AddonsCommand());
    }

    /**
     * On tab complete list.
     *
     * @param sender the sender
     * @param cmd    the cmd
     * @param label  the label
     * @param args   the args
     * @return the list
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        Player player = (Player) sender;
        List<String> result = new ArrayList<>();
        if (args.length == 1 || args[0].equalsIgnoreCase(" ")) {
            for (ICommand command : commands) {
                if (player.hasPermission(command.getPermission()) || player.isOp()) {
                    result.add(command.getSubCommand());
                }
            }
        }
        switch (args.length) {
            case 2 -> {
                switch (args[0].toLowerCase()) {
                    case "delete", "summon", "rename", "gift" -> result.addAll(getCompletedPetList(player.getUniqueId(), args[1]));
                    case "create" -> {
                        HashSet<String> completions = Database.getOpPets().getEntityManager().getAllowedEntities();
                        StringUtil.copyPartialMatches(args[1], completions, result);
                    }
                    case "admin" -> result.addAll(getPlayers(args[1]));
                    case "booster", "broadcast" -> {
                        List<String> completions = new ArrayList<>(Arrays.asList("add", "remove", "list"));
                        StringUtil.copyPartialMatches(args[1], completions, result);
                    }
                }
            }
            case 3 -> {
                switch (args[0].toLowerCase()) {
                    case "gift" -> result.addAll(getPlayers(args[2]));
                    case "admin" -> result.addAll(getCompletedPetList(OpUtils.getUUIDFromName(args[1]), args[2]));
                    case "booster" -> {
                        switch (args[1]) {
                            case "remove" -> {
                                List<String> completions = new ArrayList<>(Database.getOpPets().getBoosterProvider().getBoosters().stream().map(Booster::getName).toList());
                                StringUtil.copyPartialMatches(args[2], completions, result);
                            }
                            case "add" -> StringUtil.copyPartialMatches(args[2], List.of("(name)"), result);
                        }
                    }
                    case "create" -> StringUtil.copyPartialMatches(args[2], List.of("(name)"), result);
                    case "broadcast" -> {
                        switch (args[1].toLowerCase()) {
                            case "remove" -> StringUtil.copyPartialMatches(args[2], List.of("(type)"), result);
                            case "add" -> StringUtil.copyPartialMatches(args[2], List.of("(prefix)"), result);
                        }
                    }
                }
            }
            case 4 -> {
                switch (args[0].toLowerCase()) {
                    case "admin" -> StringUtil.copyPartialMatches(args[3], Arrays.asList("level", "xp", "prestige", "name", "settings", "type", "skill"), result);
                    case "booster" -> StringUtil.copyPartialMatches(args[3], Arrays.asList("SERVER", "PLAYER"), result);
                    case "broadcast" -> {
                        List<String> values = Arrays.stream(Broadcast.BROADCAST_TYPE.values()).map(Broadcast.BROADCAST_TYPE::name).toList();
                        StringUtil.copyPartialMatches(args[3], values, result);
                    }
                }
            }
            case 5 -> {
                switch (args[0].toLowerCase()) {
                    case "booster" -> StringUtil.copyPartialMatches(args[4], List.of("(multiplier)"), result);
                    case "admin" -> Database.getOpPets().getDatabase().getPetList(OpUtils.getUUIDFromName(args[1])).stream().filter(pet -> Objects.equals(FormatUtils.getNameString(pet.getPetName()), FormatUtils.getNameString(args[2]))).collect(Collectors.toList()).forEach(pet -> {
                        switch (args[3]) {
                            case "level" -> result.add(String.valueOf(pet.getLevel()));
                            case "xp" -> result.add(String.valueOf(pet.getPetExperience()));
                            case "prestige" -> result.add(pet.getPrestige());
                            case "name" -> result.add(pet.getPetName());
                            case "settings" -> result.add(pet.getSettingsSerialized());
                            case "type" -> result.add(String.valueOf(pet.getPetType()));
                            case "skill" -> result.add(pet.getSkillName());
                            default -> throw new IllegalStateException("Unexpected value: " + args[3]);
                        }
                    });
                }
            }
            case 6 -> {
                if (args[0].equalsIgnoreCase("booster")) {
                    StringUtil.copyPartialMatches(args[5], List.of("(time-in-seconds)"), result);
                }
            }
            case 7 -> {
                if (args[0].equalsIgnoreCase("booster") && args[3].equalsIgnoreCase("PLAYER")) {
                    StringUtil.copyPartialMatches(args[6], getPlayers(args[6]), result);
                }
            }
        }
        if (args.length > 4 && args[0].equalsIgnoreCase("broadcast")) {
            StringUtil.copyPartialMatches(args[args.length - 1], List.of("message"), result);
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Gets players.
     *
     * @param arg the arg
     * @return the players
     */
    private @NotNull List<String> getPlayers(String arg) {
        List<String> result = new ArrayList<>();
        List<String> completions = new ArrayList<>();
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            completions.add(player1.getName());
        }
        StringUtil.copyPartialMatches(arg, completions, result);
        return result;
    }

    /**
     * Gets completed pet list.
     *
     * @param uuid the uuid
     * @param args the args
     * @return the completed pet list
     */
    private @NotNull @Unmodifiable List<String> getCompletedPetList(UUID uuid, String args) {
        List<String> list = new ArrayList<>();
        if (Database.getOpPets().getDatabase().getPetList(uuid) == null) return Collections.singletonList(noPetsString);
        List<String> completions = new ArrayList<>();
        Database.getOpPets().getDatabase().getPetList(uuid).forEach(pet -> completions.add(FormatUtils.getNameString(pet.getPetName())));
        StringUtil.copyPartialMatches(args, completions, list);
        if (list.size() == 0) return Collections.singletonList(noPetsString);
        return list;
    }

    /**
     * On command boolean.
     *
     * @param sender  the sender
     * @param command the command
     * @param label   the label
     * @param args    the args
     * @return the boolean
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        String cmd = (args.length == 0 || args[0].equalsIgnoreCase(" ")) ? "help" : args[0];
        for (ICommand subCommandInterface : commands) {
            if (subCommandInterface.getSubCommand().equals(cmd)) {
                if (sender.hasPermission(subCommandInterface.getPermission()) || sender.isOp() || subCommandInterface.getPermission() == null) {
                    if (!subCommandInterface.execute(sender, args)) {
                        return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("badCommandUsage").replace("%proper_usage%", "Error occurred"));
                    }
                    return true;
                }
                return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("noPermission").replace("%permission%", subCommandInterface.getPermission()));
            }
        }
        return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets <create/summon/gift/help/rename/ride/shop/summon>"));
    }

}

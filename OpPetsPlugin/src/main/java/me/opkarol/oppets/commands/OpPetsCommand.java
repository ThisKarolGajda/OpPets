package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.Messages;
import me.opkarol.oppets.OpPets;
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

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class OpPetsCommand implements CommandExecutor, TabCompleter {
    public static String noPetsString = "<NO-PETS>";

    private final List<ICommand> commands;

    public OpPetsCommand() {
        commands = new ArrayList<>();
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
    }

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
        } else if (args.length == 2) {
            switch (args[0]) {
                case "delete", "summon", "rename", "gift" -> result.addAll(getCompletedPetList(player.getUniqueId(), args[1]));
                case "create" -> {
                    List<String> completions = new ArrayList<>(OpPets.getEntityManager().getAllowedEntities());
                    StringUtil.copyPartialMatches(args[1], completions, result);
                }
                case "admin" -> result.addAll(getPlayers(args[1]));
            }
        } else if (args.length == 3) {
            switch (args[0]) {
                case "gift" -> result.addAll(getPlayers(args[2]));
                case "admin" -> result.addAll(getCompletedPetList(OpUtils.getUUIDFromName(args[1]), args[2]));
            }
        } else if (args.length == 4) {
            if (args[0].equals("admin")) {
                result.addAll(Arrays.asList("level", "xp", "prestige", "name", "settings", "type", "skill"));
            }
        } else if (args.length == 5) {
            if (args[0].equals("admin")) {
                OpPets.getDatabase().getPetList(OpUtils.getUUIDFromName(args[1])).forEach(pet -> {
                    if (Objects.equals(FormatUtils.getNameString(pet.getPetName()), FormatUtils.getNameString(args[2]))) {
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
                    }
                });
            }
        }

        Collections.sort(result);
        return result;
    }

    private @NotNull List<String> getPlayers(String arg) {
        List<String> result = new ArrayList<>();
        List<String> completions = new ArrayList<>();
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            completions.add(player1.getName());
        }
        StringUtil.copyPartialMatches(arg, completions, result);
        return result;
    }

    private @NotNull @Unmodifiable List<String> getCompletedPetList(UUID uuid, String args) {
        List<String> list = new ArrayList<>();
        if (OpPets.getDatabase().getPetList(uuid) == null) return Collections.singletonList(noPetsString);
        List<String> completions = new ArrayList<>();
        OpPets.getDatabase().getPetList(uuid).forEach(pet -> completions.add(FormatUtils.getNameString(pet.getPetName())));
        StringUtil.copyPartialMatches(args, completions, list);
        if (list.size() == 0) return Collections.singletonList(noPetsString);
        return list;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String cmd = (args.length == 0 || args[0].equalsIgnoreCase(" ")) ? "help" : args[0];

        for (ICommand subCommandInterface : commands) {
            if (subCommandInterface.getSubCommand().equals(cmd)) {
                if (sender.hasPermission(subCommandInterface.getPermission()) || sender.isOp() || subCommandInterface.getPermission() == null) {
                    if (!subCommandInterface.execute(sender, args)) {
                        return returnMessage(sender, Messages.stringMessage("badCommandUsage").replace("%proper_usage%", "Error occurred"));
                    }
                    return true;
                }
                return returnMessage(sender, Messages.stringMessage("noPermission").replace("%permission%", subCommandInterface.getPermission()));
            }
        }

        return returnMessage(sender, Messages.stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets <create/summon/gift/help/rename/ride/shop/summon>"));
    }

}

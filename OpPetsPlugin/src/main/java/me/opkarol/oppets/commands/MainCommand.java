package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.utils.FormatUtils;
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

    private final List<SubCommandInterface> commands;

    public MainCommand() {
        commands = new ArrayList<>();
        commands.add(new SummonCommand());
        commands.add(new CreateCommand());
        commands.add(new DeleteCommand());
        commands.add(new RideCommand());
        commands.add(new HelpCommand());
        commands.add(new RenameCommand());
        commands.add(new ShopCommand());
        commands.add(new GiftCommand());

    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        Player player = (Player) sender;
        List<String> result = new ArrayList<>();

        if (args.length == 1 || args[0].equalsIgnoreCase(" ")) {
            for (SubCommandInterface command : commands) {
                if (player.hasPermission(command.getPermission()) || player.isOp()) {
                    result.add(command.getSubCommand());
                }
            }
        } else if (args.length == 2) {
            switch (args[0]) {
                case "delete", "summon", "rename" -> {
                    UUID uuid = player.getUniqueId();
                    if (OpPets.getDatabase().getPetList(uuid) == null) return Collections.singletonList(noPetsString);
                    List<String> completions = new ArrayList<>();
                    OpPets.getDatabase().getPetList(uuid).forEach(pet -> completions.add(FormatUtils.getNameString(pet.getPetName())));
                    StringUtil.copyPartialMatches(args[1], completions, result);
                    Collections.sort(result);
                    if (result.size() == 0) result.add(noPetsString);
                }
                case "create" -> {
                    List<String> completions = new ArrayList<>(OpPets.getEntityManager().getAllowedEntities());
                    StringUtil.copyPartialMatches(args[1], completions, result);
                    Collections.sort(result);
                }
            }
        }

        return result;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String cmd = (args.length == 0 || args[0].equalsIgnoreCase(" ")) ? "help" : args[0];

        for (SubCommandInterface subCommandInterface : commands) {
            if (subCommandInterface.getSubCommand().equals(cmd)) {
                if (sender.hasPermission(subCommandInterface.getPermission()) || sender.isOp() || subCommandInterface.getPermission() == null) {
                    if (!subCommandInterface.execute(sender, args)) {
                        return returnMessage(sender, "");
                    }
                }
                return true;
            }
        }

        return returnMessage(sender, "");
    }

}

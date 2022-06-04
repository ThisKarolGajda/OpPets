package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.broadcasts.Broadcast;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.misc.StringTransformer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static me.opkarol.oppets.utils.external.FormatUtils.returnMessage;

public class BroadcastSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();
    final String format;

    public BroadcastSubCommand(String command, String permission) {
        super(command, permission);
        format = getMessages().getString("Commands.broadcastListFormat");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 1) {
            return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets broadcast <add/remove/list> (NAME) (PREFIX) (MESSAGE)"));
        }
        if (args.length == 2 && args[1].equalsIgnoreCase("list")) {
            StringBuilder builder = new StringBuilder();
            for (Broadcast broadcast : database.getOpPets().getBroadcastManager().getBroadcastList()) {
                builder.append(getFormatted(broadcast));
            }
            if (builder.isEmpty()) {
                builder.append("List is empty.");
            }
            sender.sendMessage(builder.toString());
        }
        if (args.length == 3 && args[1].equalsIgnoreCase("remove")) {
            Optional<Broadcast.BROADCAST_TYPE> object = StringTransformer.getEnumValue(args[2], Broadcast.BROADCAST_TYPE.class);
            if (object.isEmpty()) {
                return returnMessage(sender, "");
            }
            Broadcast.BROADCAST_TYPE type = object.get();
            database.getOpPets().getBroadcastManager().removeBroadcast(type);
        }
        if (args.length >= 4 && args[1].equalsIgnoreCase("add")) {
            String prefix = args[2];
            Optional<Broadcast.BROADCAST_TYPE> object = StringTransformer.getEnumValue(args[3], Broadcast.BROADCAST_TYPE.class);
            if (object.isEmpty()) {
                return returnMessage(sender, "");
            }
            Broadcast.BROADCAST_TYPE type = object.get();
            StringBuilder builder = new StringBuilder();
            for (int i = 4; i < args.length; i++) {
                builder.append(args[i]).append(" ");
            }
            String message = builder.toString();
            database.getOpPets().getBroadcastManager().broadcastTemp(prefix, database.getOpPets().getBroadcastManager().getFormat(), type, message);
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        List<String> result = new ArrayList<>();
        switch (args.length) {
            case 2 -> {
                return Arrays.asList("add", "remove", "list");
            }
            case 3 -> {
                switch (args[1].toLowerCase()) {
                    case "remove" -> {
                        return List.of("(type)");
                    }
                    case "add" -> {
                        return List.of("(prefix)");
                    }
                }
            }
            case 4 -> {
                return Arrays.stream(Broadcast.BROADCAST_TYPE.values()).map(Broadcast.BROADCAST_TYPE::name).toList();
            }
        }
        return result;
    }

    public @NotNull String getFormatted(@NotNull Broadcast broadcast) {
        return format
                .replace("%format%", String.valueOf(broadcast.getFormat()))
                .replace("%prefix%", String.valueOf(broadcast.getPrefix()))
                .replace("%type%", broadcast.getType().name()) +
                "\n";
    }
}

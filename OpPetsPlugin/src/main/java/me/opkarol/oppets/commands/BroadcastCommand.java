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
import me.opkarol.oppets.files.Messages;
import me.opkarol.oppets.interfaces.ICommand;
import me.opkarol.oppets.misc.StringTransformer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

/**
 * The type Broadcast command.
 */
public class BroadcastCommand implements ICommand {
    /**
     * The Format.
     */
    final String format;

    /**
     * Instantiates a new Broadcast command.
     */
    public BroadcastCommand() {
        format = Database.getOpPets().getMessages().getMessagesAccess().stringMessage("broadcastListFormat");
    }

    /**
     * Execute boolean.
     *
     * @param sender the sender
     * @param args   the args
     * @return the boolean
     */
    @Override
    public boolean execute(CommandSender sender, String @NotNull [] args) {
        // /oppets broadcast <add/remove/list> (name) (prefix) (type) (message)
        if (args.length == 1) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets broadcast <add/remove/list> (NAME) (PREFIX) (MESSAGE)"));
        }
        if (args.length == 2 && args[1].equalsIgnoreCase("list")) {
            StringBuilder builder = new StringBuilder();
            for (Broadcast broadcast : Database.getOpPets().getBroadcastManager().getBroadcastList()) {
                builder.append(getFormatted(broadcast));
            }
            if (builder.isEmpty()) {
                builder.append("List is empty.");
            }
            sender.sendMessage(builder.toString());
        }
        if (args.length == 3 && args[1].equalsIgnoreCase("remove")) {
            Object object = new StringTransformer().getEnumFromString(args[2], Broadcast.BROADCAST_TYPE.class);
            if (object == null) {
                return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("invalidObjectProvided"));
            }
            Broadcast.BROADCAST_TYPE type = (Broadcast.BROADCAST_TYPE) object;
            Database.getOpPets().getBroadcastManager().removeBroadcast(type);
        }
        if (args.length >= 4 && args[1].equalsIgnoreCase("add")) {
            String prefix = args[2];
            Object object = new StringTransformer().getEnumFromString(args[3], Broadcast.BROADCAST_TYPE.class);
            if (object == null) {
                return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("invalidObjectProvided"));
            }
            Broadcast.BROADCAST_TYPE type = (Broadcast.BROADCAST_TYPE) object;
            StringBuilder builder = new StringBuilder();
            for (int i = 4; i < args.length; i++) {
                builder.append(args[i]).append(" ");
            }
            String message = builder.toString();
            // name is being ignored since it will play as a temporary broadcast
            Database.getOpPets().getBroadcastManager().broadcastTemp(prefix, Database.getOpPets().getBroadcastManager().getFormat(), type, message);
        }
        return true;
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    @Override
    public String getPermission() {
        return "oppets.command.broadcast";
    }

    /**
     * Gets sub command.
     *
     * @return the sub command
     */
    @Override
    public String getSubCommand() {
        return "broadcast";
    }

    /**
     * Gets formatted.
     *
     * @param broadcast the broadcast
     * @return the formatted
     */
    public @NotNull String getFormatted(@NotNull Broadcast broadcast) {
        return format
                .replace("%format%", String.valueOf(broadcast.getFormat()))
                .replace("%prefix%", String.valueOf(broadcast.getPrefix()))
                .replace("%type%", broadcast.getType().name()) +
                "\n";
    }
}

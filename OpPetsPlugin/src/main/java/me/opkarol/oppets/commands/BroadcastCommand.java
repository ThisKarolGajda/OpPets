package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.broadcasts.Broadcast;
import dir.databases.Database;
import dir.files.Messages;
import dir.interfaces.ICommand;
import dir.misc.StringTransformer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static dir.utils.FormatUtils.returnMessage;

public class BroadcastCommand implements ICommand {
    final String format;

    public BroadcastCommand() {
        format = Messages.stringMessage("broadcastListFormat");
    }
    @Override
    public boolean execute(CommandSender sender, String @NotNull [] args) {
        // /oppets broadcast <add/remove/list> (name) (prefix) (type) (message)
        if (args.length == 1) {
            return returnMessage(sender, Messages.stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets broadcast <add/remove/list> (NAME) (PREFIX) (MESSAGE)"));
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
                return returnMessage(sender, Messages.stringMessage("invalidObjectProvided"));
            }
            Broadcast.BROADCAST_TYPE type = (Broadcast.BROADCAST_TYPE) object;
            Database.getOpPets().getBroadcastManager().removeBroadcast(type);
        }
        if (args.length >= 4 && args[1].equalsIgnoreCase("add")) {
            String prefix = args[2];
            Object object = new StringTransformer().getEnumFromString(args[3], Broadcast.BROADCAST_TYPE.class);
            if (object == null) {
                return returnMessage(sender, Messages.stringMessage("invalidObjectProvided"));
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

    @Override
    public String getPermission() {
        return "oppets.command.broadcast";
    }

    @Override
    public String getSubCommand() {
        return "broadcast";
    }

    public @NotNull String getFormatted(@NotNull Broadcast broadcast) {
        return format
                .replace("%format%", String.valueOf(broadcast.format()))
                .replace("%prefix%", String.valueOf(broadcast.prefix()))
                .replace("%type%", broadcast.type().name()) +
                "\n";
    }
}

package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.boosters.Booster;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.files.Messages;
import me.opkarol.oppets.interfaces.ICommand;
import me.opkarol.oppets.utils.OpUtils;
import me.opkarol.oppets.misc.StringTransformer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class BoosterCommand implements ICommand {
    private final StringTransformer transformer;
    private final String format;

    public BoosterCommand() {
        transformer = new StringTransformer();
        format = Messages.stringMessage("boostersListFormat");
    }

    @Override
    public boolean execute(CommandSender sender, String @NotNull [] args) {
        if (args.length < 2) {
            return returnMessage(sender, Messages.stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets booster <add/remove/list> (name) (type) (multiplier) (time)"));
        }

        String parameter = args[1];
        switch (args.length) {
            case 2 -> {
                List<Booster> boosterList = Database.getOpPets().getBoosterProvider().getBoosters().stream().toList();
                StringBuilder builder = new StringBuilder();
                for (Booster booster : boosterList) {
                    builder.append(getFormatted(booster));
                }
                if (builder.isEmpty()) {
                    builder.append("List is empty.");
                }
                sender.sendMessage(builder.toString());
            }
            case 3 -> {
                if (parameter.equalsIgnoreCase("remove")) {
                    String name = args[2];
                    Booster booster = Database.getOpPets().getBoosterProvider().getBooster(name);
                    if (booster == null) {
                        return returnMessage(sender, "invalidBooster");
                    }
                    Database.getOpPets().getBoosterProvider().removeBooster(name);
                }
            }
            case 6, 7 -> {
                if (parameter.equalsIgnoreCase("add")) {
                    String name = args[2];
                    Object object = transformer.getEnumFromString(args[3], Booster.BOOSTER_TYPE.class);
                    if (object == null) {
                        return returnMessage(sender, Messages.stringMessage("invalidObjectProvided"));
                    }
                    Booster.BOOSTER_TYPE type = (Booster.BOOSTER_TYPE) object;
                    Double multiplier = transformer.getDoubleFromString(args[4]);
                    Integer time = transformer.getIntFromString(args[5]);
                    if (name == null || multiplier == null || time == null) {
                        return returnMessage(sender, Messages.stringMessage("invalidObjectProvided"));
                    }
                    if (!type.equals(Booster.BOOSTER_TYPE.PLAYER)) {
                        Database.getOpPets().getBoosterProvider().createNewBooster(name, multiplier, time, type);
                    } else {
                        String playerName = args[6];
                        Database.getOpPets().getBoosterProvider().createNewBooster(name, multiplier, time, type, OpUtils.getUUIDFromName(playerName));
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String getPermission() {
        return "oppets.command.booster";
    }

    @Override
    public String getSubCommand() {
        return "booster";
    }

    public @NotNull String getFormatted(@NotNull Booster booster) {
        return format
                .replace("%running%", String.valueOf(booster.isRunning()))
                .replace("%multiplier%", String.valueOf(booster.getMultiplier()))
                .replace("%type%", booster.getType().name())
                .replace("%name%", booster.getName()) +
                "\n";
    }
}

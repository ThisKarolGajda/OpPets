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
import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.utils.Utils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static me.opkarol.oppets.utils.external.FormatUtils.returnMessage;

public class BoosterSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();
    private final String format;

    public BoosterSubCommand(String command, String permission) {
        super(command, permission);
        format = getMessages().getString("Commands.boostersListFormat");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 2) {
            return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets booster <add/remove/list> (name) (type) (multiplier) (time)"));
        }

        String parameter = args[1];
        switch (args.length) {
            case 2 -> {
                List<Booster> boosterList = database.getOpPets().getBoosterProvider().getBoosters().stream().toList();
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
                    Booster booster = database.getOpPets().getBoosterProvider().getBooster(name);
                    if (booster == null) {
                        return returnMessage(sender, "invalidBooster");
                    }
                    database.getOpPets().getBoosterProvider().removeBooster(name);
                }
            }
            case 6, 7 -> {
                if (parameter.equalsIgnoreCase("add")) {
                    String name = args[2];
                    Optional<Booster.BOOSTER_TYPE> optional = StringTransformer.getEnumValue(args[3], Booster.BOOSTER_TYPE.class);
                    if (optional.isEmpty()) {
                        return returnMessage(sender, "");
                    }
                    Booster.BOOSTER_TYPE type = optional.get();
                    Double multiplier = StringTransformer.getDoubleFromString(args[4]);
                    Integer time = StringTransformer.getIntFromString(args[5]);
                    if (multiplier == -1D || time == -1) {
                        return returnMessage(sender, getMessages().getString("Commands.invalidObjectProvided"));
                    }
                    if (!type.equals(Booster.BOOSTER_TYPE.PLAYER)) {
                        database.getOpPets().getBoosterProvider().createNewBooster(name, multiplier, time, type);
                    } else {
                        String playerName = args[6];
                        database.getOpPets().getBoosterProvider().createNewBooster(name, multiplier, time, type, Utils.getUUIDFromName(playerName));
                    }
                }
            }
        }
        return true;    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        switch (args.length) {
            case 2 -> {
                return Arrays.asList("add", "remove", "list");
            }
            case 3 -> {
                return database.getOpPets().getBoosterProvider().getBoosters().stream().map(Booster::getName).toList();
            }
            case 4 -> {
                return Arrays.asList("SERVER", "PLAYER");
            }
            case 5 -> {
                return List.of("(multiplier)");
            }
            case 6 -> {
                return List.of("(time-in-seconds)");
            }
            case 7 -> {
                if (args[3].equalsIgnoreCase("PLAYER")) {
                    return getPlayerList();
                }
            }
        }
        return new ArrayList<>();
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

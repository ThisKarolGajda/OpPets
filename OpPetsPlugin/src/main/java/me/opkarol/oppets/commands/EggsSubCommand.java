package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.eggs.types.EggItem;
import me.opkarol.oppets.files.MessagesHolder;
import me.opkarol.oppets.inventory.OpInventories;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static me.opkarol.oppets.utils.external.FormatUtils.returnMessage;
import static me.opkarol.oppets.utils.OpUtils.getEggItemFromString;

@Deprecated
public class EggsSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();
    private final MessagesHolder messages = MessagesHolder.getInstance();

    public EggsSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        if (args.length == 2) {
            Optional<EggItem> optional = getEggItemFromString(args[1]);
            if (optional.isEmpty()) {
                return returnMessage(player, messages.getString("Commands.invalidObjectProvided"));
            }
            EggItem eggItem = optional.get();
            player.openInventory(new OpInventories.EggRecipesInventory(eggItem.getType(), eggItem.getRecipes(), 0).buildInventory());
        }
        if (args.length == 3) {
            Optional<EggItem> optional = getEggItemFromString(args[1]);
            if (optional.isEmpty()) {
                return returnMessage(player, messages.getString("Commands.invalidObjectProvided"));
            }
            EggItem eggItem = optional.get();
            //TODO select recipe
            player.openInventory(new OpInventories.EggRecipeInventory(eggItem.getRecipes().get(0), eggItem.getItem()).buildInventory());
            return true;
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        if (args.length == 2) {
            return database.getOpPets().getEggManager().getTypes();
        }
        return new ArrayList<>();
    }
}

package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.eggs.types.EggItem;
import me.opkarol.oppets.api.files.MessagesHolder;
import me.opkarol.oppets.eggs.types.EggRecipe;
import me.opkarol.oppets.inventory.OpInventories;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static me.opkarol.oppets.utils.external.FormatUtils.returnMessage;
import static me.opkarol.oppets.utils.Utils.getEggItemFromString;

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
            player.openInventory(new OpInventories.EggRecipesInventory(optional, 0).buildInventory());
        }
        if (args.length == 3) {
            Optional<EggItem> optional = getEggItemFromString(args[1]);
            if (optional.isEmpty()) {
                return returnMessage(player, messages.getString("Commands.invalidObjectProvided"));
            }
            EggItem eggItem = optional.get();
            String stringRecipe = args[2];
            Optional<EggRecipe> recipe = eggItem.getRecipe(stringRecipe);
            recipe.ifPresent(eggRecipe -> player.openInventory(new OpInventories.EggRecipeInventory(eggRecipe, eggItem.getItem()).buildInventory()));
            return true;
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        switch (args.length) {
            case 2 -> {
                return database.getOpPets().getEggManager().getTypes();
            }
            case 3 -> {
                Optional<TypeOfEntity> type = StringTransformer.getEnumValue(args[1].toUpperCase(), TypeOfEntity.class);
                if (type.isPresent()) {
                    Optional<EggItem> item = database.getOpPets().getEggManager().getEggFromType(type.get());
                    if (item.isPresent()) {
                        return item.get().getRecipes().stream().map(EggRecipe::getRecipeName).toList();
                    }
                }

            }
        }
        return new ArrayList<>();
    }
}

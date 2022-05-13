package me.opkarol.oppets.commands;

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.collections.commands.OpCommandExecutor;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.inventory.OpInventories;

public class OpPetsCommandExecutor extends OpCommandExecutor {
    public OpPetsCommandExecutor(OpMap<String, OpSubCommand> map) {
        super("oppets", map, player -> player.openInventory(new OpInventories.PetMainInventory().buildInventory()));
    }

}

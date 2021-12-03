package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.packets.PacketManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class RideCommand implements SubCommandInterface{
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)){
            return returnMessage(sender, "");
        }

        if (args.length != 0){
            return returnMessage(sender, "");
        }

        Entity entity = OpPets.getUtils().getEntityByUniqueId(OpPets.getDatabase().getCurrentPet(player.getUniqueId()).getOwnUUID());

        if (entity != null) {
            entity.addPassenger(player);
            PacketManager.injectPlayer(player);

        }
        return true;
    }

    @Override
    public String getPermission() {
        return "oppets.command.ride";
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public String getDescriptionAsString() {
        return null;
    }

    @Override
    public String getSubCommand() {
        return "ride";
    }
}

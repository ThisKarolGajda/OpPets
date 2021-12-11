package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import dir.packets.PacketManager;
import dir.pets.Pet;
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

        Pet pet = OpPets.getDatabase().getCurrentPet(player.getUniqueId());
        if (!pet.isRideable()){
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

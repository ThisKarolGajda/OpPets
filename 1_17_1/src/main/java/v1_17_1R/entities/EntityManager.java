package v1_17_1R.entities;

import dir.interfaces.EntityManagerInterface;
import dir.pets.Pet;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;
import v1_17_1R.PathfinderGoalPet_1_17_1;
import v1_17_1R.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityManager implements EntityManagerInterface {

    public void initPathfinder(@NotNull EntityAnimal e) {
        e.bP.c().clear();
        e.bP.a(1, new PathfinderGoalPet_1_17_1(e, 1.9, 15));
        e.bP.a(0, new PathfinderGoalFloat(e));
        e.bP.a(2, new PathfinderGoalLookAtPlayer(e, EntityHuman.class, 4.0F));

    }

    @Override
    public void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3) {
        EntityAnimal entity = (EntityAnimal) obj1;
        Player player = (Player) obj2;
        Pet pet = (Pet) obj3;
        Location location = player.getLocation();
        entity.setPos(location.getX(), location.getY(), location.getZ());
        entity.setHealth(20.0f);
        entity.ageLocked = true;
        entity.setBaby(true);
        entity.setCustomNameVisible(true);
        entity.setInvulnerable(true);
        entity.setGoalTarget((EntityLiving) player, EntityTargetEvent.TargetReason.CUSTOM, true);
        pet.setOwnerUUID(player.getUniqueId());
        pet.setOwnUUID(entity.getUUID());
        String version = Bukkit.getBukkitVersion().split("-")[0];

        Utils utils = new Utils();
        switch(version) {
            case "1.17":
                utils.removePathfinders((Object)null, entity.bP);
                break;
            case "1.17.1":
                utils.removePathfinders(entity.bP, entity.bQ);
                break;
            default:
                return;
        }
        initPathfinder(entity);
    }

    @Override
    public List<String> getAllowedEntities() {
        return new ArrayList<>(Arrays.asList("Axolotl", "Cat", "Chicken", "Cow", "Donkey", "Fox", "Goat", "Zoglin", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf"));
    }

}


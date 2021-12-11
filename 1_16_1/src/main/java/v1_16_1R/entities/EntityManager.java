package v1_16_1R.entities;

import dir.interfaces.EntityManagerInterface;
import net.minecraft.server.v1_16_R1.EntityAnimal;
import net.minecraft.server.v1_16_R1.EntityHuman;
import net.minecraft.server.v1_16_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R1.PathfinderGoalLookAtPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;
import dir.pets.Pet;
import v1_16_1R.PathfinderGoalPet_1_16_1;
import v1_16_1R.Utils;

import java.util.Arrays;
import java.util.List;

public class EntityManager implements EntityManagerInterface {

    public void initPathfinder(@NotNull EntityAnimal e) {
        e.goalSelector.d().close();
        e.goalSelector.a(1, new PathfinderGoalPet_1_16_1(e, 1.9, 15));
        e.goalSelector.a(2, new PathfinderGoalLookAtPlayer(e, EntityHuman.class, 4.0F));
        e.goalSelector.a(0, new PathfinderGoalFloat(e));

    }

    @Override
    public void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3) {
        EntityAnimal entity = (EntityAnimal) obj1;
        Player player = (Player) obj2;
        Pet pet = (Pet) obj3;
        Location location = player.getLocation();
        entity.setPosition(location.getX(), location.getY(), location.getZ());
        entity.setHealth(20.0f);
        entity.setAge(1);
        entity.ageLocked = true;
        entity.setCustomNameVisible(true);
        entity.setInvulnerable(true);
        entity.setGoalTarget(((CraftPlayer) player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
        pet.setOwnerUUID(player.getUniqueId());
        pet.setOwnUUID(entity.getUniqueID());
        new Utils().removePathfinders(entity.goalSelector, entity.targetSelector);
        initPathfinder(entity);
    }

    @Override
    public List<String> getAllowedEntities() {
        return Arrays.asList("Cat", "Chicken", "Cow", "Donkey", "Fox", "Zoglin", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf");
    }
}



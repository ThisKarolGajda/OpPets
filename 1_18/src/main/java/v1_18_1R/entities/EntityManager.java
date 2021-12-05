package v1_18_1R.entities;


import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.interfaces.EntityManagerInterface;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import v1_18_1R.PathfinderGoalPet_1_18_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityManager implements EntityManagerInterface {

    public void initPathfinder(@NotNull EntityAnimal e) {
        e.bR.c().clear();
        e.bR.a(1, new PathfinderGoalPet_1_18_1(e, 1.9, 15));
        e.bR.a(0, new PathfinderGoalFloat(e));
        e.bR.a(2, new PathfinderGoalLookAtPlayer(e, EntityHuman.class, 4.0F));

    }

    @Override
    public void spawnEntity(@NotNull Object obj1, @NotNull Object obj2, @NotNull Object obj3) {
        EntityAnimal entity = (EntityAnimal) obj1;
        Player player = (Player) obj2;
        Pet pet = (Pet) obj3;
        Location location = player.getLocation();
        entity.e(location.getX(), location.getY(), location.getZ());
        entity.c(20.0f);
        entity.ageLocked = true;
        entity.a(true);
        entity.n(true);
        entity.m(true);
        entity.h(((CraftPlayer) player).getHandle());
        pet.setOwnerUUID(player.getUniqueId());
        pet.setOwnUUID(entity.cm());
        OpPets.getUtils().removePathfinders(entity.bR, entity.bS);
        initPathfinder(entity);
    }

    @Override
    public List<String> getAllowedEntities() {
        return new ArrayList<>(Arrays.asList("Axolotl", "Cat", "Chicken", "Cow", "Donkey", "Fox", "Goat", "Zoglin", "Horse", "Llama", "Mule", "Mushroom_Cow", "Ocelot", "Panda", "Parrot", "Pig", "PolarBear", "Rabbit", "Sheep", "Turtle", "Wolf"));
    }

}



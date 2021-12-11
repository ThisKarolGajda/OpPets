package v1_17_1R;

import dir.interfaces.BabyEntityCreatorInterface;
import dir.pets.Database;
import dir.pets.Pet;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import v1_17_1R.entities.*;

public class BabyEntityCreator implements BabyEntityCreatorInterface {

    @Override
    public void spawnMiniPet(@NotNull Pet pet, @NotNull Player player){
        pet.setOwnerUUID(player.getUniqueId());
        ServerLevel world = ((CraftWorld) player.getWorld()).getHandle();
        Animal entityAnimal = getPet(pet, player);
        entityAnimal.setCustomName(Component.nullToEmpty(pet.getPetName()));
        entityAnimal.setGlowingTag(pet.isGlowing());
        pet.setOwnUUID(entityAnimal.getUUID());
        Database.getDatabase().setCurrentPet(player.getUniqueId(), pet);
        world.addFreshEntity(entityAnimal);
        int id = entityAnimal.getId();
        Database.getDatabase().addIdPet(pet.getOwnUUID(), id);
        if (pet.isVisibleToOthers()) return;
        new Utils().hideEntityFromServer(player, id);
    }

    public Animal getPet(@NotNull Pet pet, Player player){
        Animal entityAnimal;
        switch (pet.getPetType()){
            case AXOLOTL -> entityAnimal = new Axolotl(player.getLocation(), player, pet);
            case CAT -> entityAnimal = new Cat(player.getLocation(), player, pet);
            case CHICKEN -> entityAnimal = new Chicken(player.getLocation(), player, pet);
            case COW -> entityAnimal = new Cow(player.getLocation(), player, pet);
            case DONKEY -> entityAnimal = new Donkey(player.getLocation(), player, pet);
            case FOX -> entityAnimal = new Fox(player.getLocation(), player, pet);
            case GOAT -> entityAnimal = new Goat(player.getLocation(), player, pet);
            case HORSE -> entityAnimal = new Horse(player.getLocation(), player, pet);
            case LLAMA -> entityAnimal = new Llama(player.getLocation(), player, pet);
            case MULE ->  entityAnimal = new Mule(player.getLocation(), player, pet);
            case MUSHROOM_COW -> entityAnimal = new MushroomCow(player.getLocation(), player, pet);
            case OCELOT -> entityAnimal = new Ocelot(player.getLocation(), player, pet);
            case PANDA -> entityAnimal = new Panda(player.getLocation(), player, pet);
            case PARROT -> entityAnimal = new Parrot(player.getLocation(), player, pet);
            case PIG -> entityAnimal = new Pig(player.getLocation(), player, pet);
            case POLAR_BEAR -> entityAnimal = new PolarBear(player.getLocation(), player, pet);
            case RABBIT -> entityAnimal = new Rabbit(player.getLocation(), player, pet);
            case SHEEP -> entityAnimal = new Sheep(player.getLocation(), player, pet);
            case TURTLE -> entityAnimal = new Turtle(player.getLocation(), player, pet);
            case WOLF -> entityAnimal = new Wolf(player.getLocation(), player, pet);
            default -> throw new IllegalStateException("Unexpected value: " + pet.getPetType());
        }
        return entityAnimal;
    }

}

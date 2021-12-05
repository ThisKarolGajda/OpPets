package v1_16_1R;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.interfaces.BabyEntityCreatorInterface;
import me.opkarol.oppets.pets.Pet;
import net.minecraft.server.v1_16_R1.ChatMessage;
import net.minecraft.server.v1_16_R1.EntityAgeable;
import net.minecraft.server.v1_16_R1.WorldServer;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import v1_16_1R.entities.*;

public class BabyEntityCreator implements BabyEntityCreatorInterface {

    @Override
    public void spawnMiniPet(@NotNull Pet pet, @NotNull Player player){
        pet.setOwnerUUID(player.getUniqueId());
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
        EntityAgeable entityAnimal = getPet(pet, player);
        assert pet.getPetName() != null;
        entityAnimal.setCustomName(new ChatMessage(pet.getPetName()));
        entityAnimal.glowing = pet.isGlowing();
        pet.setOwnUUID(entityAnimal.getUniqueID());
        OpPets.getDatabase().setCurrentPet(player.getUniqueId(), pet);
        world.addEntity(entityAnimal);
        int id = entityAnimal.getId();
        OpPets.getDatabase().addIdPet(pet.getOwnUUID(), id);
        if (pet.isVisibleToOthers()) return;
        OpPets.getUtils().hideEntityFromServer(player, id);
    }

    public EntityAgeable getPet(@NotNull Pet pet, Player player){
        EntityAgeable entityAnimal;
        switch (pet.getPetType()){
            case CAT -> entityAnimal = new Cat(player.getLocation(), player, pet);
            case CHICKEN -> entityAnimal = new Chicken(player.getLocation(), player, pet);
            case COW -> entityAnimal = new Cow(player.getLocation(), player, pet);
            case DONKEY -> entityAnimal = new Donkey(player.getLocation(), player, pet);
            case FOX -> entityAnimal = new Fox(player.getLocation(), player, pet);
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


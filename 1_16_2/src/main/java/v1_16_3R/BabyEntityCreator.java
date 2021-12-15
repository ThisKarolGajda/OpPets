package v1_16_3R;

import dir.interfaces.BabyEntityCreatorInterface;
import net.minecraft.server.v1_16_R2.ChatMessage;
import net.minecraft.server.v1_16_R2.EntityAgeable;
import net.minecraft.server.v1_16_R2.WorldServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import dir.pets.Database;
import dir.pets.Pet;
import v1_16_3R.entities.*;

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
        Database.getDatabase().setCurrentPet(player.getUniqueId(), pet);
        world.addEntity(entityAnimal);
        int id = entityAnimal.getId();
        Database.getDatabase().addIdPet(pet.getOwnUUID(), id);
        if (pet.isVisibleToOthers()) return;
        new Utils().hideEntityFromServer(player, id);
    }

    public EntityAgeable getPet(@NotNull Pet pet, Player player){
        EntityAgeable entityAnimal;
        switch (pet.getPetType()){
            case CAT : {
                entityAnimal = new Cat(player.getLocation(), player, pet);
                break;
            }
            case CHICKEN : {
                entityAnimal = new Chicken(player.getLocation(), player, pet);
                break;
            }
            case COW : {
                entityAnimal = new Cow(player.getLocation(), player, pet);
                break;
            }
            case DONKEY : {
                entityAnimal = new Donkey(player.getLocation(), player, pet);
                break;
            }
            case FOX : {
                entityAnimal = new Fox(player.getLocation(), player, pet);
                break;
            }
            case HORSE : {
                entityAnimal = new Horse(player.getLocation(), player, pet);
                break;
            }
            case LLAMA : {
                entityAnimal = new Llama(player.getLocation(), player, pet);
                break;
            }
            case MULE :  {
                entityAnimal = new Mule(player.getLocation(), player, pet);
                break;
            }
            case MUSHROOM_COW : {
                entityAnimal = new MushroomCow(player.getLocation(), player, pet);
                break;
            }
            case OCELOT : {
                entityAnimal = new Ocelot(player.getLocation(), player, pet);
                break;
            }
            case PANDA : {
                entityAnimal = new Panda(player.getLocation(), player, pet);
                break;
            }
            case PARROT : {
                entityAnimal = new Parrot(player.getLocation(), player, pet);
                break;
            }
            case PIG : {
                entityAnimal = new Pig(player.getLocation(), player, pet);
                break;
            }
            case POLAR_BEAR : {
                entityAnimal = new PolarBear(player.getLocation(), player, pet);
                break;
            }
            case RABBIT : {
                entityAnimal = new Rabbit(player.getLocation(), player, pet);
                break;
            }
            case SHEEP : {
                entityAnimal = new Sheep(player.getLocation(), player, pet);
                break;
            }
            case TURTLE : {
                entityAnimal = new Turtle(player.getLocation(), player, pet);
                break;
            }
            case WOLF : {
                entityAnimal = new Wolf(player.getLocation(), player, pet);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + pet.getPetType());
        }
        return entityAnimal;
    }


}


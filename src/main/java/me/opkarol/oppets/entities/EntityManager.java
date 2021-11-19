package me.opkarol.oppets.entities;

import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityAnimal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityManager {
    
    public EntityManager(@NotNull EntityAnimal entity, @NotNull Player player, @NotNull Pet pet){
        Location location = player.getLocation();
        entity.setPosition(location.getX(), location.getY(), location.getZ());
        entity.setHealth(20.0f);
        entity.ageLocked = true;
        entity.setBaby(true);
        entity.setCustomNameVisible(true);
        entity.setInvulnerable(true);
        entity.setGoalTarget(((CraftPlayer)player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
        pet.setOwnerUUID(player.getUniqueId());
        pet.setOwnUUID(entity.getUniqueID());
    }
    
    public enum ENTITY_TYPES{
        AXOLOTL, CAT, CHICKEN, COW, DONKEY, FOX, GOAT, HORSE, LLAMA, MULE, MUSHROOM_COW, OCELOT, PANDA, PARROT, PIG, POLAR_BEAR, RABBIT, SHEEP, TURTLE, WOLF
    }

    @Contract(pure = true)
    public @Nullable EntityTypes<? extends EntityAnimal> getEntityTypeFromEnum(@NotNull ENTITY_TYPES type){
        switch (type){
            case COW -> {
                return EntityTypes.n;
            }
            case AXOLOTL -> {
                return EntityTypes.e;
            }
            case CAT -> {
                return EntityTypes.j;
            }
            case CHICKEN -> {
                return EntityTypes.l;
            }
            case DONKEY -> {
                return EntityTypes.q;
            }
            case FOX -> {
                return EntityTypes.E;
            }
            case GOAT -> {
                return EntityTypes.J;
            }
            case HORSE -> {
                return EntityTypes.M;
            }
            case LLAMA -> {
                return EntityTypes.V;
            }
            case MULE -> {
                return EntityTypes.ag;
            }
            case MUSHROOM_COW -> {
                return EntityTypes.ah;
            }
            case OCELOT -> {
                return EntityTypes.ai;
            }
            case PANDA -> {
                return EntityTypes.ak;
            }
            case PARROT -> {
                return EntityTypes.al;
            }
            case PIG -> {
                return EntityTypes.an;
            }
            case POLAR_BEAR -> {
                return EntityTypes.ar;
            }
            case RABBIT -> {
                return EntityTypes.au;
            }
            case SHEEP -> {
                return EntityTypes.ax;
            }
            case TURTLE -> {
                return EntityTypes.aT;
            }
            case WOLF -> {
                return EntityTypes.bc;
            }

        }
        return null;
    }
}



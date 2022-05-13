package me.opkarol.oppets.entities.manager;

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.entities.IEntityPet;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

public abstract class EntityManager implements IEntityManager {
    public OpMap<TypeOfEntity, Constructor<?>> getMap() {
        return map;
    }

    private OpMap<TypeOfEntity, Constructor<?>> map;

    public void setMap(OpMap<TypeOfEntity, Constructor<?>> map) {
        this.map = map;
    }

    public Optional<IEntityPet> getPetFromType(Player player, @NotNull Pet pet) {
        TypeOfEntity type = pet.getPetType();
        Optional<Constructor<?>> optional = getMap().getByKey(type);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        IEntityPet iPet = null;
        Constructor<?> constructor = optional.get();
        try {
            iPet = (IEntityPet) constructor.newInstance(player);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(iPet);
    }

    public void setPrivate(@NotNull IEntityPet iPet, @NotNull Pet pet, Player player, @NotNull Database database) {
        int id = iPet.getEntityId();
        database.getDatabase().addIdPet(pet.getOwnUUID(), id);
        if (!pet.isVisibleToOthers()) {
            database.getUtils().hideEntityFromServer(player, id);
        }
    }

    public void setPetIDInDatabase(@NotNull Player player, UUID uuid, @NotNull Pet pet, @NotNull Database database) {
        pet.setOwnUUID(uuid);
        database.getDatabase().setCurrentPet(player.getUniqueId(), pet);
    }

    public abstract void setAge(IEntityPet iPet, Pet pet);
}

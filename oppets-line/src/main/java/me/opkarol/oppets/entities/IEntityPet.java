package me.opkarol.oppets.entities;

import me.opkarol.oppets.pets.TypeOfEntity;

public interface IEntityPet {
    Object getEntity();

    Object getBukkitCraftEntity();

    TypeOfEntity getTypeOfEntity();

    int getEntityId();

    boolean canBeAgeable();
}

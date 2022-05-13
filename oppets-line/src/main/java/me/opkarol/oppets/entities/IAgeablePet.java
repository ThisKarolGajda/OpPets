package me.opkarol.oppets.entities;

public interface IAgeablePet extends IEntityPet {

    @Override
    default boolean canBeAgeable() {
        return true;
    }
}

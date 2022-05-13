package me.opkarol.oppets.inventory;

import me.opkarol.oppets.pets.Pet;

public abstract class PetInventoryAccessor extends InventoryAccessor {
    private Pet pet;

    public PetInventoryAccessor(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}

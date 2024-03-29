package me.opkarol.oppets.inventory.accessors;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.Pet;

public abstract class PetInventoryAccessor extends InventoryAccessor {
    private Pet pet;

    public PetInventoryAccessor(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet == null ? new Pet() : pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}

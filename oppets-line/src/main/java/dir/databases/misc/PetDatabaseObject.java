package dir.databases.misc;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

public class PetDatabaseObject {
    private final boolean abilityEnabled;
    private final float entitySpeed;
    /**
     * Is a float value after which a^2 from player distance, entity will be teleported.
     * Example: entityDistance = 15, a^2 = 225, entity will be teleported after 225 blocks distance.
     */
    private final float entityDistance;

    public PetDatabaseObject(boolean a, float b, float c) {
        this.abilityEnabled = a;
        this.entitySpeed = b;
        this.entityDistance = c;
    }

    public float getEntitySpeed() {
        return entitySpeed;
    }

    public boolean isAbilityEnabled() {
        return abilityEnabled;
    }

    public float getEntityDistance() {
        return entityDistance;
    }
}

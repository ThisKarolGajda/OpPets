package me.opkarol.oppets.pets.id;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.external.APIDatabase;

import java.io.Serializable;
import java.util.UUID;

public class UniquePet implements Serializable {
    private int databaseId;
    private UUID ownUUID;
    private UUID ownerUUID;

    public UniquePet() {
        createNewDatabaseId();
    }

    public UniquePet(int databaseId, UUID ownUUID, UUID ownerUUID) {
        this.databaseId = databaseId;
        this.ownerUUID = ownerUUID;
        this.ownUUID = ownUUID;
    }

    public String getStringDatabaseId() {
        return String.valueOf(databaseId);
    }

    public int getDatabaseId() {
        return databaseId;
    }

    private void createNewDatabaseId() {
        APIDatabase database = APIDatabase.getInstance();
        databaseId = database.getLastID() + 1;
        database.setLastID(databaseId);
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public UniquePet setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        return this;
    }

    public UUID getOwnUUID() {
        return ownUUID;
    }

    public UniquePet setOwnUUID(UUID ownUUID) {
        this.ownUUID = ownUUID;
        return this;
    }
}

package me.opkarol.oppets.uuid;

import me.opkarol.oppets.databases.APIDatabase;

import java.io.Serializable;

public class PetUUID implements Serializable {
    private int id;

    public PetUUID() {
        generateNewUUID();
    }

    public PetUUID(int id) {
        this.id = id;
    }

    public String getStringID() {
        return String.valueOf(id);
    }

    public int getID() {
        return id;
    }

    private void generateNewUUID() {
        APIDatabase database = APIDatabase.getInstance();
        id = database.getLastID() + 1;
        database.setLastID(id);
    }

}

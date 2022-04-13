package me.opkarol.oppets.uuid;

import me.opkarol.oppets.databases.APIDatabase;

/**
 * The type Pet uuid.
 */
public class PetUUID {
    /**
     * The Database.
     */
    private final APIDatabase database = APIDatabase.getInstance();
    /**
     * The Id.
     */
    private int id;

    /**
     * Instantiates a new Pet uuid.
     */
    public PetUUID() {
        generateNewUUID();
    }

    /**
     * Instantiates a new Pet uuid.
     *
     * @param id the id
     */
    public PetUUID(int id) {
        this.id = id;
    }

    /**
     * Gets string id.
     *
     * @return the string id
     */
    public String getStringID() {
        return String.valueOf(id);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getID() {
        return id;
    }

    /**
     * Generate new uuid.
     */
    private void generateNewUUID() {
        id = database.getLastID() + 1;
        database.setLastID(id);
    }

}

package dir.pets;

import org.bukkit.plugin.Plugin;

public class Database {
    private static Plugin instance;
    private static MiniPetsDatabase database;

    public Database() {
        setDatabase(new MiniPetsDatabase());
    }

    public static MiniPetsDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(MiniPetsDatabase database) {
        Database.database = database;
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static void setInstance(Plugin instance) {
        Database.instance = instance;
        new Database();
    }
}

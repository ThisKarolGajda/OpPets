package dir.databases;

import dir.interfaces.DatabaseInterface;
import dir.interfaces.UtilsInterface;
import org.bukkit.plugin.Plugin;

public class Database {
    private static Plugin instance;
    private static DatabaseInterface database;
    private static UtilsInterface utils;

    public Database() {
        boolean mySQLEnabled = instance.getConfig().getBoolean("mysql.enabled");
        if (mySQLEnabled) {
            MySQL.setupMySQL();
            setDatabase(new MySQLMiniPetsDatabase());
        } else {
            setDatabase(new MiniPetsDatabase());
        }
    }

    public static DatabaseInterface getDatabase() {
        return database;
    }

    public static void setDatabase(DatabaseInterface database) {
        Database.database = database;
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static void setInstance(Plugin instance) {
        Database.instance = instance;
        new Database();
    }

    public static UtilsInterface getUtils() {
        return utils;
    }

    public static void setUtils(UtilsInterface utils) {
        Database.utils = utils;
    }
}

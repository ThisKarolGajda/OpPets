package dir.databases;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySQL {
    private static Connection con;

    public static void setupMySQL() {
        connect();

        if (!isConnected()) {
            try {
                throw new Exception("Database error.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        createTable(table, "id VARCHAR (36), object TEXT");

    }

    public static boolean createTable(String table, String columns) {
        return update("CREATE TABLE IF NOT EXISTS " + table + " (" + columns + ");");
    }

    public static Connection getConnection() {
        return con;
    }

    public static boolean deleteData(String column, String logic_gate, String data, String table) {
        if (data != null) {
            data = "'" + data + "'";
        }

        return MySQL.update("DELETE FROM " + table + " WHERE " + column + logic_gate + data + ";");
    }

    public static boolean update(String command) {
        if (command == null) {
            return false;
        } else {
            boolean result = false;
            connect();

            try {
                if (con != null) {
                    Statement st = con.createStatement();
                    st.executeUpdate(command);
                    st.close();
                    result = true;
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            disconnect();
            return result;
        }
    }

    public static void disconnect() {
        try {
            if (isConnected()) {
                con.close();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        con = null;
    }

    public static boolean isConnected() {
        if (con != null) {
            try {
                return !con.isClosed();
            } catch (Exception var1) {
                var1.printStackTrace();
            }
        }

        return false;
    }

    public static void connect() {
        setConnection();

    }

    private static final FileConfiguration config = Database.getInstance().getConfig();
    private static final String port = config.getString("mysql.port");
    private static final String host = config.getString("mysql.host");
    private static final String user = config.getString("mysql.user");
    private static final String password = config.getString("mysql.password");
    private static final String database = config.getString("mysql.database");
    private static final String driver = config.getString("mysql.driver");
    public static final String table = config.getString("mysql.table");



    public static void setConnection() {
        if (host != null && user != null && password != null && database != null) {
            disconnect();

            try {
                assert driver != null;
                if (driver.length() != 0) {
                    con = DriverManager.getConnection("jdbc:" + driver + "://" + host + ":" + port + "/" + database, user, password);
                }
            } catch (Exception var7) {
                var7.printStackTrace();
            }

        }
    }

}

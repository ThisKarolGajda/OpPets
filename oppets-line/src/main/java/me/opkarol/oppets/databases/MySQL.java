package me.opkarol.oppets.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySQL {
    public static String table;
    private static Connection con;
    private static String port;
    private static String host;
    private static String user;
    private static String password;
    private static String database;
    private static String driver;

    public static void setupMySQL() {
        FileConfiguration config = Database.getInstance().getConfig();
        port = config.getString("mysql.port");
        host = config.getString("mysql.host");
        user = config.getString("mysql.login");
        password = config.getString("mysql.password");
        database = config.getString("mysql.database");
        driver = config.getString("mysql.driver");
        table = config.getString("mysql.table");

        update("CREATE TABLE IF NOT EXISTS " + table + " (id VARCHAR (36), object TEXT);");

    }

    public static void connect() {
        new MySQL().setConnection();
    }

    public static Connection getConnection() {
        return con;
    }

    public static boolean update(String command) {
        if (command == null) {
            return false;
        } else {
            final boolean[] result = {false};
            connect();

            try {
                if (con != null) {
                    Statement st = con.createStatement();
                    st.executeUpdate(command);
                    st.close();
                    result[0] = true;
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            disconnect();
            return result[0];
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

    }

    public static boolean isConnected() {
        boolean i = false;

        if (con != null) {
            try {
                i = !con.isClosed();
            } catch (Exception var1) {
                var1.printStackTrace();
            }
        }

        return i;
    }

    public void setConnection() {
        if (!isConnected()) {
            try {
                if (driver.length() != 0) {
                    String url = "jdbc:" + driver + "://" + host + ":" + port + "/" + database;
                    con = DriverManager.getConnection(url, user, password);
                }

            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }
    }

}

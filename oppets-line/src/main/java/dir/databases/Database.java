package dir.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.interfaces.DatabaseInterface;
import dir.interfaces.UtilsInterface;
import org.bukkit.plugin.Plugin;

public class Database {
    private static Plugin instance;
    private static DatabaseInterface database;
    private static UtilsInterface utils;
    public static boolean mySQLAccess = false;

    public Database() {
        // mySQLAccess = instance.getConfig().getBoolean("mysql.enabled");
        //TODO: change to mySQLEnabled when it will be functional
        if (mySQLAccess) {
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

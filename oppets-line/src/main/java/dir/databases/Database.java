package dir.databases;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.interfaces.IDatabase;
import dir.interfaces.IOpPets;
import dir.interfaces.IUtils;
import org.bukkit.plugin.Plugin;

public class Database {
    public static boolean mySQLAccess = false;
    private static Plugin instance;
    private static IDatabase database;
    private static IUtils utils;
    private static PetsDatabase petsDatabase;
    private static IOpPets opPets;

    public static void setupDatabase() {
        // mySQLAccess = instance.getConfig().getBoolean("mysql.enabled");
        //TODO: change to mySQLEnabled when it will be functional
        if (mySQLAccess) {
            MySQL.setupMySQL();
            setDatabase(new MySQLMiniPetsDatabase());
        } else {
            setDatabase(new MiniPetsDatabase());
        }
        petsDatabase = new PetsDatabase();

    }

    public static IDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(IDatabase database) {
        Database.database = database;
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static void setInstance(Plugin instance, IOpPets oppets) {
        Database.instance = instance;
        opPets = oppets;
        setupDatabase();
    }

    public static IUtils getUtils() {
        return utils;
    }

    public static void setUtils(IUtils utils) {
        Database.utils = utils;
    }

    public static PetsDatabase getPetsDatabase() {
        return petsDatabase;
    }

    public static IOpPets getOpPets() {
        return opPets;
    }
}

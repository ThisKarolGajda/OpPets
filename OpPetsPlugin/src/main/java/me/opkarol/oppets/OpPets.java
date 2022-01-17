package me.opkarol.oppets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.interfaces.BabyEntityCreatorInterface;
import dir.interfaces.DatabaseInterface;
import dir.interfaces.EntityManagerInterface;
import dir.interfaces.UtilsInterface;
import me.opkarol.oppets.inventories.InventoryManager;
import me.opkarol.oppets.skills.SkillDatabase;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpPets extends JavaPlugin {
    private static OpPets opPets;
    private static PetPluginController controller;
    private static InventoryManager inventoryManager;
    private static BabyEntityCreatorInterface creator;
    private static EntityManagerInterface entityManager;
    private static UtilsInterface utils;
    //private static OpPetsAPI api;
    private static SkillDatabase skillDatabase;

    @Override
    public void onEnable() {
        opPets = this;
        Database.setInstance(opPets);
        inventoryManager = new InventoryManager();
        controller = new PetPluginController(opPets);
        this.setEnabled(getController().setupVersion());
        skillDatabase = new SkillDatabase();
        //api = new OpPetsAPI();

    }

    @Override
    public void onDisable() {
        getController().saveFiles();
        opPets = null;
        creator = null;
        controller = null;
        skillDatabase = null;
        inventoryManager = null;
    }

    public static EntityManagerInterface getEntityManager() {
        return entityManager;
    }

    public static void setEntityManager(EntityManagerInterface entityManager) {
        OpPets.entityManager = entityManager;
    }

    public static UtilsInterface getUtils() {
        return utils;
    }

    public static void setUtils(UtilsInterface utils) {
        OpPets.utils = utils;
    }

    //public static OpPetsAPI getAPI() {
        //return api;
    //}

    public static PetPluginController getController() {
        return controller;
    }

    public static OpPets getInstance() {
        return opPets;
    }

    public static BabyEntityCreatorInterface getCreator() {
        return creator;
    }

    public static void setCreator(BabyEntityCreatorInterface creator2) {
        OpPets.creator = creator2;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static DatabaseInterface getDatabase() {
        return Database.getDatabase();
    }

    public static SkillDatabase getSkillDatabase() {
        return skillDatabase;
    }

    public void disablePlugin(String reason){
        this.getLogger().info(reason);
        this.setEnabled(false);
    }

}

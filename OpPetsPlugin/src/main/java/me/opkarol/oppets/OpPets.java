package me.opkarol.oppets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.interfaces.IBabyEntityCreator;
import dir.interfaces.IDatabase;
import dir.interfaces.IEntityManager;
import dir.interfaces.IUtils;
import dir.prestiges.PrestigeManager;
import me.opkarol.oppets.skills.SkillDatabase;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * OpPets main Class which extends JavaPlugin.
 * Used as a getter and setter for all classes objects.
 *
 * Overrides main methods of JavaPlugin - onEnable and onDisable
 * creating custom methods which sets private static objects.
 *
 * This class also contains disablePlugin method
 * which can be used to disable OpPets functionality.
 */

public final class OpPets extends JavaPlugin {
    private static OpPets opPets;
    private static PetPluginController controller;
    private static IBabyEntityCreator creator;
    private static IEntityManager entityManager;
    private static IUtils utils;
    private static SkillDatabase skillDatabase;
    private static PrestigeManager prestigeManager;
    private Messages messages;
    //private static Economy economy;

    public static IEntityManager getEntityManager() {
        return entityManager;
    }

    public static void setEntityManager(IEntityManager entityManager) {
        OpPets.entityManager = entityManager;
    }

    public static IUtils getUtils() {
        return utils;
    }

    public static void setUtils(IUtils utils) {
        OpPets.utils = utils;
    }

    public static PetPluginController getController() {
        return controller;
    }

    public static OpPets getInstance() {
        return opPets;
    }

    public static IBabyEntityCreator getCreator() {
        return creator;
    }

    public static void setCreator(IBabyEntityCreator creator2) {
        OpPets.creator = creator2;
    }

    public static IDatabase getDatabase() {
        return Database.getDatabase();
    }

    public static SkillDatabase getSkillDatabase() {
        return skillDatabase;
    }

    public static PrestigeManager getPrestigeManager() {
        return prestigeManager;
    }

    @Override
    public void onEnable() {
        opPets = this;
        Database.setInstance(opPets);
        controller = new PetPluginController(opPets);
        skillDatabase = new SkillDatabase();
        this.setEnabled(getController().setupVersion());
        messages = new Messages().onEnable();
        prestigeManager = new PrestigeManager();
        //economy = controller.setupEconomy();

    }

    @Override
    public void onDisable() {
        getController().saveFiles();
        opPets = null;
        creator = null;
        controller = null;
        skillDatabase = null;
        entityManager = null;
        utils = null;
        messages = null;
        prestigeManager = null;
        //economy = null;
    }

    //public static Economy getEconomy() {
    //return economy;
    //}

    /**
     * Method that informs a server with provided reason about
     * OpPets`s shutdown and logs it as a class method.
     *
     * @param reason string value is a message shown in the server's console
     */
    public void disablePlugin(String reason) {
        this.getLogger().info(reason);
        this.setEnabled(false);
    }

}

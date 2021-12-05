package me.opkarol.oppets;

import api.OpPetsAPI;
import me.opkarol.oppets.interfaces.EntityManagerInterface;
import me.opkarol.oppets.inventories.InventoryManager;
import me.opkarol.oppets.interfaces.BabyEntityCreatorInterface;
import me.opkarol.oppets.pets.MiniPetsDatabase;
import me.opkarol.oppets.interfaces.UtilsInterface;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpPets extends JavaPlugin {
    private static OpPets opPets;
    private static PetPluginController controller;
    private static MiniPetsDatabase database;
    private static OpPetsAPI api;
    private static InventoryManager inventoryManager;
    private static BabyEntityCreatorInterface creator;
    private static EntityManagerInterface entityManager;
    private static UtilsInterface utils;

    public static EntityManagerInterface getEntityManager() {
        return entityManager;
    }

    public static void setEntityManager(EntityManagerInterface entityManager2) {
        OpPets.entityManager = entityManager2;
    }

    public static void setCreator(BabyEntityCreatorInterface creator2) {
        OpPets.creator = creator2;
    }

    public static UtilsInterface getUtils() {
        return utils;
    }

    public static void setUtils(UtilsInterface utils) {
        OpPets.utils = utils;
    }

    @Override
    public void onEnable() {
        opPets = this;
        database = new MiniPetsDatabase();
        inventoryManager = new InventoryManager();
        api = new OpPetsAPI();
        controller = new PetPluginController(opPets);
        if (!getController().setupVersion()){
            this.setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
        getController().saveFiles();
        opPets = null;
        database = null;
        creator = null;
        controller = null;
    }

    public static PetPluginController getController() {
        return controller;
    }

    public static OpPets getInstance() {
        return opPets;
    }

    public static MiniPetsDatabase getDatabase() {
        return database;
    }

    public static BabyEntityCreatorInterface getCreator() {
        return creator;
    }

    public static OpPetsAPI getAPI() {
        return api;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}

package me.opkarol.oppets;

import api.OpPetsAPI;
import dir.interfaces.BabyEntityCreatorInterface;
import dir.interfaces.EntityManagerInterface;
import dir.interfaces.UtilsInterface;
import me.opkarol.oppets.inventories.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;
import dir.pets.Database;
import dir.pets.MiniPetsDatabase;

public final class OpPets extends JavaPlugin {
    private static OpPets opPets;
    private static PetPluginController controller;
    private static InventoryManager inventoryManager;
    private static BabyEntityCreatorInterface creator;
    private static EntityManagerInterface entityManager;
    private static UtilsInterface utils;
    private static OpPetsAPI api;

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

    public static OpPetsAPI getApi() {
        return api;
    }

    @Override
    public void onEnable() {
        opPets = this;
        Database.setInstance(opPets);
        inventoryManager = new InventoryManager();
        controller = new PetPluginController(opPets);
        if (!getController().setupVersion()){
            this.setEnabled(false);
        }
        api = new OpPetsAPI();
    }

    @Override
    public void onDisable() {
        getController().saveFiles();
        opPets = null;
        creator = null;
        controller = null;
    }

    public static PetPluginController getController() {
        return controller;
    }

    public static OpPets getInstance() {
        return opPets;
    }

    public static BabyEntityCreatorInterface getCreator() {
        return creator;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static MiniPetsDatabase getDatabase(){
        return Database.getDatabase();
    }


}

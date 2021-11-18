package me.opkarol.oppets;

import me.opkarol.oppets.commands.MainCommand;
import me.opkarol.oppets.listeners.PlayerInteract;
import me.opkarol.oppets.listeners.PlayerJoin;
import me.opkarol.oppets.listeners.PlayerLeaves;
import me.opkarol.oppets.listeners.PlayerSteerVehicle;
import me.opkarol.oppets.misc.Metrics;
import me.opkarol.oppets.packets.PacketManager;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.EntityUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;

public class PetPluginController {
    private final OpPets instance;
    private final String localPath = OpPets.getInstance().getDataFolder().getAbsolutePath();

    public PetPluginController(OpPets opPets){
        this.instance = opPets;
        init();
    }

    public OpPets getInstance() {
        return instance;
    }

    public void init(){
        getInstance().saveDefaultConfig();
        loadFiles();
        OpPets.getDatabase().startLogic();
        registerEvents();
        registerCommands();
        setupInventories();
        bStatsActivation();
    }

    public void bStatsActivation(){
        int pluginId = 13211;
        Metrics metrics = new Metrics(getInstance(), pluginId);

        metrics.addCustomChart(new Metrics.SingleLineChart("pets", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                final int[] i = {0};
                OpPets.getDatabase().getPetsMap().keySet().forEach(uuid -> i[0] += OpPets.getDatabase().getPetList(uuid).size());
                return 0;
            }
        }));

    }

    public void saveFiles(){
        File file = new File(localPath + "/PetsMap.db");
        File secondFile = new File(localPath + "/ActivePetMap.db");
        try {
            file.createNewFile();
            secondFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileManager.saveObject(localPath + "/PetsMap.db", OpPets.getDatabase().getPetsMap());
        FileManager.saveObject(localPath + "/ActivePetMap.db", OpPets.getDatabase().getActivePetMap());
        removeAllPets();

    }

    public void loadFiles(){
        OpPets.getDatabase().setPetsMap((HashMap<UUID, List<Pet>>) FileManager.loadObject(localPath + "/PetsMap.db"));
        OpPets.getDatabase().setActivePetMap((HashMap<UUID, Pet>) FileManager.loadObject(localPath + "/ActivePetMap.db"));

    }

    public void registerEvents(){
        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(new PlayerJoin(), instance);
        manager.registerEvents(new PlayerLeaves(), instance);
        manager.registerEvents(new PlayerInteract(), instance);
        manager.registerEvents(new PlayerSteerVehicle(), instance);

    }

    public void registerCommands(){
        Objects.requireNonNull(getInstance().getCommand("oppets")).setExecutor(new MainCommand());
        Objects.requireNonNull(getInstance().getCommand("oppets")).setTabCompleter(new MainCommand());
    }

    public void removeAllPets(){
        for (UUID uuid : OpPets.getDatabase().getActivePetMap().keySet()){
            if (OpPets.getDatabase().getCurrentPet(uuid).getOwnUUID() != null) {
                Entity entity = EntityUtils.getEntityByUniqueId(OpPets.getDatabase().getCurrentPet(uuid).getOwnUUID());
                if (entity != null) {
                    ((LivingEntity) EntityUtils.getEntityByUniqueId(OpPets.getDatabase().getCurrentPet(uuid).getOwnUUID())).setHealth(0);
                }
            }
        }
    }


    public void setupInventories(){
        OpPets.getInventoryManager().setupList();

    }
}

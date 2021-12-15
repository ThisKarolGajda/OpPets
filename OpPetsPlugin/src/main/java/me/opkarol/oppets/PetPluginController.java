package me.opkarol.oppets;

import dir.interfaces.PacketPlayInSteerVehicleEvent;
import me.opkarol.oppets.commands.MainCommand;
import me.opkarol.oppets.listeners.PlayerInteract;
import me.opkarol.oppets.listeners.PlayerJoin;
import me.opkarol.oppets.listeners.PlayerLeaves;
import me.opkarol.oppets.misc.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.PluginManager;
import dir.packets.PacketManager;
import dir.pets.Database;
import dir.pets.Pet;
import v1_16_1R.BabyEntityCreator;
import v1_16_1R.PacketPlayInSteerVehicleEvent_v1_16_1;
import v1_16_1R.PlayerSteerVehicleEvent_v1_16_1;
import v1_16_1R.Utils;
import v1_16_1R.entities.EntityManager;
import v1_16_3R.PacketPlayInSteerVehicleEvent_v1_16_3;
import v1_16_3R.PlayerSteerVehicleEvent_v1_16_3;
import v1_16_5R.PacketPlayInSteerVehicleEvent_v1_16_5;
import v1_16_5R.PlayerSteerVehicleEvent_v1_16_5;
import v1_17_1R.PacketPlayInSteerVehicleEvent_v1_17_1;
import v1_17_1R.PlayerSteerVehicleEvent_v1_17_1;
import v1_18_1R.PacketPlayInSteerVehicleEvent_v1_18_1;
import v1_18_1R.PlayerSteerVehicleEvent_v1_18_1;

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
        Database.getDatabase().startLogic();
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
                Database.getDatabase().getPetsMap().keySet().forEach(uuid -> i[0] += Database.getDatabase().getPetList(uuid).size());
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
        FileManager.saveObject(localPath + "/PetsMap.db", Database.getDatabase().getPetsMap());
        FileManager.saveObject(localPath + "/ActivePetMap.db", Database.getDatabase().getActivePetMap());
        removeAllPets();

    }

    public void loadFiles(){
        Database.getDatabase().setPetsMap((HashMap<UUID, List<Pet>>) FileManager.loadObject(localPath + "/PetsMap.db"));
        Database.getDatabase().setActivePetMap((HashMap<UUID, Pet>) FileManager.loadObject(localPath + "/ActivePetMap.db"));

    }

    public void registerEvents(){
        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(new PlayerJoin(), instance);
        manager.registerEvents(new PlayerLeaves(), instance);
        manager.registerEvents(new PlayerInteract(), instance);

    }

    public void registerCommands(){
        Objects.requireNonNull(getInstance().getCommand("oppets")).setExecutor(new MainCommand());
        Objects.requireNonNull(getInstance().getCommand("oppets")).setTabCompleter(new MainCommand());
    }

    public void removeAllPets(){
        for (UUID uuid : Database.getDatabase().getActivePetMap().keySet()){
            if (Database.getDatabase().getCurrentPet(uuid).getOwnUUID() != null) {
                Entity entity = OpPets.getUtils().getEntityByUniqueId(Database.getDatabase().getCurrentPet(uuid).getOwnUUID());
                if (entity != null) {
                    ((LivingEntity) OpPets.getUtils().getEntityByUniqueId(Database.getDatabase().getCurrentPet(uuid).getOwnUUID())).setHealth(0);
                }
            }
        }
    }

    public void setupInventories(){
        OpPets.getInventoryManager().setupList();

    }

    public boolean setupVersion() {
        String version;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            this.setVersion(version);
        } catch (ArrayIndexOutOfBoundsException var5) {
            return false;
        }

        this.instance.getLogger().info("Your server is running version " + version);
        PluginManager manager = this.instance.getServer().getPluginManager();

        switch (getVersion()) {
            case "v1_16_R1": {
                OpPets.setEntityManager(new EntityManager());
                OpPets.setCreator(new BabyEntityCreator());
                OpPets.setUtils(new Utils());
                this.setPacketEvent(new PacketPlayInSteerVehicleEvent_v1_16_1());
                manager.registerEvents(new PlayerSteerVehicleEvent_v1_16_1(), this.instance);
                PacketManager.setEvent((PacketPlayInSteerVehicleEvent_v1_16_1) OpPets.getController().getPacketEvent());
                PacketManager.setUtils(new v1_16_1R.Utils());
                return true;
            }
            case "v1_16_R2": {
                OpPets.setEntityManager(new v1_16_3R.entities.EntityManager());
                OpPets.setCreator(new v1_16_3R.BabyEntityCreator());
                OpPets.setUtils(new v1_16_3R.Utils());
                this.setPacketEvent(new PacketPlayInSteerVehicleEvent_v1_16_3());
                manager.registerEvents(new PlayerSteerVehicleEvent_v1_16_3(), this.instance);
                PacketManager.setEvent((PacketPlayInSteerVehicleEvent_v1_16_3) OpPets.getController().getPacketEvent());
                PacketManager.setUtils(new v1_16_3R.Utils());
                return true;
            }
            case "v1_16_R3": {
                OpPets.setEntityManager(new v1_16_5R.entities.EntityManager());
                OpPets.setCreator(new v1_16_5R.BabyEntityCreator());
                OpPets.setUtils(new v1_16_5R.Utils());
                this.setPacketEvent(new PacketPlayInSteerVehicleEvent_v1_16_5());
                manager.registerEvents(new PlayerSteerVehicleEvent_v1_16_5(), this.instance);
                PacketManager.setEvent((PacketPlayInSteerVehicleEvent_v1_16_5) OpPets.getController().getPacketEvent());
                PacketManager.setUtils(new v1_16_5R.Utils());
                return true;
            }
            case "v1_17_R1": {
                OpPets.setEntityManager(new v1_17_1R.entities.EntityManager());
                OpPets.setCreator(new v1_17_1R.BabyEntityCreator());
                OpPets.setUtils(new v1_17_1R.Utils());
                this.setPacketEvent(new PacketPlayInSteerVehicleEvent_v1_17_1());
                manager.registerEvents(new PlayerSteerVehicleEvent_v1_17_1(), this.instance);
                PacketManager.setEvent((PacketPlayInSteerVehicleEvent_v1_17_1) OpPets.getController().getPacketEvent());
                PacketManager.setUtils(new v1_17_1R.Utils());
                return true;
            }
            case "v1_18_R1": {
                OpPets.setEntityManager(new v1_18_1R.entities.EntityManager());
                OpPets.setCreator(new v1_18_1R.BabyEntityCreator());
                OpPets.setUtils(new v1_18_1R.Utils());
                PacketManager.setUtils(new v1_18_1R.Utils());
                this.setPacketEvent(new PacketPlayInSteerVehicleEvent_v1_18_1());
                manager.registerEvents(new PlayerSteerVehicleEvent_v1_18_1(), this.instance);
                PacketManager.setEvent((PacketPlayInSteerVehicleEvent_v1_18_1) OpPets.getController().getPacketEvent());
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public PacketPlayInSteerVehicleEvent getPacketEvent() {
        return this.packetEvent;
    }

    public void setPacketEvent(PacketPlayInSteerVehicleEvent packetEvent) {
        this.packetEvent = packetEvent;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private PacketPlayInSteerVehicleEvent packetEvent;
    private String version;
}

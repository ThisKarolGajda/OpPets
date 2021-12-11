package dir.interfaces;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface UtilsInterface {

    @Nullable Entity getEntityByUniqueId(UUID uniqueId);

    void hideEntityFromServer(Player owner, int entityId);

    void hideEntityFromPlayer(Player player, int entityId);

    String getVanillaPetName(@NotNull EntityType entityType);

    void killPetFromPlayerUUID(UUID playerUUID);

    /*
    void respawnPet(Pet objectOfPet, @NotNull Player playerObject)
    - used for respawning pet object
     */
    void respawnPet(Object pet, @NotNull Player player);

    Object getVersion();

    void removePathfinders(Object var1, Object var2);

    void removeEntity(Object obj1);
    /*
    Channel getPlayerChannel(Player player)
    - used for getting player's channel from player connection
     */
    Object getPlayerChannel(Player player);
    /*
    ChannelPipeline getPlayerPipeline(Player player)
    - used for getting player's pipeline from player connection and player channel
     */
    Object getPlayerPipeline(Player player);
}

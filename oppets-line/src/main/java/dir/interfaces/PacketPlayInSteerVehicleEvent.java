package dir.interfaces;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PacketPlayInSteerVehicleEvent {

    @Contract(pure = true)
    static @Nullable HandlerList getHandlerList() {
        return null;
    }

    @NotNull
    HandlerList getHandlers();

    PacketPlayInSteerVehicleEvent initialize(Object var1, Object var2);

    Object getPacket();

    Object getPlayer();

    Object getEvent();

    Object getVehicleKeys();

    Object getVehicle();
}

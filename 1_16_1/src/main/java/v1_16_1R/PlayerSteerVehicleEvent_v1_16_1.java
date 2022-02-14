package v1_16_1R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.packets.PacketManager;
import net.minecraft.server.v1_16_R1.PacketPlayInSteerVehicle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class PlayerSteerVehicleEvent_v1_16_1 implements Listener {

    @NotNull
    private static Vector getVelocityVector(@NotNull Vector vector, Player player, float side, float forw) {
        vector.setX(0.0D);
        vector.setZ(0.0D);
        Vector mot = new Vector((double) forw * -1.0D, 0.0D, (double) side);
        if (mot.length() > 0.0D) {
            mot.rotateAroundY(Math.toRadians((double) (player.getLocation().getYaw() * -1.0F + 90.0F)));
            mot.normalize().multiply(0.25F);
        }

        return mot.add(vector);
    }

    @EventHandler
    public void playerSteerVehicle(PacketPlayInSteerVehicleEvent_v1_16_1 event) {
        Player player = event.getPlayer();
        PacketPlayInSteerVehicle packet = event.getPacket();
        Entity vehicle = player.getVehicle();
        float side = packet.b();
        float forw = packet.c();
        boolean jump = event.isJumping();
        boolean sneaking = event.isSneaking();

        if (vehicle == null) {
            return;
        }

        if (sneaking) {
            vehicle.removePassenger(player);
            PacketManager.removePlayer(player);
        }

        if (jump && vehicle.isOnGround()) {
            vehicle.setVelocity(vehicle.getVelocity().add(new Vector(0.0D, 0.5D, 0.0D)));
        }

        Vector vel = getVelocityVector(vehicle.getVelocity(), player, side, forw);
        vehicle.setVelocity(vel);
    }
}

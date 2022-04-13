package me.opkarol.oppets.abilities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.utils.FormatUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;

import static org.bukkit.potion.PotionEffectType.*;

/**
 * The type Abilities functions.
 */
public class AbilitiesFunctions {
    /**
     * The Sender.
     */
    private final ConsoleCommandSender sender;
    /**
     * The Negative effects.
     */
    private final HashSet<PotionEffectType> negativeEffects;

    /**
     * Instantiates a new Abilities functions.
     */
    public AbilitiesFunctions() {
        sender = Bukkit.getConsoleSender();
        negativeEffects = new HashSet<>(Arrays.asList(BLINDNESS, CONFUSION, HARM, HUNGER, POISON, SLOW, SLOW_DIGGING, WEAKNESS, WITHER, UNLUCK));
    }

    /**
     * Send actionbar.
     *
     * @param player  the player
     * @param message the message
     */
    public void sendActionbar(@NotNull Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(FormatUtils.formatMessage(message)));
    }

    /**
     * Add food.
     *
     * @param player the player
     * @param toAdd  the to add
     */
    public void addFood(@NotNull Player player, int toAdd) {
        int playerFoodLevel = player.getFoodLevel();
        for (int i = 0; i < toAdd; i++) {
            if ((player.getFoodLevel() + i) != 20f) {
                player.setFoodLevel(playerFoodLevel + i + 1);
            } else {
                player.setSaturation(toAdd - i + 1);
                break;
            }
        }
    }

    /**
     * Add health.
     *
     * @param player the player
     * @param toAdd  the to add
     */
    public void addHealth(@NotNull Player player, int toAdd) {
        double health = player.getHealth();
        health += toAdd;
        if (health > 20.0) {
            health = 20.0;
        }
        player.setHealth(health);
    }

    /**
     * Console command.
     *
     * @param player  the player
     * @param command the command
     */
    public void consoleCommand(@NotNull Player player, @NotNull String command) {
        Bukkit.dispatchCommand(sender, command.replace("%player%", player.getName()));
    }

    /**
     * Cure.
     *
     * @param player the player
     */
    public void cure(@NotNull Player player) {
        for (PotionEffect effects : player.getActivePotionEffects()) {
            if (negativeEffects.contains(effects.getType())) {
                player.removePotionEffect(effects.getType());
            }
        }
    }

    /**
     * Lighting.
     *
     * @param receiver the receiver
     */
    public void lighting(@NotNull Player receiver) {
        receiver.getWorld().spawnEntity(receiver.getLocation(), EntityType.LIGHTNING);
    }

    /**
     * Potion.
     *
     * @param player    the player
     * @param type      the type
     * @param duration  the duration
     * @param amplifier the amplifier
     */
    public void potion(@NotNull Player player, @NotNull PotionEffectType type, int duration, int amplifier) {
        player.addPotionEffect(type.createEffect(duration, amplifier));
    }

    /**
     * Message.
     *
     * @param player  the player
     * @param message the message
     */
    public void message(@NotNull Player player, String message) {
        player.sendMessage(FormatUtils.formatMessage(message));
    }

    /**
     * Revive.
     *
     * @param player the player
     */
    public void revive(@NotNull Player player) {
        player.setHealth(20);
        player.setNoDamageTicks(60);
    }

    /**
     * Experience.
     *
     * @param player the player
     * @param exp    the exp
     */
    public void experience(@NotNull Player player, int exp) {
        player.setExp(player.getExp() + exp);
    }

    /**
     * Particle.
     *
     * @param player   the player
     * @param particle the particle
     * @param size     the size
     */
    public void particle(@NotNull Player player, Particle particle, int size) {
        World world = player.getLocation().getWorld();
        if (world == null) {
            return;
        }
        player.getLocation().getWorld().spawnParticle(particle, player.getLocation(), size);
    }

    /**
     * Invincible.
     *
     * @param player the player
     * @param ticks  the ticks
     */
    public void invincible(@NotNull Player player, int ticks) {
        player.setNoDamageTicks(ticks);
    }

    /**
     * Player command.
     *
     * @param player  the player
     * @param command the command
     */
    public void playerCommand(Player player, @NotNull String command) {
        Bukkit.dispatchCommand(player, command.replace("%player%", player.getName()));
    }

    /**
     * Fireball.
     *
     * @param player the player
     */
    public void fireball(Player player) {
        launch(player, 25, 7, Fireball.class);
    }

    /**
     * Launch.
     *
     * @param player          the player
     * @param distOverHead    the dist over head
     * @param distToFacing    the dist to facing
     * @param projectileClass the projectile class
     */
    private void launch(@NotNull Player player, double distOverHead, double distToFacing, Class<? extends Projectile> projectileClass) {
        final Location start = player.getLocation().add(0, distOverHead, 0);
        final Vector facing = player.getEyeLocation().add(player.getLocation().getDirection().multiply(distToFacing)).toVector();
        final Vector initialV = facing.subtract(start.toVector()).normalize();
        final Projectile projectile = player.getWorld().spawn(start, projectileClass);
        projectile.setVelocity(initialV);
    }
}

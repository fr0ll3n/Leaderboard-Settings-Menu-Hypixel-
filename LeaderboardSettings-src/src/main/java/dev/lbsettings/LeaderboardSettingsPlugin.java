/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package dev.lbsettings;

import dev.lbsettings.AlignExpansion;
import dev.lbsettings.Color;
import dev.lbsettings.MenuListener;
import dev.lbsettings.SettingsMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class LeaderboardSettingsPlugin
extends JavaPlugin {
    private SettingsMenu menu;

    public void onEnable() {
        this.menu = new SettingsMenu(this);
        Bukkit.getPluginManager().registerEvents((Listener)new MenuListener(this), (Plugin)this);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            try {
                new AlignExpansion(this).register();
                this.getLogger().info("Registered %lbalign_*% placeholder for block leaderboards.");
            }
            catch (Throwable throwable) {
                this.getLogger().warning("Could not register lbalign expansion: " + throwable.getMessage());
            }
        } else {
            this.getLogger().warning("PlaceholderAPI not found \u2014 block alignment padding will not run.");
        }
        this.getLogger().info("LeaderboardSettings enabled. Use /lbsettings");
    }

    public SettingsMenu menu() {
        return this.menu;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] stringArray) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Color.color("&cPlayers only."));
            return true;
        }
        Player player = (Player)commandSender;
        if (!player.hasPermission("leaderboardsettings.use") && !player.isOp()) {
            player.sendMessage(Color.color("&cNo permission."));
            return true;
        }
        this.menu.open(player);
        return true;
    }
}


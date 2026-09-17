/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryDragEvent
 *  org.bukkit.inventory.Inventory
 */
package dev.lbsettings;

import dev.lbsettings.LeaderboardSettingsPlugin;
import dev.lbsettings.MenuHolder;
import dev.lbsettings.SettingsSession;
import dev.lbsettings.Sounds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public final class MenuListener
implements Listener {
    private final LeaderboardSettingsPlugin plugin;

    public MenuListener(LeaderboardSettingsPlugin leaderboardSettingsPlugin) {
        this.plugin = leaderboardSettingsPlugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        boolean bl;
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        Inventory inventory = inventoryClickEvent.getView().getTopInventory();
        if (inventory == null || !(inventory.getHolder() instanceof MenuHolder)) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getClickedInventory() == null || inventoryClickEvent.getClickedInventory() != inventory) {
            return;
        }
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        SettingsSession settingsSession = this.plugin.menu().session(player);
        if (settingsSession == null) {
            this.plugin.menu().open(player);
            settingsSession = this.plugin.menu().session(player);
            if (settingsSession == null) {
                return;
            }
        }
        int n = inventoryClickEvent.getRawSlot();
        boolean bl2 = inventoryClickEvent.getClick() == ClickType.RIGHT || inventoryClickEvent.getClick() == ClickType.SHIFT_RIGHT;
        boolean bl3 = bl = !bl2;
        if (n == 11) {
            Sounds.click(player);
            this.plugin.menu().cycleMode(settingsSession, bl);
            this.plugin.menu().draw(player, settingsSession);
            return;
        }
        if (n == 12) {
            Sounds.click(player);
            this.plugin.menu().cycleTime(settingsSession, bl);
            this.plugin.menu().draw(player, settingsSession);
            return;
        }
        if (n == 13) {
            Sounds.click(player);
            this.plugin.menu().cycleView(settingsSession, bl);
            this.plugin.menu().draw(player, settingsSession);
            return;
        }
        if (n == 15) {
            Sounds.click(player);
            this.plugin.menu().cycleAlign(settingsSession, bl);
            this.plugin.menu().draw(player, settingsSession);
            return;
        }
        if (n == 30) {
            this.plugin.menu().accept(player, settingsSession);
            return;
        }
        if (n == 32) {
            this.plugin.menu().reject(player);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent inventoryDragEvent) {
        Inventory inventory = inventoryDragEvent.getView().getTopInventory();
        if (inventory != null && inventory.getHolder() instanceof MenuHolder) {
            inventoryDragEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {
        if (!(inventoryCloseEvent.getPlayer() instanceof Player)) {
            return;
        }
        if (!(inventoryCloseEvent.getInventory().getHolder() instanceof MenuHolder)) {
            return;
        }
        Player player = (Player)inventoryCloseEvent.getPlayer();
        if (this.plugin.menu().wasRedraw(player)) {
            return;
        }
        this.plugin.menu().close(player);
    }
}


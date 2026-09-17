/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 */
package dev.lbsettings;

import java.util.UUID;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class MenuHolder
implements InventoryHolder {
    private final UUID owner;
    private Inventory inventory;

    public MenuHolder(UUID uUID) {
        this.owner = uUID;
    }

    public UUID owner() {
        return this.owner;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}


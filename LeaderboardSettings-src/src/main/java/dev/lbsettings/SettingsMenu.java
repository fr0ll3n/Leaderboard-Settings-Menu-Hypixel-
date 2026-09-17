/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 */
package dev.lbsettings;

import dev.lbsettings.Color;
import dev.lbsettings.Items;
import dev.lbsettings.LeaderboardSettingsPlugin;
import dev.lbsettings.LuckPermsHook;
import dev.lbsettings.MenuHolder;
import dev.lbsettings.SettingKeys;
import dev.lbsettings.SettingsSession;
import dev.lbsettings.Sounds;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public final class SettingsMenu {
    public static final int SIZE = 45;
    public static final int SLOT_MODE = 11;
    public static final int SLOT_TIME = 12;
    public static final int SLOT_VIEW = 13;
    public static final int SLOT_PLAYERS = 14;
    public static final int SLOT_ALIGN = 15;
    public static final int SLOT_ACCEPT = 30;
    public static final int SLOT_REJECT = 32;
    private final LeaderboardSettingsPlugin plugin;
    private final Map<UUID, SettingsSession> sessions = new ConcurrentHashMap<UUID, SettingsSession>();
    private final Set<UUID> redrawing = ConcurrentHashMap.newKeySet();

    public SettingsMenu(LeaderboardSettingsPlugin leaderboardSettingsPlugin) {
        this.plugin = leaderboardSettingsPlugin;
    }

    public void open(Player player) {
        SettingsSession settingsSession = this.sessions.get(player.getUniqueId());
        if (settingsSession == null) {
            String string = SettingsMenu.valueOr(LuckPermsHook.meta(player, "bw_mode_key"), "coremodes");
            String string2 = SettingsMenu.valueOr(LuckPermsHook.meta(player, "bw_time_key"), "lifetime");
            String string3 = SettingsMenu.valueOr(LuckPermsHook.meta(player, "bw_view_key"), "top10");
            String string4 = SettingsMenu.valueOr(LuckPermsHook.meta(player, "bw_align_key"), "center");
            if (SettingKeys.indexOf(SettingKeys.MODES, string, -1) < 0) {
                string = "coremodes";
            }
            if (SettingKeys.indexOf(SettingKeys.TIMES, string2, -1) < 0) {
                string2 = "lifetime";
            }
            if (SettingKeys.indexOf(SettingKeys.VIEWS, string3, -1) < 0) {
                string3 = "top10";
            }
            if (SettingKeys.indexOf(SettingKeys.ALIGNS, string4, -1) < 0) {
                string4 = "center";
            }
            settingsSession = new SettingsSession(string, string2, string3, string4);
            this.sessions.put(player.getUniqueId(), settingsSession);
        }
        this.draw(player, settingsSession);
    }

    public void draw(Player player, SettingsSession settingsSession) {
        MenuHolder menuHolder = new MenuHolder(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory((InventoryHolder)menuHolder, (int)45, (String)Color.color("&8Leaderboard Settings"));
        menuHolder.setInventory(inventory);
        inventory.setItem(11, this.modeItem(settingsSession.mode));
        inventory.setItem(12, this.timeItem(settingsSession.time));
        inventory.setItem(13, this.viewItem(settingsSession.view));
        inventory.setItem(14, this.playersItem());
        inventory.setItem(15, this.alignItem(settingsSession.align));
        inventory.setItem(30, Items.of("GREEN_CONCRETE", (short)13, "&aApply Changes", Items.lore("&eClick to apply changes!")));
        inventory.setItem(32, Items.of("RED_CONCRETE", (short)14, "&cDiscard Changes", Items.lore("&eClose the menu without applying", "&echanges!")));
        this.redrawing.add(player.getUniqueId());
        player.openInventory(inventory);
    }

    public SettingsSession session(Player player) {
        return this.sessions.get(player.getUniqueId());
    }

    public void close(Player player) {
        this.sessions.remove(player.getUniqueId());
    }

    public boolean wasRedraw(Player player) {
        return this.redrawing.remove(player.getUniqueId());
    }

    public void cycleMode(SettingsSession settingsSession, boolean bl) {
        settingsSession.mode = SettingKeys.MODES[SettingKeys.next(SettingKeys.indexOf(SettingKeys.MODES, settingsSession.mode, 0), SettingKeys.MODES.length, bl)];
    }

    public void cycleTime(SettingsSession settingsSession, boolean bl) {
        settingsSession.time = SettingKeys.TIMES[SettingKeys.next(SettingKeys.indexOf(SettingKeys.TIMES, settingsSession.time, 0), SettingKeys.TIMES.length, bl)];
    }

    public void cycleView(SettingsSession settingsSession, boolean bl) {
        settingsSession.view = SettingKeys.VIEWS[SettingKeys.next(SettingKeys.indexOf(SettingKeys.VIEWS, settingsSession.view, 0), SettingKeys.VIEWS.length, bl)];
    }

    public void cycleAlign(SettingsSession settingsSession, boolean bl) {
        settingsSession.align = SettingKeys.ALIGNS[SettingKeys.next(SettingKeys.indexOf(SettingKeys.ALIGNS, settingsSession.align, 0), SettingKeys.ALIGNS.length, bl)];
    }

    public void accept(Player player, SettingsSession settingsSession) {
        if (!settingsSession.dirty()) {
            this.sessions.remove(player.getUniqueId());
            player.closeInventory();
            player.sendMessage(Color.color("&aYour settings are unchanged."));
            return;
        }
        Sounds.levelUp(player);
        String string = SettingKeys.label(SettingKeys.MODES, SettingKeys.MODE_LABELS, settingsSession.mode);
        String string2 = SettingKeys.label(SettingKeys.TIMES, SettingKeys.TIME_LABELS, settingsSession.time);
        String string3 = SettingKeys.label(SettingKeys.VIEWS, SettingKeys.VIEW_LABELS, settingsSession.view);
        String string4 = SettingKeys.label(SettingKeys.ALIGNS, SettingKeys.ALIGN_LABELS, settingsSession.align);
        String string5 = player.getName();
        this.dispatch("lp user " + string5 + " permission clear");
        this.dispatch("lp user " + string5 + " meta set bw_mode " + SettingsMenu.quote(string));
        this.dispatch("lp user " + string5 + " meta set bw_mode_key " + settingsSession.mode);
        this.dispatch("lp user " + string5 + " meta set bw_time " + SettingsMenu.quote(string2));
        this.dispatch("lp user " + string5 + " meta set bw_time_key " + settingsSession.time);
        this.dispatch("lp user " + string5 + " meta set bw_view " + SettingsMenu.quote(string3));
        this.dispatch("lp user " + string5 + " meta set bw_view_key " + settingsSession.view);
        this.dispatch("lp user " + string5 + " meta set bw_align " + SettingsMenu.quote(string4));
        this.dispatch("lp user " + string5 + " meta set bw_align_key " + settingsSession.align);
        boolean bl = "block".equalsIgnoreCase(settingsSession.align);
        this.dispatch("lp user " + string5 + " permission set leaderboard.alignment.center " + (!bl ? 1 : 0));
        this.dispatch("lp user " + string5 + " permission set leaderboard.alignment.block " + bl);
        this.grantView(string5, "bedsbroken", settingsSession);
        this.grantView(string5, "finalkills", settingsSession);
        this.grantView(string5, "wins", settingsSession);
        this.sessions.remove(player.getUniqueId());
        player.closeInventory();
        player.sendMessage(Color.color("&aApplied your leaderboard settings changes!"));
    }

    public void reject(Player player) {
        Sounds.teleport(player);
        this.sessions.remove(player.getUniqueId());
        player.closeInventory();
        player.sendMessage(Color.color("&cDiscarded your leaderboard settings changes!"));
    }

    private void grantView(String string, String string2, SettingsSession settingsSession) {
        String string3 = "leaderboard." + string2 + ".view." + settingsSession.view + "." + settingsSession.mode + "." + settingsSession.time;
        if ("block".equalsIgnoreCase(settingsSession.align)) {
            string3 = string3 + ".block";
        }
        this.dispatch("lp user " + string + " permission set " + string3 + " true");
    }

    private void dispatch(String string) {
        try {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)string);
        }
        catch (Throwable throwable) {
            this.plugin.getLogger().warning("Failed command: " + string + " (" + throwable.getMessage() + ")");
        }
    }

    private static String quote(String string) {
        if (string != null && string.contains(" ")) {
            return "\"" + string + "\"";
        }
        return string == null ? "\"\"" : string;
    }

    private static String valueOr(String string, String string2) {
        if (string == null || string.trim().isEmpty() || string.equalsIgnoreCase("null")) {
            return string2;
        }
        return string.trim();
    }

    private static String mark(boolean bl) {
        return bl ? "  &a\u27a0  &7" : "     &7";
    }

    private ItemStack modeItem(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("");
        for (int n = 0; n < SettingKeys.MODES.length; n = (int)((byte)(n + 1))) {
            arrayList.add(SettingsMenu.mark(SettingKeys.MODES[n].equalsIgnoreCase(string)) + SettingKeys.MODE_LABELS[n]);
        }
        arrayList.add("");
        arrayList.add("&8&oThis setting will save across all lobbies.");
        arrayList.add("");
        arrayList.add("&8&oLeaderboard data is cached and");
        arrayList.add("&8&odoes not update immediately");
        arrayList.add("");
        arrayList.add("&eLeft/Right to change");
        return Items.of("BED", "&aSelect the Mode!", arrayList);
    }

    private ItemStack timeItem(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("");
        for (int n = 0; n < SettingKeys.TIMES.length; n = (int)((byte)(n + 1))) {
            arrayList.add(SettingsMenu.mark(SettingKeys.TIMES[n].equalsIgnoreCase(string)) + SettingKeys.TIME_LABELS[n]);
        }
        arrayList.add("");
        arrayList.add("&8&oThis setting will save across all lobbies.");
        arrayList.add("");
        arrayList.add("&8&oLeaderboard data is cached and");
        arrayList.add("&8&odoes not update immediately");
        arrayList.add("");
        arrayList.add("&eLeft/Right to change");
        return Items.of("WATCH", "&aSelect the Time!", arrayList);
    }

    private ItemStack viewItem(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("");
        for (int n = 0; n < SettingKeys.VIEWS.length; n = (int)((byte)(n + 1))) {
            arrayList.add(SettingsMenu.mark(SettingKeys.VIEWS[n].equalsIgnoreCase(string)) + SettingKeys.VIEW_LABELS[n]);
        }
        arrayList.add("");
        arrayList.add("&8&oLeaderboard data is cached and");
        arrayList.add("&8&odoes not update immediately");
        arrayList.add("");
        arrayList.add("&eLeft/Right to change");
        return Items.of("LADDER", "&aSelect the View!", arrayList);
    }

    private ItemStack playersItem() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("");
        for (int n = 0; n < SettingKeys.PLAYER_FILTER_LABELS.length; n = (int)((byte)(n + 1))) {
            arrayList.add(SettingsMenu.mark(n == 0) + SettingKeys.PLAYER_FILTER_LABELS[n]);
        }
        arrayList.add("");
        arrayList.add("&8&oLeaderboard data is cached and");
        arrayList.add("&8&odoes not update immediately");
        arrayList.add("");
        arrayList.add("&cIn development!");
        return Items.of("PLAYER_HEAD", (short)3, "&aSelect the Players!", arrayList);
    }

    private ItemStack alignItem(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("");
        for (int n = 0; n < SettingKeys.ALIGN_LABELS.length; n = (int)((byte)(n + 1))) {
            arrayList.add(SettingsMenu.mark(SettingKeys.ALIGNS[n].equalsIgnoreCase(string)) + SettingKeys.ALIGN_LABELS[n]);
        }
        arrayList.add("");
        arrayList.add("&cBlock alignment is showing correctly");
        arrayList.add("&conly for Vanilla Minecraft font sizes.");
        arrayList.add("");
        arrayList.add("&8&oThis setting will save across the");
        arrayList.add("&8&onetwork.");
        arrayList.add("");
        arrayList.add("&8&oLeaderboard data is cached and");
        arrayList.add("&8&odoes not update immediately");
        arrayList.add("");
        arrayList.add("&eLeft/Right click to change");
        return Items.of("NAME_TAG", "&aSelect the Text Alignment!", arrayList);
    }
}


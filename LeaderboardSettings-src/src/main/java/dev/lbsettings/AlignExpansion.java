/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.clip.placeholderapi.PlaceholderAPI
 *  me.clip.placeholderapi.expansion.PlaceholderExpansion
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package dev.lbsettings;

import dev.lbsettings.FontWidth;
import dev.lbsettings.LeaderboardSettingsPlugin;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class AlignExpansion
extends PlaceholderExpansion {
    private static final Pattern INNER = Pattern.compile("\\{([a-zA-Z0-9_]+)\\}");
    private final LeaderboardSettingsPlugin plugin;

    public AlignExpansion(LeaderboardSettingsPlugin leaderboardSettingsPlugin) {
        this.plugin = leaderboardSettingsPlugin;
    }

    public String getIdentifier() {
        return "lbalign";
    }

    public String getAuthor() {
        return "Grok";
    }

    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    public boolean persist() {
        return true;
    }

    public String onPlaceholderRequest(Player player, String string) {
        int n;
        if (string == null || string.isEmpty()) {
            return "";
        }
        String[] stringArray = string.split(";", 3);
        if (stringArray.length < 3) {
            return "";
        }
        try {
            n = Integer.parseInt(stringArray[0].trim());
        }
        catch (NumberFormatException numberFormatException) {
            n = 180;
        }
        String string2 = AlignExpansion.resolve(player, stringArray[1]);
        String string3 = AlignExpansion.resolve(player, stringArray[2]);
        return FontWidth.padTo(string2, string3, n);
    }

    private static String resolve(Player player, String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        Matcher matcher = INNER.matcher(string);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, "%" + matcher.group(1) + "%");
        }
        matcher.appendTail(stringBuffer);
        String string2 = stringBuffer.toString();
        if (player == null) {
            return string2;
        }
        try {
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                return PlaceholderAPI.setPlaceholders((Player)player, (String)string2);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return string2;
    }
}


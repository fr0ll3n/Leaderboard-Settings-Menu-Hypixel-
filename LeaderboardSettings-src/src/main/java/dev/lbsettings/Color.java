/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package dev.lbsettings;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public final class Color {
    public static String color(String string) {
        if (string == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes((char)'&', (String)string);
    }

    public static List<String> color(List<String> list) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (list == null) {
            return arrayList;
        }
        for (String string : list) {
            arrayList.add(Color.color(string));
        }
        return arrayList;
    }
}


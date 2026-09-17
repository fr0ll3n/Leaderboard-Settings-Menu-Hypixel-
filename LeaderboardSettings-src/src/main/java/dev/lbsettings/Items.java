/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package dev.lbsettings;

import dev.lbsettings.Color;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class Items {
    public static ItemStack of(String string, String string2, List<String> list) {
        return Items.of(string, (short)0, string2, list);
    }

    public static ItemStack of(String string, short s, String string2, List<String> list) {
        ItemStack itemStack = Items.stack(string, s);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Color.color(string2));
            if (list != null) {
                itemMeta.setLore(Color.color(list));
            }
            try {
                for (ItemFlag itemFlag : ItemFlag.values()) {
                    itemMeta.addItemFlags(new ItemFlag[]{itemFlag});
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static ItemStack stack(String string, short n) {
        boolean bl;
        Material material = Items.match(string);
        ItemStack itemStack = new ItemStack(material == null ? Material.STONE : material, 1);
        String string2 = material == null ? "" : material.name();
        boolean bl2 = bl = n != 0 || "WOOL".equals(string2) || "STAINED_CLAY".equals(string2) || "STAINED_HARDENED_CLAY".equals(string2) || "SKULL_ITEM".equals(string2) || "SKULL".equals(string2);
        if (bl) {
            int n2 = n;
            if (n2 == 0 && "GREEN_CONCRETE".equalsIgnoreCase(string)) {
                n2 = 13;
            } else if (n2 == 0 && "RED_CONCRETE".equalsIgnoreCase(string)) {
                n2 = 14;
            } else if (n2 == 0 && ("PLAYER_HEAD".equalsIgnoreCase(string) || "SKULL_ITEM".equalsIgnoreCase(string) || "SKULL".equalsIgnoreCase(string))) {
                n2 = 3;
            }
            try {
                itemStack.setDurability((short)n2);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return itemStack;
    }

    public static Material match(String string) {
        if (string == null) {
            return Material.STONE;
        }
        String string2 = string.toUpperCase();
        String[] stringArray = "PLAYER_HEAD".equals(string2) || "SKULL_ITEM".equals(string2) || "SKULL".equals(string2) ? new String[]{"PLAYER_HEAD", "SKULL_ITEM", "SKULL"} : ("NAME_TAG".equals(string2) || "NAMETAG".equals(string2) ? new String[]{"NAME_TAG", "NAMETAG"} : ("WATCH".equals(string2) || "CLOCK".equals(string2) ? new String[]{"WATCH", "CLOCK"} : ("BED".equals(string2) || "RED_BED".equals(string2) ? new String[]{"BED", "RED_BED"} : ("GREEN_CONCRETE".equals(string2) || "LIME_CONCRETE".equals(string2) ? new String[]{"GREEN_CONCRETE", "LIME_CONCRETE", "GREEN_TERRACOTTA", "LIME_TERRACOTTA", "STAINED_CLAY", "STAINED_HARDENED_CLAY"} : ("RED_CONCRETE".equals(string2) ? new String[]{"RED_CONCRETE", "RED_TERRACOTTA", "STAINED_CLAY", "STAINED_HARDENED_CLAY"} : ("LIME_WOOL".equals(string2) ? new String[]{"LIME_WOOL", "WOOL"} : ("RED_WOOL".equals(string2) ? new String[]{"RED_WOOL", "WOOL"} : new String[]{string2})))))));
        for (String string3 : stringArray) {
            try {
                return Material.valueOf((String)string3);
            }
            catch (Throwable throwable) {
            }
        }
        return Material.STONE;
    }

    public static List<String> lore(String ... stringArray) {
        return Arrays.asList(stringArray);
    }
}


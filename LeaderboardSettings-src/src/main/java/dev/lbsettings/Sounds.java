/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 */
package dev.lbsettings;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class Sounds {
    public static final Sound CLICK = Sounds.first("CLICK", "UI_BUTTON_CLICK", "WOOD_CLICK");
    public static final Sound LEVEL_UP = Sounds.first("LEVEL_UP", "ENTITY_PLAYER_LEVELUP");
    public static final Sound TELEPORT = Sounds.first("ENDERMAN_TELEPORT", "ENTITY_ENDERMAN_TELEPORT", "ENTITY_ENDERMEN_TELEPORT");

    private static Sound first(String ... stringArray) {
        for (String string : stringArray) {
            try {
                return Sound.valueOf((String)string);
            }
            catch (Throwable throwable) {
            }
        }
        String[] stringArray2 = Sound.values();
        return stringArray2.length == 0 ? null : stringArray2[0];
    }

    public static void click(Player player) {
        Sounds.play(player, CLICK, 6.0f, -1000.0f);
    }

    public static void levelUp(Player player) {
        Sounds.play(player, LEVEL_UP, 1.0f, 1.0f);
    }

    public static void teleport(Player player) {
        Sounds.play(player, TELEPORT, 10.0f, 1000.0f);
    }

    private static void play(Player player, Sound sound, float f, float f2) {
        if (player == null || sound == null) {
            return;
        }
        try {
            player.playSound(player.getLocation(), sound, f, f2);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }
}


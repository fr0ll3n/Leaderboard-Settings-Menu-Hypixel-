/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package dev.lbsettings;

import java.lang.reflect.Method;
import java.util.UUID;
import org.bukkit.entity.Player;

public final class LuckPermsHook {
    public static String meta(Player player, String string) {
        if (player == null || string == null) {
            return null;
        }
        try {
            Class<?> clazz = Class.forName("net.luckperms.api.LuckPermsProvider");
            Object object = clazz.getMethod("get", new Class[0]).invoke(null, new Object[0]);
            Object object2 = object.getClass().getMethod("getUserManager", new Class[0]).invoke(object, new Object[0]);
            Object object3 = object2.getClass().getMethod("getUser", UUID.class).invoke(object2, player.getUniqueId());
            if (object3 == null) {
                return null;
            }
            Object object4 = object3.getClass().getMethod("getCachedData", new Class[0]).invoke(object3, new Object[0]);
            Object object5 = object4.getClass().getMethod("getMetaData", new Class[0]).invoke(object4, new Object[0]);
            Method method = object5.getClass().getMethod("getMetaValue", String.class);
            Object object6 = method.invoke(object5, string);
            return object6 == null ? null : String.valueOf(object6);
        }
        catch (Throwable throwable) {
            return null;
        }
    }
}


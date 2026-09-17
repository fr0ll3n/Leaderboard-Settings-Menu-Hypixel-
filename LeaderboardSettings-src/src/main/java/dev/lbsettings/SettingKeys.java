/*
 * Decompiled with CFR 0.152.
 */
package dev.lbsettings;

public final class SettingKeys {
    public static final String[] MODES = new String[]{"coremodes", "solo", "doubles", "3v3v3v3", "4v4v4v4", "4v4"};
    public static final String[] MODE_LABELS = new String[]{"Core Modes", "Solo", "Doubles", "3v3v3v3", "4v4v4v4", "4v4"};
    public static final String[] TIMES = new String[]{"lifetime", "monthly", "weekly", "daily"};
    public static final String[] TIME_LABELS = new String[]{"Lifetime", "Monthly", "Weekly", "Daily"};
    public static final String[] VIEWS = new String[]{"top10", "position"};
    public static final String[] VIEW_LABELS = new String[]{"Top 10", "Players Around You"};
    public static final String[] PLAYER_FILTERS = new String[]{"all", "friends", "bestfriends", "guild"};
    public static final String[] PLAYER_FILTER_LABELS = new String[]{"All", "Friends", "Best Friends", "Guild Members"};
    public static final String[] ALIGNS = new String[]{"center", "block"};
    public static final String[] ALIGN_LABELS = new String[]{"Center", "Block"};

    public static int indexOf(String[] stringArray, String string, int n) {
        if (string == null) {
            return n;
        }
        for (int n2 = 0; n2 < stringArray.length; n2 = (int)((byte)(n2 + 1))) {
            if (!stringArray[n2].equalsIgnoreCase(string)) continue;
            return n2;
        }
        return n;
    }

    public static int next(int n, int n2, boolean bl) {
        if (bl) {
            return (n + 1) % n2;
        }
        return (n - 1 + n2) % n2;
    }

    public static String label(String[] stringArray, String[] stringArray2, String string) {
        int n = SettingKeys.indexOf(stringArray, string, 0);
        if (n < 0 || n >= stringArray2.length) {
            return string;
        }
        return stringArray2[n];
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package dev.lbsettings;

public final class SettingsSession {
    public final String originalMode;
    public final String originalTime;
    public final String originalView;
    public final String originalAlign;
    public String mode;
    public String time;
    public String view;
    public String align;

    public SettingsSession(String string, String string2, String string3, String string4) {
        this.originalMode = string;
        this.originalTime = string2;
        this.originalView = string3;
        this.originalAlign = string4;
        this.mode = string;
        this.time = string2;
        this.view = string3;
        this.align = string4;
    }

    public boolean dirty() {
        return !SettingsSession.eq(this.mode, this.originalMode) || !SettingsSession.eq(this.time, this.originalTime) || !SettingsSession.eq(this.view, this.originalView) || !SettingsSession.eq(this.align, this.originalAlign);
    }

    private static boolean eq(String string, String string2) {
        if (string == null) {
            return string2 == null;
        }
        return string.equalsIgnoreCase(string2);
    }
}


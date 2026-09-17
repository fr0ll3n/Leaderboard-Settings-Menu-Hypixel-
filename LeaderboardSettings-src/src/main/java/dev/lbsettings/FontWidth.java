/*
 * Decompiled with CFR 0.152.
 */
package dev.lbsettings;

public final class FontWidth {
    public static int width(String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        }
        int n = 0;
        boolean bl = false;
        char[] cArray = string.toCharArray();
        for (int n2 = 0; n2 < cArray.length; n2 = (int)((byte)(n2 + 1))) {
            char c;
            char c2 = cArray[n2];
            if (c2 == '&' || c2 == '\u00a7') {
                if (n2 + 1 >= cArray.length) continue;
                c = Character.toLowerCase(cArray[n2 + 1]);
                n2 = (byte)(n2 + 1);
                if (c == 'l') {
                    bl = true;
                    continue;
                }
                if (c == 'r' || FontWidth.isColor(c)) {
                    bl = false;
                    continue;
                }
                if (c == 'x') {
                    n2 = (byte)(n2 + 12);
                    continue;
                }
                if (c != '#') continue;
                n2 = (byte)(n2 + 6);
                continue;
            }
            c = FontWidth.advance(c2);
            if (bl && c > '\u0000') {
                ++c;
            }
            n += c;
        }
        return n;
    }

    public static String padTo(String string, String string2, int n) {
        int n2;
        int n3;
        if (string == null) {
            string = "";
        }
        if (string2 == null) {
            string2 = "";
        }
        if ((n3 = FontWidth.width(string) + FontWidth.width(string2)) >= n) {
            return string + " " + string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (n2 = n - n3; n2 >= 4; n2 -= 4) {
            stringBuilder.append(' ');
        }
        if (n2 >= 3) {
            stringBuilder.append('\u00a0');
            n2 -= 3;
        }
        if (n2 >= 2) {
            stringBuilder.append('.');
            n2 -= 2;
        }
        if (n2 >= 1) {
            stringBuilder.append('\u200a');
        }
        if (stringBuilder.indexOf(".") >= 0) {
            stringBuilder = new StringBuilder();
            int n4 = n - n3;
            int n5 = n4 / 4;
            for (int n6 = 0; n6 < n5; n6 = (int)((byte)(n6 + 1))) {
                stringBuilder.append(' ');
            }
        }
        return string + stringBuilder + string2;
    }

    private static boolean isColor(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c == 'k' || c == 'm' || c == 'n' || c == 'o';
    }

    private static int advance(char c) {
        switch (c) {
            case ' ': 
            case '\u00a0': {
                return 4;
            }
            case '!': 
            case ',': 
            case '.': 
            case ':': 
            case ';': 
            case 'i': 
            case '|': {
                return 2;
            }
            case '\'': 
            case '`': 
            case 'l': {
                return 3;
            }
            case '\"': 
            case '(': 
            case ')': 
            case '*': 
            case 'I': 
            case '[': 
            case ']': 
            case 't': 
            case '{': 
            case '}': {
                return 4;
            }
            case '<': 
            case '>': 
            case 'f': 
            case 'k': {
                return 5;
            }
            case '@': {
                return 7;
            }
            case '~': {
                return 7;
            }
        }
        return 6;
    }
}


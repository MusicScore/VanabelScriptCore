package com.github.musicscore.denizensuspiccore.utilities;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CoreUtilities {

    public final static String encoding = "UTF-8";
    public final static int buff10k = 1024 * 10;
    public final static Random random = new Random();

    ///////////////////////////////
    //    Conversion Methods
    /////////////////////////////

    public static String doubleToString(double input) {
        String temp = String.valueOf(input);
        if (temp.endsWith(".0")) {
            return temp.substring(0, temp.length() - 2);
        }
        return temp;
    }

    public static String exceptionToString(Throwable ex) {
        StringBuilder strBuild = new StringBuilder();
        boolean first = true;

        while (ex != null) {
            strBuild.append(first ? "" : "Caused by: ").append(ex.toString()).append("\n");
            for (StackTraceElement el : ex.getStackTrace()) {
                strBuild.append("    at ").append(el.toString()).append("\n");
            }
            if (ex.getCause() == ex) {
                return strBuild.toString();
            }
            ex = ex.getCause();
            first = false;
        }
        return strBuild.toString();
    }

    public static String streamToString(InputStream inptStrm) {
        try {
            final char[] buffer = new char[buff10k];
            final StringBuilder output = new StringBuilder();
            try (Reader in = new InputStreamReader(inptStrm, encoding)) {
                while (true) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0) {
                        break;
                    }
                    output.append(buffer, 0, rsz);
                }
            }
            return output.toString();
        }
        catch (Exception ex) {
            return null;
        }
    }

    ///////////////////////////////
    //    Escape Methods
    /////////////////////////////

    public static String escape(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("&", "&amp")
                .replace("|", "&pipe")
                .replace("<", "&lt")
                .replace(">", "&gt")
                .replace("\n", "&nl")
                .replace("/", "&fs")
                .replace("\\", "&bs")
                .replace("@", "&at")
                .replace("[", "&lb")
                .replace("]", "&rb")
                .replace(":", "&co")
                .replace(";", "&sc")
                .replace(".", "&dot")
                .replace("'", "&sq")
                .replace("\"", "&dq")
                .replace("!", "&exc")
                .replace("#", "&ns")
                .replace("\u00A7", "&ss");
    }

    public static String unescape(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("&amp", "&")
                .replace("&pipe", "|")
                .replace("&lt", "<")
                .replace("&gt", ">")
                .replace("&nl", "\n")
                .replace("&fs", "/")
                .replace("&bs", "\\")
                .replace("&at", "@")
                .replace("&lb", "[")
                .replace("&rb", "]")
                .replace("&co", ":")
                .replace("&sc", ";")
                .replace("&dot", ".")
                .replace("&sq", "'")
                .replace("&dq", "\"")
                .replace("&exc", "!")
                .replace("&ns", "#")
                .replace("&ss", "\u00A7");
    }

    ///////////////////////////////
    //    String Methods
    /////////////////////////////

    public static String toLowerCase(String input) {
        char[] data = input.toCharArray();
        for (int i = 0; i < data.length; i++) {
            if (data[i] >= 'A' && data[i] <= 'Z') {
                data[i] -= 'A' - 'a';
            }
        }
        return new String(data);
    }

    public static String toUpperCase(String input) {
        char[] data = input.toCharArray();
        for (int i = 0; i < data.length; i++) {
            if (data[i] >= 'a' && data[i] <= 'z') {
                data[i] -= 'a' - 'A';
            }
        }
        return new String(data);
    }

    public static String concat(List<String> stringList, String split) {
        StringBuilder strBuild = new StringBuilder();
        if (!stringList.isEmpty()) {
            strBuild.append(stringList.get(0));
        }
        for (int i = 1; i < stringList.size(); i++) {
            strBuild.append(split).append(stringList.get(i));
        }
        return strBuild.toString();
    }

    public static List<String> split(String string, char splitChar) {
        List<String> strings = new ArrayList<>();
        int start = 0;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == splitChar) {
                strings.add(string.substring(start, i));
                start = i + 1;
            }
        }
        strings.add(string.substring(start));
        return strings;
    }

    public static List<String> split(String string, char splitChar, int max) {
        List<String> strings = new ArrayList<>();
        int start = 0;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == splitChar) {
                strings.add(string.substring(start, i));
                start = i + 1;
                if (strings.size() + 1 == max) {
                    break;
                }
            }
        }
        strings.add(string.substring(start));
        return strings;
    }

    public static String after(String input, String separator) {
        int ind = input.indexOf(separator);
        if (ind == -1) {
            return input;
        }
        return input.substring(ind + separator.length());
    }

    public static int count(String input, char toCount) {
        char[] data = input.toCharArray();
        int result = 0;

        for (int i = 0; i < input.length(); i++) {
            if (data[i] == toCount) {
                result++;
            }
        }
        return result;
    }

    ///////////////////////////////
    //    Argument Methods
    /////////////////////////////

    public static String getXthArg(int xthArg, String args) {
        char[] data = args.toCharArray();
        StringBuilder nArgs = new StringBuilder();
        int arg = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] == ' ') {
                arg++;
                if (arg > xthArg) {
                    return nArgs.toString();
                }
            }
            else if (arg == xthArg) {
                nArgs.append(data[i]);
            }
        }
        return nArgs.toString();
    }

    public static boolean xthArgEquals(int xthArg, String args, String compareAgainst) {
        char[] argData = args.toCharArray();
        char[] compareData = compareAgainst.toCharArray();
        int arg = 0;
        int x = 0;

        for (int i = 0; i < argData.length; i++) {
            if (argData[i] == ' ') {
                arg++;
            }
            else if (arg == xthArg) {
                if (x == compareData.length) {
                    return false;
                }
                if (compareData[x++] != argData[i]) {
                    return false;
                }
            }
        }

        return x == compareData.length;
    }

    ///////////////////////////////
    //    UUID Methods
    /////////////////////////////

    public static UUID tryGetUUID(String name) throws NumberFormatException {
        List<String> components = split(name, '-');
        if (components.size() != 5) {
            return null;
        }

        long mostSigBits = Long.parseLong(components.get(0), 16);
        mostSigBits <<= 16;
        mostSigBits |= Long.parseLong(components.get(1), 16);
        mostSigBits <<= 16;
        mostSigBits |= Long.parseLong(components.get(2), 16);
        long leastSigBits = Long.parseLong(components.get(3), 16);
        leastSigBits <<= 48;
        leastSigBits |= Long.parseLong(components.get(4), 16);
        return new UUID(mostSigBits, leastSigBits);
    }
}

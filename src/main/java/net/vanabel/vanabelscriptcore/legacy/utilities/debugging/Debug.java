package com.github.musicscore.denizensuspiccore.utilities.debugging;

import net.vanabel.vanabelscriptcore.legacy.DenizenSuspicCore;

public class Debug {

    public static void exception(Exception ex) {
        DenizenSuspicCore.getImplementation().outputException(ex);
    }

    public static void error(String msg) {
        DenizenSuspicCore.getImplementation().outputError(msg);
    }

    public static void info(String msg) {
        DenizenSuspicCore.getImplementation().outputInfo(msg);
    }

    public static void good(String msg) {
        DenizenSuspicCore.getImplementation().outputGood(msg);
    }
}

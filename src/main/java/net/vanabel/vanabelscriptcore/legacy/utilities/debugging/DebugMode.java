package com.github.musicscore.denizensuspiccore.utilities.debugging;

public enum DebugMode {

    FULL(true, true),
    MINIMAL(false, true),
    NONE(false, false);

    DebugMode(boolean full, boolean minimal) {
        showFull = full;
        showMinimal = minimal;
    }

    public final boolean showFull;
    public final boolean showMinimal;
}
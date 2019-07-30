package net.vanabel.vanabelscriptcore;

import net.vanabel.vanabelscriptcore.utils.debug.Debug;

/**
 * The main entry point of the core.
 */
public class VanabelScriptCore {

    /**
     * The current implementation that is using this core.
     */
    public static VanabelScriptImplementation implementation;

    private static Debug.Mode debugMode = Debug.Mode.ALL;

    /**
     * The version of this core.
     */
    public final static String VERSION;

    static {
        // load config version here
        VERSION = null;
    }

    /**
     * Returns the debug mode that should be used by the scripting implementation.
     * @return The debug mode.
     */
    public static Debug.Mode getDebugMode() {
        return debugMode;
    }

    /**
     * Sets the new debug mode to use.
     * @param mode The new debug mode.
     * @return True if the change was successful.
     */
    public static boolean setDebugMode(Debug.Mode mode) {
        if (mode == null || mode == debugMode) {
            return false;
        }
        debugMode = mode;
        return true;
    }
}

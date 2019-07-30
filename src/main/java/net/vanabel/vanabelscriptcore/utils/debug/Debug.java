package net.vanabel.vanabelscriptcore.utils.debug;

import net.vanabel.vanabelscriptcore.VanabelScriptCore;

/**
 * A class that holds all information that any implementation would need when printing debug.
 * The implementation should set the new debug colors.
 */
public abstract class Debug {

    ////////////////////////
    // Abstract methods
    //////////////////////

    /**
     * Outputs an information-level message to console.
     * @param msg The message to output.
     */
    public abstract void info(String msg);

    /**
     * Outputs to console that something has succeeded.
     * @param msg The message to output.
     */
    public abstract void success(String msg);

    /**
     * Outputs an exception to console.
     * @param exception The exception to output.
     */
    public abstract void exception(Exception exception);

    /**
     * Outputs an exception to console, with an additional message.
     * @param exception The exception to output.
     * @param msg The message to preface the exception with.
     */
    public abstract void exception(Exception exception, String msg);

    /**
     * Outputs a warning to console.
     * @param msg The warning to output.
     */
    public abstract void warn(String msg);



    ////////////////////////
    // Enums
    //////////////////////

    /**
     * Represents the debug mode that can be used.
     */
    public enum Mode {
        /**
         * All debug should be outputted.
         */
        ALL          (true,  true,  true),
        /**
         * No tag information should be outputted, except in cases where a tag fails to parse due to technical reasons.
         */
        NO_TAGS      (false, true,  true),
        /**
         * Only warnings and errors will be displayed.
         */
        WARNINGS_ONLY(false, false, true),
        /**
         * Warnings are suppressed. Only errors, exceptions, and debug printed by the user should be printed to console.
         */
        OFF          (false, false, false);

        Mode(boolean tags, boolean commands, boolean warnings) {
            this.tags = tags;
            this.commands = commands;
            this.warnings = warnings;
        }

        private final boolean tags;
        private final boolean commands;
        private final boolean warnings;

        /**
         * Returns whether this debug mode should display tag construction and parsing information.
         * @return True if tag debug is enabled by this mode.
         */
        public boolean displayTagInformation() {
            return tags;
        }

        /**
         * Returns whether this debug mode should display command input and result information.
         * @return True if command debug is enabled by this mode.
         */
        public boolean displayCommandInformation() {
            return commands;
        }

        /**
         * Returns whether this debug mode should display warnings regarding questionable user input.
         * These usually should only extend to cases where bad user input can be ignored safely.
         * @return True if warning debug is enabled by this mode.
         */
        public boolean displayWarnings() {
            return warnings;
        }
    }

    /**
     * Represents the colors usable by the debugging tool.
     * These colors should be set by the VanabelScript implementation using the {@link #setColor(String) setColor()} method.
     */
    public enum LevelColor {
        /**
         * A plain message.
         */
        PLAIN,
        /**
         * A message that indicates that something succeeded.
         */
        SUCCESS,
        /**
         * An emphasized message, variant 1.
         */
        EMPHASIS_1,
        /**
         * An emphasized message, variant 2.
         */
        EMPHASIS_2,
        /**
         * An emphasized message, variant 3.
         */
        EMPHASIS_3,
        /**
         * An emphasized message, variant 4.
         */
        EMPHASIS_4,
        /**
         * A warning message.
         */
        WARNING,
        /**
         * An error message.
         */
        ERROR;

        private static String[] colors = {
                "",     // "PLAIN" color
                "",     // "SUCCESS" color
                "",     // "EMPHASIS" color (variation 1)
                "",     // "EMPHASIS" color (variation 2)
                "",     // "EMPHASIS" color (variation 3)
                "",     // "EMPHASIS" color (variation 4)
                "",     // "WARNING" color
                "",     // "ERROR" color
        };

        /**
         * Sets the new String representation of a particular debugging color.
         * @param newColor The String to use as the string color for this debugging level.
         * @return True if the color was changed successfully.
         */
        public boolean setColor(String newColor) {
            if (colors[this.ordinal()].equals(newColor)) {
                return false;
            }
            colors[this.ordinal()] = newColor;
            return true;
        }

        /**
         * Returns the String representation of the color.
         * This does not return the name of the level color! Use {@link Mode#name() Mode#name()} if you want the name of the
         * debug level!
         * @return The String representation of the level.
         */
        @Override
        public String toString() {
            return colors[this.ordinal()];
        }
    }

}

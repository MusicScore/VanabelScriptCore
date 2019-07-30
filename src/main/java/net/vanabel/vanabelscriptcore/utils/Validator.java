package net.vanabel.vanabelscriptcore.utils;

/**
 * A utility class with validation methods. Derived from Apache Commons library, implemented here to avoid over-reliance
 * on multiple dependencies.
 */
public class Validator {

    private final static String DEFAULT_NULL_MESSAGE = "A null object cannot be passed here!";
    private final static String DEFAULT_EMPTY_MESSAGE = "This object cannot be empty or null!";

    /**
     * Quickly checks if an object is null.
     * If the object is null, an exception is thrown.
     * @param object The object to check.
     * @param <T> The object type of the checked object.
     * @return The object, provided that it is not null.
     * @throws NullPointerException When the object is null.
     * @see #isNotNull(Object, String)
     */
    public static <T> T isNotNull(final T object) {
        return isNotNull(object, DEFAULT_NULL_MESSAGE);
    }

    /**
     * Quickly checks if an object is null.
     * If the object is null, an exception is thrown.
     * @param object The object to check.
     * @param message The exception message if the object is null.
     * @param <T> The object type of the checked object.
     * @return The object, provided that it is not null.
     * @throws NullPointerException When the object is null.
     */
    public static <T> T isNotNull(final T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * Quickly checks if a character sequence object is either empty or null.
     * If the object is either empty or null, an exception is thrown.
     * @param object The character sequence object to check.
     * @param <T> The character sequence type.
     * @return The character sequence if it is not empty nor null.
     * @throws NullPointerException When the sequence is null.
     * @throws IllegalArgumentException When the sequence is empty.
     */
    public static <T extends CharSequence> T isEmpty(T object) {
        return isEmpty(object, DEFAULT_EMPTY_MESSAGE);
    }

    /**
     * Quickly checks if a character sequence object is either empty or null.
     * If the object is either empty or null, an exception is thrown.
     * @param object The character sequence object to check.
     * @param message The exception message if the character sequence is empty or null.
     * @param <T> The character sequence type.
     * @return The character sequence if it is not empty nor null.
     * @throws NullPointerException When the sequence is null.
     * @throws IllegalArgumentException When the sequence is empty.
     */
    public static <T extends CharSequence> T isEmpty(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        if (object.length() == 0) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    /**
     * Returns an empty string if a null String object is passed through.
     * @param string The string to validate.
     * @return The validated string if it is not null, or an empty string otherwise.
     */
    public static String emptyStringIfNull(final String string) {
        return string == null ? "" : string;
    }
}

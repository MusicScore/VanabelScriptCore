package net.vanabel.vanabelscriptcore.utils.debug;

/**
 * A helper interface designed to allow one object to be returned from two inputs.
 * @param <T> The first input.
 * @param <S> The second input.
 * @param <Y> The output.
 */
@FunctionalInterface
public interface TwoToOneFunction<T, S, Y> {

    /**
     * Takes two objects and returns a third object.
     * @param t The first object.
     * @param s The second object.
     * @return The third object.
     */
    Y apply(T t, S s);
}

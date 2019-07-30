package com.github.musicscore.denizensuspiccore.utilities;

@FunctionalInterface
public interface AbstractFunctionApply<A, B, R> {
    R apply(A a, B b);
}

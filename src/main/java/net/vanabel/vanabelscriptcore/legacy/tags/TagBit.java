package com.github.musicscore.denizensuspiccore.tags;

import com.github.musicscore.denizensuspiccore.arguments.Argument;

public class TagBit {

    public final String key;

    public final Argument variable;

    public TagBit(String key, Argument variable) {
        this.key = key;
        this.variable = variable;
    }

    @Override
    public String toString() {
        if (variable == null || variable.bits.isEmpty()) {
            return key;
        }
        return key + "[" + variable.toString() + "]";
    }
}

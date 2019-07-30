package com.github.musicscore.denizensuspiccore.tags;

public abstract class AbstractTag {

    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }

    public abstract AbstractTagObject handle(TagData data);
}

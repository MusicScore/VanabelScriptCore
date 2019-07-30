package net.vanabel.vanabelscriptcore.tags;

/**
 * Represents a generic tag base. Generally, a tag base is in the form {@code {<TAG_BASE_TEXT>[<TEXT>]}}.
 */
public abstract class AbstractTagBase {

    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }

    public abstract AbstractTagObject parse(/* TagData data */);
}

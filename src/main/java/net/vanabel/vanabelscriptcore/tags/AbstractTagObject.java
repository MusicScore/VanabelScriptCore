package net.vanabel.vanabelscriptcore.tags;

public abstract class AbstractTagObject {

    public AbstractTagObject parse(/* TagData data */) {
        //
        return null;
    }

    public abstract AbstractTagObject parseFailCase(/* TagData data */);

    public abstract String getTagTypeName();

    public String debug() {
        return toString();
    }

    public static String typeIndicator = "@";

    public String savedStringRepresentation() {
        return getTagTypeName() + typeIndicator + toString();
    }
}

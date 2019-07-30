package com.github.musicscore.denizensuspiccore.tags.objects;

import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.tags.TagData;
import com.github.musicscore.denizensuspiccore.utilities.AbstractFunctionApply;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.debugging.ColorSet;

import java.util.HashMap;

public class NullTag extends AbstractTagObject {

    public final static NullTag NULL = new NullTag();
    public final static String STRING_VAL = "&{NULL}";

    public final static HashMap<String, AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject>> handlers = new HashMap<>();

    ///////////////////////////////
    //    Static Block
    /////////////////////////////

    static {
        // Since this is a null tag, it won't be initialized
    }

    ///////////////////////////////
    //    Instance Methods
    /////////////////////////////

    @Override
    public HashMap<String, AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject>> getHandlers() {
        return handlers;
    }

    @Override
    public AbstractTagObject handle(TagData data) {
        if (data.getRemainingBits() > 0 && !data.hasFallback()) {
            data.error.run("Tag " + ColorSet.emphasis + data.bits[data.getCurrentIndex() - 1].key +
                    ColorSet.warning + " returned a NullTag, causing tag " +
                    ColorSet.emphasis + data.bits[data.getCurrentIndex()] +
                    ColorSet.warning + " to fail.");
        }
        return this;
    }

    @Override
    public AbstractTagObject handleElseCase(TagData data) {
        return new TextTag(STRING_VAL);
    }

    ///////////////////////////////
    //    Property Methods
    /////////////////////////////

    @Override
    public String getTagTypeName() {
        return "NullTag";
    }

    @Override
    public String toString() {
        return STRING_VAL;
    }

    ///////////////////////////////
    //    Static Methods
    /////////////////////////////

    public static NullTag getFor(Action<String> error, String text) {
        return NULL;
    }

    public static NullTag getFor(Action<String> error, AbstractTagObject tag) {
        return NULL;
    }
}
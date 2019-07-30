package com.github.musicscore.denizensuspiccore.tags.objects;

import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.tags.TagData;
import com.github.musicscore.denizensuspiccore.utilities.AbstractFunctionApply;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.debugging.ColorSet;

import java.util.HashMap;

public class TextTag extends AbstractTagObject {

    public final static HashMap<String, AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject>> handlers = new HashMap<>();

    public String text;

    ///////////////////////////////
    //    Constructors
    /////////////////////////////

    public TextTag(String text) {
        this.text = text;
    }

    ///////////////////////////////
    //    Static Block
    /////////////////////////////

    static {
        // Add:
        //   + distance[<TextTag>]
        //       - return IntegerTag of the Levenshtein distance between the two text values.
        //   + replace_regex[<ListTag>]
        //       - returns TextTag of the text after process a regex replacement, input is "regex|replacement"
        //       - (maybe a cleaner input method? Regex in a list tag requires escaping)

        handlers.put("to_integer", (dat, obj) -> IntegerTag.getFor(dat.checkedError, ((TextTag) obj).text));
    }

    ///////////////////////////////
    //    Instance Methods
    /////////////////////////////

    @Override
    public HashMap<String, AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject>> getHandlers() {
        return handlers;
    }

    @Override
    public AbstractTagObject handleElseCase(TagData data) {
        if (!data.hasFallback()) {
            if (data.getCurrentIndex() > 0 && data.returnsTracked[data.getCurrentIndex() - 1] != null) {
                data.error.run("Unknown tag part '" + ColorSet.emphasis + data.getNextBitKey() +
                        ColorSet.warning + "' - tag was of type: " +
                        ColorSet.emphasis + data.returnsTracked[data.getCurrentIndex() - 1].getTagTypeName() +
                        ColorSet.warning);
            }
            else {
                data.error.run("Unknown tag part '" + ColorSet.emphasis + data.getNextBitKey() +
                        ColorSet.warning + "' - original tag type is unknown.");
            }
        }
        return NullTag.NULL;
    }

    ///////////////////////////////
    //    Property Methods
    /////////////////////////////

    @Override
    public String getTagTypeName() {
        return "TextTag";
    }

    @Override
    public String toString() {
        return text;
    }

    ///////////////////////////////
    //    Static Methods
    /////////////////////////////

    public static TextTag getFor(Action<String> error, String text) {
        return new TextTag(text);
    }

    public static TextTag getFor(Action<String> error, AbstractTagObject tag) {
        return (tag instanceof TextTag) ? (TextTag) tag : getFor(error, tag.toString());
    }
}

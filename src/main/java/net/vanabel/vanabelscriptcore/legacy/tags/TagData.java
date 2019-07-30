package com.github.musicscore.denizensuspiccore.tags;

import com.github.musicscore.denizensuspiccore.arguments.Argument;
import com.github.musicscore.denizensuspiccore.arguments.TagArgumentBit;
import com.github.musicscore.denizensuspiccore.tags.objects.NullTag;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.debugging.ColorSet;
import com.github.musicscore.denizensuspiccore.utilities.debugging.DebugMode;

import java.util.HashMap;

public class TagData {

    public class TagDataEscalateException extends RuntimeException {
        //
    }

    private int currInd = 0;

    public TagData(Action<String> backingError, TagBit[] bits, Argument fallback, HashMap<String, AbstractTagObject> variables, DebugMode debugMode, CommandQueue queue, TagArgumentBit tagArgBit) {
        this.bits = bits;
        this.fallback = fallback;
        this.variables = variables;
        this.debugMode = debugMode;
        currQueue = queue;
        originalTagArgBit = tagArgBit;

        this.backingError = backingError;
        error = this::handleError;
        checkedError = this::handleCheckedGetForError;

        modifiersTracked = new AbstractTagObject[bits.length];
        returnsTracked = new AbstractTagObject[bits.length];
    }

    public final TagArgumentBit originalTagArgBit;
    public final TagBit[] bits;

    public final CommandQueue currQueue;

    public final Action<String> backingError;
    public final Action<String> error;
    public final Action<String> checkedError;

    public final AbstractTagObject[] modifiersTracked;
    public final AbstractTagObject[] returnsTracked;

    public final Argument fallback;

    public final HashMap<String, AbstractTagObject> variables;

    public final DebugMode debugMode;

    public boolean hasFallback() {
        return fallback != null;
    }

    public int getCurrentIndex() {
        return currInd;
    }

    public int getRemainingBits() {
        return bits.length - currInd;
    }

    public String getNextBitKey() {
        return bits[currInd].key;
    }

    public boolean hasNextModifier() {
        return bits[currInd].variable != null && !bits[currInd].variable.bits.isEmpty();
    }

    public AbstractTagObject getNextModifier() {
        if (bits[currInd].variable == null) {
            error.run("No tag modifier given when required for tag part '" +
                    ColorSet.emphasis + bits[currInd].key + ColorSet.warning + "'.");
            return NullTag.NULL;
        }

        AbstractTagObject tagObj = bits[currInd].variable.parse(currQueue, variables, debugMode, error);
        modifiersTracked[currInd] = tagObj;
        return tagObj;
    }

    public String getTagAsMarkedString() {
        StringBuilder tag = new StringBuilder();
        tag.append(ColorSet.emphasis);
        tag.append("<");

        for (int i = 0; i < bits.length; i++) {
            if (i > 0) {
                tag.append(ColorSet.emphasis);
                tag.append(".");
            }

            String textColor = i >= currInd ? ColorSet.warning : ColorSet.good;

            tag.append(textColor);
            tag.append(bits[i].key);

            if (bits[i].variable != null && bits[i].variable.bits.size() != 0) {
                tag.append(ColorSet.emphasis).append("[").append(textColor).append(bits[i].variable.toString());
                if (modifiersTracked[i] != null) {
                    tag.append(ColorSet.emphasis).append(" -> ").append(textColor).append(modifiersTracked[i].toString());
                }
                tag.append(ColorSet.emphasis).append("]");
            }

            if (returnsTracked[i] != null) {
                tag.append(ColorSet.emphasis).append("(returned: ").append(textColor).append(returnsTracked[i].debug()).append(ColorSet.emphasis).append(")");
            }
        }

        if (hasFallback()) {
            tag.append(ColorSet.emphasis).append("||").append(fallback.toString());
        }

        return tag.append(ColorSet.emphasis).append(">").toString();
    }

    public void handleCheckedGetForError(String errorMsg) {
        if (!hasFallback()) {
            error.run(errorMsg);
        }
        throw new TagDataEscalateException();
    }

    public void handleError(String errorMsg) {
        backingError.run(ColorSet.warning + "Tag error occurred: " + errorMsg + "\n while processing tag " + getTagAsMarkedString() + ColorSet.warning);
    }

    public TagData shrink() {
        currInd++;
        return this;
    }
}

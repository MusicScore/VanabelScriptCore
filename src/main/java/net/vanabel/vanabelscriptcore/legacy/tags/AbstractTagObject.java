package com.github.musicscore.denizensuspiccore.tags;

import com.github.musicscore.denizensuspiccore.tags.objects.NullTag;
import com.github.musicscore.denizensuspiccore.utilities.AbstractFunctionApply;

import java.util.HashMap;

public abstract class AbstractTagObject {

    public abstract HashMap<String, AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject>> getHandlers();

    public AbstractTagObject handle (TagData data) {
        if (data.returnsTracked[data.getCurrentIndex() - 1] == null) {
            data.returnsTracked[data.getCurrentIndex() - 1] = this;
        }
        if (data.getRemainingBits() == 0) {
            return this;
        }

        String type = data.getNextBitKey();
        AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject> tagAction = getHandlers().get(type);

        if (tagAction != null) {
            try {
                AbstractTagObject currentVal = tagAction.apply(data, this);
                return currentVal.handle(data.shrink());
            }
            catch (TagData.TagDataEscalateException e) {
                return NullTag.NULL;
            }
        }

        AbstractTagObject atoElseCase = handleElseCase(data);
        if (atoElseCase != null) {
            return atoElseCase.handle(data);
        }

        return NullTag.NULL;
    }

    public abstract AbstractTagObject handleElseCase(TagData data);

    public abstract String getTagTypeName();

    public String debug() {
        return toString();
    }

    public static String saveMark() {
        return "@";
    }

    /**
     * Only override if there are sub-tag-objects within the object, e.g. a list of other objects.
     */
    public String savable() {
        return getTagTypeName() + saveMark() + toString();
    }
}

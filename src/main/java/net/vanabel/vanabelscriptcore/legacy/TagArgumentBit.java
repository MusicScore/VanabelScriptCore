package net.vanabel.vanabelscriptcore.legacy;

import com.github.musicscore.denizensuspiccore.tags.AbstractTag;
import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.tags.TagBit;
import com.github.musicscore.denizensuspiccore.tags.TagData;
import com.github.musicscore.denizensuspiccore.tags.objects.NullTag;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.debugging.ColorSet;
import com.github.musicscore.denizensuspiccore.utilities.debugging.DebugMode;

import java.util.HashMap;

public class TagArgumentBit extends ArgumentBit {

    public TagArgumentBit(TagBit[] bits) {
        this.bits = bits;
    }

    public final TagBit[] bits;
    private AbstractTag start;
    private Argument fallback;

    public void setStart(AbstractTag base) {
        start = base;
    }

    public void setFallback(Argument arg) {
        fallback = arg;
    }

    @Override
    public String getString() {
        StringBuilder tagStrBuild = new StringBuilder();
        tagStrBuild.append("<").append(bits.length > 0 ? bits[0].toString() : "");
        for (int i = 1; i < bits.length; i++) {
            tagStrBuild.append(".").append(bits[i].toString());
        }
        if (fallback != null) {
            tagStrBuild.append("||").append(fallback.toString());
        }
        tagStrBuild.append(">");
        return tagStrBuild.toString();
    }

    @Override
    public AbstractTagObject parse(CommandQueue queue, HashMap<String, AbstractTagObject> variables, DebugMode debugMode, Action<String> error) {
        if (start == null && bits.length > 0) {
            start = DenizenSuspicCore.tagBases.get(bits[0].key);
        }

        AbstractTagObject abstrTagObj;
        if (start == null) {
            if (fallback == null) {
                error.run("Invalid tag bits. The tag base is invalid, or is empty: " + ColorSet.emphasis + (bits.length > 0 ? bits[0] : ""));
            }
            abstrTagObj = NullTag.NULL;
        }
        else {
            TagData data = new TagData(error, bits, fallback, variables, debugMode, queue, this);
            abstrTagObj = start.handle(data);
        }

        if (abstrTagObj instanceof NullTag && fallback != null) {
            abstrTagObj = fallback.parse(queue, variables, debugMode, error);
        }

        if (debugMode.showFull) {
            String output = "Filled tag '" + ColorSet.emphasis + getString() + ColorSet.good +
                    "' with '" + ColorSet.emphasis + abstrTagObj.debug() + ColorSet.good + "'.";

            if (queue != null) {
                queue.outGood(output);
            }
            else {
                Debug.good(output);
            }
        }

        return abstrTagObj;
    }
}

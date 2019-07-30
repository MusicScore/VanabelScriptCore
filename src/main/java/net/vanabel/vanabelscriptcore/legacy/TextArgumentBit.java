package net.vanabel.vanabelscriptcore.legacy;

import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.tags.objects.IntegerTag;
import com.github.musicscore.denizensuspiccore.tags.objects.ListTag;
import com.github.musicscore.denizensuspiccore.tags.objects.NumberTag;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.debugging.DebugMode;

import java.util.HashMap;

public class TextArgumentBit extends ArgumentBit {

    public final boolean quoted;

    public AbstractTagObject value;

    public TextArgumentBit(String input, boolean quoted) {
        this(input, quoted, true);
    }

    public TextArgumentBit(String input, boolean quoted, boolean depthAllowed) {
        this.quoted = quoted;

        try {
            long l = Long.parseLong(input);
            if (String.valueOf(l).equals(input)) {
                value = new IntegerTag(l);
                return;
            }
        }
        catch (NumberFormatException e) {
            // No reaction
        }

        try {
            double d = Double.parseDouble(input);
            if (String.valueOf(d).equals(input)) {
                value = new NumberTag(d);
                return;
            }
        }
        catch (NumberFormatException e) {
            // No reaction
        }

        if (depthAllowed) {
            ListTag list = ListTag.getFor(TextArgumentBit::noAction, input);
        }
    }

    public static void noAction(String error) {
        // Do nothing
    }

    @Override
    public String getString() {
        return value.toString();
    }

    @Override
    public AbstractTagObject parse(CommandQueue queue, HashMap<String, AbstractTagObject> variables, DebugMode debugMode, Action<String> error) {
        return value;
    }
}

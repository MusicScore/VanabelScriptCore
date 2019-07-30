package net.vanabel.vanabelscriptcore.legacy;

import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.tags.objects.TextTag;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.debugging.DebugMode;

import java.util.ArrayList;
import java.util.HashMap;

public class Argument {

    public final ArrayList<ArgumentBit> bits = new ArrayList<>();

    private boolean quoted = false;

    public boolean getQuoted() {
        return quoted;
    }

    public void setQuoted(boolean quoted) {
        this.quoted = quoted;
    }

    public boolean quoteMode = false;

    public boolean getQuoteMode() {
        return quoteMode;
    }

    public void setQuoteMode(boolean quoteMode) {
        this.quoteMode = quoteMode;
    }

    public void addBit(ArgumentBit argBit) {
        bits.add(argBit);
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        for (ArgumentBit bit : bits) {
            strBuild.append(bit.getString());
        }
        return strBuild.toString();
    }

    public AbstractTagObject parse(CommandQueue queue, HashMap<String, AbstractTagObject> vars, DebugMode mode, Action<String> error) {
        if (bits.size() == 1) {
            return bits.get(0).parse(queue, vars, mode, error);
        }
        StringBuilder strBuild = new StringBuilder();
        for (ArgumentBit bit : bits) {
            strBuild.append(bit.parse(queue, vars, mode, error).toString());
        }
        return new TextTag(strBuild.toString());
    }
}

package net.vanabel.vanabelscriptcore.legacy;

import com.github.musicscore.denizensuspiccore.arguments.Argument;
import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;

import java.util.HashMap;
import java.util.List;

public class CommandEntry {

    public final AbstractCommand command;
    public final String cmdName;
    public final List<Argument> args;
    public final String scriptName;

    public final HashMap<String, Argument> namedArgs;

    public final String originalLine;

    public final boolean waitFor;

    public int blockStart = 0;
    public int blockEnd = 0;
    public int ownIndex = 0;

    public List<CommandEntry> innerCommandBlock;

    public Object specialLocalData = null;

    public String saveName(CommandQueue queue, String def) {
        return namedArgs.containsKey("save") ? getNamedArgumentObject(queue, "save").toString() : def;
    }

    public Object getData(CommandQueue queue) {
        return queue.commandStack.peek().entryObjects[ownIndex];
    }

    public AbstractTagObject getNamedArgumentObject(CommandQueue queue, String name) {
        Argument obj = namedArgs.get(name);
    }

}

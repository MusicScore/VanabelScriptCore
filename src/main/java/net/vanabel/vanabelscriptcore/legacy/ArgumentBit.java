package net.vanabel.vanabelscriptcore.legacy;

import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.debugging.DebugMode;

import java.util.HashMap;

public abstract class ArgumentBit {

    public abstract String getString();

    public abstract AbstractTagObject parse(CommandQueue queue, HashMap<String, AbstractTagObject> variables, DebugMode debugMode, Action<String> error);
}

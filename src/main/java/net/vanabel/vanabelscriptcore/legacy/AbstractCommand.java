package net.vanabel.vanabelscriptcore.legacy;

import com.github.musicscore.denizensuspiccore.arguments.Argument;
import com.github.musicscore.denizensuspiccore.arguments.TextArgumentBit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractCommand {

    public abstract String getName();

    public abstract String getArguments();

    public abstract int getMinimumArguments();

    public abstract int getMaximumArguments();

    public boolean isWaitable() {
        return false;
    }

    public boolean isProcedural() {
        return false;
    }

    public boolean allowsBlock() {
        return false;
    }

    public boolean blockIsCustom() {
        return false;
    }

    public void customBlockHandle(CommandEntry entry, String scrName, List<Object> innards, int istart, List<CommandEntry> entries) {
        // Do nothing
    }

    public CommandEntry getFollower(CommandEntry entry) {
        Argument arg = new Argument();
        arg.addBit(new TextArgumentBit("\0CALLBACK", false));
        List<Argument> args = Arrays.asList(arg);
        CommandEntry ent = new CommandEntry(entry.scriptName, entry.command, args, new HashMap<>(), entry.cmdName " \0CALLBACK", entry.cmdName, false);
        ent.ownIndex = entry.blockEnd;
        ent.blockStart = entry.blockStart;
        ent.blockEnd = entry.blockEnd;
        return ent;
    }

    public abstract void execute(CommandQueue queue, CommandEntry entry);

    public void adaptBlockFollower(CommandEntry entry, List<CommandEntry> input, List<CommandEntry> fblock) {
        input.add(getFollower(entry));
    }
}

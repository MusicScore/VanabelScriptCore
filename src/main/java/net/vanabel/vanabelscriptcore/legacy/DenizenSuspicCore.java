package net.vanabel.vanabelscriptcore.legacy;

import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.CoreUtilities;
import com.github.musicscore.denizensuspiccore.utilities.debugging.Debug;
import com.github.musicscore.denizensuspiccore.utilities.yaml.YAMLConfiguration;
import com.github.musicscore.denizensuspiccore.commands.AbstractCommand;
import com.github.musicscore.denizensuspiccore.scripts.CommandScript;
import com.github.musicscore.denizensuspiccore.tags.AbstractTag;
import com.github.musicscore.denizensuspiccore.utilities.AbstractFunctionApply;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DenizenSuspicCore {

    private static DenizenSuspicCore implementation;

    public final static String VERSION;

    static {
        YAMLConfiguration config = null;
        try (InputStream inputStr = DenizenSuspicCore.class.getResourceAsStream("/denizensuspic.yml")) {
            config = YAMLConfiguration.load(CoreUtilities.streamToString(inputStr));
        }
        catch (Exception ex) {
            Debug.exception(ex);
        }

        if (config == null) {
            VERSION = "Unknown (Error reading version file! Possibly a corrupt jar?)";
        }
        else {
            VERSION = config.getString("VERSION", "UNKNOWN") + " (build " + config.getString("BUILD_NUMBER", "UNKNOWN") + ")";
        }
    }

    public final static HashMap<String, AbstractCommand> commands = new HashMap<>();
    public final static HashMap<String, CommandScript> currentScripts = new HashMap<>();
    public final static HashMap<String, AbstractTag> tagBases = new HashMap<>();
    public final static List<ScriptEvent> events = new ArrayList<>();
    public final static HashMap<String, AbstractFunctionApply<String, YAMLConfiguration, CommandScript>> scriptTypeGetters = new HashMap<>();

    public static DenizenSuspicCore getImplementation() {
        return implementation;
    }

    //////////////////////////////////////

    public static final HashMap<String, AbstractFunctionApply<Action<String>, String, AbstractTagObject>> customSaveLoaders = new HashMap<>();

    public static AbstractTagObject loadFromSaved(Action<String> error, String name) {
        List<String> dat = CoreUtilities.split(name, '@', 2);
        String type = dat.get(0);
        if (!customSaveLoaders.containsKey(type)) {
            //
        }
    }
}

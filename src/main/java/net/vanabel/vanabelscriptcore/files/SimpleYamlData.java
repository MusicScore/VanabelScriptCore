package net.vanabel.vanabelscriptcore.files;

import net.vanabel.vanabelscriptcore.utils.Validator;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

/**
 * A simple representation of YAML data.
 * \n\n
 * All YAML paths are represented as a String in the format "{@code parent_key.child_key.second_child_key.<...>}".
 */
public class SimpleYamlData {

    ////////////////////////
    // Constructors
    //////////////////////

    /**
     * Creates a new instance of SimpleYamlData.
     */
    public SimpleYamlData() {
        internalData = new HashMap<>();
    }



    ////////////////////////
    // Variables
    //////////////////////

    private Map<String, Object> internalData;



    ////////////////////////
    // Static methods
    //////////////////////

    /**
     * Converts a String to simple YAML data.
     * @param data
     * @return
     */
    public static SimpleYamlData stringToSimpleYaml(String data) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);

        Yaml yaml = new Yaml(options);
        Object object = yaml.load(data);

        if (object == null) {
            return null;
        }

        SimpleYamlData simpleData = new SimpleYamlData();

        if (object instanceof String) {
            simpleData.internalData.put(null, object);
        }
        else if (object instanceof Map) {
            simpleData.internalData = safeConvertGenericMap((Map) object, String.class, Object.class);
        }
        else {
            return null;
        }

        return simpleData;
    }

    private static <T, S> Map<T, S> safeConvertGenericMap(Map map, Class<T> keyType, Class<S> valueType) {
        if (map.isEmpty()) {
            return null;
        }

        if (!map.keySet().toArray()[0].getClass().isAssignableFrom(keyType) ||
            !map.values().toArray()[0].getClass().isAssignableFrom(valueType)) {
            return null;
        }

        return (Map<T, S>) map;
    }



    ////////////////////////
    // Instance methods
    //////////////////////

    /**
     * Returns the data found at a YAML path.
     * @param path The path to navigate to.
     * @return The object at the YAML path. Returns null if no data exists at the path.
     */
    public Object get(String path) {
        String[] pathParts = Validator.emptyStringIfNull(path).split("\\.");
        Map<String, Object> contents = internalData;

        for (int i = 0; i < pathParts.length; i++) {
            Object smallPart = contents.get(pathParts[i]);

            if (smallPart == null) {
                return null;
            }
            else if (pathParts.length == i + 1) {
                return smallPart;
            }
            else if (smallPart instanceof Map) {
                contents = (Map<String, Object>) smallPart;
            }
            else {
                return null;
            }
        }
        return null;
    }

    /**
     * Sets the value of a given YAML path.
     * @param path The path to navigate to or create.
     * @param object The object to set at this path.
     */
    public void set(String path, Object object) {
        if (object instanceof SimpleYamlData) {
            object = new HashMap<>(((SimpleYamlData) object).internalData);
        }

        String[] pathParts = Validator.emptyStringIfNull(path).split("\\.");
        Map<String, Object> contents = internalData;

        for (int i = 0; i < pathParts.length; i++) {
            Object smallPart = contents.get(pathParts[i]);

            // At the end of the path
            if (pathParts.length == i + 1) {
                if (object == null) {
                    contents.remove(pathParts[i]);
                    removeEmptyMaps(path);
                }
                else {
                    contents.put(pathParts[i], object);
                }
                return;
            }
            else if (smallPart instanceof Map) {
                contents = (Map<String, Object>) smallPart;
            }
            else {
                Map<String, Object> newMap = new HashMap<>();
                contents.put(pathParts[i], newMap);
                contents = newMap;
            }
        }
    }

    /**
     * Empties out any YAML path that has no data.
     * @param path The path tree to review and empty.
     */
    public void removeEmptyMaps(String path) {
        Map<String, Object> contents = internalData;
        String[] pathParts = Validator.emptyStringIfNull(path).split("\\.");

        for (int i = 0; i < pathParts.length; i++) {
            Object smallPart = contents.get(pathParts[i]);

            if (smallPart == null) {
                return;
            }
            else if (smallPart instanceof Map) {
                if (((Map<String, Object>) smallPart).size() == 0) {
                    contents.remove(pathParts[i]);
                    removeEmptyMaps(path);
                    return;
                }
                contents = (Map<String, Object>) smallPart;
            }
            else {
                return;
            }
        }
    }

    /**
     * Returns a set of all the highest parent key names only.
     * @return A Set of all the highest parent key names as strings.
     */
    public Set<String> getKeys() {
        return getKeys("", false);
    }

    /**
     * Returns a set of all the highest parent key names, or a set of every key and key path.
     * @param deep Whether to list the deep set of keys.
     * @return A Set of key and/or key paths as strings.
     */
    public Set<String> getKeys(boolean deep) {
        return getKeys("", deep);
    }

    /**
     * Gets a set of all of the keys in the YAML data.
     * @param parent The parent key. If set to null, then the top level keys will be returned.
     * @return A set of all keys in the data, not including deep keys.
     */
    public Set<String> getKeys(String parent) {
        return getKeys(parent, false);
    }

    /**
     * Gets a set of all of the keys in the YAML data.
     * @param parent The parent key. If set to null, then the top level keys will be returned.
     * @param deep Whether to list all deep keys.
     * @return A set of all keys in the data.
     */
    public Set<String> getKeys(String parent, boolean deep) {
        if (!deep) {
            return new HashSet<>(internalData.keySet());
        }
        return getDeepKeys(internalData, "");
    }

    /**
     * A helper method for the {@link #getKeys(String)} method.
     * @param objects A Map representation of the data.
     * @param base The String representation of the parent key.
     * @return A Set of all of the keys found at the initial parent YAML path.
     */
    private Set<String> getDeepKeys(Map<String, Object> objects, String base) {
        Set<String> strings = new HashSet<>();
        for (Map.Entry<String, Object> object : objects.entrySet()) {
            strings.add(base + object.getKey());
            if (object.getValue() instanceof Map) {
                strings.addAll(getDeepKeys((Map<String, Object>) object.getValue(), Validator.emptyStringIfNull(base) + object.getKey() + "."));
            }
        }
        return strings;
    }

    /**
     * Gets a replica of the full map of the YAML data.
     * @return A new HashMap of all of the YAML data.
     */
    public Map<String, Object> getMap() {
        return new HashMap<>(internalData);
    }

    /**
     * Returns a String form of the YAML data.
     * @return A String representaiton of the data.
     */
    public String writeToString() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);
        Yaml yaml = new Yaml(options);

        return yaml.dump(internalData);
    }
}

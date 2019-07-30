package com.github.musicscore.denizensuspiccore.utilities.yaml;

import com.github.musicscore.denizensuspiccore.utilities.CoreUtilities;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class YAMLConfiguration {

    public YAMLConfiguration() {
        contents = new HashMap<>();
    }

    public Set<String> getKeys(boolean deep) {
        return deep ? getKeysDeep(contents, "") : new HashSet<>(contents.keySet());
    }

    private Set<String> getKeysDeep(Map<String, Object> objects, String base) {
        Set<String> strings = new HashSet<>();
        for (Map.Entry<String, Object> obj : objects.entrySet()) {
            strings.add(new String(base + obj.getKey()));
            if (obj.getValue() instanceof Map) {
                strings.addAll(getKeysDeep((Map<String, Object>) obj.getValue(), base + obj.getKey() + ".");
            }
        }
        return strings;
    }

    public Map<String, Object> getMap() {
        return new HashMap<>(contents);
    }

    public String saveToString() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);
        Yaml yaml = new Yaml(options);
        return yaml.dump(reverse(contents));
    }

    public Object get(String path) {
        List<String> parts = CoreUtilities.split(path, '.');
        Map<String, Object> portion = contents;
        for (int i = 0; i < parts.size(); i++) {
            Object objPortion = portion.get(new String(parts.get(i)));
            if (objPortion == null) {
                return null;
            }
            else if (parts.size() == i + 1) {
                return objPortion;
            }
            else if (objPortion instanceof Map) {
                portion = (Map<String, Object>) objPortion;
            }
        }
        return null;
    }

    public void set(String path, Object obj) {
        if (obj instanceof YAMLConfiguration) {
            obj = new HashMap<>(((YAMLConfiguration) obj).contents);
        }

        List<String> parts = CoreUtilities.split(path, '.');
        Map<String, Object> portion = contents;
        for (int i = 0; i < parts.size(); i++) {
            Object objPortion = portion.get(new String(parts.get(i)));

            if (parts.size() == i + 1) {
                if (obj == null) {
                    portion.remove(new String(parts.get(i)));
                    emptyEmptyMaps(parts);
                }
                else {
                    portion.put(new String(parts.get(i)), obj);
                }
                return;
            }
            else if (objPortion == null) {
                Map<String, Object> map = new HashMap<>();
                portion.put(new String(parts.get(i)), map);
                portion = map;
            }
        }
    }

    public static YAMLConfiguration load(String data) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);

        Yaml yaml = new Yaml(options);
        Object obj = yaml.load(data);
        YAMLConfiguration config = new YAMLConfiguration();

        if (obj == null) {
            return null;
        }
        else if (obj instanceof String) {
            config.contents = new HashMap<>();
            config.contents.put(null, obj);
        }
        else if (obj instanceof Map) {
            config.contents = (Map<String, Object>) obj;
        }
    }

    Map</* TODO: StringHolder? */ String, Object> contents = null;

    private static void switchKeys(Map<String, Object> objects) {
        for (Object o : new ArrayList<>(objects.keySet())) {
            Object get = objects.get(o);
            objects.remove(o);
            objects.put(new String(o == null ? "null" : o.toString()), get);
        }
        for (Map.Entry<String, Object> string : objects.entrySet()) {
            if (string.getValue() instanceof Map) {
                Map map = (Map<String, Object>) string.getValue();
                switchKeys(map);
                objects.put(string.getKey(), map);
            }
        }
    }

    private static Map</* This one is just String */ String, Object> reverse(Map<String, Object> objects) {
        HashMap</* This one is just String */ String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> object : objects.entrySet()) {
            map.put(object.getKey()/*.str*/, object.getValue() instanceof Map ?
                    reverse((Map<String, Object>) object.getValue()) : object.getValue());
        }
        return map;
    }

    private static List<String> patchListNonsense(List<Object> objects) {
        List<String> list = new ArrayList<>();
        for (Object obj : objects) {
            list.add(obj == null ? "null" : obj.toString());
        }
        return list;
    }
}

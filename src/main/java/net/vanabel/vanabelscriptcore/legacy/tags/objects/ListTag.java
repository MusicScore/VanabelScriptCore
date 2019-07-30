package com.github.musicscore.denizensuspiccore.tags.objects;

import net.vanabel.vanabelscriptcore.legacy.DenizenSuspicCore;
import com.github.musicscore.denizensuspiccore.arguments.TextArgumentBit;
import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.tags.TagData;
import com.github.musicscore.denizensuspiccore.utilities.AbstractFunctionApply;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.CoreUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListTag extends AbstractTagObject {

    public final static HashMap<String, AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject>> handlers = new HashMap<>();

    public List<AbstractTagObject> list;

    ///////////////////////////////
    //    Constructors
    /////////////////////////////

    public ListTag() {
        list = new ArrayList<>();
    }

    public ListTag(List<AbstractTagObject> newList) {
        list = new ArrayList<>(newList);
    }

    public ListTag(int capacity) {
        list = new ArrayList<>(capacity);
    }

    ///////////////////////////////
    //    Static Block
    /////////////////////////////

    static {
        //
    }

    ///////////////////////////////
    //    Instance Methods
    /////////////////////////////

    @Override
    public HashMap<String, AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject>> getHandlers() {
        return handlers;
    }

    @Override
    public AbstractTagObject handleElseCase(TagData data) {
        return new TextTag(toString());
    }

    ///////////////////////////////
    //    Property Methods
    /////////////////////////////

    @Override
    public String getTagTypeName() {
        return "ListTag";
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            strBuild.append(CoreUtilities.escape(list.get(i).toString())).append("|");
        }
        return strBuild.toString();
    }

    @Override
    public String savable() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append(getTagTypeName()).append(saveMark());

        for (int i = 0; i < list.size(); i++) {
            strBuild.append(CoreUtilities.escape(list.get(i).savable())).append("|");
        }
        return strBuild.toString();
    }

    @Override
    public String debug() {
        StringBuilder strBuild = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            strBuild.append(list.get(i).debug()).append(" | ");
        }
        return strBuild.toString();
    }

    ///////////////////////////////
    //    Static Methods
    /////////////////////////////

    public static ListTag getFor(Action<String> error, String text) {
        List<String> strList = CoreUtilities.split(text, '|');
        ListTag listTag = new ListTag();

        for (int i = 0; i < strList.size(); i++) {
            if (i == strList.size() - 1 && strList.get(i).length() == 0) {
                break;
            }
            String data = CoreUtilities.unescape(strList.get(i));
            listTag.list.add(new TextArgumentBit(data, false, false).value);
        }
        return listTag;
    }

    public static ListTag getFor(Action<String> error, AbstractTagObject tag) {
        return (tag instanceof ListTag) ? (ListTag) tag : getFor(error, tag.toString());
    }

    public static ListTag getForSaved(Action<String> error, String text) {
        List<String> strList = CoreUtilities.split(text, '|');
        ListTag listTag = new ListTag();

        for (int i = 0; i < strList.size(); i++) {
            if (i == strList.size() - 1 && strList.get(i).length() == 0) {
                break;
            }
            String data = CoreUtilities.unescape(strList.get(i));
            listTag.list.add(DenizenSuspicCore.loadFromSaved(error, data));
        }
        return listTag;
    }
}

package com.github.musicscore.denizensuspiccore.tags.objects;

import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.tags.TagData;
import com.github.musicscore.denizensuspiccore.utilities.AbstractFunctionApply;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.CoreUtilities;
import com.github.musicscore.denizensuspiccore.utilities.numbers.NumberForm;

import java.util.HashMap;

public class NumberTag extends AbstractTagObject implements NumberForm {

    public final static HashMap<String, AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject>> handlers = new HashMap<>();

    public double number;

    ///////////////////////////////
    //    Constructors
    /////////////////////////////

    public NumberTag(double number) {
        this.number = number;
    }

    ///////////////////////////////
    //    Interface Methods
    /////////////////////////////

    @Override
    public double getNumberForm() {
        return number;
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
        return "NumberTag";
    }

    @Override
    public String toString() {
        return CoreUtilities.doubleToString(number);
    }

    ///////////////////////////////
    //    Static Methods
    /////////////////////////////

    public static NumberTag getFor(Action<String> error, String text) {
        try {
            return new NumberTag(Double.parseDouble(text));
        }
        catch (NumberFormatException e) {
            error.run("Invalid NumberTag input!");
            return null;
        }
    }

    public static NumberTag getFor(Action<String> error, AbstractTagObject tag) {
        if (tag instanceof NumberTag) {
            return (NumberTag) tag;
        }
        if (tag instanceof NumberForm) {
            return new NumberTag(((NumberForm) tag).getNumberForm());
        }
        return getFor(error, tag.toString());
    }
}

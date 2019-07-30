package com.github.musicscore.denizensuspiccore.tags.objects;

import com.github.musicscore.denizensuspiccore.tags.AbstractTagObject;
import com.github.musicscore.denizensuspiccore.tags.TagData;
import com.github.musicscore.denizensuspiccore.utilities.AbstractFunctionApply;
import com.github.musicscore.denizensuspiccore.utilities.Action;
import com.github.musicscore.denizensuspiccore.utilities.numbers.IntegerForm;
import com.github.musicscore.denizensuspiccore.utilities.numbers.NumberForm;

import java.util.HashMap;

public class IntegerTag extends AbstractTagObject implements IntegerForm, NumberForm {

    public final static HashMap<String, AbstractFunctionApply<TagData, AbstractTagObject, AbstractTagObject>> handlers = new HashMap<>();

    public long integer;

    ///////////////////////////////
    //    Constructors
    /////////////////////////////

    public IntegerTag(long integer) {
        this.integer = integer;
    }

    ///////////////////////////////
    //    Interface Methods
    /////////////////////////////

    @Override
    public long getIntegerForm() {
        return integer;
    }

    @Override
    public double getNumberForm() {
        return (double) integer;
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
        return new NumberTag(integer);
    }

    ///////////////////////////////
    //    Property Methods
    /////////////////////////////

    @Override
    public String getTagTypeName() {
        return "IntegerTag";
    }

    @Override
    public String toString() {
        return String.valueOf(integer);
    }

    ///////////////////////////////
    //    Static Methods
    /////////////////////////////

    public static IntegerTag getFor(Action<String> error, String text) {
        try {
            return new IntegerTag(Long.parseLong(text));
        }
        catch (NumberFormatException e) {
            error.run("Invalid IntegerTag input!");
            return null;
        }
    }

    public static IntegerTag getFor(Action<String> error, AbstractTagObject tag) {
        if (tag instanceof IntegerTag) {
            return (IntegerTag) tag;
        }
        if (tag instanceof IntegerForm) {
            return new IntegerTag(((IntegerForm) tag).getIntegerForm());
        }
        return getFor(error, tag.toString());
    }
}

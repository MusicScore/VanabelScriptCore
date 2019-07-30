package net.vanabel.vanabelscriptcore.tags.objects;

import net.vanabel.vanabelscriptcore.tags.AbstractTagObject;
import net.vanabel.vanabelscriptcore.utils.debug.TwoToOneFunction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Locale;

public class Text extends AbstractTagObject {

    private String text;

    public Text(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public final static HashMap<String, TwoToOneFunction<Object /*TagData*/, AbstractTagObject, AbstractTagObject>> attributes = new HashMap<>();

    static {
        attributes.put("is_number", (data, object) ->
                new Text(String.valueOf(isNumber(((Text) object).text)))
        );

        attributes.put("to_number", (data, object) -> {
            String text = ((Text) object).text;
            if (isNumber(text)) {
                return new Text(new BigDecimal(text).toPlainString());
            }
            return null;
        });

        attributes.put("is_integer", (data, object) ->
                new Text(String.valueOf(isInteger(((Text) object).text)))
        );

        attributes.put("to_integer", (data, object) -> {
            String text = ((Text) object).text;
            if (isInteger(text)) {
                return new Text(new BigInteger(text).toString());
            }
            return null;
        });

        attributes.put("to_uppercase", (data, object) ->
                new Text(((Text) object).text.toUpperCase(Locale.ENGLISH))
        );

        attributes.put("to_lowercase", (data, object) ->
                new Text(((Text) object).text.toLowerCase(Locale.ENGLISH))
        );
    }

    public static boolean isNumber(String text) {
        if (text == null) {
            return false;
        }
        return text.matches("^-?([0-9]*\\.)?(?:[1-9][0-9]*?)(e(-|\\+)?[0-9]+)?$");
    }

    public static boolean isInteger(String text) {
        if (text == null) {
            return false;
        }
        return text.matches("^-?[1-9][0-9]*$");
    }

    @Override
    public String getTagTypeName() {
        return "Text";
    }

    @Override
    public AbstractTagObject parseFailCase() {
        return null;
    }
}

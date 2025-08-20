package com.github.fecrol.assertzilla.core.helpers;

import java.lang.reflect.Field;
import java.util.List;

public class StringHelpers {

    private String value;

    private StringHelpers(String value) {
        this.value = value;
    }

    public static StringHelpers replacePlaceholdersIn(String value) {
        return new StringHelpers(value);
    }

    public String withValuesFrom(Object object, List<Field> fields) {
        for (Field field : fields) {
            String replecableString = String.format("${%s}", field.getName());

            if (value.contains(replecableString)) {
                try {
                    field.setAccessible(true);
                    String fieldValue = String.valueOf(field.get(object));
                    value = value.replace(replecableString, fieldValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return value;
    }
}

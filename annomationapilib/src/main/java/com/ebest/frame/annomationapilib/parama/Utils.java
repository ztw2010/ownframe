package com.ebest.frame.annomationapilib.parama;

public class Utils {

    /* Used for generated code.*/
    public static <T> T wrapCast (Object data) {
        //noinspection unchecked
        return (T) data;
    }

    static boolean isBaseType(Class<?> clz) {
        return clz == Integer.class
                || clz == int.class
                || clz == boolean.class
                || clz == Boolean.class
                || clz == byte.class
                || clz == Byte.class
                || clz == char.class
                || clz == Character.class
                || clz == float.class
                || clz == Float.class
                || clz == double.class
                || clz == Double.class
                || clz == long.class
                || clz == Long.class;
    }
}

package com.example;

import java.util.Map;

public class TypeMapper {

    private static final Map<Class<?>, String> TYPES = Map.ofEntries(
            Map.entry(byte.class, "number"),
            Map.entry(short.class, "number"),
            Map.entry(int.class, "number"),
            Map.entry(long.class, "number"),
            Map.entry(float.class, "number"),
            Map.entry(double.class, "number"),

            Map.entry(Byte.class, "number"),
            Map.entry(Short.class, "number"),
            Map.entry(Integer.class, "number"),
            Map.entry(Long.class, "number"),
            Map.entry(Float.class, "number"),
            Map.entry(Double.class, "number"),

            Map.entry(boolean.class, "boolean"),
            Map.entry(Boolean.class, "boolean"),

            Map.entry(String.class, "string"));

    public static String map(Class<?> type) {
        return TYPES.getOrDefault(type, "any");
    }
}

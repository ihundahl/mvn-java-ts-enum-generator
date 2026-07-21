package com.example;

import java.util.Collection;
import java.util.stream.Collectors;
import java.lang.reflect.Array;

public class TypeMapper {

    public static String toTypeScriptLiteral(Object value) {
        if (value == null) {
            return "null";
        }

        // Handle collections (List, Set, etc.)
        if (value instanceof Collection<?> coll) {
            String joined = coll.stream()
                    .map(TypeMapper::toTypeScriptLiteral)
                    .collect(Collectors.joining(", "));
            return "[" + joined + "]";
        }

        // Handle arrays (including primitive arrays)
        if (value.getClass().isArray()) {
            int len = Array.getLength(value);
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0; i < len; i++) {
                Object elem = Array.get(value, i);
                sb.append(toTypeScriptLiteral(elem));
                if (i < len - 1)
                    sb.append(", ");
            }
            sb.append(']');
            return sb.toString();
        }

        if (value instanceof String s) {
            return '"' + escape(s) + '"';
        }

        if (value instanceof Character c) {
            return '"' + escape(c.toString()) + '"';
        }

        if (value instanceof Boolean b) {
            return b.toString();
        }

        if (value instanceof Number n) {
            return n.toString();
        }

        if (value instanceof Enum<?> e) {
            return e.getClass().getSimpleName() + "Values." + e.name();
        }

        // If all else fails, try to use the toString() method of the object.
        try {
            if (value.getClass().getMethod("toString") != null) {
                return '"' + escape(value.toString()) + '"';
            }
        } catch (NoSuchMethodException e1) {
            throw new IllegalArgumentException(
                    "Unsupported value type: " + value.getClass().getName());
        }

        throw new IllegalArgumentException(
                "Unsupported value type: " + value.getClass().getName());
    }

    private static String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
